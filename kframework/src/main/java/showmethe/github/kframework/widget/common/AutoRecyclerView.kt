package showmethe.github.kframework.widget.common

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import android.util.AttributeSet
import android.util.Log
import android.view.animation.AnticipateInterpolator
import android.widget.RelativeLayout
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import showmethe.github.kframework.R
import java.lang.ref.WeakReference

/**
 * PackageName: example.ken.com.library.widget
 * Author : jiaqi Ye
 * Date : 2018/10/23
 * Time : 9:50
 */
class AutoRecyclerView @JvmOverloads constructor(
    context: Context,var  attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var isLoading: Boolean = false
    private var loadingMore: (()->Unit)? = null
    private var canLoadMore = true
    private var layoutManagerType = -1
    private var itemCount: Int = 0
    private var lastPosition: Int = 0
    private var lastPositions: IntArray? = null
    private var staggeredGridLayoutManager: StaggeredGridLayoutManager? = null
    private var findFirstVisibleItemPosition = 0
    private var previousTotal: Int = 0
    private var scrollOffset = 0
    private var refresh : WeakReference<SwipeRefreshLayout>? = null
    /**
     * 防止多次执行滑动
     */
    private var hasRunning = false
    /**
     * 滑动距离 里完整区域 剩余距离
     */
    private var leftHeight = 0
    /**
     * 整体宽度或高度
     */
    private var countWidth = 0.0

    private var interpolator = FastOutLinearInInterpolator()
    private var interpolator2 = FastOutSlowInInterpolator()
    /**
     * 间距
     */
    private var itemDecoration = 0
    /**
     * 当layoutManager为LinearLayoutManager 时候触发自动寻找临近position
     */
    private var autoFix = true


    init {
        initType()
    }

    private fun initType(){
        val array = context.obtainStyledAttributes(attrs,R.styleable.AutoRecyclerView)
        autoFix = array.getBoolean(R.styleable.AutoRecyclerView_autoFix,false)
        array.recycle()
    }


    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)

        if(dy > 50){ //下滑取消refresh动画
            refresh?.get()?.isRefreshing = false
        }

        val layoutManager = layoutManager
        when (layoutManager) {
            is GridLayoutManager -> {
                layoutManagerType = TYPE_GRID_LAYOUT
                lastPosition = layoutManager.findLastVisibleItemPosition()
            }
            is LinearLayoutManager -> {
                layoutManagerType = TYPE_LINEAR_LAYOUT
                lastPosition = layoutManager.findLastVisibleItemPosition()
                findFirstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

            }
            is StaggeredGridLayoutManager -> {
                layoutManagerType = TYPE_STAGGERED_GRID_LAYOUT
                staggeredGridLayoutManager = layoutManager
                if (lastPositions == null) {
                    lastPositions = IntArray(staggeredGridLayoutManager!!.spanCount)
                }
                staggeredGridLayoutManager!!.findLastVisibleItemPositions(lastPositions)
                lastPosition = findMax(lastPositions!!)

            }
            else -> throw RuntimeException("layoutManager not support")
        }

        itemCount = layoutManager.itemCount

        if (layoutManagerType == TYPE_LINEAR_LAYOUT) {
            if (canLoadMore) {
                if (!isLoading && lastPosition >= itemCount - 5 && itemCount > 0 && dy > 0) {
                    if (loadingMore != null) {
                        isLoading = true
                        loadingMore?.invoke()
                    }
                }
            }
        }
    }

    //找到数组中的最大值
    private fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (value in lastPositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }


    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)

        val layoutManager = layoutManager
        val visibleItemCount = layoutManager!!.childCount
        val totalItemCount = layoutManager.itemCount


        /**
         * 滑动停止后还能继续滚动时候，寻找临近位置自动移动到最近的position
         */
        if(itemCount> 1 && state == SCROLL_STATE_IDLE && autoFix){
            val linearLayoutManager = (layoutManager as LinearLayoutManager)
            val firstPos = linearLayoutManager.findFirstVisibleItemPosition()
            val firstView = linearLayoutManager.findViewByPosition(firstPos)

            if(linearLayoutManager.orientation == VERTICAL){
                scrollOffset = firstPos * firstView!!.height - firstView.top
                leftHeight = scrollOffset % firstView.measuredHeight
                countWidth = firstView.measuredHeight * 0.5
            }else{
                scrollOffset = firstPos * firstView!!.width - firstView.left
                leftHeight = scrollOffset % firstView.measuredWidth
                countWidth = firstView.measuredWidth * 0.5
            }
            /**
             * 计算间距
             */
            if(itemDecorationCount>0 && itemCount>1 && itemDecoration == 0 ){
                itemDecoration = if(layoutManager.orientation == VERTICAL){
                    (layoutManager.findViewByPosition(firstPos+1)!!.top -
                            layoutManager.findViewByPosition(firstPos)!!.bottom )
                }else{
                    (layoutManager.findViewByPosition(firstPos+1)!!.left -
                            layoutManager.findViewByPosition(firstPos)!!.right )
                }
            }

            if(leftHeight >= countWidth){
                if(leftHeight>0 && !hasRunning){

                    if(layoutManager.orientation == VERTICAL){
                        smoothScrollBy(0,linearLayoutManager.findViewByPosition(firstPos+1)!!.top
                                - itemDecoration,interpolator)
                    }else{
                        smoothScrollBy(linearLayoutManager.findViewByPosition(firstPos+1)!!.left
                                - itemDecoration,0,interpolator)
                    }
                    hasRunning = true
                }
            }else{
                if(layoutManager.orientation == VERTICAL){
                    smoothScrollBy(0,linearLayoutManager.findViewByPosition(firstPos)!!.top -
                            itemDecoration - (firstView.layoutParams as LayoutParams).topMargin,interpolator2)
                }else{
                    smoothScrollBy(linearLayoutManager.findViewByPosition(firstPos)!!.left
                            - itemDecoration  - (firstView.layoutParams as LayoutParams).leftMargin,0,interpolator2)
                }
                hasRunning = true
            }
        }else{
            hasRunning = false
        }

        if (isLoading) {
            //和之前数据的数目进行比较，判断是否加载完毕，重置加载状态
            if (totalItemCount > previousTotal) {
                // /加载更多结束
                isLoading = false
                previousTotal = totalItemCount
            } else if (totalItemCount < previousTotal) {
                //用户刷新结束
                previousTotal = totalItemCount
                isLoading = false
            }
        }

        if (layoutManagerType == TYPE_GRID_LAYOUT || layoutManagerType == TYPE_STAGGERED_GRID_LAYOUT) {
            if (canLoadMore) {
                if (!isLoading && lastPosition >= itemCount - 1 && visibleItemCount > 0 && state == SCROLL_STATE_IDLE) {
                    if (loadingMore != null) {
                        isLoading = true
                        loadingMore?.invoke()
                    }
                }
            }
        }


    }


    fun hideWhenScrolling(refreshLayout: SwipeRefreshLayout){
        this.refresh = WeakReference(refreshLayout)
    }


    fun setOnLoadMoreListener(loadingMore: ()->Unit) {
        this.loadingMore = loadingMore
    }


    fun setEnableLoadMore(boolean: Boolean){
        this.canLoadMore = boolean
    }


    companion object {

        private val TYPE_LINEAR_LAYOUT = 500
        private val TYPE_GRID_LAYOUT = 501
        private val TYPE_STAGGERED_GRID_LAYOUT = 502
    }
}
