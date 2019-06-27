package example.ken.galleymukey.ui.mine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.BundleCompat
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.shape.CornerFamily
import example.ken.galleymukey.R
import example.ken.galleymukey.ui.mine.vm.ProfileInfoViewModel
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.include_title_bar.*
import kotlinx.android.synthetic.main.include_title_bar.ivBack
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.util.CreateDrawable
import showmethe.github.kframework.util.widget.StatusBarUtil

class WalletActivity : BaseActivity<ViewDataBinding,ProfileInfoViewModel>() {


    override fun getViewId(): Int = R.layout.activity_wallet
    override fun initViewModel(): ProfileInfoViewModel = createViewModel(ProfileInfoViewModel::class.java)

    override fun onBundle(bundle: Bundle) {


    }

    override fun onLifeCreated(owner: LifecycleOwner) {
    }

    override fun observerUI() {
    }

    override fun init(savedInstanceState: Bundle?) {
        StatusBarUtil.setStatusColor(context,R.color.colorPrimaryDark)

        topBg.background = CreateDrawable.create(context,CornerFamily.ROUNDED,15,ContextCompat.getColor(context,R.color.colorPrimaryDark) ,
            CreateDrawable.CornerType.BOTTOMLEFT,CreateDrawable.CornerType.BOTTMRIGHT)



    }

    override fun initListener() {


        ivBack.setOnClickListener {
            finishAfterTransition()
        }






    }


}
