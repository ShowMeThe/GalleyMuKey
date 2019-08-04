package com.example.router.router

import android.app.ActivityOptions
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter

import com.example.router.constant.PathConst
import showmethe.github.kframework.R
import showmethe.github.kframework.base.BaseActivity

@Route(path = PathConst.SERVICE_TAG)
class IRouterService : RouterService {
    override fun getCateFragment(): Fragment {
        return ARouter.getInstance().build(PathConst.CATE_FRAGMENT).navigation() as Fragment
    }

    override fun startLikeActivity(bundle: Bundle){
        ARouter.getInstance().build(PathConst.HOME_LIKE_ACTIVITY)
            .with(bundle).navigation()
    }

    override fun getHomeFragment(): Fragment {
        return ARouter.getInstance().build(PathConst.HOME_FRAGMEMNT).navigation() as Fragment
    }


    override fun startToCartActivity() {
        ARouter.getInstance().build(PathConst.CART_ACTIVITY).navigation()
    }

    override fun getCartFragment(): Fragment {
        return  ARouter.getInstance().build(PathConst.CART_FRAGMEMNT).navigation() as Fragment
    }


}