package com.example.home.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * com.example.home.main.adapter
 * cuvsu
 * 2019/5/18
 **/
class GalleyAdapter(var texts : ArrayList<String>, var list : List<Fragment>, fm: FragmentManager, behavior: Int) :
    FragmentPagerAdapter(fm, behavior) {


    override fun getPageTitle(position: Int): CharSequence? = texts[position]

    override fun getCount(): Int =list.size

    override fun getItem(position: Int): Fragment = list[position]

}