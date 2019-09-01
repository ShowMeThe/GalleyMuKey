package showmethe.github.kframework.util.extras

import android.media.Image
import androidx.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.bumptech.glide.load.model.GlideUrl
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.widget.animView.BallRotationProgressBar

/**
 * showmethe.github.kframework.adapter
 * cuvsu
 * 2019/2/1
 **/



@BindingAdapter("img")
fun ImageView.setImg(url : Any){
    TGlide.load(url,this@setImg)
}