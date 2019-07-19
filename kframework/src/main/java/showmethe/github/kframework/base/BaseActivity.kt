package showmethe.github.kframework.base

import android.animation.Animator
import android.app.ActivityOptions
import androidx.lifecycle.*
import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import android.os.IBinder
import android.transition.Explode
import android.transition.TransitionInflater
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

import com.jeremyliao.liveeventbus.LiveEventBus
import showmethe.github.kframework.util.widget.ScreenSizeUtil
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import showmethe.github.kframework.dialog.DialogLoading
import showmethe.github.kframework.livebus.LiveBusHelper
import showmethe.github.kframework.util.ToastFactory
import showmethe.github.kframework.util.system.KeyBoardUtils
import showmethe.github.kframework.util.widget.StatusBarUtil
import java.util.concurrent.TimeUnit
import showmethe.github.kframework.R
/**
 * showmethe.github.kframework.base
 *
 * 2019/1/10
 **/
abstract class BaseActivity<V : ViewDataBinding,VM : BaseViewModel> : AppCompatActivity() {

    private val loadingDialog  = DialogLoading()
    var screenWidth = 0
    var screenHeight = 0
    lateinit var context: BaseActivity<*,*>
    var  binding : V? = null
    lateinit var viewModel : VM
    lateinit var root : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,getViewId())
        setTheme()
        root = (findViewById<View>(android.R.id.content) as ViewGroup).getChildAt(0)

        context = this
        binding?.lifecycleOwner = this
        viewModel = initViewModel()
        lifecycle.addObserver(viewModel)
        onLifeCreated(this)


        if(showCreateReveal()){
            setUpReveal(savedInstanceState)
        }

        intent.apply {
            extras?.apply {
                onBundle(this)
            }
        }

        AppManager.get().addActivity(this)

        screenWidth = ScreenSizeUtil.getWidth(this)
        screenHeight = ScreenSizeUtil.getHeight(this)


        if (isLiveEventBusHere()) {
            LiveEventBus.get().with("LiveData",LiveBusHelper::class.java).observe(this,observer)
        }


        observerUI()
        init(savedInstanceState)
        initListener()

    }





    /**
     * 创建Reveal
     */
    private fun setUpReveal(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val viewTreeObserver = root.viewTreeObserver
            if (viewTreeObserver.isAlive) {
                viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        circularReveal(root)
                        root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                })
            }
        }
    }



    protected fun addStatusBarView(color: Int, isTxBlack: Boolean) {
        StatusBarUtil.addStatusBarView(this, color, isTxBlack)
    }

    private var observer: Observer<LiveBusHelper> = Observer {
        it?.apply {
            onEventComing(this)
        }
    }



    open fun sendEvent(helper: LiveBusHelper) {
        LiveEventBus.get().with("LiveData").post(helper)
    }


    open fun sendEventDelay(helper: LiveBusHelper, delay: Long) {
        LiveEventBus.get().with("LiveData",LiveBusHelper::class.java).postDelay(helper, delay)
    }


    open fun isLiveEventBusHere(): Boolean {
        return false
    }


    open  fun showCreateReveal() : Boolean{
        return false
    }


    open fun showFinishReveal(): Boolean{
        return false
    }


    open fun onEventComing(helper : LiveBusHelper) {

    }

    fun showLoading(){
        supportFragmentManager.executePendingTransactions()
        if(!loadingDialog.isAdded){
            loadingDialog.show(supportFragmentManager,"")
        }
    }

    fun dismissLoading(){
        loadingDialog.dismissAllowingStateLoss()
    }

    fun showToast(message : String){
        ToastFactory.createToast(message)
    }



    fun createViewModel(aClass : Class<VM>) : VM{
        return  ViewModelProviders.of(this,ViewModelProvider.AndroidViewModelFactory(application)).get(aClass)
    }


    open fun setTheme(){
    }

    abstract fun getViewId() : Int

    abstract fun initViewModel() : VM

    abstract fun onBundle(bundle: Bundle)

    abstract fun onLifeCreated(owner: LifecycleOwner)

    abstract fun observerUI()

    abstract fun init(savedInstanceState: Bundle?)

    abstract fun initListener()


    fun <T> applySchedulers(): ObservableTransformer<T, T> {
        val provider = AndroidLifecycle.createLifecycleProvider(this)
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .compose(provider.bindUntilEvent(Lifecycle.Event.ON_DESTROY))
        }
    }



    fun <T> applySchedulers(interval: Long ): ObservableTransformer<T, T> {
        val inter = if (interval < 15000) 15000 else interval
        val provider = AndroidLifecycle.createLifecycleProvider(this)
        return ObservableTransformer{
            it.repeatWhen {
                objectObservable ->
                objectObservable.flatMap { Observable.just(1).
                        delay(inter, TimeUnit.MILLISECONDS)
                }
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .compose(provider.bindUntilEvent<T>(Lifecycle.Event.ON_DESTROY))
        }
    }


    override fun onBackPressed() {
        finish()
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (isShouldHideKeyboard(v, ev)) {
                KeyBoardUtils.hideSoftKeyboard(context)
            }
        }
        return super.dispatchTouchEvent(ev)
    }


    // 如果焦点不是EditText则忽略
    private fun isShouldHideKeyboard(v: View?, event: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.height
            val right = left + v.width
            return !(event.x > left && event.x < right
                    && event.y > top && event.y < bottom)
        }
        return false
    }


    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out)
    }

    fun finishReveal(call : ()->Unit){
        if(showFinishReveal()){
            circularFinishReveal(root){
                call.invoke()
            }
        }
    }

    /**
     * startActivity，加入切换动画
     */
    fun startActivity(bundle: Bundle?, target: Class<*>) {
        val intent = Intent(this, target)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
        overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out)
    }


    /**
     * startActivity，加入切换动画
     */
    fun startActivity(bundle: Bundle?, target: Class<*>, transitionView: View, transitionName: String) {
        val intent = Intent(this, target)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        val transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this, transitionView, transitionName)
        startActivity(intent, transitionActivityOptions.toBundle())
    }

   fun  startForResult(bundle: Bundle?, requestCode:Int,target: Class<*>){
       val intent = Intent(this, target)
       if (bundle != null) {
           intent.putExtras(bundle)
       }
       startActivityForResult(intent,requestCode)
       overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out)
   }


    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
        LiveEventBus.get().with("LiveData", LiveBusHelper::class.java).removeObserver(observer)
    }



    /**
     * 水波纹覆盖显示动画
     * @param rootLayout
     */
    private fun circularReveal(rootLayout: View) {
        val finalRadius = Math.max(rootLayout.width, rootLayout.height).toFloat()
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            rootLayout,
            (rootLayout.width * 0.5).toInt(),
            (rootLayout.height * 0.5).toInt(),
            0f, finalRadius
        )
        circularReveal.duration = 800
        circularReveal.interpolator = LinearInterpolator()
        circularReveal.start()
    }


    /**
     * 水波纹覆盖关闭消失动画
     * @param rootLayout
     */
    private fun circularFinishReveal(rootLayout: View,call : ()->Unit) {
        val finalRadius = Math.max(rootLayout.width, rootLayout.height).toFloat()
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            rootLayout,
            (rootLayout.width * 0.5).toInt(),
            (rootLayout.height * 0.5).toInt(), finalRadius,0f
        )
        circularReveal.duration = 800
        circularReveal.interpolator = LinearInterpolator()
        circularReveal.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                call.invoke()
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }
        })
        circularReveal.start()
    }

}