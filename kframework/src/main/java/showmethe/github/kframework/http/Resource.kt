package showmethe.github.kframework.http

import androidx.lifecycle.LiveData

/**
 * showmethe.github.kframework.http
 * cuvsu
 * 2019/3/6
 **/
data class Resource<T> (val status: Int, val data: T?,val code : Int ,val message: String) {

    companion object {
        const val SUCCESS = 1
        const val ERROR = 2
        const val LOADING = 3
    }

}