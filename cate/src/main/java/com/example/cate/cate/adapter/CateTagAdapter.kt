package com.example.cate.cate.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import com.example.cate.R
import com.example.cate.databinding.ItemCateHashBinding

import com.example.database.bean.CateTagBean


import showmethe.github.kframework.adapter.DataBindBaseAdapter

/**
 * com.example.cate.cate.adapter
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