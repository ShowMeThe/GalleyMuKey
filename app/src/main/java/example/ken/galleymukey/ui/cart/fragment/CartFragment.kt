package example.ken.galleymukey.ui.cart.fragment

import android.os.Bundle
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import example.ken.galleymukey.R
import example.ken.galleymukey.databinding.FragmentCartBinding
import example.ken.galleymukey.ui.cart.adapter.CartHomePageAdapter
import example.ken.galleymukey.ui.cart.adapter.CartTopAdapter
import example.ken.galleymukey.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.fragment_cart.*

import showmethe.github.kframework.base.BaseFragment

/**
 * example.ken.galleymukey.ui.cart.fragment
 *
 * 2019/5/25
 **/
class CartFragment : BaseFragment<FragmentCartBinding, MainViewModel>() {


    lateinit var adapter : CartTopAdapter
    val list = ObservableArrayList<String>()

    val fragmentList = ArrayList<Fragment>()
    lateinit var fraAdapter: CartHomePageAdapter

    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)
    override fun getViewId(): Int = R.layout.fragment_cart

    override fun onBundle(bundle: Bundle) {

    }

    override fun observerUI() {



    }

    override fun init(savedInstanceState: Bundle?) {

        initAdapter()






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