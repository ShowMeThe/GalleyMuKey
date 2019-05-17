package showmethe.github.kframework.base

import androidx.lifecycle.Lifecycle
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import showmethe.github.kframework.dialog.DialogLoading
import showmethe.github.kframework.util.ToastFactory
import java.io.IOException
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit

/**
 * showmethe.github.kframework.base
 *
 * 2019/1/10
 **/
/**
 * 仓库持有了 Activity 对象 需要在viewModel里释放 执行onClear() 否则viewModel 会出现暂时性内存泄露
 */
abstract class BaseRepository() : LifecycleOwnerObserver{

    private var currentRetryCount = 0
    val loadingDialog  = DialogLoading()
    var owner:LifecycleOwner? = null
    var refresh : WeakReference<SwipeRefreshLayout>? = null

    companion object {
        private const val maxConnectCount = 15
        private const val waitRetryTime = 3000
    }

    override fun update(owner: LifecycleOwner) {
        this.owner = owner
    }


    open fun initRefresh(refresh : SwipeRefreshLayout){
        if(this.refresh == null){
            this.refresh = WeakReference(refresh)
        }
    }

    fun showRefresh(isLoading : Boolean){
        refresh?.apply {
            get()?.apply {
                isRefreshing = isLoading
            }
        }
    }


    fun showLoading() {
        BaseApplication.ctx?.apply {
            get()?.apply {
                supportFragmentManager.executePendingTransactions()
                if (!loadingDialog.isAdded) {
                    loadingDialog.show(supportFragmentManager, "")
                }
            }
        }
    }


        fun dismissLoading() {
            if(loadingDialog.isAdded && loadingDialog!=null)
            loadingDialog.dismiss()
        }

        fun showToast(message: String) {
            ToastFactory.createToast(message)
        }

        fun <T> applySchedulers(): ObservableTransformer<T, T> {
            val provider = AndroidLifecycle.createLifecycleProvider(owner)

            return ObservableTransformer {
                it.retryWhen { throwableObservable ->
                    throwableObservable.flatMap { throwable ->
                        if (throwable is IOException) {
                            currentRetryCount++
                            if (currentRetryCount < maxConnectCount) {
                                Observable.just(1).delay((waitRetryTime + currentRetryCount * 500).toLong(), TimeUnit.MILLISECONDS)
                            } else {
                                Observable.error<Any>(Throwable("Connection time out"))
                            }
                        } else {
                            Observable.error(throwable)
                        }
                    }
                }.filter { t -> t != null }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).compose(provider.bindUntilEvent<T>(Lifecycle.Event.ON_DESTROY))
            }
        }


        fun <T> applySchedulers(interval: Long): ObservableTransformer<T, T> {
            val inter = if (interval < 15000) 15000 else interval
            val provider = AndroidLifecycle.createLifecycleProvider(owner)
            return ObservableTransformer {
                it.repeatWhen { objectObservable ->
                    objectObservable.flatMap {
                        Observable.just(1).delay(inter, TimeUnit.MILLISECONDS)
                    }
                }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .compose(provider.bindUntilEvent<T>(Lifecycle.Event.ON_DESTROY))
            }
        }


        /**
         * 一定要调用，否则可能造成内存泄漏
         */
        fun onClear() {
            if (loadingDialog.isAdded) {
                loadingDialog.dismiss()
            }
            if(refresh!=null){
                refresh = null
            }
            owner = null
        }

}
