package com.example.router.util

import android.content.Context
import android.view.Gravity
import android.widget.CheckBox
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.example.router.R


/**
 * com.example.router.util
 * cuvsu
 * 2019/5/21
 **/
object  CreateView {

    fun createCheckBox(context: Context,value :String,check:Boolean) : CheckBox{
        val checkBox = CheckBox(context)
        checkBox.text = value
        checkBox.isChecked = check
        checkBox.buttonDrawable = null
        checkBox.background = ContextCompat.getDrawable(context, R.drawable.selector_hash_tag_bg)
        checkBox.setTextColor(ContextCompat.getColor(context, R.color.white))
        val layout = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        layout.setMargins(15,30,30,15)
        checkBox.gravity = Gravity.CENTER
        checkBox.layoutParams= layout
        checkBox.textSize = 18f
        checkBox.setPadding(15,10,10,15)
        return checkBox
    }
}