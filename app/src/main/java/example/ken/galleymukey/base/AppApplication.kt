package example.ken.galleymukey.base

import com.alibaba.android.arouter.launcher.ARouter
import com.example.database.source.DataSourceBuilder
import showmethe.github.kframework.base.BaseApplication

/**
 * example.ken.galleymukey.base
 * cuvsu
 * 2019/5/16
 **/
class AppApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        DataSourceBuilder.build(this)
        ARouter.init(this)
    }
}