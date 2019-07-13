package com.example.router.router

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.router.constant.PathConst

@Route(path = PathConst.SERVICE_TAG)
class IRouterService : RouterService {
    override fun startToCartActivity() {
        ARouter.getInstance().build(PathConst.CART_ACTIVITY).navigation()
    }

    override fun getCartFragment(): Fragment {
        return  ARouter.getInstance().build(PathConst.CART_FRAGMEMNT).navigation() as Fragment
    }


}