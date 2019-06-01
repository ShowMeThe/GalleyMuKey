package example.ken.galleymukey.ui.cart.fragment

import android.os.Bundle
import android.util.Log
import android.view.animation.AnticipateOvershootInterpolator
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import example.ken.galleymukey.R
import example.ken.galleymukey.databinding.FragmentCartBinding
import example.ken.galleymukey.source.dto.GoodsListDto
import example.ken.galleymukey.ui.cart.adapter.CartCenterAdapter
import example.ken.galleymukey.ui.cart.adapter.CartHomeAdapter
import example.ken.galleymukey.ui.cart.adapter.CartTopAdapter
import example.ken.galleymukey.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_hot.*
import showmethe.github.kframework.base.BaseFragment

/**
 * example.ken.galleymukey.ui.cart.fragment
 *
 * 2019/5/25
 **/
class CartFragment : BaseFragment<FragmentCartBinding, MainViewModel>() {


    lateinit var adapter : CartTopAdapter
    val list = ObservableArrayList<String>()

    lateinit var centerAdapter : CartCenterAdapter
    var lists = ObservableArrayList<String>()

    lateinit var homeAdapter : CartHomeAdapter
    var data = ObservableArrayList<GoodsListDto>()

    val first =MutableLiveData<Int>()
    val secound = MutableLiveData<Int>()

    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)
    override fun getViewId(): Int = R.layout.fragment_cart

    override fun onBundle(bundle: Bundle) {

    }

    override fun observerUI() {

        viewModel.goodsBean.observe(this, Observer {
            it?.apply {
                data.clear()
                data.addAll(this)
            }
        })

        first.observe(this, Observer {
            it?.apply {
                viewModel.getGoodsList(this,centerAdapter.currentPos)
            }
        })

        secound.observe(this, Observer {
            it?.apply {
                viewModel.getGoodsList(adapter.currentPos,this)
            }
        })

    }

    override fun init(savedInstanceState: Bundle?) {

        initAdapter()


        viewModel.getGoodsList(0,0)

    }





    override fun initListener() {

        adapter.setOnItemClickListener { view, position ->
            adapter.currentPos = position
            adapter.notifyDataSetChanged()
            centerAdapter.currentPos = 0
            first.value = position
            centerAdapter.notifyDataSetChanged()
            rvTop.smoothScrollToPosition(position)
        }


        centerAdapter.setOnItemClickListener { view, position ->
            centerAdapter.currentPos = position
            centerAdapter.notifyDataSetChanged()
            secound.value = position
            rvCenter.smoothScrollToPosition(position)
        }


    }


    fun initAdapter(){

        list.add("Popular")
        list.add("Trending")
        list.add("Reductions")

        adapter = CartTopAdapter(context,list)
        rvTop.adapter= adapter
        rvTop.layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)


        lists.add("All")
        lists.add("Hot Sell")
        lists.add("Coming soon")

        centerAdapter = CartCenterAdapter(context,lists)
        rvCenter.adapter= centerAdapter
        rvCenter.layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)




        homeAdapter = CartHomeAdapter(context,data)
        rvBottom.adapter = homeAdapter
        rvBottom.layoutManager = GridLayoutManager(context,2)



    }


}