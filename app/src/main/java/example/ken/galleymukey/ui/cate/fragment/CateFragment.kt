package example.ken.galleymukey.ui.cate.fragment

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.HashTagBean
import example.ken.galleymukey.ui.main.vm.MainViewModel

import example.ken.galleymukey.util.CreateView
import kotlinx.android.synthetic.main.fragment_cate.*
import showmethe.github.kframework.base.BaseFragment
import java.lang.StringBuilder

/**
 * example.ken.galleymukey.ui.cate.fragment
 * cuvsu
 * 2019/5/20
 **/
class CateFragment  : BaseFragment<ViewDataBinding, MainViewModel>() {


    var temp = ""//初始对比
    var sb = StringBuilder()
    val list = ArrayList<HashTagBean>()


    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)
    override fun getViewId(): Int = R.layout.fragment_cate

    override fun onBundle(bundle: Bundle) {
    }

    override fun observerUI() {

    }

    override fun init(savedInstanceState: Bundle?) {
        unActive()
        addHashTag()

    }

    override fun initListener() {


        btnConfirm.setOnClickListener {
            temp = sb.toString()
            if(sb.toString().isNotEmpty()){
                activeButton()
            }else{
                unActive()
            }
        }




    }


    fun addHashTag(){

        list.add(HashTagBean(false,"YHGJKII"))
        list.add(HashTagBean(false,"12333"))
        list.add(HashTagBean(false,"JJJCC"))
        list.add(HashTagBean(false,"JJJCC"))
        list.add(HashTagBean(false,"DDD"))
        list.add(HashTagBean(false,"ADDCCSS"))
        list.add(HashTagBean(false,"GGG"))

        for((index,bean) in list.withIndex()){
            val checkBox = CreateView.createCheckBox(context,bean.hashTag,bean.active)
            flexLayout.addView(checkBox)
            checkBox.setOnClickListener {
                list[index].active = checkBox.isChecked
                activeButton()
            }
        }

    }

    fun activeButton(){
        sb.clear()
        for(bean in list){
            if(bean.active){
                sb.append(bean.hashTag).append(",")
            }
        }
        if(sb.toString().equals(temp)){
            unActive()
        }else{
            active()
        }
    }


    fun active(){
        btnConfirm.isEnabled = true
        btnConfirm.alpha = 1.0f
    }

    fun unActive(){
        btnConfirm.isEnabled = false
        btnConfirm.alpha = 0.8f
    }

}