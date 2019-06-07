package showmethe.github.kframework.http.rx

import showmethe.github.kframework.http.Resource
import showmethe.github.kframework.http.Resource.Companion.ERROR
import showmethe.github.kframework.http.Resource.Companion.SUCCESS

/**
 * showmethe.github.kframework.http
 *
 * 2019/1/11
 **/

class ResultCallBack<T>: CallBack<T>(){



    private var preLoad:(()->Unit)? = null

    fun onSubscribe(preLoad:(()->Unit)){
       this.preLoad  = preLoad
    }

    private var resource: ((resource: Resource<T>) -> Unit)? = null

    fun onResult(listener: ((response: Resource<T>) -> Unit)) {
        resource = listener
    }

    private var error: ((resource: Resource<T>) -> Unit)? = null

    fun error(listener: ((response: Resource<T>) -> Unit)) {
        error = listener
    }


    override fun onPreLoading() {
        preLoad?.apply {
            invoke()
        }
    }

    override fun success(response: T?, message: String) {
        resource?.apply {
            invoke(Resource(SUCCESS, response, 0, message))
        }
    }

    override fun fail(errCode: Int, message: String) {
        error?.apply {
            invoke(Resource(ERROR, null, errCode, message))
        }
    }
}