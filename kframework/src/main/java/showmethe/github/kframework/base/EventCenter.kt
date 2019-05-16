package showmethe.github.kframework.base

import android.util.SparseArray

/**
 * PackageName: com.library.activity
 * Author : jiaqi Ye
 * Date : 2018/5/7
 * Time : 14:54
 */

class EventCenter {
    var code: Int = 0
    var data: Any? = null
    var parmas: SparseArray<Any>? = null

    constructor(code: Int, parmas: SparseArray<Any>) {
        this.code = code
        this.parmas = parmas
    }

    constructor(code: Int) {
        this.code = code
    }

    constructor(code: Int, data: Any) {
        this.code = code
        this.data = data
    }
}
