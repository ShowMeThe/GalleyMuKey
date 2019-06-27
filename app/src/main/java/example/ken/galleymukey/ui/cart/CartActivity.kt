package example.ken.galleymukey.ui.cart

import android.os.Bundle
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.CartListBean
import example.ken.galleymukey.ui.cart.adapter.CartListAdapter
import example.ken.galleymukey.ui.cart.vm.GoodsViewModel
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.include_title.*
import showmethe.github.kframework.base.BaseActivity

class CartActivity : BaseActivity<ViewDataBinding, GoodsViewModel>() {

    lateinit var adapter : CartListAdapter
    val list = ObservableArrayList<CartListBean>()
    val pagerNumber = MutableLiveData<Int>()

    override fun getViewId(): Int = R.layout.activity_cart
    override fun initViewModel(): GoodsViewModel = createViewModel(GoodsViewModel::class.java)

    override fun onBundle(bundle: Bundle) {

    }

    override fun onLifeCreated(owner: LifecycleOwner) {
        pagerNumber.value = 1
    }

    override fun observerUI() {

        pagerNumber.observe(this, Observer {
            it?.apply {
                viewModel.findCartList(this)
            }
        })

        viewModel.reuslt.observe(this, Observer {
            it?.apply {
                refresh.isRefreshing = false
                if(pagerNumber.value == 1){
                    list.clear()
                }
                list.addAll(this)
            }
        })

    }

    override fun init(savedInstanceState: Bundle?) {
        tvTitle.text = "CART"
        refresh.setColorSchemeResources(R.color.colorPrimaryDark)

        adapter = CartListAdapter(context,list)
        rvCart.adapter = adapter
        rvCart.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)



    }

    override fun initListener() {
        ivBack.setOnClickListener {
            finish()
        }
        refresh.setOnRefreshListener {
            pagerNumber.value  = 1
        }


        fab.setOnClickListener {

        }

    }


}
