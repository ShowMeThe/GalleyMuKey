package com.example.home.main.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import com.google.android.material.shape.CornerFamily

import com.example.database.bean.PhotoWallBean
import com.example.home.R
import com.example.home.databinding.ItemLikeListBinding


import showmethe.github.kframework.adapter.DataBindBaseAdapter
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.extras.createDrawable

class LikeListAdapter(context: Context, data: ObservableArrayList<PhotoWallBean>) :
    DataBindBaseAdapter<PhotoWallBean, ItemLikeListBinding>(context, data) {
    override fun getItemLayout(): Int = R.layout.item_like_list

    override fun bindItems(binding: ItemLikeListBinding?, item: PhotoWallBean, position: Int) {
        binding?.apply {
            banner.addList(item.imagePaths!!)
            banner.setOnImageLoader { url, imageView ->
                TGlide.loadCutPicture(url, imageView,15)
            }
            banner.play()
            bean  = item
            executePendingBindings()
            container.background  = createDrawable(
                context,
                CornerFamily.CUT,
                15,
                R.color.colorAccent
            )
            tvComment.setOnClickListener {
                onAddComment?.invoke(position)
            }

        }
    }


    private var onAddComment : ((position : Int )->Unit)?  = null

    fun setOnAddCommentListener(onAddComment : ((position : Int )->Unit)){
        this.onAddComment = onAddComment
    }


}