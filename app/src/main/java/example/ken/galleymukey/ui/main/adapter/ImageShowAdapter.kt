package example.ken.galleymukey.ui.main.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import example.ken.galleymukey.R
import example.ken.galleymukey.databinding.ItemImageShowBinding
import showmethe.github.kframework.adapter.DataBindBaseAdapter

class ImageShowAdapter(context: Context, data: ObservableArrayList<String>) :
    DataBindBaseAdapter<String, ItemImageShowBinding>(context, data) {
    override fun getItemLayout(): Int = R.layout.item_image_show

    override fun bindItems(binding: ItemImageShowBinding?, item: String, position: Int) {
        binding?.apply {
            bean = item
            executePendingBindings()
        }
    }
}