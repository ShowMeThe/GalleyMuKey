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

/**
 * PackageName: example.ken.com.library.widget
 * Author : jiaqi Ye
 * Date : 2018/10/23
 * Time : 9:50
 */
class AutoRecyclerView : RecyclerView {

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
    private var hasRunning = false
    private var leftHeight = 0
    private var countWidth = 0.0
    private var interpolator = FastOutLinearInInterpolator()
    private var interpolator2 = FastOutSlowInInterpolator()
    private var itemDecoration = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun onScrolled(dx: Int, dy: Int) {
        super.onScrolled(dx, dy)

        val layoutManager = layoutManager
        if (layoutManager is GridLayoutManager) {
            layoutManagerType = TYPE_GRID_LAYOUT
            lastPosition = layoutManager.findLastVisibleItemPosition()
        } else if (layoutManager is LinearLayoutManager) {
            layoutManagerType = TYPE_LINEAR_LAYOUT
            lastPosition = layoutManager.findLastVisibleItemPosition()
            findFirstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        } else if (layoutManager is StaggeredGridLayoutManager) {
            layoutManagerType = TYPE_STAGGERED_GRID_LAYOUT
            staggeredGridLayoutManager = layoutManager
            if (lastPositions == null) {
                lastPositions = IntArray(staggeredGridLayoutManager!!.spanCount)
            }
            staggeredGridLayoutManager!!.findLastVisibleItemPositions(lastPositions)
            lastPosition = findMax(lastPositions!!)

        } else {
            throw RuntimeException("layoutManager not support")
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
        if(state == SCROLL_STATE_IDLE){

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
                if (!isLoading && lastPosition >= itemCount - 1 && visibleItemCount > 0 && state == RecyclerView.SCROLL_STATE_IDLE) {
                    if (loadingMore != null) {
                        isLoading = true
                        loadingMore?.invoke()
                    }
                }
            }
        }


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
