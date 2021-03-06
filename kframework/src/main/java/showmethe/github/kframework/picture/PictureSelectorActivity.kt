package showmethe.github.kframework.picture

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import showmethe.github.kframework.R

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_picture_selector.*
import showmethe.github.kframework.base.AppManager

import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.picture.adapter.PictureAdapter
import showmethe.github.kframework.picture.luban.LubanZip
import java.io.File
import java.lang.Exception

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

    private var count  = 0
    val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
    val mPermissionList = ArrayList<String> ()
    val imgPath = ObservableArrayList<LocalMedia>()
    val imgStringPath = ObservableArrayList<String>()
    lateinit var adapter: PictureAdapter

    override fun getViewId(): Int = R.layout.activity_picture_selector

    override fun initViewModel(): PictureViewModel = createViewModel(PictureViewModel::class.java)

    override fun onBundle(bundle: Bundle) {

    }


    override fun onLifeCreated(owner: LifecycleOwner) {

    }


    override fun observerUI() {

    }
    override fun init(savedInstanceState: Bundle?) {
        initAdapter()
        checkPermission()
    }

    override fun initListener() {

        tvBack.setOnClickListener {
            finish()
        }


        tvConfirm.setOnClickListener {
            starTozip()
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

    /**
     * 开始压缩
     */
    private fun starTozip(){
         if(count == 0){
            return
        }
          val realList = ArrayList<String>()
          for(bean in imgPath){
              if(bean.isCheck){
                  realList.add(bean.path)
              }
          }
           val temp = ArrayList<File>()
           LubanZip.get(this).CPRS(context, realList,object : LubanZip.onFilesComPressCallBack{
            override fun onStart() {

            }

            override fun onSuccess(file: File) {
                temp.add(file)
                if(temp.size == realList.size){
                    val list = ArrayList<PicturesJo>()
                    for((index, _) in realList.withIndex()){
                        val jo = PicturesJo()
                        jo.origin = realList[index]
                        jo.compress = temp[index].path
                        list.add(jo)
                    }

                    val intent = Intent()
                    intent.putParcelableArrayListExtra("PictureSelector",list)
                    setResult(Activity.RESULT_OK,intent)
                    finish()
                }
            }
            override fun onError(e: Throwable?) {

            }
        })
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



     fun findAllPic(){
         val QUERY_URI = MediaStore.Files.getContentUri("external")
         val ORDER_BY = MediaStore.Files.FileColumns._ID + " DESC"
         val PROJECTION = arrayOf(MediaStore.Files.FileColumns._ID,
             MediaStore.MediaColumns.DATA,
             MediaStore.MediaColumns.MIME_TYPE,
             MediaStore.MediaColumns.WIDTH,
             MediaStore.MediaColumns.HEIGHT,
             MediaStore.MediaColumns.DURATION,
             MediaStore.MediaColumns.SIZE,
             MediaStore.MediaColumns.BUCKET_DISPLAY_NAME)


         var c : Cursor? = null
         try {
             c = contentResolver.query(QUERY_URI, PROJECTION, null, null, ORDER_BY)
             while(c!!.moveToNext()){
                 val  id = c.getColumnIndex(MediaStore.Images.Media._ID)
                 val path = MediaStore.Images.Media
                     .EXTERNAL_CONTENT_URI
                     .buildUpon()
                     .appendPath(c.getInt(id).toString()).build().toString()
                 imgStringPath.add(path)
             }
             dismissLoading()
         }catch (e: Exception){
             showToast("Found Image error")
             e.printStackTrace()
         }finally {
             c?.close()
             if(imgStringPath.isNotEmpty()){
                 for(path in imgStringPath){
                     imgPath.add(LocalMedia(path))
                 }

             }
         }

     }

    /**
     * 检查权限
     */
    private fun checkPermission() {
        mPermissionList.clear()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permission)
            }
        }
        if (mPermissionList.isNotEmpty()) {
            ActivityCompat.requestPermissions(this,
                    mPermissionList.toTypedArray(), PERMISSIONS_REQUEST)
        } else {//未授予的权限为空，表示都授予了
            getLocal()
        }
    }


    private  fun getLocal(){
        showLoading()
        findAllPic()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        for (i in permissions.indices) {
            if (ContextCompat.checkSelfPermission(this@PictureSelectorActivity, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i])
            } else {
                ActivityCompat.shouldShowRequestPermissionRationale(this, mPermissionList[i])
            }
        }
        checkPermission()
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}
