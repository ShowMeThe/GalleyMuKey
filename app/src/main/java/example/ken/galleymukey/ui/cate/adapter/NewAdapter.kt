package example.ken.galleymukey.ui.cate.adapter

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableArrayList
import com.google.android.material.shape.CornerFamily
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.NewGoodsBean
import example.ken.galleymukey.databinding.ItemNewBinding
import example.ken.galleymukey.source.Source
import showmethe.github.kframework.adapter.DataBindBaseAdapter
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.CreateDrawable

/**
 * example.ken.galleymukey.ui.cate.adapter
 * cuvsu
 * 2019/6/2
 **/
class NewAdapter(context: Context, data: ObservableArrayList<NewGoodsBean>) :
    DataBindBaseAdapter<NewGoodsBean, ItemNewBinding>(context, data) {
    override fun bindItems(binding: ItemNewBinding?, item: NewGoodsBean, position: Int) {
        binding?.apply {
            firstCard.background =  CreateDrawable.create(context,CornerFamily.CUT,10,R.color.colorAccent)
            secondCard.background =  CreateDrawable.createWithStroke(context,CornerFamily.ROUNDED,10,R.color.white,
                R.color.colorPrimaryDark,CreateDrawable.CornerType.TOPLEFT,CreateDrawable.CornerType.BOTTMRIGHT)
            TGlide.load("http://image2.xyzs.com/upload/fc/6a/1450315960904658/20151219/145046718037409_0.jpg",ivLogo)

            container.post {
                container.setCurrentExpand(item.isRotate)
            }
            container.setOnDetachListener { item.isRotate  = it }
            progressbar.updateProgress(50f)


        }
    }

    override fun getItemLayout(): Int = R.layout.item_new

}