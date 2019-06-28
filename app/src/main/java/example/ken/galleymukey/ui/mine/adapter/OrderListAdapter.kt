package example.ken.galleymukey.ui.mine.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.OrderListBean
import example.ken.galleymukey.databinding.ItemOrderListBinding
import showmethe.github.kframework.adapter.DataBindBaseAdapter

/**
 * example.ken.galleymukey.ui.mine.adapter
 *
 * 2019/6/28
 **/
class OrderListAdapter(context: Context, data: ObservableArrayList<OrderListBean>) :
    DataBindBaseAdapter<OrderListBean, ItemOrderListBinding>(context, data) {
    override fun getItemLayout(): Int = R.layout.item_order_list

    override fun bindItems(binding: ItemOrderListBinding?, item: OrderListBean, position: Int) {
        binding?.apply {
            bean = item
            executePendingBindings()
        }
    }
}