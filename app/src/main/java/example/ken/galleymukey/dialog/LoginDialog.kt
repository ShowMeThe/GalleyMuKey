package example.ken.galleymukey.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import androidx.fragment.app.DialogFragment
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.LoginBean
import example.ken.galleymukey.databinding.DialogLoginBinding
import kotlinx.android.synthetic.main.dialog_login.view.*


/**
 * example.ken.galleymukey.dialog
 * cuvsu
 * 2019/5/16
 **/
class LoginDialog : DialogFragment() {

    lateinit var mContext: Context
    private val bean= LoginBean()
    private var binding : DialogLoginBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(mContext)
        val view = View.inflate(mContext, R.layout.dialog_login, null)
        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(false)


        if (dialog.window != null) {
            val window = dialog.window
            val dm = DisplayMetrics()
            window?.apply {
                windowManager.defaultDisplay.getMetrics(dm)
                setDimAmount(0.0f)
                setLayout(dm.widthPixels, window.attributes.height)
                setBackgroundDrawable(ColorDrawable(0x00000000))
                setGravity(Gravity.CENTER)
                setWindowAnimations(R.style.LeftRightAnim)
            }
        }

        view?.apply {

            binding?.apply {
                bean = this@LoginDialog.bean
                executePendingBindings()
            }


            ivClose.setOnClickListener { dialog.dismiss() }

            btnLogin.setOnClickListener { onLoginGet?.invoke(bean) }


        }


        return dialog
    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDialogDismiss?.invoke()
    }



    var onLoginGet : ((bean : LoginBean)->Unit)? = null

    fun setOnLoingGetListener(onLoginGet : ((bean : LoginBean)->Unit)){
        this.onLoginGet = onLoginGet
    }



    var onDialogDismiss : (()->Unit)? = null

    fun setOnDialogDismissListener (onDialogDismiss : (()->Unit)){
        this.onDialogDismiss = onDialogDismiss
    }


}