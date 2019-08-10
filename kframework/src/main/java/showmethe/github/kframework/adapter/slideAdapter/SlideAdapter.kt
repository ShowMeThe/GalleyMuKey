package showmethe.github.kframework.adapter.slideAdapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import showmethe.github.kframework.R

import java.util.ArrayList
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.util.widget.ScreenSizeUtil

/**
 * PackageName: example.ken.com.library.adapter
 * Author : jiaqi Ye
 * Date : 2018/9/30
 * Time : 16:58
 *c
 */
abstract class SlideAdapter<D, V : RecyclerView.ViewHolder>(var context: Context,
                                                            var mData: ObservableArrayList<D>) : RecyclerView.Adapter<V>() {
    private var mSlideItems: SlideItem? = null
    private var mRecycleView: RecyclerView? = null
    private var mItemViewWidth: Int = 0
    private var contentView: View? = null
    private var rightMenu: List<View>? = null

    //侧滑相关
    private var mOpenItem: SlideLayout? = null
    internal var scrollingItem: SlideLayout? = null

    abstract val viewId: Int


    private var click: OnSlideClickListener? = null

    init {
        mData.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableArrayList<D>>(){
            override fun onChanged(sender: ObservableArrayList<D>?) {
                notifyDataSetChanged()
            }
            override fun onItemRangeRemoved(sender: ObservableArrayList<D>?, positionStart: Int, itemCount: Int) {
                if (itemCount == 1) {
                    notifyItemRemoved(positionStart)
                    notifyItemRangeChanged(positionStart, itemCount)
                } else {
                    notifyDataSetChanged()
                }
            }

            override fun onItemRangeMoved(sender: ObservableArrayList<D>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
                if (itemCount == 1) {
                    notifyItemMoved(fromPosition, toPosition)
                } else {
                    notifyDataSetChanged()
                }
            }

            override fun onItemRangeInserted(sender: ObservableArrayList<D>, positionStart: Int, itemCount: Int) {
                notifyItemInserted(positionStart)
                notifyItemRangeChanged(positionStart, sender.size - positionStart)
            }

            override fun onItemRangeChanged(sender: ObservableArrayList<D>?, positionStart: Int, itemCount: Int) {
                notifyItemRangeChanged(positionStart, itemCount)

            }

        })
    }


    internal fun holdOpenItem(openItem: SlideLayout) {
        mOpenItem = openItem
    }

    internal fun closeOpenItem() {
        if (mOpenItem != null && mOpenItem!!.isOpen) {
            mOpenItem!!.close()
            mOpenItem = null
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): V {
        return createViewHolder(parent, mSlideItems)
    }

    protected abstract fun onBindData(holder: V, item: D, position: Int)

    protected abstract fun createViewHolder(parent: ViewGroup, slideItem: SlideItem?): V


    override fun onBindViewHolder(viewHolder: V, position: Int) {
        val holder = viewHolder as SlideViewHolder

        val contentView = contentView


        val contentParams = contentView!!.layoutParams as LinearLayout.LayoutParams
        contentParams.width = mItemViewWidth
        contentView.layoutParams = contentParams
        initRightMenu(holder)

        onBindData(viewHolder, mData[position], position)


        contentView.setOnClickListener {
            if (click != null) {
                click!!.onContentItemClick(position)
            }
        }

    }


    fun create(context: Context, parent: ViewGroup, slideItem: SlideItem): View {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_slide_layout, parent, false)
        val linearLayout = itemView.findViewById<LinearLayout>(R.id.slideLayout_ll_container)
        val content: View

        var rightMenu: View?
        content = LayoutInflater.from(context).inflate(slideItem.itemLayoutId, linearLayout, false)
        linearLayout.addView(content)
        this.contentView = content

        val rightList = ArrayList<View>()
        for (item in slideItem.menuItemList) {

            when (item.type) {
                MenuType.TEXT -> {
                    rightMenu = LayoutInflater.from(context).inflate(R.layout.item_slide_right_layout, linearLayout, false)
                    linearLayout.addView(rightMenu)
                    rightList.add(rightMenu)
                }
                MenuType.IMAGE -> {
                    rightMenu = LayoutInflater.from(context).inflate(R.layout.item_slide_right_image, linearLayout, false)
                    linearLayout.addView(rightMenu)
                    rightList.add(rightMenu)
                }
            }

        }
        this.rightMenu = rightList
        return itemView
    }


    private fun initRightMenu(holder: SlideViewHolder) {
        val item = mSlideItems

        for (i in 0 until rightMenu!!.size) {
            val rightMenu = rightMenu!![i]
            val rightMenuParams = rightMenu.layoutParams as LinearLayout.LayoutParams
            rightMenuParams.width = (ScreenSizeUtil.getWidth(holder.itemView.context) * item!!.rightMenuRatio).toInt()
            rightMenu.layoutParams = rightMenuParams

            rightMenu.setOnClickListener {
                if (click != null) {
                    closeOpenItem()
                    click!!.onMenuItemClick(i)
                }
            }

            when (mSlideItems!!.menuItemList[i].type) {
                MenuType.IMAGE -> {
                    holder.setImageRes(mSlideItems!!.menuItemList[i].resId)
                    if (mSlideItems!!.menuItemList[i].backgroundColor != 0) {
                        holder.setBackgroundColor(context, mSlideItems!!.menuItemList[i].backgroundColor, MenuType.IMAGE)
                    }
                }
                MenuType.TEXT -> {
                    holder.setText(mSlideItems!!.menuItemList[i].stringText)
                    if (mSlideItems!!.menuItemList[i].parseTextColor != 0) {
                        holder.setTextColor(context, mSlideItems!!.menuItemList[i].parseTextColor)
                    }

                    if (mSlideItems!!.menuItemList[i].backgroundColor != 0) {
                        holder.setBackgroundColor(context, mSlideItems!!.menuItemList[i].backgroundColor, MenuType.TEXT)
                    }
                }
            }

            (holder.getView<View>(R.id.slideLayout) as SlideLayout).setRightMenuWidth(rightMenuParams.width)

        }


    }

    fun setSlideClickListener(clickListener: OnSlideClickListener) {
        this.click = clickListener
    }

    interface OnSlideClickListener {
        fun onContentItemClick(position: Int)


        fun onMenuItemClick(menuPosition: Int)


    }


    fun setCreator(creator: SlideCreator) {
        this.mSlideItems = creator.slideItems
        this.mRecycleView = creator.recyclerView

        mRecycleView?.adapter = this
        mItemViewWidth = ScreenSizeUtil.getWidth(context)
        mRecycleView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                scrollingItem = null
                closeOpenItem()
            }
        })
    }


    override fun getItemCount(): Int {
        return mData.size
    }
}
