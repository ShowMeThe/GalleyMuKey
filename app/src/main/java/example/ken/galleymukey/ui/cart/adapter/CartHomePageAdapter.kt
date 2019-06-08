package example.ken.galleymukey.ui.cart.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * example.ken.galleymukey.ui.cart.adapter
 * cuvsu
 * 2019/6/5
 **/
class CartHomePageAdapter(var list : List<Fragment>,fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {


    override fun getCount(): Int =list.size

    override fun getItem(position: Int): Fragment = list[position]
}