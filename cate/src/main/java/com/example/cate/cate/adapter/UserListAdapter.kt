package com.example.cate.cate.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import com.example.cate.R
import com.example.cate.databinding.ItemSearchUserBinding

import com.example.database.bean.PhotoWallBean


import showmethe.github.kframework.adapter.DataBindBaseAdapter
import showmethe.github.kframework.glide.TGlide

/**
 * com.example.cate.cate.adapter
 * cuvsu
 * 2019/6/8
 **/
class UserListAdapter(context: Context, data: ObservableArrayList<PhotoWallBean>) :
    DataBindBaseAdapter<PhotoWallBean, ItemSearchUserBinding>(context, data) {
    override fun bindItems(binding: ItemSearchUserBinding?, item: PhotoWallBean, position: Int) {
        binding?.apply {
            bean =item
            executePendingBindings()
            TGlide.load(item.imagePaths!![0],bg)
        }

    }

    override fun getItemLayout(): Int = R.layout.item_search_user
}