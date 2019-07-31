package showmethe.github.kframework.parallaxback.transform

import android.graphics.Canvas
import android.view.View

import showmethe.github.kframework.parallaxback.widget.ParallaxBackLayout


interface ITransform {
    fun transform(canvas: Canvas, parallaxBackLayout: ParallaxBackLayout, child: View)
}
