package example.ken.galleymukey.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import com.google.android.material.bottomsheet.BottomSheetBehavior
import androidx.annotation.Nullable
import example.ken.galleymukey.R
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.dialog_sign_up.view.*
import showmethe.github.kframework.util.widget.RxCount
import showmethe.github.kframework.util.widget.StatusBarUtil
import java.util.concurrent.TimeUnit


/**
 *
 * example.ken.galleymukey.dialog
 *
 * 2019/5/17
 **/
class SignUpDialog   : BottomSheetDialogFragment() {


    private var mdDisposable: Disposable? = null
    private var mBehavior: BottomSheetBehavior<*>? = null

    override fun onCreateDialog(@Nullable savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)
        val dialog = BottomSheetDialog(context!!,R.style.FullScreenBottomSheet)
        val view = View.inflate(context, R.layout.dialog_sign_up, null)
        dialog.setContentView(view)
        dialog.window!!.findViewById<View>(example.ken.galleymukey.R.id.design_bottom_sheet)
            .setBackgroundResource(android.R.color.transparent);
        mBehavior = BottomSheetBehavior.from<View>(view.parent as View)

        val window = dialog.window
        val dm = DisplayMetrics()
        window?.apply {
            setDimAmount(0.0f)
            setLayout(dm.widthPixels, window.attributes.height)
            addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            setWindowAnimations(R.style.AnimBottom)
        }

        view?.apply {

            ivBack.setOnClickListener { hidden() }

            tvCode.setOnClickListener { start(tvCode,30,1) }


        }
        return dialog

    }


    fun start(tv: TextView,time: Int, interval: Int) {
        tv.isEnabled = false
        mdDisposable = Flowable.intervalRange(0, time.toLong(), 0, interval.toLong(), TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onCodeGet?.invoke(tv) }
            .doOnNext { aLong -> tv.text = "${(time - aLong!!)}s" }
            .doOnComplete {
                tv.isEnabled = true
                tv.text = "GET CODE"
                if (mdDisposable != null) {
                    mdDisposable!!.dispose()
                }
            }.subscribe()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    var onCodeGet : ((view : View)->Unit)? = null

    fun setOnCodeGetListener(onCodeGet : ((view : View)->Unit)){
        this.onCodeGet = onCodeGet
    }



    override fun onDestroyView() {
        if (mdDisposable != null) {
            mdDisposable!!.dispose()
        }
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        mBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
    }



    fun hidden(){
        mBehavior!!.state = BottomSheetBehavior.STATE_HIDDEN
    }



}