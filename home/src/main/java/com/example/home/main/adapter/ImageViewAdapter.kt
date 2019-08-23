package com.example.home.main.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import com.example.home.R
import com.example.home.databinding.ItemImageViewBinding
import showmethe.github.kframework.adapter.DataBindBaseAdapter

class ImageViewAdapter(context: Context, data: ObservableArrayList<String>) :
    DataBindBaseAdapter<String, ItemImageViewBinding>(context, data) {
    override fun getItemLayout(): Int = R.layout.item_image_view

    override fun bindItems(binding: ItemImageViewBinding?, item: String, position: Int) {
        binding?.apply {
            bean = item
            executePendingBindings()


            image.setOnDownProgressListener { offsetY, onComplete ->
                onDownProgress?.invoke(offsetY, onComplete)
            }

            image.setOnDownCompleteListener {
                onDownComplete?.invoke(it)
            }
        }
    }

    private var onDownProgress : ((offsetY :Float,onComplete:Boolean)->Unit)? = null
    fun setOnDownProgressListener( onDownProgress : ((offsetY :Float,onComplete:Boolean)->Unit)){
        this.onDownProgress = onDownProgress
    }
    private var onDownComplete : ((onComplete:Boolean)->Unit)? = null
    fun setOnDownCompleteListener( onComplete : ((onComplete:Boolean)->Unit)){
        this.onDownComplete = onComplete
    }

}