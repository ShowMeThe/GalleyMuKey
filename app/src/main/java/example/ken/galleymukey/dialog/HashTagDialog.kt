package example.ken.galleymukey.dialog

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.HashTagBean

import example.ken.galleymukey.ui.cate.adapter.CateMenuAdapter
import kotlinx.android.synthetic.main.dialog_hash_tag.view.*


/**
 * example.ken.galleymukey.ui.cate
 * cuvsu
 * 2019/5/21
 **/
class HashTagDialog: BottomSheetDialogFragment() {

    private var mBehavior: BottomSheetBehavior<*>? = null
    var beanList  = MutableLiveData<ArrayList<HashTagBean>>()
    val list = ObservableArrayList<HashTagBean>()
    lateinit var adapter : CateMenuAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreate(savedInstanceState)
        val dialog = BottomSheetDialog(context!!, R.style.FullScreenBottomSheet)
        val view = View.inflate(context, R.layout.dialog_hash_tag, null)
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
            list.clear()
            beanList.observe(context as LifecycleOwner, Observer {
                it?.apply {
                    list.addAll(this)
                }
            })

            adapter = CateMenuAdapter(context, list)
            rv.adapter = adapter
            rv.layoutManager = GridLayoutManager(context,3)

            btnConfirm.setOnClickListener {
                onConfirm?.invoke()
            }
        }

        return dialog
    }


   private var onConfirm:(()->Unit)? = null

    fun setOnConfirmClickListener(onConfirm:(()->Unit)){
        this.onConfirm  = onConfirm
    }

}