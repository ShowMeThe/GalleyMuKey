package showmethe.github.kframework.widget.common

import android.content.Context
import androidx.core.widget.NestedScrollView
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import showmethe.github.kframework.util.widget.ScreenSizeUtil
import java.lang.ref.WeakReference


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
    private var refresh : WeakReference<SwipeRefreshLayout>? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}


    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)


    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {

        if(t > 50){ //下滑取消refresh动画
            refresh?.get()?.isRefreshing = false
        }

        if (getChildAt(0).height <=  t  +   height * 1.45) {
            if (!isLoading && canLoadMore) {
                if (loadingMore != null) {
                    isLoading = true
                    loadingMore?.invoke()
                }
            }
        }
    }

    fun hideWhenScrolling(refreshLayout: SwipeRefreshLayout){
        this.refresh = WeakReference(refreshLayout)
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
