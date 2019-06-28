package example.ken.galleymukey.ui.cart

import android.os.Bundle
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.CartListBean
import example.ken.galleymukey.bean.OrderListBean
import example.ken.galleymukey.dialog.CheckOutDialog
import example.ken.galleymukey.source.dto.OrderDto
import example.ken.galleymukey.ui.cart.adapter.CartListAdapter
import example.ken.galleymukey.ui.cart.vm.GoodsViewModel
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.include_title.*
import showmethe.github.kframework.base.BaseActivity

class CartActivity : BaseActivity<ViewDataBinding, GoodsViewModel>() {

    lateinit var adapter : CartListAdapter
    val list = ObservableArrayList<CartListBean>()
    val pagerNumber = MutableLiveData<Int>()
    private val dialog = CheckOutDialog()

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
                viewModel.findCartList()
            }
        })

        viewModel.reuslt.observe(this, Observer {
            it?.apply {
                refresh.isRefreshing = false
                list.clear()
                list.addAll(this)
                if(list.isEmpty()){
                    smrl.showEmpty()
                }else{
                    smrl.showContent()
                }
            }
        })

        viewModel.boolean.observe(this, Observer {
            it?.apply {

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
            if(list.isNotEmpty())
            dialog.show(supportFragmentManager,"")
        }

        dialog.setOnButtonCheckListener {
            val data  = ArrayList<OrderDto>()
            for(dto in adapter.data){
                if(dto.check){
                    val bean = OrderDto()
                    bean.orderGoodsId = dto.goodsId
                    bean.cardId = dto.cardId
                    bean.totalCount = dto.count
                    bean.totalPrice = dto.count * dto.price.toDouble()
                    data.add(bean)
                }
            }
            if (data.isNotEmpty()){
                viewModel.addOrder(data)
            }
            dialog.dismiss()
        }

    }


}
