package com.example.cart.cart

import android.os.Bundle
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.cart.R

import com.example.database.bean.CartListBean
import com.example.router.dialog.CheckOutDialog
import com.example.database.source.dto.OrderDto

import com.example.cart.cart.adapter.CartListAdapter
import com.example.cart.cart.vm.GoodsViewModel
import com.example.router.constant.PathConst
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.include_title.*


import showmethe.github.kframework.base.BaseActivity

@Route(path = PathConst.CART_ACTIVITY)
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
                if(dto.isCheck()){
                    val bean = OrderDto()
                    bean.orderGoodsId = dto.getGoodsId()
                    bean.cardId = dto.getCardId()
                    bean.totalCount = dto.getCount()
                    bean.totalPrice = dto.getCount() * dto.getPrice().toDouble()
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
