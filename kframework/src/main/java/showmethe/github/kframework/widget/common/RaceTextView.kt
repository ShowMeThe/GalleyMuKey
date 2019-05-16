package showmethe.github.kframework.widget.common

import android.content.Context
import android.graphics.Rect
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
 * showmethe.github.kframework.widget.common
 * ken
 * 2019/4/2
 */

/**
 * 字体内容过多，滚动跑马灯MARQUEE形式显示
 */
class RaceTextView @JvmOverloads constructor(context: Context,attrs: AttributeSet? = null,defStyleAttr: Int = 0)
    : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        init()
    }

    private fun init() {
        setSingleLine(true)
        ellipsize = TextUtils.TruncateAt.MARQUEE
        isSelected = true
        marqueeRepeatLimit = -1
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        if (focused) {
            super.onFocusChanged(focused, direction, previouslyFocusedRect)
        }
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        if (hasWindowFocus) {
            super.onWindowFocusChanged(hasWindowFocus)
        }
    }
}
