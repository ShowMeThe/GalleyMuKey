package showmethe.github.kframework.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.Window

import showmethe.github.kframework.R
import showmethe.github.kframework.widget.animView.RectScaleAnim


class DialogLoading : androidx.fragment.app.DialogFragment() {

    lateinit var mContext: Context
    internal var progressbar: RectScaleAnim? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(mContext)
        val view = View.inflate(mContext, R.layout.dialog_loading_layout, null)
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(false)
        progressbar = view.findViewById(R.id.progressbar)

        if (dialog.window != null) {
            val window = dialog.window
            val dm = DisplayMetrics()
            window?.apply {
              // attributes.dimAmount = 0f  //底层不变暗
               windowManager.defaultDisplay.getMetrics(dm)
               setLayout(dm.widthPixels, window.attributes.height)
               setBackgroundDrawable(ColorDrawable(0x00000000))
               setGravity(Gravity.CENTER)
            }

        }

        return dialog
    }


    override fun show(manager: androidx.fragment.app.FragmentManager, tag: String?) {
        try {
           if(!isAdded){
               super.show(manager, tag)
           }
        }catch (e : Exception){
        }
    }

}
