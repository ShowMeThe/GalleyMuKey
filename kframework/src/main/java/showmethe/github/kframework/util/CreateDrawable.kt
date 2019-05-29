package showmethe.github.kframework.util

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.IntDef
import androidx.core.content.ContextCompat
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import kotlinx.android.synthetic.main.dialog_canlendar.view.*
import showmethe.github.kframework.R

/**
 * showmethe.github.kframework.util
 *
 * 2019/5/29
 **/
object CreateDrawable {

    fun create(context: Context, @CornerFamily family: Int, radius :Int,color:Int) : Drawable{
        val shapeAppearanceModel = ShapeAppearanceModel()
        shapeAppearanceModel.setTopLeftCorner(family,radius)
        val drawable = MaterialShapeDrawable(shapeAppearanceModel)
        drawable.fillColor = ContextCompat.getColorStateList(context, color)
        return  drawable
    }

}