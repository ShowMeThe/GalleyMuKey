package showmethe.github.kframework.picture.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import showmethe.github.kframework.R
import showmethe.github.kframework.adapter.DataBindBaseAdapter
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.databinding.ItemPictureSelectorBinding
import showmethe.github.kframework.picture.LocalMedia
import showmethe.github.kframework.picture.PictureSelectorActivity.Companion.SINGLE
import showmethe.github.kframework.picture.PictureSelectorActivity.Companion.MULTI


/**
 * showmethe.github.kframework.picture.adapter
 * cuvsu
 * 2019/4/5
 **/
class PictureAdapter(context: Context, data: ObservableArrayList<LocalMedia>) :
        DataBindBaseAdapter<LocalMedia, ItemPictureSelectorBinding>(context, data) {



    var currentPos = 0

    var mode = MULTI

    override fun getItemLayout(): Int = R.layout.item_picture_selector

    override fun bindItems(binding: ItemPictureSelectorBinding?, item: LocalMedia, position: Int) {
        binding?.apply {
            dto = item
            if(mode == SINGLE){
             cbImg .isChecked = (currentPos == position)
            }

            executePendingBindings()

            cbImg.setOnClickListener {
                onItemBoxListener?.apply {
                    invoke(position)
                }
            }
        }
    }

    var onItemBoxListener : (((position : Int)->Unit)?) = null

    fun setOnItemBoxChangeListener(onItemBoxListener : (((position : Int)->Unit)?)){
        this.onItemBoxListener = onItemBoxListener
    }

}