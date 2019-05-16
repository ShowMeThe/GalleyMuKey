package showmethe.github.kframework.picture


import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.functions.Function
import io.reactivex.subjects.PublishSubject

/**
 * PackageName: example.ewhale.com.demo
 * Author : jiaqi Ye
 * Date : 2018/9/19
 * Time : 10:54
 */
class RxMedia(activity: FragmentActivity) {
    private val videoFragmentLazy: Lazy<MediaFragment>

    init {
        videoFragmentLazy = getLazySingleton(activity.supportFragmentManager)
    }


    private fun getLazySingleton(fragmentManager: FragmentManager): Lazy<MediaFragment> {
        return object : Lazy<MediaFragment> {

            private var mediaFragment: MediaFragment? = null

            @Synchronized
            override fun get(): MediaFragment {
                if (mediaFragment == null) {
                    mediaFragment = getRxPermissionsFragment(fragmentManager)
                }
                return mediaFragment!!
            }

        }
    }

    private fun getRxPermissionsFragment(fragmentManager: FragmentManager): MediaFragment {
        var VideoFragment = fragmentManager.findFragmentByTag(TAG) as MediaFragment?
        val isNewInstance = VideoFragment == null
        if (isNewInstance) {
            VideoFragment = MediaFragment()
            fragmentManager
                    .beginTransaction()
                    .add(VideoFragment, TAG)
                    .commitNow()
        }
        return VideoFragment!!
    }


    fun capture(type: MediaFragment.MediaType): Observable<String> {
        return Observable.just(TRIGGER).compose(getPath(type))
    }


    private fun <T> getPath(type: MediaFragment.MediaType): ObservableTransformer<T, String> {
        return ObservableTransformer {
            get(type).map(object :Function<String,String>{
                override fun apply(t: String): String {
                    return t ?: ""
                }
            })
        }

    }


    private operator fun get(type: MediaFragment.MediaType): PublishSubject<String> {
        return videoFragmentLazy.get().mediaCapture(type)
    }


    @FunctionalInterface
    private interface Lazy<V> {
        fun get(): V
    }

    companion object {

        internal val TAG = RxMedia::class.java.simpleName

        private val TRIGGER = Any()
    }
}
