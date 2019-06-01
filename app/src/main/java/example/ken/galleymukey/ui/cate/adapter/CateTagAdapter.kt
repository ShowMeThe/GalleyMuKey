package example.ken.galleymukey.ui.cate.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.CateTagBean
import example.ken.galleymukey.databinding.ItemCateHashBinding
import example.ken.galleymukey.databinding.ItemCateHashBindingImpl
import showmethe.github.kframework.adapter.DataBindBaseAdapter

/**
 * example.ken.galleymukey.ui.cate.adapter
 * cuvsu
 * 2019/6/1
 **/
class CateTagAdapter(context: Context, data: ObservableArrayList<CateTagBean>) :
    DataBindBaseAdapter<CateTagBean, ItemCateHashBinding>(context, data) {
    override fun bindItems(binding: ItemCateHashBinding?, item: CateTagBean, position: Int) {
        binding?.apply {
            bean = item
            executePendingBindings()
        }
    }
    override fun getItemLayout(): Int = R.layout.item_cate_hash
}