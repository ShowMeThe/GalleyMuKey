package showmethe.github.kframework.util.system

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import android.os.IBinder



/**
 * 软键盘工具类
 */
object KeyBoardUtils {

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    fun openKeyboard(mContext: Context, mEditText: EditText) {
        val imm = mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }


    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    fun closeKeyboard(mContext: Context, mEditText: EditText): Boolean {
        val imm = mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return imm.hideSoftInputFromWindow(mEditText.windowToken, 0)
    }


    fun hideSoftKeyboard(mContext: Context) {
        val activity = mContext as Activity
        val view = activity.currentFocus
        if (view != null) {
            val inputMethodManager = mContext.getSystemService(
                    Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


}
