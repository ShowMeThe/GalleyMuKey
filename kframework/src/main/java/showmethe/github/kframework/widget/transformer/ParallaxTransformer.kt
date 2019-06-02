package showmethe.github.kframework.widget.transformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2

/**
 * showmethe.github.kframework.widget.transformer
 * cuvsu
 * 2019/6/2
 */
class ParallaxTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        val width = page.width
        if (position < -1) {
            page.scrollX = (width.toDouble() * 0.75 * -1.0).toInt()
        } else if (position <= 1) {
            if (position < 0) {
                page.scrollX = (width.toDouble() * 0.75 * position.toDouble()).toInt()
            } else {
                page.scrollX = (width.toDouble() * 0.75 * position.toDouble()).toInt()
            }
        } else {
            page.scrollX = (width * 0.75).toInt()
        }
    }
}
