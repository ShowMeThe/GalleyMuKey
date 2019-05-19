package example.ken.galleymukey.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.parallaxbacklayout.ParallaxBack
import example.ken.galleymukey.R
import example.ken.galleymukey.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.activity_image_show.*
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.widget.StatusBarUtil

@ParallaxBack
class ImageShowActivity : BaseActivity<ViewDataBinding,MainViewModel>() {

    var url = ""
    override fun getViewId(): Int = R.layout.activity_image_show

    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)

    override fun onBundle(bundle: Bundle) {
        url = bundle.getString("photo","")
        TGlide.load(url,image)
    }

    override fun observerUI() {


    }

    override fun init(savedInstanceState: Bundle?) {
        StatusBarUtil.fixToolbarScreen(this,toolbar)



    }

    override fun initListener() {

        ivBack.setOnClickListener {
            finishAfterTransition()
        }


    }


    override fun onBackPressed() {
        finishAfterTransition()
    }

}
