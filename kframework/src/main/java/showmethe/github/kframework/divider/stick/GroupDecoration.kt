package showmethe.github.kframework.divider.stick

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GroupDecoration(var factory : DecorationFactory) : RecyclerView.ItemDecoration() {


    private var  textPaint: TextPaint = TextPaint()
    private var groutPaint: Paint = Paint()
    private var groupHeight = factory.groupHeight


    init {

        textPaint.textSize = factory.textSize
        textPaint.isAntiAlias = true
        textPaint.color = factory.textColor

        groutPaint.color = factory.groupColor

    }



    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val  position = parent.getChildAdapterPosition(view)
        if(factory.groupModule.isFirstPositionInGroup(position)){
            outRect.set(0, groupHeight.toInt(), 0, 0)
        }else{
            outRect.set(0, 0, 0, 0)
        }
    }



    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val  childCount = parent.childCount


        for (i in 0 until childCount) {
            val childView = parent.getChildAt(i)
            val  position = parent.getChildAdapterPosition(childView)
            var  top :Float
            var firstPosition = 0
            var flag = false

            if(parent.layoutManager is LinearLayoutManager){
                firstPosition = (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            }else if(parent.layoutManager is GridLayoutManager){
                firstPosition = (parent.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
            }


            if(factory.groupModule.isFirstPositionInGroup(position)){
                top = childView.top.toFloat()
                drawOver(c,factory.groupModule.getGroupName(position),0f,top - groupHeight, parent.width.toFloat(),top)
            }else{
                if(factory.groupModule.getGroupName(firstPosition + 1) != factory.groupModule.getGroupName(firstPosition)){ //group只有一个时候无法固定
                    c.save()
                    flag = true
                    c.translate(0f,childView.height + childView.top - groupHeight)
                }

                if(factory.groupModule.getGroupName(position) == factory.groupModule.getGroupName(firstPosition)){
                    drawOver(c,factory.groupModule.getGroupName(position),0f,0f, parent.width.toFloat(),groupHeight)
                }
                 if(flag){
                     c.restore()
                 }
            }
        }
    }




    private fun drawOver(c: Canvas, groupName:String, left:Float, top:Float, right:Float, bottom:Float){
        c.drawRect(left,top,right, bottom,groutPaint)
        val fm = textPaint.fontMetrics
        val baseLine = bottom  - (groupHeight - (groupHeight - fm.bottom)/2 - 2 * fm.bottom)
        c.drawText(groupName,0f + factory.textMarinStart,baseLine,textPaint)
    }
}