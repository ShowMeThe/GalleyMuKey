package example.ken.galleymukey.ui.main.adapter

import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.ObservableArrayList
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.PhotoWallBean
import example.ken.galleymukey.databinding.ItemPhotoBinding
import example.ken.galleymukey.source.dao.PhotoWallDao
import example.ken.galleymukey.source.dto.PhotoWallDto
import showmethe.github.kframework.adapter.AutoLoadAdapter
import showmethe.github.kframework.adapter.DataBindBaseAdapter
import showmethe.github.kframework.glide.TGlide

/**
 * example.ken.galleymukey.ui.main.adapter
 * cuvsu
 * 2019/5/18
 **/
class PhotoAdapter(context: Context, data: ObservableArrayList<PhotoWallBean>) :
    DataBindBaseAdapter<PhotoWallBean, ItemPhotoBinding>(context, data) {


    override fun bindItems(binding: ItemPhotoBinding?, item: PhotoWallBean, position: Int) {

        binding?.apply {
            bean = item
            executePendingBindings()

            banner.addList(item.imagePaths!!)
            banner.setCurrentPosition(item.currentPos)
            banner.setOnImageLoader { url, imageView -> TGlide.loadNoCrop(url, imageView) }
            banner.setOnPagerLisnener {
                data[position].currentPos = it
                tvSelect.text = "${data[position].currentPos+1}/${item.imagePaths!!.size}"
            }

            tvSelect.text = "${item.currentPos+1}/${item.imagePaths!!.size}"
            banner.setOnPageClickListner {
                onPhotoClick?.invoke(banner,item.imagePaths!![it])
            }
            like.setLike(item.like,false)
            like.setOnClickListener {
                item.like = !item.like
                like.setLike( item.like,true)
                onLikeClick?.invoke(item.id,item.like)
            }
        }
    }

    var onLikeClick: ((id: Int, like:Boolean)->Unit)? = null

    fun setOnLikeClickListener(onLikeClick: ((id: Int, like:Boolean)->Unit)){
        this.onLikeClick = onLikeClick
    }


    var onPhotoClick: ((view: View, url:String)->Unit)? = null

    fun setOnPhotoClickListener(onPhotoClick: ((view: View,url:String)->Unit)){
        this.onPhotoClick = onPhotoClick
    }


    override fun getItemLayout(): Int = R.layout.item_photo
}