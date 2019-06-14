package example.ken.galleymukey.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import example.ken.galleymukey.R
import example.ken.galleymukey.dialog.adapter.SelectorAdapter
import example.ken.galleymukey.source.Source
import kotlinx.android.synthetic.main.dialog_selector_img.view.*
import showmethe.github.kframework.widget.transformer.CardStackTransformer

/**
 * example.ken.galleymukey.dialog
 * cuvsu
 * 2019/6/12
 **/
class SelectorDialog : DialogFragment() {

    lateinit var mContext: Context
    lateinit var adapter: SelectorAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(mContext)
        val view = View.inflate(mContext, R.layout.dialog_selector_img, null)

        dialog.setContentView(view)
        dialog.setCanceledOnTouchOutside(false)


        if (dialog.window != null) {
            val window = dialog.window
            val dm = DisplayMetrics()
            window?.apply {
                windowManager.defaultDisplay.getMetrics(dm)
                setLayout(dm.widthPixels, dm.heightPixels)
                setBackgroundDrawable(ColorDrawable(0x00000000))
                setGravity(Gravity.CENTER)
                setWindowAnimations(R.style.LeftRightAnim)
            }
        }

        view?.apply {
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
            viewPager.orientation = RecyclerView.HORIZONTAL
            viewPager.offscreenPageLimit = 4
            viewPager.setPageTransformer(CardStackTransformer())

            adapter.setOnTapImageListner {
                onTapImageListener?.invoke(it)
            }
            ivBack.setOnClickListener {
                dialog.dismiss()
            }

        }

        return dialog
    }

    var onTapImageListener:((url:String)->Unit)? = null

    fun setOnTapImageListner( onTapImageListener:((url:String)->Unit)){
        this.onTapImageListener = onTapImageListener
    }

}