package showmethe.github.kframework.adapter

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import android.view.View

/**
 * example.ken.com.library.adapter
 * YeJq
 * 2018/11/23
 */
class GridSpaceItemDecoration(private val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) : androidx.recyclerview.widget.RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: androidx.recyclerview.widget.RecyclerView, state: androidx.recyclerview.widget.RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % this.spanCount
        if (this.includeEdge) {
            outRect.left = this.spacing - column * this.spacing / this.spanCount
            outRect.right = (column + 1) * this.spacing / this.spanCount
            if (position < this.spanCount) {
                outRect.top = this.spacing
            }

            outRect.bottom = this.spacing
        } else {
            outRect.left = column * this.spacing / this.spanCount
            outRect.right = this.spacing - (column + 1) * this.spacing / this.spanCount
            if (position < this.spanCount) {
                outRect.top = this.spacing
            }

            outRect.bottom = this.spacing
        }

    }
}
