package com.example.main.ui.mine.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList

import com.example.database.bean.OrderListBean
import com.example.main.R
import com.example.main.databinding.ItemOrderListBinding

import showmethe.github.kframework.adapter.DataBindBaseAdapter

/**
 * com.example.main.ui.mine.adapter
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