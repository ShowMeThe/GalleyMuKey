package showmethe.github.kframework.parallaxbacklayout.transform

import android.graphics.Canvas
import android.view.View
import showmethe.github.kframework.parallaxbacklayout.ViewDragHelper.Companion.EDGE_BOTTOM
import showmethe.github.kframework.parallaxbacklayout.ViewDragHelper.Companion.EDGE_LEFT
import showmethe.github.kframework.parallaxbacklayout.ViewDragHelper.Companion.EDGE_RIGHT
import showmethe.github.kframework.parallaxbacklayout.ViewDragHelper.Companion.EDGE_TOP

import showmethe.github.kframework.parallaxbacklayout.widget.ParallaxBackLayout



class ParallaxTransform : ITransform {
    override fun transform(canvas: Canvas, parallaxBackLayout: ParallaxBackLayout, child: View) {
        val mEdgeFlag = parallaxBackLayout.edgeFlag
        val width = parallaxBackLayout.width
        val height = parallaxBackLayout.height
        val leftBar = parallaxBackLayout.systemLeft
        val topBar = parallaxBackLayout.systemTop
        if (mEdgeFlag == EDGE_LEFT) {
            val left = (child.left - width) / 2
            canvas.translate(left.toFloat(), 0f)
            canvas.clipRect(0, 0, left + width, child.bottom)
        } else if (mEdgeFlag == EDGE_TOP) {
            val top = (child.top - child.height) / 2
            canvas.translate(0f, top.toFloat())
            canvas.clipRect(0, 0, child.right, child.height + top + topBar)
        } else if (mEdgeFlag == EDGE_RIGHT) {
            val left = (child.left + child.width - leftBar) / 2
            canvas.translate(left.toFloat(), 0f)
            canvas.clipRect(left + leftBar, 0, width, child.bottom)
        } else if (mEdgeFlag == EDGE_BOTTOM) {
            val top = (child.bottom - topBar) / 2
            canvas.translate(0f, top.toFloat())
            canvas.clipRect(0, top + topBar, child.right, height)
        }
    }
}
