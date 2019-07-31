package showmethe.github.kframework.util

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.IntDef
import androidx.core.content.ContextCompat
import androidx.media.VolumeProviderCompat
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

/**
 * showmethe.github.kframework.util
 *
 * 2019/5/29
 **/


@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@IntDef(CornerType.TOPLEFT, CornerType.TOPRIGHT,CornerType. BOTTMRIGHT,CornerType. BOTTOMLEFT, CornerType.ALL)
annotation class  CornerType{
    companion object{
        const val TOPLEFT = 0x0001
        const val TOPRIGHT = 0x0002
        const val BOTTOMLEFT = 0x0003
        const  val BOTTMRIGHT = 0x0004
        const val ALL = 0x0005
    }
}


fun createDrawable(context: Context, @CornerFamily family: Int, radius :Int,color:Int, @CornerType type: Int) : Drawable{
    val shapeAppearanceModel = ShapeAppearanceModel()
    val rad = (Resources.getSystem().displayMetrics.density * radius).toInt()
    when(type){
        CornerType.TOPLEFT -> shapeAppearanceModel.setTopLeftCorner(family,rad)
        CornerType.TOPRIGHT -> shapeAppearanceModel.setTopRightCorner(family,rad)
        CornerType.BOTTOMLEFT -> shapeAppearanceModel.setBottomLeftCorner(family,rad)
        CornerType.BOTTMRIGHT -> shapeAppearanceModel.setBottomRightCorner(family,rad)
        CornerType.ALL -> shapeAppearanceModel.setAllCorners(family,rad)
    }
    val drawable = MaterialShapeDrawable.createWithElevationOverlay(context,15f)
    drawable.shapeAppearanceModel = shapeAppearanceModel
    drawable.fillColor = ColorStateList.valueOf(color)
    return  drawable
}


fun createDrawable(context: Context, @CornerFamily family: Int, radius :Int,color:Int,@CornerType vararg  types: Int) : Drawable{
    val shapeAppearanceModel = ShapeAppearanceModel()
    val rad = (Resources.getSystem().displayMetrics.density * radius).toInt()
    for(type in types){
        when(type){
            CornerType.TOPLEFT -> shapeAppearanceModel.setTopLeftCorner(family,rad)
            CornerType.TOPRIGHT -> shapeAppearanceModel.setTopRightCorner(family,rad)
            CornerType.BOTTOMLEFT -> shapeAppearanceModel.setBottomLeftCorner(family,rad)
            CornerType.BOTTMRIGHT -> shapeAppearanceModel.setBottomRightCorner(family,rad)
            CornerType.ALL -> shapeAppearanceModel.setAllCorners(family,rad) }
    }
    val drawable = MaterialShapeDrawable.createWithElevationOverlay(context,15f)
    drawable.shapeAppearanceModel = shapeAppearanceModel
    drawable.fillColor = ColorStateList.valueOf(color)
    return  drawable
}



fun createWithStroke(context: Context, @CornerFamily family: Int, radius :Int,color:Int,stroke:Int,@CornerType vararg  types: Int): Drawable{
    val shapeAppearanceModel = ShapeAppearanceModel()
    val rad = (Resources.getSystem().displayMetrics.density * radius).toInt()
    for(type in types){
        when(type){
            CornerType.TOPLEFT -> shapeAppearanceModel.setTopLeftCorner(family,rad)
            CornerType.TOPRIGHT -> shapeAppearanceModel.setTopRightCorner(family,rad)
            CornerType.BOTTOMLEFT -> shapeAppearanceModel.setBottomLeftCorner(family,rad)
            CornerType.BOTTMRIGHT -> shapeAppearanceModel.setBottomRightCorner(family,rad)
            CornerType.ALL -> shapeAppearanceModel.setAllCorners(family,rad) }
    }
    val drawable = MaterialShapeDrawable.createWithElevationOverlay(context,15f)
    drawable.shapeAppearanceModel = shapeAppearanceModel
    drawable.fillColor = ColorStateList.valueOf(color)
    drawable.strokeColor = ColorStateList.valueOf(stroke)
    drawable.strokeWidth = (Resources.getSystem().displayMetrics.density * 1)
    return  drawable
}

