package com.example.cate.cate.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import com.example.cate.R
import com.example.cate.databinding.ItemNewBinding
import com.google.android.material.shape.CornerFamily

import com.example.database.bean.NewGoodsBean


import showmethe.github.kframework.adapter.DataBindBaseAdapter
import showmethe.github.kframework.util.CreateDrawable

/**
 * com.example.cate.cate.adapter
 * cuvsu
 * 2019/6/2
 **/
class NewAdapter(context: Context, data: ObservableArrayList<NewGoodsBean>) :
    DataBindBaseAdapter<NewGoodsBean, ItemNewBinding>(context, data) {
    override fun getItemLayout(): Int = R.layout.item_new

    val colorAccent = ContextCompat.getColor(context,R.color.colorAccent)
    val white = ContextCompat.getColor(context,R.color.white)
    val stroke = ContextCompat.getColor(context,R.color.colorPrimaryDark)

    override fun bindItems(binding: ItemNewBinding?, item: NewGoodsBean, position: Int) {
        binding?.apply {
            firstCard.background =  CreateDrawable.create(context,CornerFamily.CUT,10,colorAccent)
            secondCard.background =  CreateDrawable.createWithStroke(context,CornerFamily.ROUNDED,10,white,
                stroke,CreateDrawable.CornerType.TOPLEFT,CreateDrawable.CornerType.BOTTMRIGHT)

            bean = item
            executePendingBindings()

            container.post {
                container.setCurrentExpand(item.isRotate)
            }
            container.setOnDetachListener { item.isRotate  = it }
            progressbar.updateProgress(item.hotSell * 100f)

        }
    }

}