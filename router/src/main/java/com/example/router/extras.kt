package com.example.router

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import showmethe.github.kframework.glide.TGlide

/**
 * example.ken.galleymukey
 * cuvsu
 * 2019/5/18
 **/

@BindingAdapter("cut")
fun ImageView.cut(url:String?){
    url?.apply {
        TGlide.loadCutPicture(url,this@cut,15)
    }
}

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

@BindingAdapter("imgPathCut")
fun ImageView.imgPathCut(url : String?){
    url?.apply {
        TGlide.loadCutPicture(url,this@imgPathCut,10)
    }
}

@BindingAdapter("circle")
fun ImageView.circle(url : String?){
    url?.apply {
        TGlide.loadCirclePicture(url,this@circle)
    }
}