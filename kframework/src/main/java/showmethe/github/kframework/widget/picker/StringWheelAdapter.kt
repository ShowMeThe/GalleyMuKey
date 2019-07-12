package showmethe.github.kframework.widget.picker

import android.content.Context
import android.widget.TextView

/**
 * com.example.ken.kmvvm.picker
 *
 * 2019/7/12
 **/

class StringWheelAdapter(context: Context, list: ArrayList<String>) : WheelAdapter<String>(context, list) {

    override fun bindItems(textView: TextView, item: String, position: Int) {
        textView.text = item
    }
}