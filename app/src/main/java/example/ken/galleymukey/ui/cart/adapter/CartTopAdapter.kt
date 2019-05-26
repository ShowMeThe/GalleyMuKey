package example.ken.galleymukey.ui.cart.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import example.ken.galleymukey.R
import example.ken.galleymukey.databinding.ItemCartTopBinding
import showmethe.github.kframework.adapter.DataBindBaseAdapter

/**
 * example.ken.galleymukey.ui.cart.adapter
 * cuvsu
 * 2019/5/26
 **/
class CartTopAdapter(context: Context, data: ObservableArrayList<String>) :
    DataBindBaseAdapter<String, ItemCartTopBinding>(context, data) {

    var currentPos = 0

    override fun bindItems(binding: ItemCartTopBinding?, item: String, position: Int) {
        binding?.apply {
            bean = item
            executePendingBindings()

            if(currentPos == position){
                tvTop.setTextColor(ContextCompat.getColor(context,R.color.color_0288d1))
            }else{
                tvTop.setTextColor(ContextCompat.getColor(context,R.color.color_64b5f6))
            }
        }
    }

    override fun getItemLayout(): Int = R.layout.item_cart_top
}