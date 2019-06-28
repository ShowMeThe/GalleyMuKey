package example.ken.galleymukey.dialog

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import example.ken.galleymukey.R
import example.ken.galleymukey.constant.RdenConstant
import kotlinx.android.synthetic.main.dialog_check_out.view.*
import showmethe.github.kframework.util.ToastFactory
import showmethe.github.kframework.util.match.MD5
import showmethe.github.kframework.util.rden.RDEN

class CheckOutDialog   : BottomSheetDialogFragment(){

    private var mBehavior: BottomSheetBehavior<*>? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)
        val dialog = BottomSheetDialog(context!!, R.style.FullScreenBottomSheet)
        val view = View.inflate(context, R.layout.dialog_check_out, null)
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

            btnCheck.setOnClickListener {
               if( edPswd.text.toString() == "123456"){
                   onButtonCheck?.invoke()
               }else{
                   ToastFactory.createToast("Please input correctly password")
               }
            }


        }

        return  dialog
    }


    private var onButtonCheck :(()->Unit)? = null

    fun setOnButtonCheckListener(onButtonCheck :(()->Unit)){
        this.onButtonCheck = onButtonCheck
    }

}