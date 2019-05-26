package example.ken.galleymukey.ui.cart.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import example.ken.galleymukey.R
import example.ken.galleymukey.databinding.ItemCartHomeBinding
import showmethe.github.kframework.adapter.DataBindBaseAdapter

/**
 * example.ken.galleymukey.ui.cart.adapter
 * cuvsu
 * 2019/5/26
 **/
class CartHomeAdapter(context: Context, data: ObservableArrayList<String>) :
    DataBindBaseAdapter<String, ItemCartHomeBinding>(context, data) {
    override fun bindItems(binding: ItemCartHomeBinding?, item: String, position: Int) {
    }

    override fun getItemLayout(): Int = R.layout.item_cart_home
}