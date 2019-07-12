package showmethe.github.kframework.widget.picker

import android.content.Context
import android.widget.TextView

class IntegerWheelAdapter(context: Context, list: ArrayList<Int>) : WheelAdapter<Int>(context, list) {
    override fun bindItems(textView: TextView, item: Int, position: Int) {
        textView.text = "$item"
    }
}