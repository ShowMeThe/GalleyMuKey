package showmethe.github.kframework.adapter.slideAdapter

import androidx.recyclerview.widget.RecyclerView

import java.util.ArrayList

/**
 * Created by Ken on 2018/9/30.
 */
class SlideCreator {

    lateinit var slideItems: SlideItem
    lateinit var menuItemList: ArrayList<MenuItem>
    lateinit var mAdapter: RecyclerView.Adapter<*>
    lateinit var recyclerView: RecyclerView


    fun addItemMenu(content: String, textColor: Int, backgroundColor: Int): SlideCreator {

        val item = MenuItem(MenuType.TEXT, content, textColor, backgroundColor)
        menuItemList.add(item)
        return this
    }

    fun addImageItem(resId: Int, imageBackground: Int): SlideCreator {

        val item = MenuItem(MenuType.IMAGE, resId, imageBackground)
        menuItemList.add(item)
        return this
    }


    fun bind(adapter: RecyclerView.Adapter<*>, rightMenuRatio: Float): SlideCreator {
        if (adapter is SlideAdapter<*, *>) {
            mAdapter = adapter
            slideItems = SlideItem(adapter.viewId, rightMenuRatio, menuItemList)
        }
        return this
    }

    fun into(recyclerView: RecyclerView): SlideCreator {
        this.recyclerView = recyclerView
        recyclerView.adapter = mAdapter
        return this
    }


}
