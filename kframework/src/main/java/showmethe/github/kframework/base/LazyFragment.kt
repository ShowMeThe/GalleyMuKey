package showmethe.github.kframework.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle
import com.trello.rxlifecycle3.components.support.RxFragment

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import showmethe.github.kframework.dialog.DialogLoading
import showmethe.github.kframework.util.ToastFactory

/**
 * 适用于ViewPage加载fragment 仅加载一次且懒加载（非常用类，需要正常流程选择同包下的BaseFragment）
 * @param <V>
 * @param <VM>
</VM></V> */
abstract class LazyFragment<V : ViewDataBinding, VM : BaseViewModel> : RxFragment() {


    val loadingDialog  = DialogLoading()
    lateinit var context : BaseActivity<*,*>
    var rootView : View ? = null
    var  binding : V ? = null
    lateinit var viewModel : VM

    private var isFragmentVisible: Boolean = false
    private var isFirstVisible: Boolean = false





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = activity as BaseActivity<*, *>
        resetFlag()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = setContentView(inflater, getViewId())
        view?.apply {
            binding = DataBindingUtil.bind(view.rootView)
        }
        viewModel = initViewModel()
        return view
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isFirstVisible && isVisibleToUser) {
            lazyLoad()
            isFirstVisible = false
        }
        if (isVisibleToUser) {
            isFragmentVisible = true
            onVisible()
            return
        }
        if (isFragmentVisible) {
            isFragmentVisible = false
            onHidden()
        }
    }


    fun showLoading(){
        loadingDialog.show(context.supportFragmentManager,"")
    }

    fun dismissLoading(){
        loadingDialog.dismiss()
    }

    fun showToast(message : String){
        ToastFactory.createToast(message)
    }

    private fun lazyLoad() {

        observerUI()

        init()
        initListener()


    }

    /**
     * fragment可见的时候操作
     */
    protected fun onVisible() {

    }

    /**
     * fragment不可见的时候操作
     */
    protected fun onHidden() {

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (userVisibleHint) {
            if (isFirstVisible) {
                lazyLoad()
                isFirstVisible = false
            }
            onHidden()
            isFragmentVisible = true
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    fun createViewModel(aClass : Class<VM>) : VM{
        return  ViewModelProviders.of(this, ViewModelProvider.AndroidViewModelFactory(activity!!.application)).get(aClass)
    }

    override fun onDestroy() {
        super.onDestroy()
        resetFlag()
    }

    private fun resetFlag() {
        isFirstVisible = true
        isFragmentVisible = false
        rootView = null
    }

     abstract fun getViewId() : Int

     abstract fun initViewModel() : VM

     abstract fun observerUI()

     abstract fun init()

     abstract fun initListener()


    /**
     * 调用该办法可避免重复加载UI
     */
    fun setContentView(inflater: LayoutInflater, resId: Int): View? {
        if (rootView == null) {
            rootView = inflater.inflate(resId, null)
        }
        rootView?.apply {
            rootView.parent?.apply {
                val parent = rootView.parent as ViewGroup
                parent.removeView(rootView)
            }
        }
        return rootView
    }




    fun <T> applySchedulers(): ObservableTransformer<T, T> {
        val provider = AndroidLifecycle.createLifecycleProvider(this)
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .compose(provider.bindUntilEvent(Lifecycle.Event.ON_DESTROY))
        }
    }


    fun startActivity(bundle: Bundle?, target: Class<*>) {
        context.startActivity(bundle, target)
    }


    protected fun finish() {
        context.finish()
    }


}
