package example.ken.galleymukey.ui.cate.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.CornerFamily
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.NewGoodsBean
import example.ken.galleymukey.databinding.ItemNewBinding
import example.ken.galleymukey.source.Source
import showmethe.github.kframework.adapter.DataBindBaseAdapter
import showmethe.github.kframework.base.BaseApplication.Companion.context
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.CreateDrawable

/**
 * example.ken.galleymukey.ui.cate.adapter
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