package example.ken.galleymukey.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.format.DateUtils
import android.view.Menu
import androidx.databinding.ViewDataBinding
import com.parallaxbacklayout.ParallaxBack
import example.ken.galleymukey.R
import example.ken.galleymukey.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.activity_image_show.*
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.widget.StatusBarUtil
import androidx.appcompat.widget.PopupMenu
import android.view.MenuInflater
import android.view.MenuItem
import showmethe.github.kframework.util.system.DateUtil
import java.io.File


@ParallaxBack
class ImageShowActivity : BaseActivity<ViewDataBinding,MainViewModel>(),
    android.widget.PopupMenu.OnMenuItemClickListener {


    companion object{
        private val storeDir =
            Environment.getExternalStorageDirectory().path + File.separator+Environment.DIRECTORY_PICTURES

    }

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

        ivMenu.setOnClickListener {
            val popup = PopupMenu(this, it)
            val inflater = popup.menuInflater
            inflater.inflate(R.menu.pre_view_menu, popup.menu);
            popup.show()
        }

    }
    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.save_item ->{
                TGlide.saveInDir(storeDir, DateUtil.getNowTime(),url){
                    showToast(it)
                }
            }
        }
       return false
    }

    override fun onBackPressed() {
        finishAfterTransition()
    }

}
