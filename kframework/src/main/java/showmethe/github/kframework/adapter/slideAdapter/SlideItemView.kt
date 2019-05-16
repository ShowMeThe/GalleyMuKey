package showmethe.github.kframework.adapter.slideAdapter

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import showmethe.github.kframework.R

/**
 * PackageName: example.ken.com.library.adapter
 * Author : jiaqi Ye
 * Date : 2018/9/30
 * Time : 17:00
 */
class SlideItemView(private val mItemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mItemView) {
    private val mViews: SparseArray<View>


    init {
        mViews = SparseArray()
    }


    fun <T : View> getView(viewId: Int): T? {
        var view: View? = mViews.get(viewId)
        if (view == null) {
            view = mItemView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T?
    }


    fun setText(text: String) {
        val textView = getView<TextView>(R.id.slideLayout_tv_content)
        if (textView != null) {
            textView.text = text
        }
    }

    fun setTextColor(context: Context, textColor: Int) {
        val textView = getView<TextView>(R.id.slideLayout_tv_content)
        textView?.setTextColor(ContextCompat.getColor(context, textColor))
    }

    fun setBackgroundColor(context: Context, backgroundColor: Int, type: MenuType) {
        val layout: RelativeLayout?
        when (type) {
            MenuType.TEXT -> {
                layout = getView(R.id.slideLayout_rl_bg)
                layout?.setBackgroundColor(ContextCompat.getColor(context, backgroundColor))
            }
            MenuType.IMAGE -> {
                layout = getView(R.id.slideLayout_img_bg)
                layout?.setBackgroundColor(ContextCompat.getColor(context, backgroundColor))
            }
        }
    }

    fun setImageRes(imgRes: Int) {
        val imageView = getView<ImageView>(R.id.slideLayout_iv_img)
        imageView?.setBackgroundResource(imgRes)
    }


    fun closeMenu(): SlideItemView {
        (getView<View>(R.id.slideLayout) as SlideLayout).adapter.closeOpenItem()
        return this
    }
}
