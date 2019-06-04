package example.ken.galleymukey.ui.cart.fragment

import android.os.Bundle
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import example.ken.galleymukey.R
import example.ken.galleymukey.databinding.FragmentCartChildBinding
import example.ken.galleymukey.source.dto.GoodsListDto
import example.ken.galleymukey.ui.cart.adapter.CartHomeAdapter
import example.ken.galleymukey.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.fragment_cart_child.*
import showmethe.github.kframework.base.BaseFragment

/**
 * example.ken.galleymukey.ui.cart.fragment
 * cuvsu
 * 2019/6/4
 **/
class CardChildFragment : BaseFragment<FragmentCartChildBinding, MainViewModel>() {



    lateinit var homeAdapter : CartHomeAdapter
    val goodsBean = MutableLiveData<List<GoodsListDto>>()
    var data = ObservableArrayList<GoodsListDto>()
    var type = 0

    companion object{

        fun create(type:Int) : CardChildFragment{
            val bundle = Bundle()
            bundle.putInt("type",type)
            val fragment =  CardChildFragment()
            fragment.arguments = bundle
            return  fragment
        }

    }

    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)

    override fun getViewId(): Int = R.layout.fragment_cart_child

    override fun onBundle(bundle: Bundle) {
        type = bundle.getInt("type",0)
    }

    override fun observerUI() {

        goodsBean.observe(this, Observer {
            it?.apply {
                smrl.showContent()
                refresh.isRefreshing = false
                data.clear()
                data.addAll(this)
            }
        })

    }

    override fun init(savedInstanceState: Bundle?) {
        refresh.setColorSchemeResources(R.color.colorPrimaryDark)
        homeAdapter = CartHomeAdapter(context,data)
        rvBottom.adapter = homeAdapter
        rvBottom.layoutManager = GridLayoutManager(context,2)


        viewModel.getGoodsList(type,goodsBean)

    }

    override fun initListener() {

        refresh.setOnRefreshListener {
            viewModel.getGoodsList(type,goodsBean)
        }


    }
}