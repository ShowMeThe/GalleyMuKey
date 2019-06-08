package example.ken.galleymukey.ui.cate.fragment

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import example.ken.galleymukey.R
import example.ken.galleymukey.databinding.FragmentSearchBinding
import example.ken.galleymukey.ui.main.adapter.GalleyAdapter
import example.ken.galleymukey.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.fragment_cate.*
import kotlinx.android.synthetic.main.fragment_search.*
import showmethe.github.kframework.base.BaseFragment

/**
 * example.ken.galleymukey.ui.cate.fragment
 * cuvsu
 * 2019/5/21
 **/
class SearchFragment : BaseFragment<FragmentSearchBinding, MainViewModel>() {

    lateinit var adapter: GalleyAdapter

    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)

    override fun getViewId(): Int = R.layout.fragment_search

    override fun onBundle(bundle: Bundle) {

    }

    override fun observerUI() {


    }

    override fun init(savedInstanceState: Bundle?) {

        initVp()


    }


    fun initVp(){
        val titles = ArrayList<String>()
        titles.add("User")

        val fragments = ArrayList<Fragment>()
        fragments.add(SearchUserFragment())

        adapter = GalleyAdapter(titles,fragments,childFragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 2
        tab.setupWithViewPager(viewPager,false)
    }



    override fun initListener() {


    }
}