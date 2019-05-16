package showmethe.github.kframework.widget.common

import android.content.Context

import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*


import java.util.ArrayList

import androidx.annotation.AnimRes
import androidx.core.content.ContextCompat
import showmethe.github.kframework.R
import showmethe.github.kframework.util.widget.DisplayUtil

/**
 *
 * Author : jiaqi Ye
 * Date : 2018/8/29
 * Time : 16:37
 */
class TextViewFlipper @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : ViewFlipper(context, attrs) {

    private var mInterval = 3000
    private var animDuration = 1500
    @AnimRes
    private val inAnimResId = R.anim.anim_right_in
    @AnimRes
    private val outAnimResId = R.anim.anim_left_out
    private var mDatas: ArrayList<String>? = null
    private var isStarted: Boolean = false
    private var isDetachedFromWindow: Boolean = false
    private var mTextColor: Int = 0
    private var mTextSize: Int = 0
    private var mGravity: Int = 0
    private var mListener: TextItemClickListener? = null

    /**
     * 设置延时间隔
     */
    private val mRunnable = AnimRunnable()

    init {
        init(context, attrs)
    }


    private fun init(context: Context, attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.TextViewFlipper)
        mInterval = a.getInt(R.styleable.TextViewFlipper_interval, 3000)
        mTextColor = a.getColor(R.styleable.TextViewFlipper_textColor, ContextCompat.getColor(context, R.color.text_333333))
        mTextSize = a.getDimension(R.styleable.TextViewFlipper_textSize, DisplayUtil.dip2px(context, 15f).toFloat()).toInt()
        animDuration = a.getInt(R.styleable.TextViewFlipper_duration, 1500)
        val gravity = a.getInt(R.styleable.TextViewFlipper_textGravity, CENTER)
        when (gravity) {
            START -> mGravity = Gravity.START
            END -> mGravity = Gravity.END
            CENTER -> mGravity = Gravity.CENTER
        }
        a.recycle()
        layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        setInAndOutAnimation(inAnimResId, outAnimResId)


        setOnClickListener {
            val position = displayedChild//当前显示的子视图的索引位置
            if (mListener != null) {
                mListener!!.onItemClick(mDatas!![position], position)
            }
        }
    }

    fun setTextItemClickListener(mListener: TextItemClickListener) {
        this.mListener = mListener
    }


    /**设置数据集合 */
    fun setData(data: ArrayList<String>) {
        this.mDatas = data
        if (mDatas!!.size > 0) {
            removeAllViews()
            for (i in mDatas!!.indices) {
                val textView = RaceTextView(context)
                textView.text = mDatas!![i]
                textView.setTextColor(mTextColor)
                textView.textSize = mTextSize.toFloat()
                textView.gravity = mGravity
                addView(textView, i)//添加子view,并标识子view位置
            }
        }
    }


    interface TextItemClickListener {
        fun onItemClick(data: String, position: Int)
    }


    /**
     * 设置进入动画和离开动画
     *
     * @param inAnimResId  进入动画的resID
     * @param outAnimResID 离开动画的resID
     */
    fun setInAndOutAnimation(@AnimRes inAnimResId: Int, @AnimRes outAnimResID: Int) {
        val inAnim = AnimationUtils.loadAnimation(context, inAnimResId)
        inAnim.duration = animDuration.toLong()
        inAnimation = inAnim

        val outAnim = AnimationUtils.loadAnimation(context, outAnimResID)
        outAnim.duration = animDuration.toLong()
        outAnimation = outAnim
    }

    private inner class AnimRunnable : Runnable {

        override fun run() {
            if (isStarted) {
                showNext()//手动显示下一个子view。
                postDelayed(this, (mInterval + animDuration).toLong())
            } else {
                stopViewAnimator()
            }

        }
    }


    /**暂停动画 */
    fun stopViewAnimator() {
        if (isStarted) {
            removeCallbacks(mRunnable)
            isStarted = false
        }
    }

    /**开始动画 */
    fun startViewAnimator() {
        if (!isStarted && mDatas!!.size > 0) {
            if (!isDetachedFromWindow) {
                isStarted = true
                postDelayed(mRunnable, mInterval.toLong())
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        isDetachedFromWindow = true
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        isDetachedFromWindow = false
    }

    companion object {
        private val START = 1
        private val END = 2
        private val CENTER = 3
    }


}
