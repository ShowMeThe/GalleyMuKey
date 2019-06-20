package example.ken.galleymukey.dialog.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.HashTagBean
import example.ken.galleymukey.databinding.ItemCateMenuBinding
import showmethe.github.kframework.adapter.DataBindBaseAdapter

/**
 * example.ken.galleymukey.ui.cate
 * cuvsu
 * 2019/5/21
 **/
class CateMenuAdapter(context: Context, data: ObservableArrayList<HashTagBean>) :
    DataBindBaseAdapter<HashTagBean, ItemCateMenuBinding>(context, data) {
    override fun bindItems(binding: ItemCateMenuBinding?, item: HashTagBean, position: Int) {
        binding?.apply {
            bean = item
            executePendingBindings()

            cardView.setOnClickListener {
                item.isActive = !item.isActive
                onCateClick?.invoke(position)
            }
        }
    }

    override fun getItemLayout(): Int = R.layout.item_cate_menu


    var onCateClick : ((position : Int )->Unit)? = null

    fun setOnCateClickListener( onCateClick : ((position : Int )->Unit)){
        this.onCateClick = onCateClick
    }

}