package example.ken.galleymukey.ui.mine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.BundleCompat
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.CornerFamily
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.OrderListBean
import example.ken.galleymukey.constant.RdenConstant
import example.ken.galleymukey.databinding.ActivityWalletBinding
import example.ken.galleymukey.ui.mine.adapter.OrderListAdapter
import example.ken.galleymukey.ui.mine.vm.ProfileInfoViewModel
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.activity_wallet.rv

import kotlinx.android.synthetic.main.include_title_bar.ivBack
import showmethe.github.kframework.adapter.SpaceItemDecoration
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.util.CreateDrawable
import showmethe.github.kframework.util.rden.RDEN
import showmethe.github.kframework.util.widget.StatusBarUtil

class WalletActivity : BaseActivity<ActivityWalletBinding,ProfileInfoViewModel>() {

    lateinit var adapter : OrderListAdapter
    val list = ObservableArrayList<OrderListBean>()
    val pagerNumber = MutableLiveData<Int>()

    override fun getViewId(): Int = R.layout.activity_wallet
    override fun initViewModel(): ProfileInfoViewModel = createViewModel(ProfileInfoViewModel::class.java)

    override fun onBundle(bundle: Bundle) {


    }

    override fun onLifeCreated(owner: LifecycleOwner) {
        viewModel.queryAccount(RDEN.get(RdenConstant.account,""))
        pagerNumber.value = 1
    }

    override fun observerUI() {

        viewModel.bean.observe(this, Observer {
            binding?.apply {
                    it?.apply {
                    bean = this
                    executePendingBindings()
                }
            }
        })

        pagerNumber.observe(this, Observer {
            it?.apply {
                viewModel.qureyOrderList(this)
            }
        })

        viewModel.data.observe(this, Observer {
            it?.apply {
                refresh.isRefreshing = false
                if(pagerNumber.value == 1){
                    list.clear()
                }
                list.addAll(this)
                if(size <10){
                    rv.setEnableLoadMore(false)
                }else{
                    rv.setEnableLoadMore(true)
                }
            }
        })

    }

    override fun init(savedInstanceState: Bundle?) {
        StatusBarUtil.setStatusColor(context,R.color.colorPrimaryDark)

        topBg.background = CreateDrawable.create(context,CornerFamily.ROUNDED,15,ContextCompat.getColor(context,R.color.colorPrimaryDark) ,
            CreateDrawable.CornerType.BOTTOMLEFT,CreateDrawable.CornerType.BOTTMRIGHT)

        refresh.setColorSchemeResources(R.color.colorPrimaryDark)

        adapter = OrderListAdapter(context,list)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        rv.addItemDecoration(SpaceItemDecoration(30,20))
    }

    override fun initListener() {


        ivBack.setOnClickListener {
            finishAfterTransition()
        }

        refresh.setOnRefreshListener {
            pagerNumber.value = 1
        }


    }


}
