package showmethe.github.kframework.divider.stick

import android.content.Context
import android.graphics.Color
import androidx.core.content.ContextCompat
import showmethe.github.kframework.divider.stick.module.GroupModule
import showmethe.github.kframework.divider.stick.module.SortModule

class DecorationFactory private constructor(){

    companion object{

        fun newBuilder() : DecorationFactory {
            val instant : DecorationFactory by lazy { DecorationFactory() }
            return  instant
        }
    }


    var textColor = Color.BLACK
    var textSize = 43f
    var textMarinStart = 35f
    var groupHeight = 120f
    var groupColor = Color.WHITE
    lateinit var groupModule : GroupModule
    lateinit var sortModule : SortModule<*>




    fun setTextColor(color:Int) : DecorationFactory {
        this.textColor = color
        return  this
    }

    fun setTextColor(context: Context,color:Int) : DecorationFactory {
        this.textColor = ContextCompat.getColor(context,color)
        return  this
    }

    fun setTextSize(textSize:Float) : DecorationFactory {
        this.textSize = textSize
        return  this
    }

    fun setMarinStart(textMarinStart:Float) : DecorationFactory {
        this.textMarinStart = textMarinStart
        return  this
    }

    fun setGroupHeight(groupHeight:Float) : DecorationFactory {
        this.groupHeight = groupHeight
        return  this
    }

    fun setGroupColor(groupColor:Int) : DecorationFactory {
        this.groupColor = groupColor
        return  this
    }

    fun setGroupColor(context: Context,groupColor:Int) : DecorationFactory {
        this.groupColor = ContextCompat.getColor(context,groupColor)
        return  this
    }

    fun build(sortModule: SortModule<*>) : DecorationFactory {
        this.groupModule = GroupModule()
        this.sortModule = sortModule
        this.groupModule.init(this.sortModule)
        return  this
    }


}