package showmethe.github.kframework.parallaxbacklayout.transform

import android.graphics.Canvas
import android.view.View
import showmethe.github.kframework.parallaxbacklayout.ViewDragHelper.Companion.EDGE_BOTTOM
import showmethe.github.kframework.parallaxbacklayout.ViewDragHelper.Companion.EDGE_LEFT
import showmethe.github.kframework.parallaxbacklayout.ViewDragHelper.Companion.EDGE_RIGHT
import showmethe.github.kframework.parallaxbacklayout.ViewDragHelper.Companion.EDGE_TOP

import showmethe.github.kframework.parallaxbacklayout.widget.ParallaxBackLayout




class CoverTransform : ITransform {
    override fun transform(canvas: Canvas, parallaxBackLayout: ParallaxBackLayout, child: View) {
        val edge = parallaxBackLayout.edgeFlag
        if (edge == EDGE_LEFT) {
            canvas.clipRect(0, 0, child.left, child.bottom)
        } else if (edge == EDGE_TOP) {
            canvas.clipRect(0, 0, child.right, child.top + parallaxBackLayout.systemTop)
        } else if (edge == EDGE_RIGHT) {
            canvas.clipRect(child.right, 0, parallaxBackLayout.width, child.bottom)
        } else if (edge == EDGE_BOTTOM) {
            canvas.clipRect(0, child.bottom, child.right, parallaxBackLayout.height)
        }
    }
}
