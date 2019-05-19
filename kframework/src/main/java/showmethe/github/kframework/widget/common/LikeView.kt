package showmethe.github.kframework.widget.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.R.attr.centerY
import android.R.attr.centerX
import android.R.attr.centerY
import android.R.attr.centerX
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.res.TypedArray
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.animation.*
import androidx.core.content.ContextCompat
import showmethe.github.kframework.R


/**
 * com.example.ewhale.cartshopmall.widget
 *  ken
 * 2019/1/22
 **/
class LikeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    private val DOTS_COUNT = 8
    private val DOTS_POSITION_ANGLE = 360 / DOTS_COUNT
    private var colors : Array<Int>? = null
    private var currentProgress = 0f
    private var centerX: Int = 0
    private var centerY: Int = 0
    private var maxDotSize: Float = 0.toFloat()
    private var currentRadius = 0f
    private var currentDotSize = 0f
    private val circlePaint = Paint()
    private val bitmapPaint = Paint()
    private var maxDotsRadius: Float = 0.toFloat()
    private var  dotsAnimator : ObjectAnimator? = null
    private val rect=  Rect()
    private var like : Bitmap? = null
    private var unlike : Bitmap? = null
    private var mHeight = 0
    private var mWidth = 0
    private var isLike = false

    val set  =  AnimatorSet();

    init {
        init(context,attrs)
    }

    private fun init(context: Context,attrs: AttributeSet?) {
        colors = arrayOf(Color.parseColor("#f44336"))
        circlePaint.style = Paint.Style.FILL
        initAnim()
        initType(context,attrs)
    }

    private fun initType(context: Context, attrs: AttributeSet?) {
        val arrary = context.obtainStyledAttributes(attrs!!,R.styleable.LikeView)
        isLike = arrary.getBoolean(R.styleable.LikeView_like_state,false)
        arrary.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mHeight = MeasureSpec.getSize(heightMeasureSpec)
        mWidth = MeasureSpec.getSize(widthMeasureSpec)
        setLike(isLike,false)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2
        centerY = h / 2
        //粒子半径
        maxDotSize = 5f
        //最大半径
        maxDotsRadius = w / 2 - maxDotSize * 4
        like = BitmapFactory.decodeResource(resources, R.drawable.heart)
        unlike = BitmapFactory.decodeResource(resources,R.drawable.heart_1)

        val widthDis = mWidth/4
        val heightDis = mHeight/4
        rect.set(mWidth/2 - widthDis,mHeight/2 - heightDis,mWidth/2 + widthDis,mHeight/2 + heightDis)
    }

    override fun onDraw(canvas: Canvas) {
        drawOuterDotsFrame(canvas)
        if(isLike){
            canvas.drawBitmap(like,null,rect,bitmapPaint)
        }else{
            canvas.drawBitmap(unlike,null,rect,bitmapPaint)
        }
    }

    fun setLike(boolean: Boolean,state: Boolean){
        isLike = boolean
        if(isLike && state){
            play()
        }
        postInvalidate()
    }

    private fun drawOuterDotsFrame(canvas: Canvas) {
        for (i in 0 until DOTS_COUNT) {
            val cX = (centerX + currentRadius * Math.cos(i * DOTS_POSITION_ANGLE * Math.PI / 180)) .toFloat()
            val cY = (centerY + currentRadius * Math.sin(i * DOTS_POSITION_ANGLE * Math.PI / 180)).toFloat()
            circlePaint.color = colors!![0]
            canvas.drawCircle(cX, cY, currentDotSize, circlePaint)
        }
    }

    private fun setCurrentProgress(currentProgress: Float) {
        this.currentProgress = currentProgress
        updateOuterDotsPosition()
        postInvalidate()
    }

    private fun updateOuterDotsPosition() {
        if (currentProgress < 0.3f) {
            this.currentRadius = mapValueFromRangeToRange(currentProgress, 0.0f, 0.3f, 0.0f, maxDotsRadius * 0.8f).toFloat()
        } else {
            this.currentRadius = mapValueFromRangeToRange(currentProgress, 0.3f, 1.0f, 0.8f * maxDotsRadius, maxDotsRadius).toFloat()
        }
        if (currentProgress < 0.7) {
            this.currentDotSize = maxDotSize
        } else {
            this.currentDotSize = mapValueFromRangeToRange(currentProgress, 0.7f, 1.0f, maxDotSize, 0.0f).toFloat()
        }
    }

    private fun mapValueFromRangeToRange(value: Float, fromLow: Float, fromHigh: Float, toLow: Float, toHigh: Float): Float {
        return toLow + (value - fromLow) / (fromHigh - fromLow) * (toHigh - toLow)
    }

   private fun initAnim(){
       dotsAnimator = ObjectAnimator.ofFloat(this, "currentProgress", 0f, 1f)
       set.play(dotsAnimator)
       set.duration = 750
       set.interpolator = AccelerateDecelerateInterpolator()
   }

   private fun play(){
        if(set.isStarted || set.isRunning){
            return
        }
        set.start()
    }

}