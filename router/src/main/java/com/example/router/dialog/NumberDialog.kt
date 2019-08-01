package com.example.router.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.DialogFragment
import com.example.database.bean.LoginBean
import com.example.router.R
import com.example.router.constant.RdenConstant
import com.example.router.dialog.adapter.NumberAdapter

import kotlinx.android.synthetic.main.dialog_number.view.*
import showmethe.github.kframework.dialog.SimpleDialogFragment
import showmethe.github.kframework.dialog.WindowParam
import showmethe.github.kframework.util.rden.RDEN
import showmethe.github.kframework.widget.picker.WheelAdapter
import showmethe.github.kframework.widget.picker.onItemTextChange

@WindowParam(gravity = Gravity.BOTTOM)
class NumberDialog : SimpleDialogFragment() {




    lateinit var adapter:NumberAdapter
    private var num = 1
    var onTextItemChange :((value:Int)->Unit)? = null


    override fun build(savedInstanceState: Bundle?) {
        buildDialog {
            R.layout.dialog_number
        }.onView { view ->
            view.apply {
                val list = ArrayList<Int>()
                for(i in 1..20){
                    list.add(i)
                }
                adapter = NumberAdapter(context,list)

                picker.setWheelAdapter(adapter)

                picker.setOnItemTextChangeListener {
                    num = list[it]
                }

                tvCancel.setOnClickListener {
                    dialog?.dismiss()
                }

                tvConfirm.setOnClickListener {
                    onTextItemChange?.invoke(num)
                }

            }
        }

    }



    fun setOnTextItemChangeListemer(onTextItemChange :((value:Int)->Unit)){
        this.onTextItemChange = onTextItemChange
    }


}