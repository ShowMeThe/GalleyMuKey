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
class SlideViewHolder(private val mItemView: View) : RecyclerView.ViewHolder(mItemView) {
    private val views: SparseArray<View> = SparseArray()


    fun <T : View> getView(viewId: Int): T? {
        var view: View? = views.get(viewId)
        if (view == null) {
            view = mItemView.findViewById(viewId)
            views.put(viewId, view)
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


    fun closeMenu(): SlideViewHolder {
        (getView<View>(R.id.slideLayout) as SlideLayout).adapter.closeOpenItem()
        return this
    }
}
