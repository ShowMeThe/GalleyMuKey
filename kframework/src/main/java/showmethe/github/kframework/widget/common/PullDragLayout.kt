package showmethe.github.kframework.widget.common

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Point
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

import androidx.customview.widget.ViewDragHelper
import showmethe.github.kframework.R
import showmethe.github.kframework.util.widget.ScreenSizeUtil

/**
 * com.example.ken.kmvvm 上拉拖动viewGroup
 * ken
 * 2019/5/2
 */
class PullDragLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ViewGroup(context, attrs, defStyleAttr) {

    private var mViewDragHelper: ViewDragHelper? = null//拖拽帮助类
    private var mBottomView: View? = null//底部内容View
    private var mContentView: View? = null//内容View
    private var mLayoutInflater: LayoutInflater? = null
    private var mBottomBorderHeight = 80//底部边界凸出的高度
    private val mAutoBackBottomPos = Point()
    private val mAutoBackTopPos = Point()
    private var mBoundTopY: Int = 0
    var isOpen: Boolean = false
        private set
    private var mMaxHeight: Int = 0//最大高度
    private var mOnStateListener: OnStateListener? = null
    private var mScrollChageListener: OnScrollChageListener? = null



    internal var mCallback: ViewDragHelper.Callback = object : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return mBottomView === child
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return measuredWidth - child.measuredWidth
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return measuredHeight - child.measuredHeight
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            val leftBound = paddingLeft
            val rightBound = width - mBottomView!!.width - leftBound

            return Math.min(Math.max(left, leftBound), rightBound)
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            val topBound = mContentView!!.height - mBottomView!!.height
            val bottomBound = mContentView!!.height - mBottomBorderHeight
            return Math.min(bottomBound, Math.max(top, topBound))
        }

        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            if (changedView === mBottomView) {
                val startPosition = (mContentView!!.height - mBottomView!!.height).toFloat()
                val endPosition = (mContentView!!.height - mBottomBorderHeight).toFloat()
                val totalLength = endPosition - startPosition
                val rate = 1 - (top - startPosition) / totalLength
                if (mScrollChageListener != null) {
                    mScrollChageListener!!.onScrollChange(rate)
                }

            }
        }

        //手指释放的时候回调
        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            if (releasedChild === mBottomView) {
                if (releasedChild.y < mBoundTopY || yvel <= -1000) {
                    mViewDragHelper!!.settleCapturedViewAt(mAutoBackTopPos.x, mAutoBackTopPos.y)
                    isOpen = true
                    if (mOnStateListener != null) mOnStateListener!!.open()
                } else if (releasedChild.y >= mBoundTopY || yvel >= 1000) {
                    mViewDragHelper!!.settleCapturedViewAt(mAutoBackBottomPos.x, mAutoBackBottomPos.y)
                    isOpen = false
                    if (mOnStateListener != null) mOnStateListener!!.close()
                }
                invalidate()
            }
        }


    }

    fun setOnStateListener(onStateListener: OnStateListener) {
        mOnStateListener = onStateListener
    }

    fun setScrollChageListener(scrollChageListener: OnScrollChageListener) {
        mScrollChageListener = scrollChageListener
    }

    interface OnStateListener {
        fun open()

        fun close()
    }

    interface OnScrollChageListener {
        fun onScrollChange(rate: Float)
    }

    init {
        init(context)
        initAttrs(context, attrs)
    }

    private fun init(context: Context) {
        mMaxHeight = (ScreenSizeUtil.getHeight(context) * 0.8).toInt()
        mLayoutInflater = LayoutInflater.from(context)
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, mCallback)
    }

    /**
     * 切换底部View
     */
    fun toggleBottomView() {
        if (isOpen) {
            mViewDragHelper!!.smoothSlideViewTo(mBottomView!!, mAutoBackBottomPos.x, mAutoBackBottomPos.y)
            if (mOnStateListener != null) mOnStateListener!!.close()
        } else {
            mViewDragHelper!!.smoothSlideViewTo(mBottomView!!, mAutoBackTopPos.x, mAutoBackTopPos.y)
            if (mOnStateListener != null) mOnStateListener!!.open()
        }
        invalidate()
        isOpen = !isOpen
    }


    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PullDragLayout)
        if (typedArray != null) {
            if (typedArray.hasValue(R.styleable.PullDragLayout_ContentView)) {
                inflateContentView(typedArray.getResourceId(R.styleable.PullDragLayout_ContentView, 0))
            }
            if (typedArray.hasValue(R.styleable.PullDragLayout_BottomView)) {
                inflateBottomView(typedArray.getResourceId(R.styleable.PullDragLayout_BottomView, 0))
            }
            if (typedArray.hasValue(R.styleable.PullDragLayout_BottomBorderHeight)) {
                mBottomBorderHeight = typedArray.getDimension(R.styleable.PullDragLayout_BottomBorderHeight, 80f).toInt()
            }
            typedArray.recycle()
        }

    }

    private fun inflateContentView(resourceId: Int) {
        mContentView = mLayoutInflater!!.inflate(resourceId, this, true)
    }

    private fun inflateBottomView(resourceId: Int) {
        mBottomView = mLayoutInflater!!.inflate(resourceId, this, true)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        if (heightMode == View.MeasureSpec.EXACTLY) {
            heightSize = if (heightSize <= mMaxHeight) heightSize else mMaxHeight
        }

        if (heightMode == View.MeasureSpec.UNSPECIFIED) {
            heightSize = if (heightSize <= mMaxHeight) heightSize else mMaxHeight
        }
        if (heightMode == View.MeasureSpec.AT_MOST) {
            heightSize = if (heightSize <= mMaxHeight) heightSize else mMaxHeight
        }

        mContentView = getChildAt(0)
        mBottomView = getChildAt(1)

        if(first){
            measureChild(mBottomView, widthMeasureSpec, heightSize)
            first = false
            Log.e("222222222222","12313")
            first = false
        }

        measureChild(mContentView, widthMeasureSpec, heightMeasureSpec)

        val bottomViewHeight = mBottomView!!.measuredHeight
        val contentHeight = mContentView!!.measuredHeight
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec),
            bottomViewHeight + contentHeight + paddingBottom + paddingTop)
    }

    var first = true


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        mContentView = getChildAt(0)
        mBottomView = getChildAt(1)
        mContentView!!.layout(paddingLeft, paddingTop, width - paddingRight, mContentView!!.measuredHeight)
        mBottomView!!.layout(paddingLeft, mContentView!!.height - mBottomBorderHeight, width - paddingRight, measuredHeight - mBottomBorderHeight)
        mAutoBackBottomPos.x = mBottomView!!.left
        mAutoBackBottomPos.y = mBottomView!!.top

        mAutoBackTopPos.x = mBottomView!!.left
        mAutoBackTopPos.y = mContentView!!.height - mBottomView!!.height
        mBoundTopY = mContentView!!.height - mBottomView!!.height / 2

    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return mViewDragHelper!!.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        mViewDragHelper!!.processTouchEvent(event)
        return true
    }

    override fun computeScroll() {
        if (mViewDragHelper!!.continueSettling(true)) {
            invalidate()
        }
    }
}
