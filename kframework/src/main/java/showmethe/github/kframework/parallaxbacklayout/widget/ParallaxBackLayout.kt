package showmethe.github.kframework.parallaxbacklayout.widget

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import androidx.annotation.IntDef
import androidx.core.view.ViewCompat
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.FrameLayout

import showmethe.github.kframework.parallaxbacklayout.ViewDragHelper
import showmethe.github.kframework.parallaxbacklayout.ViewDragHelper.Companion.EDGE_BOTTOM
import showmethe.github.kframework.parallaxbacklayout.ViewDragHelper.Companion.EDGE_RIGHT
import showmethe.github.kframework.parallaxbacklayout.ViewDragHelper.Companion.EDGE_TOP
import showmethe.github.kframework.parallaxbacklayout.transform.CoverTransform
import showmethe.github.kframework.parallaxbacklayout.transform.ITransform
import showmethe.github.kframework.parallaxbacklayout.transform.ParallaxTransform
import showmethe.github.kframework.parallaxbacklayout.transform.SlideTransform
import kotlin.annotation.Retention


/**
 * The type Parallax back layout.
 */
class ParallaxBackLayout
//endregion

//region super method

/**
 * Instantiates a new Parallax back layout.
 *
 * @param context the context
 */
    (context: Context) : FrameLayout(context) {
    //endregion

    //region field
    /**
     * Threshold of scroll, we will close the activity, when scrollPercent over
     * this value;
     */
    private var mScrollThreshold = DEFAULT_SCROLL_THRESHOLD

    private var mSwipeHelper: Activity? = null
    private val mInsets = Rect()

    private var mEnable = true


    private var mContentView: View? = null

    private val mDragHelper: ViewDragHelper
    private var mSlideCallback: ParallaxSlideCallback? = null
    private var mTransform: ITransform? = null
    private var mContentLeft: Int = 0
    private var mEdgeMode = EDGE_MODE_DEFAULT

    private var mContentTop: Int = 0
    var layoutType = LAYOUT_PARALLAX
        private set

    private var mBackgroundView: IBackgroundView? = null
    //    private String mThumbFile;
    private var mShadowLeft: Drawable? = null

    //    private Bitmap mSecondBitmap;
    //    private Paint mPaintCache;


    private var mInLayout: Boolean = false

    /**
     * Edge being dragged
     */
    private var mTrackingEdge: Int = 0
    private var mFlingVelocity = 30
    /**
     * Sets edge flag.
     *
     * @param edgeFlag the edge flag
     */
    @Edge
    var edgeFlag = ViewDragHelper.EDGE_LEFT
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        set(@Edge edgeFlag) {
            if (this.edgeFlag == edgeFlag)
                return
            field = edgeFlag
            mDragHelper.setEdgeTrackingEnabled(edgeFlag)
            var orientation: GradientDrawable.Orientation = GradientDrawable.Orientation.LEFT_RIGHT
            if (edgeFlag == EDGE_LEFT)
                orientation = GradientDrawable.Orientation.RIGHT_LEFT
            else if (edgeFlag == EDGE_TOP) {
                orientation = GradientDrawable.Orientation.BOTTOM_TOP
            } else if (edgeFlag == EDGE_RIGHT)
                orientation = GradientDrawable.Orientation.LEFT_RIGHT
            else if (edgeFlag == EDGE_BOTTOM)
                orientation = GradientDrawable.Orientation.TOP_BOTTOM
            if (mShadowLeft == null) {
                val colors = intArrayOf(0x66000000, 0x11000000, 0x00000000)
                val drawable = ShadowDrawable(orientation, colors)
                drawable.gradientRadius = 90f
                drawable.setSize(50, 50)
                mShadowLeft = drawable
            } else if (mShadowLeft is ShadowDrawable) {
                (mShadowLeft as ShadowDrawable).orientation = orientation
            }
            applyWindowInset()
        }

    val systemTop: Int
        get() = mInsets.top

    val systemLeft: Int
        get() = mInsets.left

    //region cont
    @IntDef(LAYOUT_COVER, LAYOUT_PARALLAX, LAYOUT_SLIDE, LAYOUT_CUSTOM)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class LayoutType

    @IntDef(ViewDragHelper.EDGE_LEFT, EDGE_RIGHT, EDGE_TOP, EDGE_BOTTOM)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Edge

    @IntDef(EDGE_MODE_DEFAULT, EDGE_MODE_FULL)
    @Retention(AnnotationRetention.RUNTIME)
    annotation class EdgeMode

    init {
        mDragHelper = ViewDragHelper.create(this, ViewDragCallback())
        edgeFlag = EDGE_LEFT
    }

    @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
        val top = insets.systemWindowInsetTop
        if (mContentView!!.layoutParams is ViewGroup.MarginLayoutParams) {
            val params = mContentView!!.layoutParams as ViewGroup.MarginLayoutParams
            mInsets.set(params.leftMargin, params.topMargin + top, params.rightMargin, params.bottomMargin)
        }
        applyWindowInset()
        return super.onApplyWindowInsets(insets)
    }


    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (!mEnable || !mBackgroundView!!.canGoBack()) {
            return false
        }
        try {
            return mDragHelper.shouldInterceptTouchEvent(event)
        } catch (e: ArrayIndexOutOfBoundsException) {
            return false
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!mEnable || !mBackgroundView!!.canGoBack()) {
            return false
        }
        mDragHelper.processTouchEvent(event)
        return true
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        mInLayout = true
        applyWindowInset()
        if (mContentView != null) {
            var cleft = mContentLeft
            var ctop = mContentTop
            val params = mContentView!!.layoutParams
            if (params is ViewGroup.MarginLayoutParams) {
                cleft += params.leftMargin
                ctop += params.topMargin
            }
            mContentView!!.layout(
                cleft, ctop,
                cleft + mContentView!!.measuredWidth,
                ctop + mContentView!!.measuredHeight
            )
        }
        mInLayout = false
    }

    override fun requestLayout() {
        if (!mInLayout) {
            super.requestLayout()
        }
    }

    override fun computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    override fun drawChild(canvas: Canvas, child: View, drawingTime: Long): Boolean {
        Log.d(View.VIEW_LOG_TAG, "drawChild")
        val drawContent = child === mContentView
        if (mEnable)
            drawThumb(canvas, child)
        val ret = super.drawChild(canvas, child, drawingTime)
        if (mEnable && drawContent
            && mDragHelper.viewDragState != ViewDragHelper.STATE_IDLE
        ) {
            drawShadow(canvas, child)
        }
        return ret
    }
    //endregion

    //region private method

    /**
     * Set up contentView which will be moved by user gesture
     *
     * @param view
     */
    private fun setContentView(view: View) {
        mContentView = view
    }

    private fun applyWindowInset() {
        if (mInsets == null)
            return
        if (mEdgeMode == EDGE_MODE_FULL) {
            mDragHelper.edgeSize = Math.max(width, height)
        } else if (edgeFlag == EDGE_TOP)
            mDragHelper.edgeSize = mInsets.top + mDragHelper.edgeSizeDefault
        else if (edgeFlag == EDGE_BOTTOM) {
            mDragHelper.edgeSize = mInsets.bottom + mDragHelper.edgeSizeDefault
        } else if (edgeFlag == ViewDragHelper.EDGE_LEFT) {
            mDragHelper.edgeSize = mDragHelper.edgeSizeDefault + mInsets.left
        } else
            mDragHelper.edgeSize = mDragHelper.edgeSizeDefault + mInsets.right
    }


    /**
     *
     */
    private fun drawThumb(canvas: Canvas, child: View) {
        if (mContentLeft == 0 && mContentTop == 0)
            return
        val store = canvas.save()
        mTransform!!.transform(canvas, this, child)
        mBackgroundView!!.draw(canvas)

        canvas.restoreToCount(store)
    }

    /**
     * draw shadow
     */
    private fun drawShadow(canvas: Canvas, child: View) {
        if (mContentLeft == 0 && mContentTop == 0)
            return
        if (mShadowLeft == null)
            return
        if (edgeFlag == EDGE_LEFT) {
            mShadowLeft!!.setBounds(
                child.left - mShadowLeft!!.intrinsicWidth, child.top,
                child.left, child.bottom
            )
            mShadowLeft!!.alpha = (width - child.left) * 255 / width
        } else if (edgeFlag == EDGE_RIGHT) {
            mShadowLeft!!.setBounds(
                child.right, child.top,
                child.right + mShadowLeft!!.intrinsicWidth, child.bottom
            )
            mShadowLeft!!.alpha = child.right * 255 / width
        } else if (edgeFlag == EDGE_BOTTOM) {
            mShadowLeft!!.setBounds(
                child.left, child.bottom,
                child.right, child.bottom + mShadowLeft!!.intrinsicHeight
            )

            mShadowLeft!!.alpha = child.bottom * 255 / height
        } else if (edgeFlag == EDGE_TOP) {
            mShadowLeft!!.setBounds(
                child.left, child.top - mShadowLeft!!.intrinsicHeight + systemTop,
                child.right, child.top + systemTop
            )
            mShadowLeft!!.alpha = (height - child.top) * 255 / height
        }
        mShadowLeft!!.draw(canvas)
    }

    //endregion

    //region Public Method

    /**
     * Sets enable gesture.
     *
     * @param enable the enable
     */
    fun setEnableGesture(enable: Boolean) {
        mEnable = enable
    }

    /**
     * set slide callback
     *
     * @param slideCallback callback
     */
    fun setSlideCallback(slideCallback: ParallaxSlideCallback) {
        mSlideCallback = slideCallback
    }

    /**
     * Set scroll threshold, we will close the activity, when scrollPercent over
     * this value
     *
     * @param threshold the threshold
     */
    fun setScrollThresHold(threshold: Float) {
        if (threshold >= 1.0f || threshold <= 0) {
            throw IllegalArgumentException("Threshold value should be between 0 and 1.0")
        }
        mScrollThreshold = threshold
    }

    /**
     * Set scroll threshold, we will close the activity, when scrollPercent over
     * this value
     *
     * @param velocity the fling velocity
     */
    fun setVelocity(velocity: Int) {
        mFlingVelocity = velocity
    }

    /**
     * attach to activity
     *
     * @param activity the activity
     */
    fun attachToActivity(activity: Activity) {
        mSwipeHelper = activity

        val decor = activity.window.decorView as ViewGroup
        val decorChild = decor.getChildAt(0) as ViewGroup
        decor.removeView(decorChild)
        addView(decorChild, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        setContentView(decorChild)
        decor.addView(this)
    }

    /**
     * set the slide mode fullscreen or default
     *
     * @param mode
     */
    fun setEdgeMode(@EdgeMode mode: Int) {
        mEdgeMode = mode
        applyWindowInset()
    }

    /**
     * Scroll out contentView and finish the activity
     *
     * @param duration default 0
     */
    fun scrollToFinishActivity(duration: Int): Boolean {
        if (!mEnable || !mBackgroundView!!.canGoBack()) {
            return false
        }
        val childWidth = width
        var left = 0
        var top = 0
        mTrackingEdge = edgeFlag
        when (mTrackingEdge) {
            EDGE_LEFT -> left = childWidth
            EDGE_BOTTOM -> top = -height
            EDGE_RIGHT -> left = -width
            EDGE_TOP -> top = height
        }
        if (mDragHelper.smoothSlideViewTo(mContentView!!, left, top, duration)) {
            ViewCompat.postInvalidateOnAnimation(this)
            postInvalidate()
            return true
        }
        return false
    }

    /**
     * shadow drawable
     *
     * @param drawable
     */
    fun setShadowDrawable(drawable: Drawable) {
        mShadowLeft = drawable
    }

    /**
     * Sets background view.
     *
     * @param backgroundView the background view
     */
    fun setBackgroundView(backgroundView: IBackgroundView) {
        mBackgroundView = backgroundView
    }

    /**
     * Sets layout type.
     *
     * @param layoutType the layout type
     */
    fun setLayoutType(@LayoutType layoutType: Int, transform: ITransform?) {
        this.layoutType = layoutType
        when (layoutType) {
            LAYOUT_CUSTOM -> {
                assert(transform != null)
                mTransform = transform
            }
            LAYOUT_COVER -> mTransform = CoverTransform()
            LAYOUT_PARALLAX -> mTransform = ParallaxTransform()
            LAYOUT_SLIDE -> mTransform = SlideTransform()
        }
    }


    //endregion

    //region class

    private inner class ViewDragCallback : ViewDragHelper.Callback() {

        private var mScrollPercent: Float = 0.toFloat()

        override fun tryCaptureView(view: View, pointerId: Int): Boolean {
            val ret = mDragHelper.isEdgeTouched(edgeFlag, pointerId)
            if (ret) {
                mTrackingEdge = edgeFlag
            }
            var directionCheck = false
            if (edgeFlag == EDGE_LEFT || edgeFlag == EDGE_RIGHT) {
                directionCheck = !mDragHelper.checkTouchSlop(ViewDragHelper.DIRECTION_VERTICAL, pointerId)
            } else if (edgeFlag == EDGE_BOTTOM || edgeFlag == EDGE_TOP) {
                directionCheck = !mDragHelper
                    .checkTouchSlop(ViewDragHelper.DIRECTION_HORIZONTAL, pointerId)
            }
            return ret and directionCheck
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return edgeFlag and (EDGE_LEFT or EDGE_RIGHT)
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return edgeFlag and (EDGE_BOTTOM or EDGE_TOP)
        }

        override fun onViewPositionChanged(changedView: View?, left: Int, top: Int, dx: Int, dy: Int) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
            if (mTrackingEdge and EDGE_LEFT != 0) {
                mScrollPercent = Math.abs((left - mInsets.left).toFloat() / mContentView!!.width)
            }
            if (mTrackingEdge and EDGE_RIGHT != 0) {
                mScrollPercent = Math.abs((left - mInsets.left).toFloat() / mContentView!!.width)
            }
            if (mTrackingEdge and EDGE_BOTTOM != 0) {
                mScrollPercent = Math.abs((top - systemTop).toFloat() / mContentView!!.height)
            }
            if (mTrackingEdge and EDGE_TOP != 0) {
                mScrollPercent = Math.abs(top.toFloat() / mContentView!!.height)
            }
            mContentLeft = left
            mContentTop = top
            invalidate()
            if (mSlideCallback != null)
                mSlideCallback!!.onPositionChanged(mScrollPercent)
            if (mScrollPercent >= 0.999f) {
                if (!mSwipeHelper!!.isFinishing) {
                    mSwipeHelper!!.finish()
                    mSwipeHelper!!.overridePendingTransition(0, 0)
                }
            }
        }


        override fun onViewReleased(releasedChild: View?, xvel: Float, yvel: Float) {
            val childWidth = releasedChild!!.width
            val childHeight = releasedChild.height
            var fling = false
            var left = mInsets.left
            var top = 0
            if (mTrackingEdge and EDGE_LEFT != 0) {
                if (Math.abs(xvel) > mFlingVelocity) {
                    fling = true
                }
                left = if (xvel >= 0 && (fling || mScrollPercent > mScrollThreshold))
                    childWidth + mInsets.left
                else
                    mInsets.left
            }
            if (mTrackingEdge and EDGE_RIGHT != 0) {
                if (Math.abs(xvel) > mFlingVelocity) {
                    fling = true
                }
                left = if (xvel <= 0 && (fling || mScrollPercent > mScrollThreshold))
                    -childWidth + mInsets.left
                else
                    mInsets.left
            }
            if (mTrackingEdge and EDGE_TOP != 0) {
                if (Math.abs(yvel) > mFlingVelocity) {
                    fling = true
                }
                top = if (yvel >= 0 && (fling || mScrollPercent > mScrollThreshold))
                    childHeight
                else
                    0
            }
            if (mTrackingEdge and EDGE_BOTTOM != 0) {
                if (Math.abs(yvel) > mFlingVelocity) {
                    fling = true
                }
                top = if (yvel <= 0 && (fling || mScrollPercent > mScrollThreshold))
                    -childHeight + systemTop
                else
                    0
            }
            mDragHelper.settleCapturedViewAt(left, top)
            invalidate()
        }

        override fun onViewDragStateChanged(state: Int) {
            super.onViewDragStateChanged(state)
            if (mSlideCallback != null)
                mSlideCallback!!.onStateChanged(state)
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            var ret = mInsets.left
            if (mTrackingEdge and EDGE_LEFT != 0) {
                ret = Math.min(child.width, Math.max(left, 0))
            } else if (mTrackingEdge and EDGE_RIGHT != 0) {
                ret = Math.min(mInsets.left, Math.max(left, -child.width))
            }
            return ret
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            var ret = mContentView!!.top
            if (mTrackingEdge and EDGE_BOTTOM != 0) {
                ret = Math.min(0, Math.max(top, -child.height))
            } else if (mTrackingEdge and EDGE_TOP != 0) {
                ret = Math.min(child.height, Math.max(top, 0))
            }
            return ret
        }

    }

    /**
     * The interface Background view.
     */
    interface IBackgroundView {
        /**
         * Draw.
         *
         * @param canvas the canvas
         */
        fun draw(canvas: Canvas)

        /**
         * Can go back boolean.
         *
         * @return the boolean
         */
        fun canGoBack(): Boolean
    }

    interface ParallaxSlideCallback {
        fun onStateChanged(state: Int)

        fun onPositionChanged(percent: Float)
    }

    companion object {

        private val DEFAULT_SCRIM_COLOR = -0x67000000

        private val FULL_ALPHA = 255

        /**
         * Default threshold of scroll
         */
        private val DEFAULT_SCROLL_THRESHOLD = 0.5f

        private val OVERSCROLL_DISTANCE = 0
        private val EDGE_LEFT = ViewDragHelper.EDGE_LEFT

        /**
         * The constant LAYOUT_PARALLAX.
         */
        const val LAYOUT_PARALLAX = 1
        /**
         * The constant LAYOUT_COVER.
         */
        const val LAYOUT_COVER = 0
        /**
         * The constant LAYOUT_SLIDE.
         */
        const val LAYOUT_SLIDE = 2
        const val LAYOUT_CUSTOM = -1
        const val EDGE_MODE_FULL = 0
        const  val EDGE_MODE_DEFAULT = 1
    }
    //endregion

}
