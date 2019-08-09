package showmethe.github.kframework.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.*
import com.jeremyliao.liveeventbus.LiveEventBus
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle
import com.trello.rxlifecycle3.components.support.RxFragment
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import showmethe.github.kframework.dialog.DialogLoading
import showmethe.github.kframework.livebus.LiveBusHelper
import showmethe.github.kframework.util.toast.ToastFactory

/**
 * showmethe.github.kframework.base
 * ken
 * 2019/6/8
 **/
abstract class LazyFragment <V : ViewDataBinding,VM : BaseViewModel> : RxFragment() {


    val loadingDialog  = DialogLoading()
    lateinit var context : BaseActivity<*,*>
    var rootView : View? = null
    var  binding : V? = null
    lateinit var viewModel : VM
    private var firstLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = activity as BaseActivity<*, *>
        if(arguments!=null){
            onBundle(arguments!!)
        }
        viewModel = initViewModel()
        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = setContentView(inflater,getViewId())
        view?.apply {
            binding = DataBindingUtil.bind(view.rootView)
        }
        return view
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


    fun createViewModel(aClass : Class<VM>) : VM{
        return  ViewModelProvider(activity!!, ViewModelProvider.AndroidViewModelFactory(activity!!.application)).get(aClass)
    }

    fun <T> applySchedulers(): ObservableTransformer<T, T> {
        val provider = AndroidLifecycle.createLifecycleProvider(this)
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .compose(provider.bindUntilEvent(Lifecycle.Event.ON_DESTROY))
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }




    /**
     * fragment可见的时候操作，取代onResume，且在可见状态切换到可见的时候调用
     */
    open fun onVisible() {


    }

    /**
     * fragment不可见的时候操作,onPause的时候,以及不可见的时候调用
     */
    open fun onHidden() {

    }

    override fun onResume() {//和activity的onResume绑定，Fragment初始化的时候必调用，但切换fragment的hide和visible的时候可能不会调用！
        super.onResume()
        if(firstLoad){
            if (isLiveEventBusHere()) {
                LiveEventBus.get().with("LiveData", LiveBusHelper::class.java).observe(this,observer)
            }
            onLifeCreated(this)
            observerUI()
            init()
            initListener()
            firstLoad = false
        }
        if (isAdded && !isHidden) {//用isVisible此时为false，因为mView.getWindowToken为null
            onVisible()
        }
    }


    private var observer: Observer<LiveBusHelper> = Observer {
        it?.apply {
            onEventComing(this)
        }
    }

    open fun onEventComing(helper : LiveBusHelper) {

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

    override fun onPause() {
        if (isVisible)
            onHidden()
        super.onPause()
    }


    //默认fragment创建的时候是可见的，但是不会调用该方法！切换可见状态的时候会调用，但是调用onResume，onPause的时候却不会调用
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            onVisible()
        } else {
            onHidden()
        }
    }


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


    abstract fun initViewModel() : VM

    abstract fun getViewId() : Int

    abstract fun onLifeCreated(owner: LifecycleOwner)

    abstract fun onBundle(bundle: Bundle)

    abstract fun observerUI()

    abstract fun init()

    abstract fun initListener()



}