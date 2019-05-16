package showmethe.github.kframework.widget.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity

/**
 * example.ken.com.library.widget
 * YeJq
 * 2018/12/17
 */

/**
 * 左边图片和文字一起居中
 */
class CenterTDRadioButton : androidx.appcompat.widget.AppCompatRadioButton {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val drawables = compoundDrawables
        val drawable = drawables[0]
        val gravity = gravity
        var left = 0
        if (gravity == Gravity.CENTER) {
            left = (width.toFloat() - drawable.intrinsicWidth.toFloat() - paint.measureText(text.toString())).toInt() / 2
        }
        drawable.setBounds(left, 0, left + drawable.intrinsicWidth, drawable.intrinsicHeight)
    }


}
