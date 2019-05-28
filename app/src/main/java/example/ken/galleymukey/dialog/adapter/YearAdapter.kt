package example.ken.galleymukey.dialog.adapter

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import example.ken.galleymukey.R
import example.ken.galleymukey.databinding.ItemYearBinding
import showmethe.github.kframework.adapter.DataBindBaseAdapter

/**
 * example.ken.galleymukey.dialog.adapter
 *
 * 2019/5/28
 **/
class YearAdapter(context: Context, data: ObservableArrayList<String>) :
    DataBindBaseAdapter<String, ItemYearBinding>(context, data) {

    var currentPos = 0

    override fun bindItems(binding: ItemYearBinding?, item: String, position: Int) {
        binding?.apply {
            bean = item
            executePendingBindings()

            if(currentPos == position){
                tvYear.setTextColor(ContextCompat.getColor(context,R.color.colorAccent))
                tvYear.typeface = Typeface.DEFAULT_BOLD
                tvYear.textSize = 25f
            }else{
                tvYear.setTextColor(ContextCompat.getColor(context,R.color.text_333333))
                tvYear.typeface = Typeface.DEFAULT;
                tvYear.textSize = 15f
            }

        }
    }

    override fun getItemLayout(): Int = R.layout.item_year
}