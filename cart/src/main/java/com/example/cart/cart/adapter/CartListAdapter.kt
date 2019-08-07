package com.example.cart.cart.adapter

import android.content.Context
import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import androidx.palette.graphics.Palette
import com.bumptech.glide.request.transition.Transition
import com.example.cart.R
import com.example.cart.databinding.ItemCartListBinding
import com.google.android.material.shape.CornerFamily

import com.example.database.bean.CartListBean


import showmethe.github.kframework.adapter.DataBindBaseAdapter
import showmethe.github.kframework.glide.BitmapTarget
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.extras.CornerType
import showmethe.github.kframework.util.extras.createWithStroke
import showmethe.github.kframework.util.extras.double2Decimal


class CartListAdapter(context: Context, data: ObservableArrayList<CartListBean>) :
    DataBindBaseAdapter<CartListBean, ItemCartListBinding>(context, data) {

    val defaultColor = ContextCompat.getColor(context, R.color.color_ff6e00)
    val white = ContextCompat.getColor(context, R.color.white)

    override fun getItemLayout(): Int = R.layout.item_cart_list
    override fun bindItems(binding: ItemCartListBinding?, item: CartListBean, position: Int) {
        binding?.apply {

            if(item.vibrantColor == -1){
                TGlide.loadIntoBitmap(item.getCoverImg(),object : BitmapTarget(){
                    override fun resourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        Palette.from(resource).generate {
                            it?.apply {
                                item.vibrantColor = getVibrantColor(defaultColor)
                                tvDes.setTextColor(item.vibrantColor)
                                tvName.setTextColor(item.vibrantColor)
                                tvCount.setTextColor(item.vibrantColor)
                                tvPrice.setTextColor(item.vibrantColor)
                                item.drawable = createWithStroke(
                                    context, CornerFamily.CUT, 10, white, item.vibrantColor,
                                    CornerType.ALL
                                )
                                layout.background = item.drawable
                            }
                        }
                    }
                })
            }else{
                tvDes.setTextColor(item.vibrantColor)
                tvName.setTextColor(item.vibrantColor)
                layout.background = item.drawable
                tvCount.setTextColor(item.vibrantColor)
                tvPrice.setTextColor(item.vibrantColor)
            }

            tvPrice.text = context.getString(R.string.dollars) + double2Decimal(
                item.getCount() * item.getPrice().toDouble()
            )
            bean = item
            executePendingBindings()
        }
    }
}