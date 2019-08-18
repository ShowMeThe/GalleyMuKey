package showmethe.github.kframework.divider.stick

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import androidx.recyclerview.widget.RecyclerView
import android.text.TextPaint
import android.graphics.Rect
import android.view.View


class StickDecoration : RecyclerView.ItemDecoration() {

    private var  textPaint: TextPaint = TextPaint()
    private var groutPaint: Paint = Paint()
    private var groupHeight = 80f


    init {

        textPaint.textSize = 24f
        textPaint.isAntiAlias = true
        textPaint.color = Color.WHITE

        groutPaint.color = Color.RED

    }


    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(0, groupHeight.toInt(), 0, 0)
    }




    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val  childCount = parent.childCount
        for (i in 0 until childCount) {
            val childView = parent.getChildAt(i)
            var  top :Float
            if(i == 0){
                drawOver(c,"123",0f,0f, parent.width.toFloat(),groupHeight)
            }else{
                top = childView.top.toFloat()
                drawOver(c,"123",0f,top - groupHeight, parent.width.toFloat(),top)
            }
        }
    }

    private fun drawOver(c: Canvas,groupName:String,left:Float ,top:Float,right:Float,bottom:Float){
        c.drawRect(left,top,right, bottom,groutPaint)
        val fm = textPaint.fontMetrics
        val baseLine = bottom - (fm.bottom - fm.top)/2 - fm.bottom
        c.drawText(groupName,0f,baseLine,textPaint)
    }

}