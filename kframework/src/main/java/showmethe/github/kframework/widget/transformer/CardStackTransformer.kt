package showmethe.github.kframework.widget.transformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2

/**
 * showmethe.github.kframework.widget.transformer
 *
 *
 * 2019/6/13
 */
class CardStackTransformer  constructor(var offset:Int = 60) : ViewPager2.PageTransformer{

    override fun transformPage(page: View, position: Float) {
        if (position <= 0) {
            page.translationX = -position * page.width
            page.rotation = -45 * position;
        }else{
            page.rotation = 45 * position;
            page.translationX = (page.width / 2 * position);
        }
    }


}
