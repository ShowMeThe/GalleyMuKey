package showmethe.github.kframework.picture

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import showmethe.github.kframework.R

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_picture_selector.*

import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.picture.adapter.PictureAdapter
import showmethe.github.kframework.util.widget.StatusBarUtil
import java.io.File

import java.sql.Array
import java.util.*
import kotlin.collections.ArrayList

class PictureSelectorActivity : BaseActivity<ViewDataBinding,PictureViewModel>() {


    companion object {

        val SINGLE = 500
        val MULTI = 600

        private val basePath = Environment.getExternalStorageDirectory().absolutePath + "/DCIM/"

        private val picPath = Environment.getExternalStorageDirectory().absolutePath + "/Pictures/"

        private val photoPath = Environment.getExternalStorageDirectory().absolutePath + "/Photo/"

        private val PERMISSIONS_REQUEST = 200
    }

    private var count  = 0;
    val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
    val mPermissionList = ArrayList<String> ()
    val imgPath = ObservableArrayList<LocalMedia>()
    val imgStringPath = ObservableArrayList<String>()
    lateinit var adapter: PictureAdapter

    override fun getViewId(): Int = R.layout.activity_picture_selector

    override fun initViewModel(): PictureViewModel = createViewModel(PictureViewModel::class.java)

    override fun onBundle(bundle: Bundle) {
    }

    override fun observerUI() {
    }

    override fun init(savedInstanceState: Bundle?) {
        initAdapter()
        CheckPermission()
    }

    override fun initListener() {

        tvBack.setOnClickListener {
            finish()
        }


        tvConfirm.setOnClickListener {

        }


        adapter.setOnItemBoxChangeListener {
            if(adapter.mode == SINGLE){
                imgPath[adapter.currentPos].isCheck = false
                adapter.currentPos = it
                imgPath[it].isCheck = true
            }
            countSize()
        }


        adapter.setOnItemClickListener { view, position ->
            if(adapter.mode == SINGLE){
                PreViewActivity.startToDetail(context,imgPath[position].path)
            }else{

                PreViewActivity.startToMoreDetail(context,imgStringPath,position)
            }

        }

    }


    private fun countSize(){
        count = 0
        for(media in imgPath){
            if(media.isCheck){
                count++
            }
        }
        tvConfirm.text = "确定($count)"
    }


    private fun initAdapter() {
        adapter = PictureAdapter(context,imgPath)
        rvImg.adapter = adapter
        rvImg.layoutManager = GridLayoutManager(context,3)
    }


    @SuppressLint("CheckResult")
    private fun mapPic(rootPath: String){
        val temp = ObservableArrayList<LocalMedia>()
        val root = File(rootPath)
        if(root.exists() && root.isDirectory)
        Observable.fromArray(*root.listFiles()).flatMap({ file -> MapFile.list(file) }).filter { o ->
            (((!o.absolutePath.contains("emoji"))
                    && (!o.absolutePath.contains("thumbnails") )
                    && (o.absolutePath.substringAfter(basePath).length<50)
                    && (o.name.toLowerCase().endsWith(".png"))
                    || o.name.toLowerCase().endsWith(".jpg")
                    || o.name.toLowerCase().endsWith(".jpeg")
                    || o.name.toLowerCase().endsWith(".webp")))
        }.map { o -> o.absolutePath }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(s: String) {
                imgStringPath.add(s)
                temp.add(LocalMedia(s))

            }

            override fun onError(e: Throwable) {

            }

            override fun onComplete() {
                dismissLoading()
                imgPath.addAll(temp)
            }
        })
    }


    private fun CheckPermission() {
        mPermissionList.clear()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permission)
            }
        }
        if (!mPermissionList.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    mPermissionList.toTypedArray(), PERMISSIONS_REQUEST)
        } else {//未授予的权限为空，表示都授予了
            getLocal()
        }
    }


    private  fun getLocal(){
        showLoading()
        mapPic(basePath)
        mapPic(picPath)
        mapPic(photoPath)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: kotlin.Array<out String>, grantResults: IntArray) {
        for (i in permissions.indices) {
            if (ContextCompat.checkSelfPermission(this@PictureSelectorActivity, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i])
            } else {
                ActivityCompat.shouldShowRequestPermissionRationale(this, mPermissionList[i])
            }
        }
        CheckPermission()
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}
