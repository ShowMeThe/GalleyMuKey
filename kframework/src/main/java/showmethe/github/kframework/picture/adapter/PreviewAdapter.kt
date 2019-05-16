package showmethe.github.kframework.picture.adapter

import androidx.databinding.ObservableArrayList
import showmethe.github.kframework.R
import showmethe.github.kframework.adapter.BaseRecyclerViewAdapter
import showmethe.github.kframework.adapter.DataBindBaseAdapter
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.databinding.ItemPreviewBinding
import showmethe.github.kframework.glide.TGlide

/**
 * showmethe.github.kframework.picture.adapter
 * cuvsu
 * 2019/4/6
 **/
class PreviewAdapter(context: BaseActivity<*, *>, data: ObservableArrayList<String>)
    : DataBindBaseAdapter<String, ItemPreviewBinding>(context, data) {
    override fun bindItems(binding: ItemPreviewBinding?, item: String, position: Int) {
        binding?.apply {
            TGlide.loadNoCrop(item,iv)
        }
    }

    override fun getItemLayout(): Int = R.layout.item_preview
}