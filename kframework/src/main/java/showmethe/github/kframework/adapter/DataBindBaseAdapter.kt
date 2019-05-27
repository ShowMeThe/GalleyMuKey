package showmethe.github.kframework.adapter

import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup






/**
 * PackageName: example.ken.com.library.adapter
 * Author : jiaqi Ye
 * Date : 2018/9/18
 * Time : 10:04
 */
abstract class DataBindBaseAdapter<D, V : ViewDataBinding>(var context: Context,
                                                           var data: ObservableArrayList<D>)
    : RecyclerView.Adapter<DataBindingViewHolder<V>>() {



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



    override fun onBindViewHolder(holder: DataBindingViewHolder<V>, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClick?.apply {
                invoke(it,holder.layoutPosition)
            }
        }
        bindItems(holder.binding, data[position], position)
    }


    abstract fun bindItems(binding: V?, item: D, position: Int)

    abstract fun getItemLayout() : Int

    fun inflateItemView(viewGroup: ViewGroup, layoutId: Int): View {
        return LayoutInflater.from(viewGroup.context).inflate(layoutId, viewGroup, false)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<V> {
        val binding = DataBindingUtil.bind<V>(inflateItemView(parent, getItemLayout()))
        return DataBindingViewHolder<V>(binding!!)
    }


    override fun getItemCount(): Int = data.size

    var onItemClick : ((view: View, position: Int) -> Unit)? = null

    fun setOnItemClickListener(onItemClickListener:(view: View, position: Int) -> Unit) {
        this.onItemClick = onItemClickListener
    }




}
