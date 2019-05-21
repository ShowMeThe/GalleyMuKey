package example.ken.galleymukey

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import showmethe.github.kframework.glide.TGlide

/**
 * example.ken.galleymukey
 * cuvsu
 * 2019/5/18
 **/


@BindingAdapter("imgPath")
fun ImageView.setUrl(url : String?){
    url?.apply {
        TGlide.loadNoCrop(url,this@setUrl)
    }
}

@BindingAdapter("imgPathCrop")
fun ImageView.imgPathCrop(url : String?){
    url?.apply {
        TGlide.load(url,this@imgPathCrop)
    }
}

@BindingAdapter("circle")
fun ImageView.circle(url : String?){
    url?.apply {
        TGlide.loadCirclePicture(url,this@circle)
    }
}