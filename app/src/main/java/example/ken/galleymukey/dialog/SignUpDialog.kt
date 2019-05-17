package example.ken.galleymukey.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.google.android.material.bottomsheet.BottomSheetBehavior
import androidx.annotation.Nullable
import example.ken.galleymukey.R
import showmethe.github.kframework.util.widget.StatusBarUtil





/**
 *
 * example.ken.galleymukey.dialog
 *
 * 2019/5/17
 **/
class SignUpDialog   : BottomSheetDialogFragment() {




    private var mBehavior: BottomSheetBehavior<*>? = null

    override fun onCreateDialog(@Nullable savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = View.inflate(context, R.layout.dialog_sign_up, null)
        dialog.setContentView(view)
        dialog.window!!.findViewById<View>(example.ken.galleymukey.R.id.design_bottom_sheet)
            .setBackgroundResource(android.R.color.transparent);
        mBehavior = BottomSheetBehavior.from<View>(view.parent as View)

        val window = dialog.window
        val dm = DisplayMetrics()
        window?.apply {
            windowManager.defaultDisplay.getMetrics(dm)
            setDimAmount(0.0f)
            setLayout(dm.widthPixels,   window.attributes.height)
        }


        view?.apply {





        }
        return dialog

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    override fun onStart() {
        super.onStart()
        mBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED


    }

    /**
     * 点击布局里的ImageView，触发的点击事件
     * @param v
     */
    fun hidden(v: View) {
        mBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
    }


}