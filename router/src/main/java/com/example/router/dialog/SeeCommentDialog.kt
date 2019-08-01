package com.example.router.dialog

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.example.router.dialog.adapter.SeeCommentAdapter
import com.example.database.source.dto.CommentDto
import com.example.router.R
import kotlinx.android.synthetic.main.dialog_see_comment.view.*
import showmethe.github.kframework.dialog.SimpleBSheetDialogFragment
import showmethe.github.kframework.dialog.WindowParam
import java.lang.Exception

@WindowParam(dimAmount = 0.0f)
class SeeCommentDialog  : SimpleBSheetDialogFragment() {

    lateinit var adapter : SeeCommentAdapter
    val list = ObservableArrayList<CommentDto>()

    override fun build(savedInstanceState: Bundle?) {
        buildDialog {
            R.layout.dialog_see_comment
        }.onView {
            it.apply {
                adapter = SeeCommentAdapter(context,list)
                rvComment.adapter = adapter
                rvComment.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)

            }
        }
    }
}