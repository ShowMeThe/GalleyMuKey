package example.ken.galleymukey.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import example.ken.galleymukey.R
import example.ken.galleymukey.ui.auth.vm.AuthViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.util.widget.StatusBarUtil

class SplashActivity : BaseActivity<ViewDataBinding,AuthViewModel>() {


    override fun getViewId(): Int = R.layout.activity_splash

    override fun initViewModel(): AuthViewModel = createViewModel(AuthViewModel::class.java)

    override fun onBundle(bundle: Bundle) {


    }

    override fun observerUI() {
    }

    override fun init(savedInstanceState: Bundle?) {


        GlobalScope.launch(Dispatchers.Main) {
            delay(3000)
            startActivity(null,LoginActivity::class.java)
            finish()
        }


    }

    override fun initListener() {



    }


}
