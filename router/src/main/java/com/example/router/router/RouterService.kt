package com.example.router.router

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

interface RouterService : IProvider {

    override fun init(context: Context?) {

    }

    fun getCartFragment() : Fragment


    fun startToCartActivity()
}