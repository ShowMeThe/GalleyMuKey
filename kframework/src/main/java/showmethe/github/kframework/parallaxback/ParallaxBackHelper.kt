package showmethe.github.kframework.parallaxback

import android.app.Activity
import android.graphics.Canvas
import android.os.Bundle
import android.view.ViewGroup
import showmethe.github.kframework.R
import showmethe.github.kframework.base.SimpleLifecyclerCallbacks
import showmethe.github.kframework.parallaxback.widget.ParallaxBackLayout
import java.lang.ref.SoftReference


class ParallaxBackHelper private constructor(): SimpleLifecyclerCallbacks(){

    private val linkedStack = SoftReference(LinkedStack<Activity?, TraceInfo>())
    val mLinkedStack = linkedStack.get()!!

    companion object{
        private val  instant : ParallaxBackHelper by  lazy{  ParallaxBackHelper() }

        fun get() : ParallaxBackHelper{
            return  instant
        }
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        val traceInfo = TraceInfo()
        mLinkedStack.put(activity, traceInfo)
        traceInfo.mCurrent = activity
        activity?.apply {
            val parallaxBack = checkAnnotation(activity.javaClass)
            if (parallaxBack != null && mLinkedStack.size() > 0  ) {
                val layout = enableParallaxBack(activity)
                layout.edgeFlag = parallaxBack.edge.value
                layout.setEdgeMode(parallaxBack.edgeMode.value)
                layout.setLayoutType(parallaxBack.layout.value, null)
            }

        }

    }


    override fun onActivityDestroyed(activity: Activity?) {
        mLinkedStack.remove(activity)
    }

    private fun checkAnnotation(c: Class<out Activity>): ParallaxBack? {
        var mc: Class<*> = c
        var parallaxBack: ParallaxBack?
        while (Activity::class.java.isAssignableFrom(mc)) {
            parallaxBack = mc.getAnnotation(ParallaxBack::class.java)
            if (parallaxBack != null)
                return parallaxBack
            mc = mc.superclass as Class<*>
        }
        return null
    }

}



class GoBackView constructor(private val mActivity: Activity) : ParallaxBackLayout.IBackgroundView {
    private var mActivityBack: Activity? = null


    override fun draw(canvas: Canvas) {
        if (mActivityBack != null) {
            mActivityBack!!.window.decorView.requestLayout()
            mActivityBack!!.window.decorView.draw(canvas)
        }
    }

    override fun canGoBack(): Boolean {
        mActivityBack = ParallaxBackHelper.get().mLinkedStack.before(mActivity)
        return (mActivityBack) != null
    }
}


fun getParallaxBackLayout(activity: Activity, create: Boolean): ParallaxBackLayout? {
    var view = (activity.window.decorView as ViewGroup).getChildAt(0)
    if (view is ParallaxBackLayout)
        return view
    view = activity.findViewById(R.id.pllayout)
    if (view is ParallaxBackLayout)
        return view
    if (create) {
        val backLayout = ParallaxBackLayout(activity)
        backLayout.id = R.id.pllayout
        backLayout.attachToActivity(activity)
        backLayout.setBackgroundView(GoBackView(activity))
        return backLayout
    }
    return null
}

fun getParallaxBackLayout(activity: Activity): ParallaxBackLayout? {
    return getParallaxBackLayout(activity, false)
}

fun enableParallaxBack(activity: Activity): ParallaxBackLayout {
    val layout = getParallaxBackLayout(activity, true)
    layout!!.setEnableGesture(true)
    return layout
}

class TraceInfo {
    var  mCurrent: Activity? = null
}