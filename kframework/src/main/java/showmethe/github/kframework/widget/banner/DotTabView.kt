package showmethe.github.kframework.widget.banner

import android.content.Context

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout

import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import showmethe.github.kframework.R
import showmethe.github.kframework.util.widget.dip2px


/**
 *
 * Author : jiaqi Ye
 * Date : 2018/5/31
 * Time : 11:32
 */

/**
 * ViewPage底部圆点导航点
 */
class DotTabView @JvmOverloads constructor(private val mContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(mContext, attrs, defStyleAttr) {

    private var viewPager2: ViewPager2? = null
    private var viewPager: ViewPager? = null
    private var mDotDis: Int = 0//小圆点的距离
    private var divideWidth: Int = 0
    private var selectRadius = default_radius
    private var unSelectRadius = default_select_radius
    private val selectDot: ImageView = ImageView(mContext)

    fun setViewPager2(viewPager: ViewPager2?, tabCount: Int, dWidth: Int, selectColor: Int, unSelectColor: Int) {
        if (viewPager == null) return
        this.viewPager2 = viewPager

        createView(tabCount, dWidth, selectColor, unSelectColor)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
               calculate(position, positionOffset)
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }


    fun calculate(position: Int, positionOffset: Float){
        val params = selectDot
            .layoutParams as LayoutParams
        params.leftMargin = (mDotDis * positionOffset).toInt() + position * mDotDis
        selectDot.layoutParams = params
    }


    fun setViewPager(viewPager: ViewPager?, tabCount: Int, dWidth: Int, selectColor: Int, unSelectColor: Int) {
        if (viewPager == null) return
        this.viewPager = viewPager

        createView(tabCount, dWidth, selectColor, unSelectColor)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                calculate(position, positionOffset)
            }

            override fun onPageSelected(position: Int) {

            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }


    private fun createView(tabCount: Int, dWidth: Int, selectColor: Int, unSelectColor: Int) {

        divideWidth = if (dWidth <= 0) {
            dip2px(mContext, 10f)
        } else {
            dip2px(mContext, dWidth.toFloat())
        }
        removeAllViews()
        init(mContext, tabCount, unSelectColor)

        selectDot.minimumHeight = selectRadius
        selectDot.minimumWidth = selectRadius

        val select = ContextCompat.getDrawable(context, R.drawable.banner_selet)
        select!!.setColorFilter(selectColor, PorterDuff.Mode.SRC_ATOP)
        selectDot.setImageDrawable(select)
        val param = LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

        selectDot.layoutParams = param

        selectDot.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                selectDot.viewTreeObserver.removeOnGlobalLayoutListener(this)
                //计算得到小圆点距离
                mDotDis = getChildAt(1).left - getChildAt(0)
                        .left
            }
        })
        addView(selectDot, param)
    }


    fun setIndicatorRadius(dp1: Int, dp2: Int) {
        selectRadius = dp1
        unSelectRadius = dp2
    }


    private fun init(context: Context, tabCount: Int, unSelectColor: Int) {

        for (i in 0 until tabCount) {
            val unSelectDot = ImageView(context)
            val unselect = ContextCompat.getDrawable(getContext(), R.drawable.banner_unselect)
            unselect!!.setColorFilter(unSelectColor, PorterDuff.Mode.SRC_ATOP)
            unSelectDot.setImageDrawable(unselect)
            unSelectDot.minimumHeight = unSelectRadius
            unSelectDot.minimumWidth = unSelectRadius
            val params = LayoutParams(LinearLayout
                    .LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            if (i > 0) {
                params.leftMargin = divideWidth + (i - 1) * divideWidth//设置圆点边距
            }
            unSelectDot.layoutParams = params
            addView(unSelectDot, params)
        }
    }

    companion object {
        private const val default_radius = 15
        private const val default_select_radius = 16
    }

}
