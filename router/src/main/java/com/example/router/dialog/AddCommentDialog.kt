package com.example.router.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import com.example.router.R

import kotlinx.android.synthetic.main.dialog_add_comment.view.*

import showmethe.github.kframework.dialog.SimpleDialogFragment
import showmethe.github.kframework.dialog.WindowParam

@WindowParam(gravity = Gravity.BOTTOM,outSideCanceled = true)
class AddCommentDialog : SimpleDialogFragment() {

    private var comPosition  = 0

    override fun build(savedInstanceState: Bundle?) {
        buildDialog {
            R.layout.dialog_add_comment
        }.onWindow {

        }.onView {
            if(arguments!=null){
                comPosition = arguments!!.getInt("position",0)
            }

            it.apply {

                btnSend.setOnClickListener {
                    onAddComment?.invoke(comPosition,edText.text.toString())
                }
            }
        }
    }

    private var onAddComment : ((position:Int,comment:String)->Unit)? = null

    fun setOnAddCommentListner(onAddComment : ((position:Int,comment:String)->Unit)){
        this.onAddComment = onAddComment
    }


}