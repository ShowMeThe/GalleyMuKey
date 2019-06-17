package showmethe.github.kframework.parallaxbacklayout

import android.app.Activity
import android.app.Application
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.view.ViewGroup

import showmethe.github.kframework.R
import showmethe.github.kframework.parallaxbacklayout.widget.ParallaxBackLayout


class ParallaxHelper private constructor() : Application.ActivityLifecycleCallbacks {
    private val mLinkedStack = LinkedStack<Activity, TraceInfo>()

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {
        val traceInfo = TraceInfo()
        mLinkedStack.put(activity, traceInfo)
        traceInfo.mCurrent = activity

        val parallaxBack = checkAnnotation(activity.javaClass)
        if (mLinkedStack.size() > 0 && parallaxBack != null) {
            val layout = enableParallaxBack(activity)
            layout.edgeFlag = parallaxBack.edgeMode.value
            layout.setEdgeMode(parallaxBack.edgeMode.value)
            layout.setLayoutType(parallaxBack.layout.value, null)
        }
    }

    private fun checkAnnotation(c: Class<out Activity>): ParallaxBack? {
        var mc: Class<*>? = c
        var parallaxBack: ParallaxBack?
        while (Activity::class.java.isAssignableFrom(mc!!)) {
            parallaxBack = mc.getAnnotation(ParallaxBack::class.java)
            if (parallaxBack != null)
                return parallaxBack
            mc = mc.superclass
        }
        return null
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {
        mLinkedStack.remove(activity)
    }

    /**
     * The type Trace info.
     */
    class TraceInfo {
        var mCurrent: Activity? = null
    }

    class GoBackView  constructor(private val mActivity: Activity) : ParallaxBackLayout.IBackgroundView {
        private var mActivityBack: Activity? = null


        override fun draw(canvas: Canvas) {
            if (mActivityBack != null) {
                mActivityBack!!.window.decorView.requestLayout()
                mActivityBack!!.window.decorView.draw(canvas)
            }
        }

        override fun canGoBack(): Boolean {
            mActivityBack = sParallaxHelper!!.mLinkedStack.before(mActivity)
            return (mActivityBack)!= null
        }
    }

    companion object {

        private var sParallaxHelper: ParallaxHelper? = null

        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {  sParallaxHelper = ParallaxHelper()  }


        /**
         * Disable parallax back.
         *
         * @param activity the activity
         */
        fun disableParallaxBack(activity: Activity) {
            val layout = getParallaxBackLayout(activity)
            layout?.setEnableGesture(false)
        }

        /**
         * Enable parallax back.
         *
         * @param activity the activity
         */
        fun enableParallaxBack(activity: Activity): ParallaxBackLayout {
            val layout = getParallaxBackLayout(activity, true)
            layout!!.setEnableGesture(true)
            return layout
        }

        /**
         * Gets parallax back layout.
         *
         * @param activity the activity
         * @param create   the create
         * @return the parallax back layout
         */
        @JvmOverloads
        fun getParallaxBackLayout(activity: Activity, create: Boolean = false): ParallaxBackLayout? {
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
    }
}
/**
 * Gets parallax back layout.
 *
 * @param activity the activity
 * @return the parallax back layout
 */
