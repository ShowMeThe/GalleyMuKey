package com.example.main.ui.cate.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList

import com.example.database.bean.CateTagBean
import com.example.main.R
import com.example.main.databinding.ItemCateHashBinding

import showmethe.github.kframework.adapter.DataBindBaseAdapter

/**
 * com.example.main.ui.cate.adapter
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