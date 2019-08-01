package com.example.router.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.DialogFragment
import androidx.viewpager2.widget.ViewPager2

import com.example.router.dialog.adapter.SelectorAdapter
import com.example.database.source.Source
import com.example.router.R
import kotlinx.android.synthetic.main.dialog_selector_img.view.*
import showmethe.github.kframework.dialog.SimpleDialogFragment
import showmethe.github.kframework.dialog.WindowParam
import showmethe.github.kframework.widget.transformer.CardStackTransformer

/**
 * com.example.router.dialog
 * cuvsu
 * 2019/6/12
 **/
@WindowParam(gravity = Gravity.CENTER)
class SelectorDialog : SimpleDialogFragment() {

    lateinit var adapter: SelectorAdapter

    override fun build(savedInstanceState: Bundle?) {
        buildDialog {
            R.layout.dialog_selector_img
        }.onView {
            it.apply {
                val list = ObservableArrayList<String>()
                list.add(Source.get().getBanner()[2])
                list.add(Source.get().getBanner()[4])
                list.add(Source.get().getBanner()[10])
                list.add(Source.get().getBanner()[7])
                list.add(Source.get().getBanner()[11])
                list.add(Source.get().getBanner()[21])
                list.add(Source.get().getBanner()[18])


                adapter = SelectorAdapter(context,list)
                viewPager.adapter = adapter
                viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
                viewPager.offscreenPageLimit = 4
                viewPager.setPageTransformer(CardStackTransformer())

                adapter.setOnTapImageListner {
                    onTapImageListener?.invoke(it)
                }

                ivBack.setOnClickListener {
                    dialog?.dismiss()
                }

            }
        }
    }


    var onTapImageListener:((url:String)->Unit)? = null

    fun setOnTapImageListner( onTapImageListener:((url:String)->Unit)){
        this.onTapImageListener = onTapImageListener
    }

}