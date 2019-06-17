package showmethe.github.kframework.parallaxbacklayout.transform

import android.graphics.Canvas
import android.view.View
import showmethe.github.kframework.parallaxbacklayout.ViewDragHelper.Companion.EDGE_BOTTOM
import showmethe.github.kframework.parallaxbacklayout.ViewDragHelper.Companion.EDGE_LEFT
import showmethe.github.kframework.parallaxbacklayout.ViewDragHelper.Companion.EDGE_RIGHT
import showmethe.github.kframework.parallaxbacklayout.ViewDragHelper.Companion.EDGE_TOP

import showmethe.github.kframework.parallaxbacklayout.widget.ParallaxBackLayout


/**
 * ParallaxBackLayout
 *
 */

class SlideTransform : ITransform {
    override fun transform(canvas: Canvas, parallaxBackLayout: ParallaxBackLayout, child: View) {
        val mEdgeFlag = parallaxBackLayout.edgeFlag
        val width = parallaxBackLayout.width
        val height = parallaxBackLayout.height
        val leftBar = parallaxBackLayout.systemLeft
        val topBar = parallaxBackLayout.systemTop
        if (mEdgeFlag == EDGE_LEFT) {
            val left = child.left - child.width - leftBar
            canvas.translate(left.toFloat(), 0f)
        } else if (mEdgeFlag == EDGE_TOP) {
            val top = child.top - child.height + topBar
            canvas.translate(0f, top.toFloat())
        } else if (mEdgeFlag == EDGE_RIGHT) {
            val left = child.right - leftBar
            canvas.translate(left.toFloat(), 0f)
            canvas.clipRect(leftBar, 0, width, height)
        } else if (mEdgeFlag == EDGE_BOTTOM) {
            val top = child.bottom - topBar
            canvas.translate(0f, top.toFloat())
            canvas.clipRect(0, topBar, child.right, height)
        }
    }
}
