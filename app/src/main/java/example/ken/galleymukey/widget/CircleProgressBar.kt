package example.ken.galleymukey.widget

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.annotation.FloatRange
import androidx.core.content.ContextCompat
import example.ken.galleymukey.R
import showmethe.github.kframework.util.widget.DisplayUtil
import java.text.DecimalFormat

class CircleProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr){

    private var mPaint = Paint()
    private var textPaint = Paint()
    private var strokeWidth = 0f
    private var rectF = RectF()
    private var defaultRoundColor =  -1
    private var runningRoundColor = -1
    private  var progressType = Paint.Style.STROKE
    private var progress = 0f
    private var decimalFormat  = DecimalFormat("#.00")


    init {
        init(context,attrs)
    }

    private  fun init(context: Context,attrs: AttributeSet?){
        initAttr(context,attrs)
        mPaint.color = defaultRoundColor
        mPaint.isAntiAlias = true
        mPaint.style = progressType
        mPaint.strokeWidth = strokeWidth

        textPaint.textSize = DisplayUtil.dp2px(context,strokeWidth).toFloat()
        textPaint.color = defaultRoundColor
        textPaint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val mWidth = MeasureSpec.getSize(widthMeasureSpec)
        val mHeight = MeasureSpec.getSize(heightMeasureSpec)

        if(mWidth > mHeight){
            rectF.set(strokeWidth,(mHeight - mWidth)/2 + strokeWidth,mWidth - strokeWidth,(mHeight + mWidth)/2 + strokeWidth)
        }else if (mWidth > mHeight){
            rectF.set((mWidth - mHeight)/2 + strokeWidth,strokeWidth,(mWidth + mHeight)/2 + strokeWidth,mHeight - strokeWidth)
        }else{
            rectF.set(strokeWidth, strokeWidth, mWidth - strokeWidth, mHeight - strokeWidth)
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.color = defaultRoundColor
        if(progress < 360) {
            canvas.drawArc(rectF, 270 + progress, 360 - progress, progressType ==  Paint.Style.FILL, mPaint)
        }
        mPaint.color = runningRoundColor
        canvas.drawArc(rectF, 270f, progress, progressType== Paint.Style.FILL, mPaint)


        val top = textPaint.fontMetrics.top
        val bottom = textPaint.fontMetrics.bottom
        val baseLine = (rectF.centerY() - top/2 - bottom/2)
        textPaint.textAlign = Paint.Align.CENTER
        canvas.drawText("${decimalFormat.format(progress/360f*100)}%",rectF.centerX(),baseLine,textPaint)

    }


    fun updateProgress(@FloatRange(from = 1.0, to = 100.0) progress:Float){
        this.progress  = (progress/100f)*360
        postInvalidate()
    }

    private  fun initAttr(context: Context,attrs: AttributeSet?){
        val array = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar)
        strokeWidth = array.getDimension(R.styleable.CircleProgressBar_strokeWidth, Resources.getSystem().displayMetrics.density * 10f)
        defaultRoundColor = array.getColor(R.styleable.CircleProgressBar_defaultColor,ContextCompat.getColor(context,R.color.colorAccent))
        runningRoundColor = array.getColor(R.styleable.CircleProgressBar_runningColor,ContextCompat.getColor(context,R.color.colorPrimaryDark))
        array.recycle()
    }


}