package example.ken.galleymukey.ui.main.adapter

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableArrayList
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.PhotoWallBean
import example.ken.galleymukey.databinding.ItemPhotoBinding
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
            banner.addList(item.imagePaths!!)
            banner.setCurrentPosition(item.currentPos)
            banner.setOnImageLoader { url, imageView -> TGlide.load(url, imageView) }
            banner.setOnPagerLisnener {
                data[position].currentPos = it
                tvSelect.text = "${data[position].currentPos+1}/${item.imagePaths!!.size}"
            }

            tvSelect.text = "${item.currentPos+1}/${item.imagePaths!!.size}"

        }

    }

    override fun getItemLayout(): Int = R.layout.item_photo
}