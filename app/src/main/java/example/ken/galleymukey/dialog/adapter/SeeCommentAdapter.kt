package example.ken.galleymukey.dialog.adapter

import android.content.Context
import androidx.databinding.ObservableArrayList
import example.ken.galleymukey.R
import example.ken.galleymukey.constant.RdenConstant
import example.ken.galleymukey.databinding.ItemSeeCommentBinding
import example.ken.galleymukey.source.dto.CommentDto
import showmethe.github.kframework.adapter.DataBindBaseAdapter
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.util.rden.RDEN

class SeeCommentAdapter(context: Context, data: ObservableArrayList<CommentDto>) :
    DataBindBaseAdapter<CommentDto, ItemSeeCommentBinding>(context, data) {
    override fun getItemLayout(): Int = R.layout.item_see_comment

    override fun bindItems(binding: ItemSeeCommentBinding?, item: CommentDto, position: Int) {
        binding?.apply {
            bean = item
            executePendingBindings()
            TGlide.loadCirclePicture(RDEN.get(RdenConstant.avatar,""),ivAvatar)
            tvName.text = RDEN.get(RdenConstant.account,"")
        }
    }
}