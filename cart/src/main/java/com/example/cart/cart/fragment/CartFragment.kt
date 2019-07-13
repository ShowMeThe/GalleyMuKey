package com.example.cart.cart.fragment

import android.os.Bundle
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.cart.R

import com.example.cart.cart.adapter.CartHomePageAdapter
import com.example.cart.cart.adapter.CartTopAdapter
import com.example.cart.cart.vm.GoodsViewModel
import com.example.cart.databinding.FragmentCartBinding
import com.example.router.constant.PathConst
import kotlinx.android.synthetic.main.fragment_cart.*

import showmethe.github.kframework.base.BaseFragment

/**
 * com.example.cart.cart.fragment
 *
 * 2019/5/25
 **/
@Route(path = PathConst.CART_FRAGMEMNT)
class CartFragment : BaseFragment<FragmentCartBinding, GoodsViewModel>() {

    lateinit var adapter : CartTopAdapter
    val list = ObservableArrayList<String>()

    val fragmentList = ArrayList<Fragment>()
    lateinit var fraAdapter: CartHomePageAdapter

    override fun initViewModel(): GoodsViewModel = createViewModel(GoodsViewModel::class.java)
    override fun getViewId(): Int = R.layout.fragment_cart

    override fun onBundle(bundle: Bundle) {

    }

    override fun observerUI() {



    }

    override fun init(savedInstanceState: Bundle?) {

        initAdapter()






    }

    override fun onLifeCreated(owner: LifecycleOwner) {



    }


    override fun initListener() {

        adapter.setOnItemClickListener { view, position ->
            adapter.currentPos = position
            adapter.notifyDataSetChanged()
            rvTop.smoothScrollToPosition(position)
            viewPager.setCurrentItem(position,true)
        }

    }


    fun initAdapter(){

        list.add("Popular")
        list.add("Trending")
        list.add("Reductions")

        adapter = CartTopAdapter(context,list)
        rvTop.adapter= adapter
        rvTop.layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)



        fragmentList.add(CardChildFragment.create(0))
        fragmentList.add(CardChildFragment.create(1))
        fragmentList.add(CardChildFragment.create(2))

        fraAdapter = CartHomePageAdapter(fragmentList,childFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        viewPager.adapter = fraAdapter
        viewPager.offscreenPageLimit = 3

        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener(){
            override fun onPageSelected(position: Int) {
                adapter.currentPos = position
                adapter.notifyDataSetChanged()
                rvTop.smoothScrollToPosition(position)
            }
        })

    }


}