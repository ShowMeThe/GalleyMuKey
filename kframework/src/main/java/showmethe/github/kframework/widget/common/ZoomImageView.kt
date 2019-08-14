package showmethe.github.kframework.widget.common

import android.content.Context
import android.graphics.Matrix
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import kotlin.math.min
import android.R.attr.bottom
import android.R.attr.top
import android.graphics.PointF
import android.graphics.RectF
import android.view.*
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import showmethe.github.kframework.util.widget.ScreenSizeUtil
import kotlin.math.abs
import kotlin.math.sqrt


class ZoomImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr), View.OnTouchListener, ScaleGestureDetector.OnScaleGestureListener,
    ViewTreeObserver.OnGlobalLayoutListener {


    private var mLastPointerCount = 0
    private var mLastPointerX = 0f
    private var mLastPointerY = 0f


    private val SCALE_MIN = 0.8f

    /**
     * 最大放大倍数
     */
    private val SCALE_MAX = 4.0f

    /**
     * 默认缩放
     */
    private var initScale = 1.0f

    /**
     * 手势检测
     */
    private val scaleGestureDetector: ScaleGestureDetector

    private var scaleMatrix = Matrix()
    private var isCanDrag = false

    private var isCheckLeftAndRight = false
    private var isCheckTopAndBottom = false
    private val mTouchSlop: Float
    private var once = true


    private var offsetY = 0f
    private var dPoint = PointF()
    private var imageX = 0f
    private var imageY = 0f



    /**
     * 处理矩阵的9个值
     */
    var martixValue = FloatArray(9)

    init {
        scaleType = ScaleType.MATRIX
        scaleGestureDetector = ScaleGestureDetector(context, this)
        setOnTouchListener(this)
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop.toFloat()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewTreeObserver.removeOnGlobalLayoutListener(this)
    }

    /**
     * 获取当前缩放比例
     */
    fun getScale(): Float {
        scaleMatrix.getValues(martixValue)
        return martixValue[Matrix.MSCALE_X]
    }


    override fun onScale(detector: ScaleGestureDetector): Boolean {
        val scale = getScale()
        var scaleFactor = detector.scaleFactor
        if (drawable == null)
            return true

        if (scale < SCALE_MAX && scaleFactor > 1.0f || scale > SCALE_MIN && scaleFactor < 1.0f) {

            if (scaleFactor * scale < SCALE_MIN){
                scaleFactor = SCALE_MIN / scale
                Log.e("ZoomImageView","缩小到了边界")
            }


            if (scaleFactor * scale > SCALE_MAX){
                scaleFactor = SCALE_MAX / scale
                Log.e("222222222222","放大到了边界")
            }



            //设置缩放比例
            scaleMatrix.postScale(scaleFactor, scaleFactor, detector.focusX, detector.focusY)
            checkBorderAndCenterWhenScale()
            imageMatrix = scaleMatrix
        }
        return true
    }


    /**
     * 在缩放时，控制范围
     */
    private fun checkBorderAndCenterWhenScale() {
        val matrix = scaleMatrix
        val rectF = RectF()
        val d = drawable
        if (d != null) {
            rectF.set(0f, 0f, d.intrinsicWidth.toFloat(), d.intrinsicHeight.toFloat())
            matrix.mapRect(rectF)
        }

        var deltaX = 0f
        var deltaY = 0f
        val width = width
        val height = height
        // 如果宽或高大于屏幕，则控制范围
        if (rectF.width() >= width) {
            if (rectF.left > 0) {
                deltaX = -rectF.left
            }
            if (rectF.right < width) {
                deltaX = width - rectF.right
            }
        }
        if (rectF.height() >= height) {
            if (rectF.top > 0) {
                deltaY = -rectF.top
            }
            if (rectF.bottom < height) {
                deltaY = height - rectF.bottom
            }
        }
        // 如果宽或高小于屏幕，则让其居中
        if (rectF.width() < width) {
            deltaX = width * 0.5f - rectF.right + 0.5f * rectF.width()
        }
        if (rectF.height() < height) {
            deltaY = height * 0.5f - rectF.bottom + 0.5f * rectF.height()
        }
        scaleMatrix.postTranslate(deltaX, deltaY)
    }


    private val matrixRectF: RectF
        get() {
            val matrix = scaleMatrix
            val rect = RectF()
            val drawable = drawable
            if (null != drawable) {
                rect.set(0f, 0f, drawable.intrinsicWidth.toFloat(), drawable.intrinsicHeight.toFloat())
                matrix.mapRect(rect)
            }
            return rect
        }

    override fun onTouch(p0: View?, event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)

        var pointerX = 0f
        var pointerY = 0f
        val pointerCount = event.pointerCount
        for (i in 0 until pointerCount) {
            pointerX += event.getX(i)
            pointerY += event.getY(i)
        }
        pointerX /= pointerCount.toFloat()
        pointerY /= pointerCount.toFloat()
        if (mLastPointerCount != pointerCount) {

            isCanDrag = false
            mLastPointerX = pointerX
            mLastPointerY = pointerY
        }
        mLastPointerCount = pointerCount
        val rectF = matrixRectF
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (parent is ViewPager || parent is ViewPager2) {
                    if (rectF.width() - width > 0.01 || rectF.height() - height > 0.01) {
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                }


                if(pointerCount == 1){
                    dPoint.set(event.rawX,event.rawY)
                }

            }

            MotionEvent.ACTION_MOVE -> {
                if (parent is ViewPager || parent is ViewPager2) {
                    if (rectF.width() - width > 0.01 || rectF.height() - height > 0.01) {
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                }
                var dx = pointerX - mLastPointerX
                var dy = pointerY - mLastPointerY
                if (!isCanDrag) {
                    isCanDrag = isMoveAction(dx, dy)
                }
                if (isCanDrag) {
                    if (drawable != null) {
                        isCheckTopAndBottom = true
                        isCheckLeftAndRight = isCheckTopAndBottom
                        if (rectF.width() < width) {
                            isCheckLeftAndRight = false
                            dx = 0f
                        }
                        if (rectF.height() < height) {
                            isCheckTopAndBottom = false
                            dy = 0f
                        }
                        scaleMatrix.postTranslate(dx, dy)
                        checkBorderWhenTranslate()
                        imageMatrix = scaleMatrix
                    }
                }
                mLastPointerX = pointerX
                mLastPointerY = pointerY

                if(pointerCount == 1){

                    if(getScale() in 0.65f..1.00f){

                        offsetY =  event.rawY - dPoint.y
                        Log.e("2222222222","${1  - abs(offsetY/ScreenSizeUtil.height)}")

                        scaleMatrix.postTranslate(event.x - dPoint.x ,  event.y - dPoint.y)

                        /*scaleMatrix.postScale(1  - abs(offsetY/ScreenSizeUtil.height),
                            1  - abs(offsetY/ScreenSizeUtil.height),(event.x - dPoint.x),(event.y - dPoint.y))*/


                        imageMatrix = scaleMatrix
                    }


                }

            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                mLastPointerCount = 0

                /**
                 * 缩小图片 松手 恢复为原始大小
                 */
                if(getScale() < initScale){
                    val x = event.x
                    val y = event.y
                    postDelayed(SlowlyScaleRunnable(initScale, x, y), 5)
                }
            }
        }

        return true
    }


    private inner class SlowlyScaleRunnable(
        private val mTargetScale: Float,
        private val x: Float,
        private val y: Float
    ) : Runnable {
        private val min = 0.75f

        private var tmpScale = 0f

        init {

            if (min < mTargetScale) {
                tmpScale = mTargetScale
            }

        }

        override fun run() {
            scaleMatrix.postScale(tmpScale, tmpScale, x, y)
            checkBorderAndCenterWhenScale()
            imageMatrix = scaleMatrix
            val currentScale = getScale()
            if (tmpScale > 1.0f && currentScale < mTargetScale || tmpScale < 1.0f && currentScale > mTargetScale) {
                postDelayed(this, 14)
            } else {
                val scale = mTargetScale / currentScale
                scaleMatrix.postScale(scale, scale, x, y)
                checkBorderAndCenterWhenScale()
                imageMatrix = scaleMatrix
            }
        }

    }


    private fun checkBorderWhenTranslate() {

        val rect = matrixRectF
        var deltaX = 0f
        var deltaY = 0f

        val width = width
        val height = height

        if (rect.top > 0 && isCheckTopAndBottom) {
            deltaY = -rect.top
        }
        if (rect.bottom < height && isCheckTopAndBottom) {
            deltaY = height - rect.bottom
        }

        if (rect.left > 0 && isCheckLeftAndRight) {
            deltaX = -rect.left
        }
        if (rect.right < width && isCheckLeftAndRight) {
            deltaX = width - rect.right
        }

        scaleMatrix.postTranslate(deltaX, deltaY)

    }


    private fun isMoveAction(dx: Float, dy: Float): Boolean {
        return sqrt((dx * dx + dy * dy).toDouble()) > mTouchSlop
    }


    override fun onScaleEnd(p0: ScaleGestureDetector?) {
        if (!once)
            return
        val d = drawable ?: return

        //获取imageview宽高
        val width = width
        val height = height

        //获取图片宽高
        val imgWidth = d.intrinsicWidth
        val imgHeight = d.intrinsicHeight

        var scale = 1.0f

        //如果图片的宽或高大于屏幕，缩放至屏幕的宽或者高
        if (imgWidth > width && imgHeight <= height)
            scale = width.toFloat() / imgWidth

        if (imgHeight > height && imgWidth <= width)
            scale = height.toFloat() / imgHeight

        //如果图片宽高都大于屏幕，按比例缩小
        if (imgWidth > width && imgHeight > height)
            scale = min(imgWidth.toFloat() / width, imgHeight.toFloat() / height)

        initScale = scale
        //将图片移动至屏幕中心
        scaleMatrix.postTranslate(((width - imgWidth) / 2).toFloat(), ((height - imgHeight) / 2).toFloat())
        scaleMatrix.postScale(scale, scale, (getWidth() / 2).toFloat(), (getHeight() / 2).toFloat())
        imageMatrix = scaleMatrix
        once = false
    }


    override fun onGlobalLayout() {

    }

    override fun onScaleBegin(p0: ScaleGestureDetector?): Boolean {
        return true
    }


}