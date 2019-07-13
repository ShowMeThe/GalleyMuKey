package com.example.router.dialog.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList

import com.example.router.constant.RdenConstant

import com.example.database.source.dto.CommentDto
import com.example.router.R
import com.example.router.databinding.ItemSeeCommentBinding
import showmethe.github.kframework.adapter.DataBindBaseAdapter
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.rden.RDEN

class SeeCommentAdapter(context: Context, data: ObservableArrayList<CommentDto>) :
    DataBindBaseAdapter<CommentDto, ItemSeeCommentBinding>(context, data) {
    override fun getItemLayout(): Int = R.layout.item_see_comment

    override fun bindItems(binding: ItemSeeCommentBinding?, item: CommentDto, position: Int) {
        binding?.apply {
            bean = item
            executePendingBindings()
            TGlide.loadCirclePicture(RDEN.get(RdenConstant.avatar,""),ivAvatar)
            tvName.text = RDEN.get(RdenConstant.account,"")
        }
    }
}