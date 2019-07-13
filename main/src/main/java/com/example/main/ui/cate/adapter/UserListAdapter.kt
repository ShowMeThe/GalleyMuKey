package com.example.main.ui.cate.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList

import com.example.database.bean.PhotoWallBean
import com.example.main.R
import com.example.main.databinding.ItemSearchUserBinding

import showmethe.github.kframework.adapter.DataBindBaseAdapter
import showmethe.github.kframework.glide.TGlide

/**
 * com.example.main.ui.cate.adapter
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