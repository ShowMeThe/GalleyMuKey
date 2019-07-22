package com.example.router.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
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
import showmethe.github.kframework.util.rden.RDEN
import showmethe.github.kframework.widget.picker.WheelAdapter
import showmethe.github.kframework.widget.picker.onItemTextChange

class NumberDialog : DialogFragment() {


    lateinit var mContext: Context
    lateinit var adapter:NumberAdapter
    private var num = 1
    var onTextItemChange :((value:Int)->Unit)? = null



    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(mContext)
        val view = View.inflate(mContext, R.layout.dialog_number, null)
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(true)



        if (dialog.window != null) {
            val window = dialog.window
            val dm = DisplayMetrics()
            window?.apply {
                windowManager.defaultDisplay.getMetrics(dm)
                setLayout(dm.widthPixels, window.attributes.height)
                setBackgroundDrawable(ColorDrawable(0x00000000))
                setGravity(Gravity.BOTTOM)
                setWindowAnimations(R.style.LeftRightAnim)
            }
        }


        view?.apply {

            val list = ArrayList<Int>()
            for(i in 1..20){
                list.add(i)
            }
            adapter = NumberAdapter(mContext,list)

            picker.setWheelAdapter(adapter)

            picker.setOnItemTextChangeListener {
                Log.e("222222222222","$it")
                //num = list[it]
            }

            tvCancel.setOnClickListener {
                dialog.dismiss()
            }

            tvConfirm.setOnClickListener {
                onTextItemChange?.invoke(num)
            }

        }
        return dialog
    }


    fun setOnTextItemChangeListemer(onTextItemChange :((value:Int)->Unit)){
        this.onTextItemChange = onTextItemChange
    }


}