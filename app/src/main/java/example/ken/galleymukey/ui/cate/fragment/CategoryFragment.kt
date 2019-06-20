package example.ken.galleymukey.ui.cate.fragment

import android.os.Bundle
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.CateTagBean
import example.ken.galleymukey.bean.NewGoodsBean
import example.ken.galleymukey.databinding.FragmentCategoryBinding
import example.ken.galleymukey.ui.cate.adapter.CateTagAdapter
import example.ken.galleymukey.ui.cate.adapter.NewAdapter
import example.ken.galleymukey.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.fragment_category.smrl
import showmethe.github.kframework.adapter.SpaceItemDecoration
import showmethe.github.kframework.base.BaseFragment

/**
 * example.ken.galleymukey.ui.cate.fragment
 * cuvsu
 * 2019/5/21
 **/
class CategoryFragment : BaseFragment<FragmentCategoryBinding, MainViewModel>() {

    val list = ObservableArrayList<CateTagBean>()
    lateinit var adapter : CateTagAdapter

    val datas = ObservableArrayList<NewGoodsBean>()
    lateinit var newAdapter: NewAdapter

    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)
    override fun getViewId(): Int = R.layout.fragment_category

    override fun onBundle(bundle: Bundle) {

    }

    override fun observerUI() {

        viewModel.cate.observe(this, Observer {
            it?.apply {
                smrl.showContent()
                list.clear()
                list.addAll(this)
            }
        })

        viewModel.keyword.observe(this, Observer {
            it?.apply {
                viewModel.getCate(this)
            }
        })


    }

    override fun init(savedInstanceState: Bundle?) {

        adapter = CateTagAdapter(context,list)
        rvHashTag.adapter = adapter
        rvHashTag.layoutManager = LinearLayoutManager(context,RecyclerView.HORIZONTAL,false)
        rvHashTag.addItemDecoration(SpaceItemDecoration(20,10))

        for(i in 1..10){
            datas.add(NewGoodsBean())
        }

        newAdapter = NewAdapter(context,datas)
        rvNew.adapter = newAdapter
        rvNew.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        rvNew.addItemDecoration(SpaceItemDecoration(30,20))



        viewModel.getCate("")

    }

    override fun initListener() {


    }
}