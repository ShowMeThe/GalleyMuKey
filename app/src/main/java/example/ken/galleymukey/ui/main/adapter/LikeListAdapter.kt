package example.ken.galleymukey.ui.main.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.shape.CornerFamily
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.PhotoWallBean
import example.ken.galleymukey.databinding.ItemLikeListBinding
import showmethe.github.kframework.adapter.DataBindBaseAdapter
import showmethe.github.kframework.glide.DrawableTarget
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.CreateDrawable

class LikeListAdapter(context: Context, data: ObservableArrayList<PhotoWallBean>) :
    DataBindBaseAdapter<PhotoWallBean, ItemLikeListBinding>(context, data) {
    override fun getItemLayout(): Int = R.layout.item_like_list

    override fun bindItems(binding: ItemLikeListBinding?, item: PhotoWallBean, position: Int) {
        binding?.apply {
            banner.addList(item.imagePaths!!)
            banner.setOnImageLoader { url, imageView ->
                TGlide.loadRoundPicture(url, imageView,20)
            }
            banner.play()
            bean  = item
            executePendingBindings()
        }
    }
}