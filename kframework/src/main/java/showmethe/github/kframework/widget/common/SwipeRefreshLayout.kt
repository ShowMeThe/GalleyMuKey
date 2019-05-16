package showmethe.github.kframework.widget.common

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView

import java.util.Objects

/**
 * Created by Ken on 2018/10/24.
 */
class SwipeRefreshLayout : androidx.swiperefreshlayout.widget.SwipeRefreshLayout {


    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}


    fun setColorScheme(color: Int) {
        setColorSchemeColors(ContextCompat.getColor(context, color))
    }

    override fun canChildScrollUp(): Boolean {
        return canChildScrollUp(this)
    }

    /**
     * 修整嵌套后滑动不到顶端就触发下拉问题
     * @param v
     * @return
     */
    fun canChildScrollUp(v: ViewGroup): Boolean {

        for (i in 0 until v.childCount) {
            val temp = v.getChildAt(i)
            if (temp is RecyclerView) {
                if (Objects.requireNonNull<RecyclerView.Adapter<*>>(temp.adapter).getItemCount() == 0) {
                    return false
                } else if (canRecycleViewScroll(temp)) return true
            } else if (temp is NestedScrollView) {
                if (canScrollVertically(-1)) return true
            } else if (temp is AbsListView) {
                if (canScrollVertically(-1)) return true
            } else if (temp is ViewGroup) {
                if (canChildScrollUp(temp)) return true
            } else if (canScrollVertically(-1)) return true
        }
        return false

    }


    fun canRecycleViewScroll(recyclerView: RecyclerView): Boolean {
        val layoutManager = recyclerView.layoutManager!!
        return (layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition() != 0

    }
}
