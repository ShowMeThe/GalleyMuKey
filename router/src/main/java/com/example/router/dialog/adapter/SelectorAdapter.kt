package com.example.router.dialog.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.databinding.ObservableArrayList
import com.example.router.R
import com.example.router.databinding.ItemSelectorImgBinding

import showmethe.github.kframework.adapter.DataBindBaseAdapter

/**
 * com.example.router.dialog.adapter
 *
 * 2019/6/13
 **/
class SelectorAdapter(context: Context, data: ObservableArrayList<String>) :
    DataBindBaseAdapter<String, ItemSelectorImgBinding>(context, data) {



    @SuppressLint("ClickableViewAccessibility")
    override fun bindItems(binding: ItemSelectorImgBinding?, item: String, position: Int) {
        binding?.apply {
            tvNum.text = "$position"
            beam = item
            executePendingBindings()
            val mGestureDetector = GestureDetector(context,object : GestureDetector.SimpleOnGestureListener(){
                override fun onDoubleTap(e: MotionEvent?): Boolean {
                    onTapImageListener?.invoke(item)
                    return false
                }
            })
            ivImg.setOnTouchListener { v, event ->
                mGestureDetector.onTouchEvent(event)
                true
            }

        }
    }
    override fun getItemLayout(): Int = R.layout.item_selector_img


    var onTapImageListener:((url:String)->Unit)? = null

    fun setOnTapImageListner( onTapImageListener:((url:String)->Unit)){
        this.onTapImageListener = onTapImageListener
    }

}