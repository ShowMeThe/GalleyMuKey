package com.example.main.ui.mine.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import com.example.main.R
import com.example.main.databinding.ItemImageShowBinding


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