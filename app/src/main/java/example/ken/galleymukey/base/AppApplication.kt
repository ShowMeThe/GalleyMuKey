package example.ken.galleymukey.base

import example.ken.galleymukey.source.DataSourceBuilder
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
    }
}