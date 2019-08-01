package com.example.router.dialog

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.WindowManager

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.example.database.bean.HashTagBean
import com.example.router.R

import com.example.router.dialog.adapter.CateMenuAdapter
import kotlinx.android.synthetic.main.dialog_hash_tag.view.*
import showmethe.github.kframework.dialog.SimpleBSheetDialogFragment
import showmethe.github.kframework.dialog.WindowParam
import java.lang.StringBuilder


/**
 * com.example.cate.cate
 * cuvsu
 * 2019/5/21
 **/
@WindowParam(gravity = Gravity.BOTTOM,dimAmount = 0.0f)
class HashTagDialog: SimpleBSheetDialogFragment() {


    var beanList  = MutableLiveData<ArrayList<HashTagBean>>()
    val list = ObservableArrayList<HashTagBean>()
    lateinit var adapter : CateMenuAdapter

    override fun build(savedInstanceState: Bundle?) {
        buildDialog {
            R.layout.dialog_hash_tag
        }.onView { view ->

            view.apply {
                list.clear()
                beanList.observe(context as LifecycleOwner, Observer {
                    it?.apply {
                        list.addAll(this)
                    }
                })

                adapter = CateMenuAdapter(context, list)
                rv.adapter = adapter
                rv.layoutManager = GridLayoutManager(context,3)

                btnConfirm.setOnClickListener {
                    val sb = StringBuilder()
                    for(dto in list){
                        if(dto.isActive){
                            sb.append(dto.hashTag).append(",")
                        }
                    }
                    onConfirm?.invoke(sb.toString())
                }
            }
        }
    }

   private var onConfirm:((keywords: String)->Unit)? = null

    fun setOnConfirmClickListener(onConfirm:((keywords: String)->Unit)){
        this.onConfirm  = onConfirm
    }

}