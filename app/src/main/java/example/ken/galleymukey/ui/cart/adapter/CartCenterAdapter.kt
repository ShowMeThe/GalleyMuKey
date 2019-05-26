package example.ken.galleymukey.ui.cart.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import example.ken.galleymukey.R
import example.ken.galleymukey.databinding.ItemCenterTitleBinding
import showmethe.github.kframework.adapter.DataBindBaseAdapter

/**
 * example.ken.galleymukey.ui.cart.adapter
 * cuvsu
 * 2019/5/26
 **/
class CartCenterAdapter(context: Context, data: ObservableArrayList<String>) :
    DataBindBaseAdapter<String, ItemCenterTitleBinding>(context, data) {

    var currentPos = 0

    override fun bindItems(binding: ItemCenterTitleBinding?, item: String, position: Int) {
        binding?.apply {
            bean = item
            executePendingBindings()

            if(currentPos == position){
                tvTop.setTextColor(ContextCompat.getColor(context,R.color.color_f50057))
            }else{
                tvTop.setTextColor(ContextCompat.getColor(context,R.color.color_f48fb1))
            }
        }

    }

    override fun getItemLayout(): Int = R.layout.item_center_title
}