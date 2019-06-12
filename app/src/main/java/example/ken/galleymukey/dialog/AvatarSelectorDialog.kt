package example.ken.galleymukey.dialog

import android.content.Context
import androidx.fragment.app.DialogFragment

/**
 * example.ken.galleymukey.dialog
 * cuvsu
 * 2019/6/12
 **/
class AvatarSelectorDialog : DialogFragment() {

    lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }




}