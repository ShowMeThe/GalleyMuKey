package showmethe.github.kframework.dialog

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import java.lang.Exception

/**
 * showmethe.github.kframework.dialog
 * cuvsu
 * 2019/6/2
 **/
open class BaseDialogFragment : DialogFragment() {


    override fun show(manager: FragmentManager, tag: String?) {
        try {
            if(!isAdded){
                val transaction = manager.beginTransaction()
                transaction.add(this, tag)
                transaction.commitAllowingStateLoss()
                transaction.show(this)
            }
        }catch (e:Exception){}
    }
}