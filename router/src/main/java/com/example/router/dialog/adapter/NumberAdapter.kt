package com.example.router.dialog.adapter

import android.content.Context
import android.widget.TextView
import showmethe.github.kframework.widget.picker.WheelAdapter


class NumberAdapter(context: Context, list: ArrayList<Int>) : WheelAdapter<Int>(context, list) {
    override fun bindItems(textView: TextView, item: Int, position: Int) {
        textView.text  = item.toString()
    }
}