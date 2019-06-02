package example.ken.galleymukey.ui.main.adapter

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import androidx.databinding.ObservableArrayList
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.PhotoWallBean
import example.ken.galleymukey.databinding.ItemPhotoBinding
import example.ken.galleymukey.ui.main.ImageShowActivity
import showmethe.github.kframework.adapter.DataBindBaseAdapter
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.widget.transformer.ParallaxTransformer
import showmethe.github.kframework.widget.transformer.SpinnerTransformer

/**
 * example.ken.galleymukey.ui.main.adapter
 * cuvsu
 * 2019/5/18
 **/
class PhotoAdapter(context: Context, data: ObservableArrayList<PhotoWallBean>) :
    DataBindBaseAdapter<PhotoWallBean, ItemPhotoBinding>(context, data) {


    override fun bindItems(binding: ItemPhotoBinding?, item: PhotoWallBean, position: Int) {

        binding?.apply {
            bean = item
            executePendingBindings()

            banner.addList(item.imagePaths!!)

            banner.setCurrentPosition(item.currentPos)
            banner.setOnImageLoader { url, imageView -> TGlide.loadNoCrop(url, imageView) }
            banner.setOnPagerLisnener {
                data[position].currentPos = it
                tvSelect.text = "${data[position].currentPos+1}/${item.imagePaths!!.size}"
            }


            tvSelect.text = "${item.currentPos+1}/${item.imagePaths!!.size}"

            banner.setOnPageClickListner {
                val bundle = Bundle()
                val intent = Intent(context,ImageShowActivity::class.java)
                val option = ActivityOptions.makeSceneTransitionAnimation(context as BaseActivity<*, *>,
                    *arrayOf<Pair<View, String>>(Pair.create(banner,"photo"))).toBundle()
                bundle.putString("photo",data[position].imagePaths!![item.currentPos])
                bundle.putInt("id",data[position].id)
                intent.putExtras(bundle)
                context.startActivity(intent,option)

            }

            like.setLike(item.like,false)
            like.setOnClickListener {
                item.like = !item.like
                like.setLike( item.like,true)
                onLikeClick?.invoke(item.id,item.like)
            }
        }
    }

    var onLikeClick: ((id: Int, like:Boolean)->Unit)? = null

    fun setOnLikeClickListener(onLikeClick: ((id: Int, like:Boolean)->Unit)){
        this.onLikeClick = onLikeClick
    }




    override fun getItemLayout(): Int = R.layout.item_photo
}