package com.example.router.router

import android.app.ActivityOptions
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.facade.template.IProvider
import showmethe.github.kframework.base.BaseActivity

interface RouterService : IProvider {

    override fun init(context: Context?) {

    }

    fun getCateFragment() : Fragment

    fun getHomeFragment() : Fragment

    fun startLikeActivity(bundle: Bundle, compat : ActivityOptionsCompat)

    fun getCartFragment() : Fragment


    fun startToCartActivity()
}