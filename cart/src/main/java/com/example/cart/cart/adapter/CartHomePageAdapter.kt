package com.example.cart.cart.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * com.example.cart.cart.adapter
 * cuvsu
 * 2019/6/5
 **/
class CartHomePageAdapter(var list : List<Fragment>,fm: FragmentManager, behavior: Int) : FragmentPagerAdapter(fm, behavior) {


    override fun getCount(): Int =list.size

    override fun getItem(position: Int): Fragment = list[position]
}