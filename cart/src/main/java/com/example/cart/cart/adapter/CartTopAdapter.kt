package com.example.cart.cart.adapter

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import com.example.cart.R
import com.example.cart.databinding.ItemCartTopBinding

import showmethe.github.kframework.adapter.DataBindBaseAdapter

/**
 * com.example.cart.cart.adapter
 * cuvsu
 * 2019/5/26
 **/
class CartTopAdapter(context: Context, data: ObservableArrayList<String>) :
    DataBindBaseAdapter<String, ItemCartTopBinding>(context, data) {

    var currentPos = 0

    override fun bindItems(binding: ItemCartTopBinding?, item: String, position: Int) {
        binding?.apply {
            bean = item
            executePendingBindings()

            if(currentPos == position){
                tvTop.setTextColor(ContextCompat.getColor(context, R.color.color_ff6e00))
                tvTop.textSize = 31f
                tvTop.typeface = Typeface.DEFAULT_BOLD
            }else{
                tvTop.setTextColor(ContextCompat.getColor(context, R.color.color_ffab91))
                tvTop.textSize = 26f
            }
        }
    }

    override fun getItemLayout(): Int = R.layout.item_cart_top
}