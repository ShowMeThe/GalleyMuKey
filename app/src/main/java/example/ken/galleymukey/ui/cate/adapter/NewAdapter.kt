package example.ken.galleymukey.ui.cate.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import example.ken.galleymukey.R
import example.ken.galleymukey.databinding.ItemNewBinding
import showmethe.github.kframework.adapter.DataBindBaseAdapter
import showmethe.github.kframework.glide.TGlide

/**
 * example.ken.galleymukey.ui.cate.adapter
 * cuvsu
 * 2019/6/2
 **/
class NewAdapter(context: Context, data: ObservableArrayList<String>) :
    DataBindBaseAdapter<String, ItemNewBinding>(context, data) {
    override fun bindItems(binding: ItemNewBinding?, item: String, position: Int) {
        binding?.apply {


        }
    }

    override fun getItemLayout(): Int = R.layout.item_new

}