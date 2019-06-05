package example.ken.galleymukey.ui.cart.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import androidx.palette.graphics.Palette
import com.bumptech.glide.request.transition.Transition
import example.ken.galleymukey.R
import example.ken.galleymukey.databinding.ItemCartHomeBinding
import example.ken.galleymukey.source.dto.GoodsListDto
import example.ken.galleymukey.ui.cart.GoodsDetailActivity
import showmethe.github.kframework.adapter.DataBindBaseAdapter
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.glide.BitmapTarget
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.ToastFactory

/**
 * example.ken.galleymukey.ui.cart.adapter
 * cuvsu
 * 2019/5/26
 **/
class CartHomeAdapter(context: Context, data: ObservableArrayList<GoodsListDto>) :
    DataBindBaseAdapter<GoodsListDto, ItemCartHomeBinding>(context, data) {

    val defaultColor = ContextCompat.getColor(context,R.color.color_ff6e00)

    override fun bindItems(binding: ItemCartHomeBinding?, item: GoodsListDto, position: Int) {
        binding?.apply {
            bean = item
            executePendingBindings()

            if(item.vibrantColor == -1){
                TGlide.loadIntoBitmap(item.coverImg,object : BitmapTarget(){
                    override fun resourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        Palette.from(resource).generate {
                            it?.apply {
                                item.vibrantColor = getVibrantColor(defaultColor)
                                tvDes.setTextColor(item.vibrantColor)
                                cardView.strokeColor = item.vibrantColor
                                btnBuy.setBackgroundColor(item.vibrantColor)
                            }
                        }
                    }
                })
            }else{
                tvDes.setTextColor(item.vibrantColor)
                cardView.strokeColor = item.vibrantColor
                btnBuy.setBackgroundColor(item.vibrantColor)
            }


            cardView.setOnClickListener {
                val option = ActivityOptions.makeSceneTransitionAnimation(context as BaseActivity<*,*>,
                    *arrayOf<Pair<View,String>>(Pair.create(ivLogo,"ivLogo"),
                        Pair.create(tvName,"tvName"),
                        Pair.create(tvDes,"tvDes"),
                        Pair.create(btnBuy,"btnBuy")))
                val intent = Intent(context as BaseActivity<*,*> ,GoodsDetailActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("dto",item)
                intent.putExtras(bundle)
                context.startActivity(intent,option.toBundle())
            }
        }
    }



    override fun getItemLayout(): Int = R.layout.item_cart_home
}