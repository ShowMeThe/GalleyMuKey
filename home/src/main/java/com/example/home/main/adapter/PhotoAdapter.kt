package com.example.home.main.adapter

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import androidx.databinding.ObservableArrayList

import com.example.database.bean.PhotoWallBean
import com.example.home.R
import com.example.home.databinding.ItemPhotoBinding


import com.example.home.main.ImageShowActivity
import showmethe.github.kframework.adapter.DataBindBaseAdapter
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.glide.TGlide

/**
 * com.example.home.main.adapter
 * cuvsu
 * 2019/5/18
 **/
class PhotoAdapter(context: Context, data: ObservableArrayList<PhotoWallBean>) :
    DataBindBaseAdapter<PhotoWallBean, ItemPhotoBinding>(context, data) {


    override fun bindItems(binding: ItemPhotoBinding?, item: PhotoWallBean, position: Int) {

        binding?.apply {
            bean = item
            executePendingBindings()

            banner.addList(item.imagePaths!!)

            banner.setCurrentPosition(item.currentPos,true)
            banner.setOnImageLoader { url, imageView -> TGlide.loadNoCrop(url, imageView) }
            banner.setOnPagerLisnener {
                data[position].currentPos = it
                tvSelect.text = "${data[position].currentPos+1}/${item.imagePaths!!.size}"
            }


            tvSelect.text = "${item.currentPos+1}/${item.imagePaths!!.size}"

            banner.setOnPageClickListner {
                ImageShowActivity.startImgs(context as BaseActivity<*, *>,data[position].imagePaths!!,it)
            }

            like.setLike(item.like,false)
            like.setOnClickListener {
                item.like = !item.like
                like.setLike( item.like,true)
                onLikeClick?.invoke(item.id,item.like)
            }

            tvSee.setOnClickListener {
                onCommentClick?.invoke(position)
            }
        }
    }

    private  var onLikeClick: ((id: Int, like:Boolean)->Unit)? = null

    fun setOnLikeClickListener(onLikeClick: ((id: Int, like:Boolean)->Unit)){
        this.onLikeClick = onLikeClick
    }


    private  var onCommentClick: ((position: Int)->Unit)? = null

    fun setOnCommentClickListener(onCommentClick: ((position: Int)->Unit)){
        this.onCommentClick = onCommentClick
    }


    override fun getItemLayout(): Int = R.layout.item_photo
}