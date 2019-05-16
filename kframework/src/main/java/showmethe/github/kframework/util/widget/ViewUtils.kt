package showmethe.github.kframework.util.widget

import android.app.Activity
import android.content.Context
import android.os.Build
import com.google.android.material.tabs.TabLayout
import android.util.DisplayMetrics
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView

import java.lang.reflect.Field

/**
 * PackageName: example.ken.com.library.widget
 * Author : jiaqi Ye
 * Date : 2018/9/18
 * Time : 15:35
 */
object ViewUtils {
    /**
     * 反射获取mLayoutScreen 设置对象为true,全屏效果，覆盖状态栏
     * @param needFullScreen
     */
    fun fitPWOverStatusBar(mPopupWindow: PopupWindow?, needFullScreen: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                val mLayoutInScreen = PopupWindow::class.java.getDeclaredField("mLayoutInScreen")
                mLayoutInScreen.isAccessible = true
                mLayoutInScreen.set(mPopupWindow, needFullScreen)
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        }
    }
    /**
     * 用于修改tabLayout下Indicator的宽度
     * @param tabs
     */
    fun setTabIndicator(tabs: TabLayout, padding: Int) {

        tabs.post {
            try {
                //拿到tabLayout的mTabStrip属性
                val mTabStrip = tabs.getChildAt(0) as LinearLayout

                for (i in 0 until mTabStrip.childCount) {
                    val tabView = mTabStrip.getChildAt(i)
                    //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView

                    val mTextViewField: Field
                    if (Build.VERSION.SDK_INT < 28) {
                        mTextViewField = tabView.javaClass.getDeclaredField("mTextView")
                    } else {
                        mTextViewField = tabView.javaClass.getDeclaredField("textView")
                    }
                    mTextViewField.isAccessible = true
                    val mTextView = mTextViewField.get(tabView) as TextView
                    tabView.setPadding(0, 0, 0, 0)

                    //测量mTextView的宽度
                    var width = 0
                    width = mTextView.width
                    if (width == 0) {
                        mTextView.measure(0, 0)
                        width = mTextView.measuredWidth
                    }

                    //设置tab左右间距 注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                    val params = tabView.layoutParams as LinearLayout.LayoutParams
                    params.width = width
                    params.leftMargin = padding
                    params.rightMargin = padding
                    tabView.layoutParams = params
                    tabView.invalidate()
                }
            } catch (e: NoSuchFieldException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        }
    }


    fun getDisplayMetrics(context: Context): DisplayMetrics {
        val metric = DisplayMetrics()
        (context as Activity).windowManager.defaultDisplay.getMetrics(metric)
        return metric
    }

}
