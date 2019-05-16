package showmethe.github.kframework.picture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.load.model.GlideUrl
import kotlinx.android.synthetic.main.activity_pre_view.*
import showmethe.github.kframework.R
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.picture.adapter.PreviewAdapter
import showmethe.github.kframework.picture.luban.LubanZip
import java.io.File

class PreViewActivity : BaseActivity<ViewDataBinding,PictureViewModel>() {

    companion object {
        val imgPath = "imgPath"
        val imgPaths = "imgPaths"
        val mode = "mode"
        val curPos = "curPos"

        fun  startToDetail(context : BaseActivity<*,*>,path:String){
            val bundle  = Bundle()
            bundle.putString(imgPath,path)
            bundle.putInt(mode,PictureSelectorActivity.SINGLE)
            context.startActivity(bundle,PreViewActivity::class.java)
        }

        fun  startToMoreDetail(context : BaseActivity<*,*>,paths:ArrayList<String>,position : Int){
            val bundle  = Bundle()
            bundle.putStringArrayList(imgPaths,paths)
            bundle.putInt(mode,PictureSelectorActivity.MULTI)
            bundle.putInt(curPos,position)
            context.startActivity(bundle,PreViewActivity::class.java)
        }


    }

    private var img = ""
    private var imgList = ArrayList<String>()
    private val realList = ObservableArrayList<String>()
    private var type = 0
    private var positions = 0
    lateinit var adapter: PreviewAdapter

    override fun getViewId(): Int = R.layout.activity_pre_view

    override fun initViewModel(): PictureViewModel = createViewModel(PictureViewModel::class.java)

    override fun onBundle(bundle: Bundle) {
        bundle.apply {
            type  = getInt(mode,PictureSelectorActivity.SINGLE)
            if(type ==  PictureSelectorActivity.MULTI){
                imgList  = getStringArrayList(imgPaths)
                positions = getInt(curPos,0)
            }else{
                img = getString(imgPath,"")
                imgList.add(img)
            }
            realList.addAll(imgList)
        }
    }

    override fun observerUI() {

    }


    override fun init(savedInstanceState: Bundle?) {

        initAdapter()

    }


    fun initAdapter(){
        adapter = PreviewAdapter(context,realList)
        vp.adapter = adapter
        vp.setCurrentItem(positions,true)
        vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }


    override fun initListener() {



        tvBack.setOnClickListener {
            finish()
        }


       vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
           override fun onPageSelected(position: Int) {
               positions = position
           }
       })



        tvConfirm.setOnClickListener {
             when(type){
                 PictureSelectorActivity.MULTI -> startZipFiles()

                 PictureSelectorActivity.SINGLE -> startZipFile()
             }
        }

    }

    fun startZipFiles(){
        LubanZip.get().CPRS(context, realList,object : LubanZip.onFilesComPressCallBack{
            override fun onStart() {
            }

            override fun onSuccess(file: File?) {
            }

            override fun onError(e: Throwable?) {
            }

            override fun onFinish(files: MutableList<File>?) {
            }
        })
    }

    fun startZipFile(){
        LubanZip.get().CPR(context, realList[positions],object : LubanZip.onComPressCallBack{
            override fun onError(e: Throwable?) {

            }

            override fun onStart() {

            }

            override fun onSuccess(file: File?) {

            }
        })
    }


}
