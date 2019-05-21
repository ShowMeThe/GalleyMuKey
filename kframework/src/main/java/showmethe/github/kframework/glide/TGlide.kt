package showmethe.github.kframework.glide

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.media.MediaMetadataRetriever
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.HashMap

/**
 * showmethe.github.kframework.glide
 *
 * 2019/1/10
 **/
class TGlide private constructor(context: Context){
    private var mRequestManager : RequestManager = GlideApp.with(context.applicationContext)
    private var mContext: Context = context
    private var options : RequestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
    private var transitionOptions : DrawableTransitionOptions = DrawableTransitionOptions()
            .crossFade();
    lateinit var requestOptions: RequestOptions

    companion object {

        @SuppressLint("StaticFieldLeak")
       private lateinit var INSTANT : TGlide

        fun init(context: Context){
                synchronized(TGlide::class){
                    INSTANT = TGlide(context)
                }
        }


        fun load(url: Any, placeholder: Int, error: Int, imageView: ImageView) {
            INSTANT.apply {
                mRequestManager
                        .load(url)
                        .apply(options.centerCrop()
                                .placeholder(placeholder)
                                .error(error))
                        .into(imageView)
            }
        }


        fun load(url: Any, imageView: ImageView) {
            INSTANT.apply {
                mRequestManager
                        .load(url)
                        .apply(options.centerCrop())
                        .into(imageView)
            }
        }

        fun loadNoCrop(url: Any, imageView: ImageView) {
            INSTANT.apply {
                mRequestManager
                        .load(url)
                        .into(imageView)
            }
        }

        fun loadInBackground(url: Any, imageView: ImageView) {
            INSTANT.apply {
                mRequestManager
                        .load(url)
                        .transition(transitionOptions).into(object : DrawableViewTarget(imageView){
                            override fun resourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                                imageView.background = resource
                            }
                        })
            }
        }


        fun loadFile(url: String, imageView: ImageView) {
            INSTANT.apply {
                mRequestManager
                        .load(File(url))
                        .transition(transitionOptions)
                        .into(imageView)
            }
        }

        fun loadWithKey(res: String, key: String, defaultImg: Int, imageView: ImageView) {
            INSTANT.apply {
                mRequestManager
                        .load(res)
                        .apply(options.signature(ObjectKey(key)).placeholder(defaultImg).error(defaultImg))
                        .into(imageView)
            }
        }

        //加载圆型的图片
        fun loadCirclePicture(url: Any, placeholder: Int, error: Int, imageView: ImageView) {
          INSTANT.apply {
              requestOptions = RequestOptions.circleCropTransform()
              mRequestManager
                      .load(url).apply(requestOptions.placeholder(placeholder).error(error)).into(imageView)
          }
        }

        //加载圆型的图片
        fun loadCirclePicture(url: Any, imageView: ImageView) {
            INSTANT.apply {
                requestOptions = RequestOptions.circleCropTransform()
                mRequestManager.load(url).apply(requestOptions).into(imageView)
            }
        }


        //加载圆角的图片
        fun loadRoundPicture(url: Any, placeholder: Int, error: Int, imageView: ImageView) {
            INSTANT.apply {
                requestOptions = RequestOptions.bitmapTransform(GlideRoundTransform())
                mRequestManager
                        .load(url).apply(requestOptions.placeholder(placeholder).error(error))
                        .into(imageView)
            }

        }


        //加载圆角的图片(带半径）
        fun loadRoundPicture(url: Any, imageView: ImageView, radius: Int) {
            INSTANT.apply {
                requestOptions = RequestOptions.bitmapTransform(GlideRoundTransform(radius))
                mRequestManager.load(url).apply(requestOptions)
                        .transition(transitionOptions)
                        .into(imageView)
            }
        }


        fun loadBlurImage(url : Any,imageView: ImageView){
            INSTANT.apply {
                requestOptions = RequestOptions.bitmapTransform(BlurImageTransform())
                mRequestManager.load(url).apply(requestOptions)
                        .transition(transitionOptions)
                        .into(imageView)
            }
        }


        fun loadBlurImage(url : Any,imageView: ImageView,radius: Int){
            INSTANT.apply {
                requestOptions = RequestOptions.bitmapTransform(BlurImageTransform(radius))
                mRequestManager.load(url).apply(requestOptions)
                        .transition(transitionOptions)
                        .into(imageView)
            }
        }

        //读取到Drawable
        fun loadIntoDrawable(url: Any, target: DrawableTarget) {
           INSTANT.apply {
               mRequestManager.load(url).into(target)
           }
        }

        //读取到Bitmap
        fun loadIntoBitmap(url: Any, target: BitmapTarget) {
            INSTANT.apply {
                mRequestManager.asBitmap().load(url).into(target)
            }
        }



        //保存到指定文件夹
        fun saveInDir(storeDir: String, fileName: String, url: String,callBack: (path:String)->Unit) {
            INSTANT.apply {
                mRequestManager.asBitmap().load(url).into(object : BitmapTarget() {
                    override fun resourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        Log.e("222222222","222222222")
                        savePicture(storeDir, fileName, resource){
                            callBack.invoke(it)
                        }
                    }
                })
            }
        }
    }

    //储存到本地操作
    @SuppressLint("CheckResult")
    @Throws(IOException::class)
    private fun savePicture(storeDir: String, fileName: String, bitmap: Bitmap,callBack: (path:String)->Unit) {
        Observable.create(ObservableOnSubscribe<String> { ee ->
            val dir = File(storeDir)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val filePath = dir.path + "/" + fileName + ".png"
            val saveFile = File(filePath)
            if (saveFile.exists()) {
                if (dir.delete()) {
                    dir.mkdirs()
                    saveFile.createNewFile()
                }
            } else {
                saveFile.createNewFile()
            }

            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(saveFile)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos)
            try {
                fos?.flush()
                ee.onNext(saveFile.path)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                Log.e("222222222","${it}")
                callBack.invoke(it)
            }.subscribe()
    }




    /**
     * 获取视频第一帧图片
     * @param videoUrl
     * @param imageView
     */
    @SuppressLint("CheckResult")
    fun getNetVideoBitmap(videoUrl: String, imageView: ImageView) {
        Observable.create(ObservableOnSubscribe<Bitmap> { e ->
            val map = HashMap<String, String>()
            val retriever = MediaMetadataRetriever()
            var bitmap: Bitmap? = null
            try {
                //根据url获取缩略图
                retriever.setDataSource(videoUrl, map)
                //获得第一帧图片
                bitmap = retriever.frameAtTime
                e.onNext(bitmap)
            } catch (ee: IllegalArgumentException) {
                ee.printStackTrace()
            } finally {
                retriever.release()
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    mRequestManager.load(it).into(imageView)
                }
    }



}