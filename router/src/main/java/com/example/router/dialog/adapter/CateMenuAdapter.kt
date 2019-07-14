package com.example.router.dialog.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList

import com.example.database.bean.HashTagBean
import com.example.router.R
import com.example.router.databinding.ItemCateMenuBinding

import showmethe.github.kframework.adapter.DataBindBaseAdapter

/**
 * com.example.cate.cate
 * cuvsu
 * 2019/5/21
 **/
class CateMenuAdapter(context: Context, data: ObservableArrayList<HashTagBean>) :
    DataBindBaseAdapter<HashTagBean, ItemCateMenuBinding>(context, data) {
    override fun bindItems(binding: ItemCateMenuBinding?, item: HashTagBean, position: Int) {
        binding?.apply {
            bean = item
            executePendingBindings()

            cardView.setOnClickListener {
                item.isActive = !item.isActive
                onCateClick?.invoke(position)
            }
        }
    }

    override fun getItemLayout(): Int = R.layout.item_cate_menu


    var onCateClick : ((position : Int )->Unit)? = null

    fun setOnCateClickListener( onCateClick : ((position : Int )->Unit)){
        this.onCateClick = onCateClick
    }

}