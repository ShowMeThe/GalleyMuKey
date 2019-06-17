package example.ken.galleymukey.dialog

import android.app.Dialog
import android.database.Observable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import example.ken.galleymukey.R
import example.ken.galleymukey.dialog.adapter.SeeCommentAdapter
import example.ken.galleymukey.source.dto.CommentDto
import kotlinx.android.synthetic.main.dialog_see_comment.view.*
import kotlinx.android.synthetic.main.fragment_hot.view.*
import java.lang.Exception

class SeeCommentDialog  : BottomSheetDialogFragment() {
    private var mBehavior: BottomSheetBehavior<*>? = null
    lateinit var adapter : SeeCommentAdapter
    val list = ObservableArrayList<CommentDto>()


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)
        val dialog = BottomSheetDialog(context!!, R.style.FullScreenBottomSheet)
        val view = View.inflate(context, R.layout.dialog_see_comment, null)
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(true)
        dialog.window!!.findViewById<View>(R.id.design_bottom_sheet)
            .setBackgroundResource(android.R.color.transparent);
        mBehavior = BottomSheetBehavior.from<View>(view.parent as View)
        val window = dialog.window
        val dm = DisplayMetrics()
        window?.apply {
            setDimAmount(0.0f)
            setLayout(dm.widthPixels, window.attributes.height)
            addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            setWindowAnimations(R.style.AnimBottom)
        }


        view.apply {

            adapter = SeeCommentAdapter(context,list)
            rvComment.adapter = adapter
            rvComment.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)




        }

        return  dialog
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            if(!isAdded){
                val transaction = manager.beginTransaction()
                transaction.add(this, tag)
                transaction.commitAllowingStateLoss()
                transaction.show(this)
            }
        }catch (e: Exception){}
    }


}