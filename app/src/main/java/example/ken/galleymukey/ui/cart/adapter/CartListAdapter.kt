package example.ken.galleymukey.ui.cart.adapter

import android.content.Context
import android.graphics.Bitmap
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import androidx.palette.graphics.Palette
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.shape.CornerFamily
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.CartListBean
import example.ken.galleymukey.databinding.ItemCartListBinding
import showmethe.github.kframework.adapter.DataBindBaseAdapter
import showmethe.github.kframework.glide.BitmapTarget
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.CreateDrawable

class CartListAdapter(context: Context, data: ObservableArrayList<CartListBean>) :
    DataBindBaseAdapter<CartListBean, ItemCartListBinding>(context, data) {

    val defaultColor = ContextCompat.getColor(context,R.color.color_ff6e00)
    val white = ContextCompat.getColor(context,R.color.white)

    override fun getItemLayout(): Int = R.layout.item_cart_list
    override fun bindItems(binding: ItemCartListBinding?, item: CartListBean, position: Int) {
        binding?.apply {

            if(item.vibrantColor == -1){
                TGlide.loadIntoBitmap(item.coverImg,object : BitmapTarget(){
                    override fun resourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        Palette.from(resource).generate {
                            it?.apply {
                                item.vibrantColor = getLightVibrantColor(defaultColor)
                                tvDes.setTextColor(item.vibrantColor)
                                tvName.setTextColor(item.vibrantColor)
                                item.drawable =  CreateDrawable.createWithStroke(context,CornerFamily.CUT,10,white,item.vibrantColor,CreateDrawable.CornerType.ALL)
                                layout.background = item.drawable
                            }
                        }
                    }
                })
            }else{
                tvDes.setTextColor(item.vibrantColor)
                tvName.setTextColor(item.vibrantColor)
                layout.background = item.drawable
            }

            bean = item
            executePendingBindings()
        }
    }
}