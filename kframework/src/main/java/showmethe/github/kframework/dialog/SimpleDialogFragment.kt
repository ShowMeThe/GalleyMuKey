package showmethe.github.kframework.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.Window
import androidx.annotation.IntDef
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import showmethe.github.kframework.R
import showmethe.github.kframework.util.CornerType
import java.lang.Exception

abstract class SimpleDialogFragment  : DialogFragment() {


    private lateinit var activity : Context

    private var onCreate :(()->Int)? = null

    private var onWindow :((window:Window)->Unit)? = null

    private var onView :((view:View)->Unit)? = null

    abstract fun build(savedInstanceState: Bundle?)

    fun buildDialog(onCreate :(()->Int)) : SimpleDialogFragment{
        this.onCreate = onCreate
        return this
    }


    fun onWindow(onWindow :((window:Window)->Unit)) : SimpleDialogFragment{
        this.onWindow = onWindow
        return this
    }

    fun <T : ViewDataBinding> View.onBindingView(onBindingView :((binding : T?)->Unit)){
        onBindingView.invoke(DataBindingUtil.bind<T>(this))
    }


    fun onView(onView :((view:View)->Unit)) : SimpleDialogFragment{
        this.onView = onView
        return this
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        build(savedInstanceState)
        val viewId = onCreate?.invoke()
        if(viewId!= null){

            val param = javaClass.getAnnotation(WindowParam::class.java)
            val gravity:Int = param.gravity
            val outSideCanceled:Boolean = param.outSideCanceled
            val canceled:Boolean = param.canceled
            val dimAmount :Float = param.dimAmount

            val view = View.inflate(activity, viewId, null)
            val dialog = Dialog(activity)
            dialog.setContentView(view)

            dialog.setCanceledOnTouchOutside(outSideCanceled)
            dialog.setCancelable(canceled)

            val window = dialog.window
            val dm = DisplayMetrics()
            window?.apply {
                windowManager.defaultDisplay.getMetrics(dm)
                setLayout(dm.widthPixels, window.attributes.height)
                setBackgroundDrawable(ColorDrawable(0x00000000))
                setGravity(gravity)
                setWindowAnimations(R.style.LeftRightAnim)
                if(dimAmount!=-1f){
                    setDimAmount(dimAmount)
                }
                onWindow?.invoke(this)
            }
            view?.apply {
                onView?.invoke(this)
            }

            return dialog
        }
        return super.onCreateDialog(savedInstanceState)
    }



    @Target(AnnotationTarget.CLASS)
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    annotation class  WindowParam(val gravity:Int = Gravity.CENTER,
                                  val outSideCanceled:Boolean = true,val canceled:Boolean = true,val dimAmount :Float = -1f)


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