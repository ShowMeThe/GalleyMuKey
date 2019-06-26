package example.ken.galleymukey.ui.cart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import example.ken.galleymukey.R
import example.ken.galleymukey.ui.cart.vm.GoodsViewModel
import kotlinx.android.synthetic.main.include_title.*
import showmethe.github.kframework.base.BaseActivity

class CartActivity : BaseActivity<ViewDataBinding, GoodsViewModel>() {

    override fun getViewId(): Int = R.layout.activity_cart
    override fun initViewModel(): GoodsViewModel = createViewModel(GoodsViewModel::class.java)

    override fun onBundle(bundle: Bundle) {

    }

    override fun onLifeCreated(owner: LifecycleOwner) {
        viewModel.findCartList()
    }

    override fun observerUI() {



    }

    override fun init(savedInstanceState: Bundle?) {
        tvTitle.text = "CART"





    }

    override fun initListener() {
        ivBack.setOnClickListener {
            finish()
        }


    }


}
