package showmethe.github.kframework.util

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
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
        val rad = (Resources.getSystem().displayMetrics.density * radius).toInt()
        when(type){
            CreateDrawable.CornerType.TOPLEFT -> shapeAppearanceModel.setTopLeftCorner(family,rad)
            CreateDrawable.CornerType.TOPRIGHT -> shapeAppearanceModel.setTopRightCorner(family,rad)
            CreateDrawable.CornerType.BOTTOMLEFT -> shapeAppearanceModel.setBottomLeftCorner(family,rad)
            CreateDrawable.CornerType.BOTTMRIGHT -> shapeAppearanceModel.setBottomRightCorner(family,rad)
            CreateDrawable.CornerType.ALL -> shapeAppearanceModel.setAllCorners(family,rad)
        }
        val drawable = MaterialShapeDrawable.createWithElevationOverlay(context,15f)
        drawable.shapeAppearanceModel = shapeAppearanceModel
       drawable.fillColor = ColorStateList.valueOf(color)
        return  drawable
    }



    fun create(context: Context, @CornerFamily family: Int, radius :Int,color:Int,vararg  types:CornerType) : Drawable{
        val shapeAppearanceModel = ShapeAppearanceModel()
        val rad = (Resources.getSystem().displayMetrics.density * radius).toInt()
        for(type in types){
             when(type){
           CreateDrawable.CornerType.TOPLEFT -> shapeAppearanceModel.setTopLeftCorner(family,rad)
           CreateDrawable.CornerType.TOPRIGHT -> shapeAppearanceModel.setTopRightCorner(family,rad)
           CreateDrawable.CornerType.BOTTOMLEFT -> shapeAppearanceModel.setBottomLeftCorner(family,rad)
           CreateDrawable.CornerType.BOTTMRIGHT -> shapeAppearanceModel.setBottomRightCorner(family,rad)
           CreateDrawable.CornerType.ALL -> shapeAppearanceModel.setAllCorners(family,rad) }
        }
        val drawable = MaterialShapeDrawable.createWithElevationOverlay(context,15f)
        drawable.shapeAppearanceModel = shapeAppearanceModel
        drawable.fillColor = ColorStateList.valueOf(color)
        return  drawable
    }


    fun createWithStroke(context: Context, @CornerFamily family: Int, radius :Int,color:Int,stroke:Int,vararg  types:CornerType) : Drawable{
        val shapeAppearanceModel = ShapeAppearanceModel()
        val rad = (Resources.getSystem().displayMetrics.density * radius).toInt()
        for(type in types){
            when(type){
                CreateDrawable.CornerType.TOPLEFT -> shapeAppearanceModel.setTopLeftCorner(family,rad)
                CreateDrawable.CornerType.TOPRIGHT -> shapeAppearanceModel.setTopRightCorner(family,rad)
                CreateDrawable.CornerType.BOTTOMLEFT -> shapeAppearanceModel.setBottomLeftCorner(family,rad)
                CreateDrawable.CornerType.BOTTMRIGHT -> shapeAppearanceModel.setBottomRightCorner(family,rad)
                CreateDrawable.CornerType.ALL -> shapeAppearanceModel.setAllCorners(family,rad) }
        }
        val drawable = MaterialShapeDrawable.createWithElevationOverlay(context,15f)
        drawable.shapeAppearanceModel = shapeAppearanceModel
        drawable.fillColor = ColorStateList.valueOf(color)
        drawable.strokeColor = ColorStateList.valueOf(stroke)
        drawable.strokeWidth = (Resources.getSystem().displayMetrics.density * 1)
        return  drawable
    }


}