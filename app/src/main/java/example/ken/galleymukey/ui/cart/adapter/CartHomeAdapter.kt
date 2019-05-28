package example.ken.galleymukey.ui.cart.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.Pair
import android.view.View
import androidx.databinding.ObservableArrayList
import example.ken.galleymukey.R
import example.ken.galleymukey.databinding.ItemCartHomeBinding
import example.ken.galleymukey.ui.cart.GoodsDetailActivity
import showmethe.github.kframework.adapter.DataBindBaseAdapter
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.ToastFactory

/**
 * example.ken.galleymukey.ui.cart.adapter
 * cuvsu
 * 2019/5/26
 **/
class CartHomeAdapter(context: Context, data: ObservableArrayList<String>) :
    DataBindBaseAdapter<String, ItemCartHomeBinding>(context, data) {
    override fun bindItems(binding: ItemCartHomeBinding?, item: String, position: Int) {
        binding?.apply {
            TGlide.load("http://image3.xyzs.com/upload/b9/40/1449104703418440/20151205/144925600471264_0.jpg",ivLogo)

            cardView.setOnClickListener {
                val option = ActivityOptions.makeSceneTransitionAnimation(context as BaseActivity<*,*>,
                    *arrayOf<Pair<View,String>>(Pair.create(ivLogo,"ivLogo"),Pair.create(btnBuy,"btnBuy")))
                val intent = Intent(context as BaseActivity<*,*> ,GoodsDetailActivity::class.java)
                context.startActivity(intent,option.toBundle())
            }
        }
    }



    override fun getItemLayout(): Int = R.layout.item_cart_home
}