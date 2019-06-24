package showmethe.github.kframework.widget.common

import android.content.Context
import androidx.core.widget.NestedScrollView
import android.util.AttributeSet
import android.view.View

import showmethe.github.kframework.util.widget.ScreenSizeUtil


/**
 * PackageName: example.ken.com.library.widget
 * Author : jiaqi Ye
 * Date : 2018/10/23
 * Time : 11:58
 */
class AutoNestedScrollView : NestedScrollView {


    private var isLoading: Boolean = false
    private var loadingMore: (()->Unit)? = null
    private var canLoadMore = true

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}


    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)


    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {

        if (t >= getChildAt(0).measuredHeight -  measuredHeight  - ScreenSizeUtil.getHeight(context)) {

            if (!isLoading && canLoadMore) {
                if (loadingMore != null) {
                    isLoading = true
                    loadingMore?.invoke()
                }
            }
        }
    }


    fun setOnLoadMore(loadingMore: ()->Unit) {
        this.loadingMore = loadingMore
    }

    fun finishLoading() {
        isLoading = false
    }

    fun setEnableLoadMore(canLoadMore: Boolean) {
        this.canLoadMore = canLoadMore
    }




}
