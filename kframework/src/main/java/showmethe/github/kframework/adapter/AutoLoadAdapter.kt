package showmethe.github.kframework.adapter


import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import android.os.Handler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_adapter_footer.view.*
import showmethe.github.kframework.R

import java.lang.ref.WeakReference


/**
 * showmethe.github.kframework.adapter
 * cuvsu
 * 2019/2/1
 **/
abstract class AutoLoadAdapter<D, V : ViewDataBinding>(var context: Context,
                                                       var data: ObservableArrayList<D>) :
        RecyclerView.Adapter<DataBindingViewHolder<V>>()   {

    companion object {
        private const  val TYPE_COMMON_VIEW = 10001
        private const  val TYPE_FOOTER_VIEW = 10002
    }

    private var isLoading = false
    private var loadingFail = false
    private var canLoadMore = true

    private var footerView : View? = null
    private var lastPosition = 0
    private var rv : androidx.recyclerview.widget.RecyclerView? = null
    private lateinit var lastPositions : IntArray
    private var staggeredGridLayoutManager : androidx.recyclerview.widget.StaggeredGridLayoutManager? = null
    private var gridManager : androidx.recyclerview.widget.GridLayoutManager? = null

    private val handle = Handler(WeakReference<Handler.Callback>(Handler.Callback {
        when(it.what){
            0 ->{
                footerView?.apply {
                    loadState.text = context.getString(R.string.loadingSucceed)
                    rlLoading.postDelayed(Runnable {
                        isLoading = false
                        rlLoading.visibility = View.GONE
                    },300)
                }

            }

            1 ->{
                footerView?.apply {
                    loadState.text = context.getString(R.string.loadingFailed)
                    rlLoading.postDelayed(Runnable {
                        isLoading = false
                        rlLoading.visibility = View.GONE
                    },300)
                }
            }

            2 ->{
                footerView?.apply {
                    rlLoading.postDelayed(Runnable {
                        rlLoading.visibility = View.VISIBLE
                        loadState.text = context.getString(R.string.no_more_data_to_read)
                        progressbar.visibility = View.GONE
                        isLoading = false
                    },350)
                }
            }

            3 ->{
                footerView?.apply {
                    progressbar.visibility = View.VISIBLE
                    isLoading = false
                }
            }
        }
        true
    }).get())

    init {


        this.data.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableArrayList<D>>(){
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


    private val listener  = object  : androidx.recyclerview.widget.RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
            findLastPosition(recyclerView,dy)
            super.onScrolled(recyclerView, dx, dy)
        }

    }

    private val sizeLookup = object : androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup(){
        override fun getSpanSize(p0: Int): Int {
            if (getItemViewType(p0) == TYPE_FOOTER_VIEW) {
                return gridManager!!.spanCount;
            }
            return 1;
        }
    }

    override fun onViewAttachedToWindow(holder: DataBindingViewHolder<V>) {
        super.onViewAttachedToWindow(holder)
        val position = holder.layoutPosition
        if (getItemViewType(position) == TYPE_FOOTER_VIEW) {
            val lp = holder.itemView.layoutParams

            if (lp != null && lp is androidx.recyclerview.widget.StaggeredGridLayoutManager.LayoutParams) {
                lp.isFullSpan = true
            }
        }
    }


    override fun onAttachedToRecyclerView(recyclerView: androidx.recyclerview.widget.RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        recyclerView.apply {
            rv = this
            recyclerView.addOnScrollListener(listener)
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        handle.removeCallbacksAndMessages(null)
    }

    private fun findLastPosition(recyclerView : androidx.recyclerview.widget.RecyclerView, dy : Int){
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is androidx.recyclerview.widget.GridLayoutManager) {
            lastPosition = (layoutManager as androidx.recyclerview.widget.GridLayoutManager).findLastVisibleItemPosition()
            gridManager  = layoutManager as androidx.recyclerview.widget.GridLayoutManager
            gridManager?.apply {
                spanSizeLookup = sizeLookup
            }
        } else if (layoutManager is androidx.recyclerview.widget.LinearLayoutManager) {
            lastPosition = (layoutManager as androidx.recyclerview.widget.LinearLayoutManager).findLastVisibleItemPosition()
        } else if (layoutManager is androidx.recyclerview.widget.StaggeredGridLayoutManager) {
            staggeredGridLayoutManager = layoutManager as androidx.recyclerview.widget.StaggeredGridLayoutManager
            if (lastPositions == null) {
                lastPositions = IntArray(staggeredGridLayoutManager!!.spanCount)
            }
            staggeredGridLayoutManager!!.findLastVisibleItemPositions(lastPositions)
            lastPosition = findMax(lastPositions)

        } else {
            throw RuntimeException("layoutManager not support")
        }


        if (canLoadMore){
           if((lastPosition >= itemCount -2) && itemCount >= 2 && !isLoading && dy>0){
               isLoading = true
               footerView?.apply {
                   rlLoading.visibility = View.VISIBLE
                   loadState.text = context.getString(R.string.loading)
               }
               onLoadMore?.apply {
                   invoke()
               }
           }
        }
    }

    private fun findMax(lastPositions: IntArray): Int {
        var max = lastPositions[0]
        for (value in lastPositions) {
            if (value > max) {
                max = value
            }
        }
        return max
    }


    fun setCanLoadMore(boolean: Boolean){
        canLoadMore  = boolean
        if(!canLoadMore){
            handle.sendEmptyMessage(2)
        }else{
            handle.sendEmptyMessage(3)
        }
    }

    fun isFinishing(){
        isLoading  = false
        handle.sendEmptyMessage(0)
    }

    fun loadingFail(){
        loadingFail  = false
        handle.sendEmptyMessage(1)
    }

    abstract fun bindItems(binding: V?, item: D, position: Int)

    abstract fun getItemLayout() : Int

    fun inflateItemView(viewGroup: ViewGroup, layoutId: Int): View {
        return LayoutInflater.from(viewGroup.context).inflate(layoutId, viewGroup, false)
    }




    override fun onBindViewHolder(holder: DataBindingViewHolder<V>, position: Int) {
        when(getItemViewType(position)){
            TYPE_COMMON_VIEW->{
                bindItems(holder.binding, data[position], position)

                holder.itemView.setOnClickListener {
                    onItemClick?.apply {
                        invoke(it,holder.layoutPosition)
                    }
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<V> {
        var viewHolder : DataBindingViewHolder<V>?  = null
        when(viewType){
            TYPE_FOOTER_VIEW ->{
                footerView = inflateItemView(parent, R.layout.item_adapter_footer)
                viewHolder =  DataBindingViewHolder<V>(DataBindingUtil.bind<V>(footerView!!)!!)
            }

            TYPE_COMMON_VIEW ->{
                val binding = DataBindingUtil.bind<V>(inflateItemView(parent, getItemLayout()))
                viewHolder =  DataBindingViewHolder<V>(binding!!)
            }
        }
       return viewHolder!!

    }

    override fun getItemViewType(position: Int): Int {
        if(position == itemCount -1 ){
            return  TYPE_FOOTER_VIEW
        }
        return  TYPE_COMMON_VIEW
    }

    var onLoadMore : (()-> Unit)? = null

    fun setOnLoadMoreListener(onLoadMore : (()-> Unit)){
        this.onLoadMore = onLoadMore
    }


    override fun getItemCount(): Int = data.size + 1

    var onItemClick : ((view: View, position: Int) -> Unit)? = null

    fun setOnItemClickListener(onItemClickListener:(view: View, position: Int) -> Unit) {
        this.onItemClick = onItemClickListener
    }



}