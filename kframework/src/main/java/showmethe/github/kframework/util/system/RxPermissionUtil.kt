package showmethe.github.kframework.util.system

import android.app.Activity
import androidx.fragment.app.FragmentActivity

import com.tbruyelle.rxpermissions2.RxPermissions

/**
 * PackageName: com.example.ken.librarypack.Util
 * Author : jiaqi Ye
 * Date : 2018/6/29
 * Time : 11:54
 */

class RxPermissionUtil {

    lateinit var rxPermissions: RxPermissions

    companion object {

        val instance : RxPermissionUtil by lazy { RxPermissionUtil() }
        fun get(): RxPermissionUtil {
            return instance
        }
    }


    fun with(activity: FragmentActivity): RxPermissions {
        rxPermissions = RxPermissions(activity)
        return rxPermissions
    }
}
