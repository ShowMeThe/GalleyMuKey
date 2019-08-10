package showmethe.github.kframework.picture

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Message

import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View


import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import showmethe.github.kframework.R
import kotlin.math.min


/**
 * 录像按钮
 */
class RecordButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {
    /**
     * 视频录制最小时间 秒
     */
    private val VIDEO_RECORD_DEFAULT_MIN_TIME = 1
    /**
     * 视频录制内圆半径
     */
    private val VIDEO_RECORD_DEFAULT_INNER_CIRCLE_RADIUS = 5f
    /**
     * 视频录制外圆半径
     */
    private val VIDEO_RECORD_DEFAULT_EXCIRCLE_RADIUS = 12f
    /**
     * 视频录制圆环默认颜色
     */
    private val VIDEO_RECORD_DEFAULT_ANNULUS_COLOR = Color.parseColor("#FFFFFF")
    /**
     * 视频录制内圆默认颜色
     */
    private val VIDEO_RECORD_DEFAULT_INNER_CIRCLE_COLOR = Color.parseColor("#F5F5F5")
    /**
     * 视频录制进度默认颜色
     */
    private val VIDEO_RECORD_DEFAULT_PROGRESS_COLOR = Color.parseColor("#00A653")

    /**
     * 外圆放大倍数
     */
    private val EXCICLE_MAGNIFICATION = 1.25f
    private var excicleMagnification: Float = 0.toFloat()

    /**
     * 内圆缩小倍数
     */
    private val INNER_CIRCLE_SHRINKS = 0.75f
    private var innerCircleShrinks: Float = 0.toFloat()
    /**
     * 视频实际录制最大时间
     */
    private var mMaxTime: Int = 0
    /**
     * 视频实际录制最小时间
     */
    private var mMinTime: Int = 0
    /**
     * 外圆半径
     */
    private var mExCircleRadius: Float = 0.toFloat()
    private var mInitExCircleRadius: Float = 0.toFloat()
    /**
     * 内圆半径
     */
    private var mInnerCircleRadius: Float = 0.toFloat()
    private var mInitInnerRadius: Float = 0.toFloat()
    /**
     * 外圆颜色
     */
    private var mAnnulusColor: Int = 0
    /**
     * 内圆颜色
     */
    private var mInnerCircleColor: Int = 0
    /**
     * 进度条颜色
     */
    private var mProgressColor: Int = 0
    /**
     * 外圆画笔
     */
    private var mExCirclePaint: Paint? = null
    /**
     * 内圆画笔
     */
    private var mInnerCirclePaint: Paint? = null
    /**
     * 进度条画笔
     */
    private var mProgressPaint: Paint? = null

    /**
     * 是否正在录制
     */
    private var isRecording = false
    /**
     * 进度条值动画
     */
    private var mProgressAni: ValueAnimator? = null

    /**
     * 开始录制时间
     */
    private var mStartTime: Long = 0
    /**
     * 录制 结束时间
     */
    private var mEndTime: Long = 0
    /**
     * 长按最短时间  单位毫秒
     */
    var LONG_CLICK_MIN_TIME: Long = 200
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mCurrentProgress: Float = 0.toFloat()
    private val handler = MHandler(this)
    private var onRecordListener: OnRecordListener? = null



    init {
        initData(context, attrs)
    }

