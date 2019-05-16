package showmethe.github.kframework.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * showmethe.github.kframework.adapter.slideAdapter
 *
 * 2019/4/2
 **/
class DataBindingViewHolder<T : ViewDataBinding>(var binding: T) : RecyclerView.ViewHolder(binding.root)