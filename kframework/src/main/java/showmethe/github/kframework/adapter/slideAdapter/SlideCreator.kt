package showmethe.github.kframework.adapter.slideAdapter

import androidx.recyclerview.widget.RecyclerView

import java.util.ArrayList

/**
 * Created by Ken on 2018/9/30.
 */
class SlideCreator {

    lateinit var slideItems: SlideItem
    lateinit var menuItemList: ArrayList<MenuItem>
    lateinit var mAdapter: androidx.recyclerview.widget.RecyclerView.Adapter<*>
    lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView


    fun addItemMenu(content: String, textColor: Int, backgroundColor: Int): SlideCreator {

        if (menuItemList == null) {
            menuItemList = ArrayList()
        }
        val item = MenuItem(MenuType.TEXT, content, textColor, backgroundColor)
        menuItemList.add(item)
        return this
    }

    fun addImageItem(resId: Int, imageBackground: Int): SlideCreator {

        if (menuItemList == null) {
            menuItemList = ArrayList()
        }
        val item = MenuItem(MenuType.IMAGE, resId, imageBackground)
        menuItemList!!.add(item)
        return this
    }


    fun bind(adapter: androidx.recyclerview.widget.RecyclerView.Adapter<*>, rightMenuRatio: Float): SlideCreator {
        if (adapter is SlideAdapter<*, *>) {
            mAdapter = adapter
            slideItems = SlideItem(adapter.viewId, rightMenuRatio, menuItemList)
        }
        return this
    }

    fun into(recyclerView: androidx.recyclerview.widget.RecyclerView): SlideCreator {
        this.recyclerView = recyclerView
        recyclerView.adapter = mAdapter
        return this
    }


}