    /**
     * 获取布局属性
     *
     * @param context
     * @param attrs
     */
    private fun initData(context: Context, attrs: AttributeSet?) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.RecordButton)
        mMaxTime = a.getInt(R.styleable.RecordButton_maxTime, VIDEO_RECORD_DEFAULT_MAX_TIME)
        mMinTime = a.getInt(R.styleable.RecordButton_minTime, VIDEO_RECORD_DEFAULT_MIN_TIME)

        excicleMagnification = a.getFloat(R.styleable.RecordButton_excicleMagnification, EXCICLE_MAGNIFICATION)
        innerCircleShrinks = a.getFloat(R.styleable.RecordButton_excicleMagnification, INNER_CIRCLE_SHRINKS)
        if (excicleMagnification < 1) {
            throw RuntimeException("外圆放大倍数必须大于1")
        }
        if (innerCircleShrinks > 1) {
            throw RuntimeException("内圆缩小倍数必须小于1")
        }

        mExCircleRadius = a.getDimension(R.styleable.RecordButton_excircleRadius, VIDEO_RECORD_DEFAULT_EXCIRCLE_RADIUS)
        mInitExCircleRadius = mExCircleRadius
        mInnerCircleRadius =
            a.getDimension(R.styleable.RecordButton_innerCircleRadius, VIDEO_RECORD_DEFAULT_INNER_CIRCLE_RADIUS)
        mInitInnerRadius = mInnerCircleRadius

        mAnnulusColor = a.getColor(R.styleable.RecordButton_annulusColor, VIDEO_RECORD_DEFAULT_ANNULUS_COLOR)
        mInnerCircleColor =
            a.getColor(R.styleable.RecordButton_innerCircleColor, VIDEO_RECORD_DEFAULT_INNER_CIRCLE_COLOR)
        mProgressColor = a.getColor(R.styleable.RecordButton_progressColor, VIDEO_RECORD_DEFAULT_PROGRESS_COLOR)
        a.recycle()
        //初始化外圆画笔
        mExCirclePaint = Paint()
        mExCirclePaint!!.color = mAnnulusColor

        //初始化内圆画笔
        mInnerCirclePaint = Paint()
        mInnerCirclePaint!!.color = mInnerCircleColor

        //初始化进度条画笔
        mProgressPaint = Paint()
        mProgressPaint!!.color = mProgressColor
        mProgressPaint!!.strokeWidth = mExCircleRadius - mInnerCircleRadius
        mProgressPaint!!.style = Paint.Style.STROKE
        //进度条的属性动画
        mProgressAni = ValueAnimator.ofFloat(0f, 360f)
        mProgressAni!!.duration = (mMaxTime * 1000).toLong()

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        mHeight = View.MeasureSpec.getSize(heightMeasureSpec)

        if (mExCircleRadius * 2f * excicleMagnification > min(mWidth, mHeight)) {
            mExCircleRadius = mInnerCircleRadius
        }
        if (mInnerCircleRadius > mExCircleRadius) {
            mInnerCircleRadius = mExCircleRadius
        } else if (mInnerCircleRadius == mExCircleRadius) {
            Log.e(TAG, "mInnerCircleRadius == mExCircleRadius 你将看不到进度条")
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //画外圆
        canvas.drawCircle((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), mExCircleRadius, mExCirclePaint!!)
        //画内圆
        canvas.drawCircle((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), mInnerCircleRadius, mInnerCirclePaint!!)
        if (isRecording) {
            drawProgress(canvas)
        }
    }

    /**
     * 绘制圆形进度条
     * Draw a circular progress bar.
     *
     * @param canvas
     */
    private fun drawProgress(canvas: Canvas) {
        val rectF = RectF(
            mWidth / 2 - (mInnerCircleRadius + (mExCircleRadius - mInnerCircleRadius) / 2),
            mHeight / 2 - (mInnerCircleRadius + (mExCircleRadius - mInnerCircleRadius) / 2),
            mWidth / 2 + (mInnerCircleRadius + (mExCircleRadius - mInnerCircleRadius) / 2),
            mHeight / 2 + (mInnerCircleRadius + (mExCircleRadius - mInnerCircleRadius) / 2)
        )
        canvas.drawArc(rectF, -90f, mCurrentProgress, false, mProgressPaint!!)
    }

    override fun performClick(): Boolean {
        return false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isRecording = true
                mStartTime = System.currentTimeMillis()
                handler.sendEmptyMessageDelayed(MSG_START_LONG_RECORD, LONG_CLICK_MIN_TIME)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                mEndTime = System.currentTimeMillis()
                if (mEndTime - mStartTime < LONG_CLICK_MIN_TIME) {
                    //Long press the action time too short.
                    if (handler.hasMessages(MSG_START_LONG_RECORD)) {
                        handler.removeMessages(MSG_START_LONG_RECORD)
                    }
                    isRecording = false
                    if (onRecordListener != null) {
                        onRecordListener!!.onShortClick()
                    }

                } else {
                    if (mProgressAni != null && mProgressAni!!.currentPlayTime / 1000 < mMinTime) {
                        //The recording time is less than the minimum recording time.
                        GlobalScope.launch(Dispatchers.Main) {
                            isRecording = true
                            delay(mMinTime.toLong())
                            isRecording = false
                            mProgressAni!!.cancel()
                            mExCircleRadius = mInitExCircleRadius
                            mInnerCircleRadius = mInitInnerRadius
                            startAnimation(
                                mInitExCircleRadius * excicleMagnification,
                                mInitExCircleRadius,
                                mInitInnerRadius * innerCircleShrinks,
                                mInitInnerRadius
                            )
                            if (onRecordListener != null) {
                                onRecordListener!!.OnFinish()
                            }
                        }

                    } else {
                        isRecording = false
                        if (onRecordListener != null) {
                            onRecordListener!!.OnFinish()
                        }
                        mExCircleRadius = mInitExCircleRadius
                        mInnerCircleRadius = mInitInnerRadius
                        startAnimation(
                            mInitExCircleRadius * excicleMagnification,
                            mInitExCircleRadius,
                            mInitInnerRadius * innerCircleShrinks,
                            mInitInnerRadius
                        )
                        mProgressAni!!.cancel()
                    }

                }
            }
            MotionEvent.ACTION_MOVE -> {
            }
        }
        return true
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if(bigObjAni!=null){
            if(bigObjAni!!.isStarted){
                bigObjAni?.cancel()
            }
            bigObjAni = null
        }

        if(smallObjAni!=null){
            if(smallObjAni!!.isStarted){
                smallObjAni?.cancel()
            }
            smallObjAni = null
        }
    }


    /**
     * 设置外圆 内圆缩放动画
     *
     * @param bigStart
     * @param bigEnd
     * @param smallStart
     * @param smallEnd
     */
    private var  bigObjAni:ValueAnimator? = null
    private var  smallObjAni:ValueAnimator? = null
    private fun startAnimation(bigStart: Float, bigEnd: Float, smallStart: Float, smallEnd: Float) {
        bigObjAni = ValueAnimator.ofFloat(bigStart, bigEnd)
        bigObjAni?.duration = 150
        bigObjAni?.addUpdateListener { animation ->
            mExCircleRadius = animation.animatedValue as Float
            invalidate()
        }

        smallObjAni = ValueAnimator.ofFloat(smallStart, smallEnd)
        smallObjAni?.duration = 150
        smallObjAni?.addUpdateListener { animation ->
            mInnerCircleRadius = animation.animatedValue as Float
            invalidate()
        }

        smallObjAni?.start()
        bigObjAni?.start()

        bigObjAni?.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                //开始绘制圆形进度
                if (isRecording) {
                    startAniProgress()
                }

            }


            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })

    }

    /**
     * 开始圆形进度值动画
     */
    private fun startAniProgress() {
        if (mProgressAni == null) {
            return
        }
        mProgressAni!!.start()
        mProgressAni!!.addUpdateListener { animation ->
            mCurrentProgress = animation.animatedValue as Float
            invalidate()
        }

        mProgressAni!!.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator) {
                isRecording = false
                mCurrentProgress = 0f
                invalidate()
            }
        })
    }

    /**
     * 设置最大录制时间
     *
     * @param mMaxTime 最大录制时间 秒
     */
    fun setMaxTime(mMaxTime: Int) {
        this.mMaxTime = mMaxTime
    }

    /**
     * 设置最小录制时间
     *
     * @param mMinTime 最小录制时间 秒
     */
    fun setMinTime(mMinTime: Int) {
        this.mMinTime = mMinTime
    }

    /**
     * 设置外圆半径
     *
     * @param mExCircleRadius 外圆半径
     */
    fun setExCircleRadius(mExCircleRadius: Float) {
        this.mExCircleRadius = mExCircleRadius
    }

    /**
     * 设置内圆半径
     *
     * @param mInnerCircleRadius 内圆半径
     */
    fun setInnerCircleRadius(mInnerCircleRadius: Float) {
        this.mInnerCircleRadius = mInnerCircleRadius
    }

    /**
     * 设置颜色外圆颜色
     *
     * @param mAnnulusColor
     */
    fun setAnnulusColor(mAnnulusColor: Int) {
        this.mAnnulusColor = mAnnulusColor
        mExCirclePaint!!.color = mAnnulusColor
    }

    /**
     * 设置进度圆环颜色
     *
     * @param mProgressColor
     */
    fun setProgressColor(mProgressColor: Int) {
        this.mProgressColor = mProgressColor
        mProgressPaint!!.color = mProgressColor
    }

    /**
     * 设置内圆颜色
     *
     * @param mInnerCircleColor
     */
    fun setInnerCircleColor(mInnerCircleColor: Int) {
        this.mInnerCircleColor = mInnerCircleColor
        mInnerCirclePaint!!.color = mInnerCircleColor
    }

    fun setOnRecordListener(onRecordListener: OnRecordListener) {
        this.onRecordListener = onRecordListener
    }

    override fun setOnClickListener(l: View.OnClickListener?) {
        super.setOnClickListener(l)
    }

    abstract class OnRecordListener {

        /**
         * 点击拍照
         */
        abstract fun onShortClick()

        /**
         * 开始录制
         */
        abstract fun OnRecordStartClick()

        /**
         * 录制结束
         *
         */
        abstract fun OnFinish()
    }


    internal class MHandler(controlView: RecordButton) : android.os.Handler() {

        private var weakReference: WeakReference<RecordButton>? = null

        init {
            weakReference = WeakReference(controlView)
        }

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (weakReference == null || weakReference!!.get() == null) return
            val videoControlView = weakReference!!.get()
            when (msg.what) {
                MSG_START_LONG_RECORD -> {
                    if (videoControlView!!.onRecordListener != null) {
                        videoControlView.onRecordListener!!.OnRecordStartClick()
                    }
                    //内外圆动画，内圆缩小，外圆放大
                    videoControlView.startAnimation(
                        videoControlView.mExCircleRadius,
                        videoControlView.mExCircleRadius * videoControlView.excicleMagnification,
                        videoControlView.mInnerCircleRadius,
                        videoControlView.mInnerCircleRadius * videoControlView.excicleMagnification
                    )
                }
            }
        }
    }

    companion object {

        private val TAG = RecordButton::class.java.name
        /**
         * 视频录制最长时间 秒
         */
        private val VIDEO_RECORD_DEFAULT_MAX_TIME = 10

        /**
         * 长按录制
         */
        private val MSG_START_LONG_RECORD = 0x1
    }
}
