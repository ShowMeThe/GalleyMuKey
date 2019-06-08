package example.ken.galleymukey.ui.cate.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.PhotoWallBean
import example.ken.galleymukey.databinding.ItemSearchUserBinding
import showmethe.github.kframework.adapter.DataBindBaseAdapter
import showmethe.github.kframework.glide.TGlide

/**
 * example.ken.galleymukey.ui.cate.adapter
 * cuvsu
 * 2019/6/8
 **/
class UserListAdapter(context: Context, data: ObservableArrayList<PhotoWallBean>) :
    DataBindBaseAdapter<PhotoWallBean, ItemSearchUserBinding>(context, data) {
    override fun bindItems(binding: ItemSearchUserBinding?, item: PhotoWallBean, position: Int) {
        binding?.apply {
            bean =item
            executePendingBindings()
            TGlide.load(item.imagePaths!![0],bg)
        }

    }

    override fun getItemLayout(): Int = R.layout.item_search_user
}