package showmethe.github.kframework.widget.banner

import android.content.Context
import android.media.Image
import android.widget.ImageView

import androidx.databinding.ObservableArrayList

import showmethe.github.kframework.R
import showmethe.github.kframework.adapter.DataBindBaseAdapter
import showmethe.github.kframework.databinding.ItemBannerViewBinding

/**
 * showmethe.github.kframework.widget.banner
 * cuvsu
 * 2019/5/8
 */
class BannerViewAdapter(context: Context, data: ObservableArrayList<Any>) : DataBindBaseAdapter<Any, ItemBannerViewBinding>(context, data) {

    private var imageLoader: onImageLoader? = null

    override fun bindItems(binding: ItemBannerViewBinding?, item: Any, position: Int) {
        if (binding != null) {
            if (imageLoader != null) {
                imageLoader!!.display(item, binding.ivBanner)
            }
        }
    }


    interface onImageLoader {

        fun display(url: Any, imageView: ImageView)

    }

    fun setOnImageLoader(loader: onImageLoader) {
        imageLoader = loader
    }

    override fun getItemLayout(): Int {
        return R.layout.item_banner_view
    }
}
