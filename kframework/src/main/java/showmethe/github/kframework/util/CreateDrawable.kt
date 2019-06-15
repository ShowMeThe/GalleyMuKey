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

    enum class  CornerType{
        TOPLEFT,TOPRIGHT,BOTTOMLEFT,BOTTMRIGHT,ALL
    }

   fun create(context: Context, @CornerFamily family: Int, radius :Int,color:Int,type:CornerType = CreateDrawable.CornerType.ALL) : Drawable{
        val shapeAppearanceModel = ShapeAppearanceModel()
        when(type){
            CreateDrawable.CornerType.TOPLEFT -> shapeAppearanceModel.setTopLeftCorner(family,radius)
            CreateDrawable.CornerType.TOPRIGHT -> shapeAppearanceModel.setTopRightCorner(family,radius)
            CreateDrawable.CornerType.BOTTOMLEFT -> shapeAppearanceModel.setBottomLeftCorner(family,radius)
            CreateDrawable.CornerType.BOTTMRIGHT -> shapeAppearanceModel.setBottomRightCorner(family,radius)
            CreateDrawable.CornerType.ALL -> shapeAppearanceModel.setAllCorners(family,radius)
        }
        shapeAppearanceModel.setTopLeftCorner(family,radius)
        val drawable = MaterialShapeDrawable.createWithElevationOverlay(context,10f)
        drawable.shapeAppearanceModel = shapeAppearanceModel
        drawable.fillColor = ContextCompat.getColorStateList(context, color)
        return  drawable
    }


}