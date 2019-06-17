package showmethe.github.kframework.parallaxbacklayout.transform

import android.graphics.Canvas
import android.view.View

import showmethe.github.kframework.parallaxbacklayout.widget.ParallaxBackLayout


interface ITransform {
    fun transform(canvas: Canvas, parallaxBackLayout: ParallaxBackLayout, child: View)
}
