package com.example.home.main.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.database.bean.HotWallBean
import com.example.home.R
import com.example.home.databinding.ItemHotBBinding
import com.example.home.databinding.ItemHotBinding


import showmethe.github.kframework.adapter.BaseRecyclerViewAdapter



/**
 * com.example.home.main.adapter
 * cuvsu
 * 2019/5/18
 **/
class HotAdapter(context: Context, mData: ObservableArrayList<HotWallBean>) :
    BaseRecyclerViewAdapter<HotWallBean, RecyclerView.ViewHolder>(context, mData) {


    override fun bindDataToItemView(holder: RecyclerView.ViewHolder, item: HotWallBean, position: Int) {
        when(item.type){
            0 ->{
                (holder as NormalViewHolder).binding.apply {
                    bean = item
                    executePendingBindings()
                    iv.setOnClickListener {
                        onPhotoClick?.invoke(it,item.pathTop!!)
                    }

                    iv2.setOnClickListener {
                        onPhotoClick?.invoke(it,item.pathBottom!!)
                    }
                }

            }
            1 ->{
                (holder as GriViewHolder).binding.apply {
                    bean = item
                    executePendingBindings()
                    iv.setOnClickListener {
                        onPhotoClick?.invoke(it,item.pathTop!!)
                    }
                }

            }
        }
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        ( recyclerView.layoutManager as GridLayoutManager).spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
               when( getItemViewType(position)){
                     0 -> return 1
                     1 -> return 2
               }
                return  1
            }
        }
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
             0 -> return NormalViewHolder(DataBindingUtil.bind<ItemHotBinding>(inflateItemView(parent, R.layout.item_hot))!!)
             1 -> return  GriViewHolder(DataBindingUtil.bind<ItemHotBBinding>(inflateItemView(parent, R.layout.item_hot_b))!!)
        }
        return  NormalViewHolder(DataBindingUtil.bind<ItemHotBinding>(inflateItemView(parent, R.layout.item_hot))!!)
    }


    var onPhotoClick: ((view: View, url:String)->Unit)? = null

    fun setOnPhotoClickListener(onPhotoClick: ((view: View,url:String)->Unit)){
        this.onPhotoClick = onPhotoClick
    }

    override fun getItemViewType(position: Int): Int {
        when(mData[position].type){
               0 -> return 0
               1 -> return 1
        }
        return super.getItemViewType(position)
    }

    class NormalViewHolder(var binding: ItemHotBinding) : RecyclerView.ViewHolder(binding.root)


    class GriViewHolder(var binding: ItemHotBBinding) : RecyclerView.ViewHolder(binding.root)
}