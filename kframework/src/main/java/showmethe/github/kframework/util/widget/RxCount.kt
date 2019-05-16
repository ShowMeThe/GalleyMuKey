package showmethe.github.kframework.util.widget


import android.widget.Button
import java.util.concurrent.TimeUnit
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * 倒计时
 */
class RxCount(private val mGetCodeBtn: Button) {
    private var mdDisposable: Disposable? = null

    fun start(time: Int, interval: Int) {
        mGetCodeBtn.isEnabled = false
        mdDisposable = Flowable.intervalRange(0, time.toLong(), 0, interval.toLong(), TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { preStart.invoke(mGetCodeBtn) }
                .doOnNext { aLong -> mGetCodeBtn.text = "重新获取" + "(" + (time - aLong!!) + ")" }
                .doOnComplete {
                    onCountFinish.invoke(mGetCodeBtn)
                    mGetCodeBtn.isEnabled = true
                    mGetCodeBtn.text = "获取验证码"

                    if (mdDisposable != null) {
                        mdDisposable!!.dispose()
                    }
                }.subscribe()

    }

    private lateinit var preStart : (button : Button) -> Unit

    private lateinit var onCountFinish: (button : Button) -> Unit

    fun onPreStart(preStart : (button : Button) -> Unit){
        this.preStart = preStart
    }

    fun onCountFinish(onCountFinish : (button : Button) -> Unit){
        this.onCountFinish = onCountFinish
    }

}
