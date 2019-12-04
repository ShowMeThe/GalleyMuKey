package showmethe.github.kframework.widget.common

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Checkable
import android.widget.LinearLayout

/**
 * showmethe.github.kframework.widget.common
 * cuvsu
 * 2019/3/9
 */
class CheckableLinearLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr), Checkable {


    private var checked = false

    init {
        isClickable = true
        isLongClickable = true
    }


    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener { v ->
            isChecked = !checked
            l?.onClick(v)
        }
    }

    override fun setOnLongClickListener(l: OnLongClickListener?) {
        super.setOnLongClickListener { v ->
            isChecked = !checked
            l?.onLongClick(v) ?: false
        }
    }


    override fun setChecked(checked: Boolean) {
        this.checked = checked
        refreshDrawableState()
    }


    override fun performClick(): Boolean {
        toggle()
        return super.performClick()
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked)
            View.mergeDrawableStates(drawableState, CHECKED_STATE_SET)
        return drawableState
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        val drawable = background
        if (drawable != null) {
            val myDrawableState = drawableState
            drawable.state = myDrawableState
            invalidate()
        }
    }

    override fun isChecked(): Boolean {
        return checked
    }

    override fun toggle() {
        isChecked = !checked
    }

    public override fun onSaveInstanceState(): Parcelable? {
        // Force our ancestor class to save its state
        val superState = super.onSaveInstanceState()
        val savedState = SavedState(superState!!)
        savedState.checked = isChecked
        return savedState
    }


    private class SavedState : BaseSavedState {

        internal var checked: Boolean = false

        internal constructor(superState: Parcelable) : super(superState)

        private constructor(`in`: Parcel) : super(`in`) {
            checked = `in`.readValue(null) as Boolean
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeValue(checked)
        }

        init {
            object : Parcelable.Creator<SavedState> {
                override fun createFromParcel(`in`: Parcel): SavedState {
                    return SavedState(`in`)
                }

                override fun newArray(size: Int): Array<SavedState?> {
                    return arrayOfNulls(size)
                }
            }

        }
    }

    companion object {
        private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
    }


}
