package example.ken.galleymukey.ui.cart.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.View
import androidx.databinding.ObservableArrayList
import example.ken.galleymukey.R
import example.ken.galleymukey.databinding.ItemCartHomeBinding
import example.ken.galleymukey.source.dto.GoodsListDto
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
class CartHomeAdapter(context: Context, data: ObservableArrayList<GoodsListDto>) :
    DataBindBaseAdapter<GoodsListDto, ItemCartHomeBinding>(context, data) {
    override fun bindItems(binding: ItemCartHomeBinding?, item: GoodsListDto, position: Int) {
        binding?.apply {
            bean = item
            executePendingBindings()
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