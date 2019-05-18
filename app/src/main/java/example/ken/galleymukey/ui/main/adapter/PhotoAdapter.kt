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
        val list = ArrayList<String>()
        list.add("http://image1.xyzs.com/upload/a6/1c/1450580015244844/20151224/145089874795426_0.jpg")
        list.add("http://image2.xyzs.com/upload/9f/f3/1449368181406009/20151209/144960051320867_0.jpg")
        list.add("http://image3.xyzs.com/upload/b9/40/1449104703418440/20151205/144925600471264_0.jpg")

        binding?.apply {
            banner.addList(list)
            banner.setCurrentPosition(item.currentPos)
            banner.setOnImageLoader { url, imageView -> TGlide.load(url, imageView) }
            banner.setOnPagerLisnener {
                data[position].currentPos = it
                tvSelect.text = "${data[position].currentPos+1}/${list.size}"
            }

            tvSelect.text = "${item.currentPos+1}/${list.size}"

        }

    }

    override fun getItemLayout(): Int = R.layout.item_photo
}