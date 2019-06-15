package example.ken.galleymukey.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import example.ken.galleymukey.R
import kotlinx.android.synthetic.main.dialog_add_comment.view.*
import showmethe.github.kframework.dialog.BaseDialogFragment

class AddCommentDialog : BaseDialogFragment() {

    lateinit var mContext: Context
    private var comPosition  = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(mContext)
        val view = View.inflate(mContext, R.layout.dialog_add_comment, null)

        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(true)

        if(arguments!=null){
            comPosition = arguments!!.getInt("position",0)
        }

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

            btnSend.setOnClickListener {
                onAddComment?.invoke(comPosition,edText.text.toString())
            }
        }

        return dialog
    }


    private var onAddComment : ((position:Int,comment:String)->Unit)? = null

    fun setOnAddCommentListner(onAddComment : ((position:Int,comment:String)->Unit)){
        this.onAddComment = onAddComment
    }


}