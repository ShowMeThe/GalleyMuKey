package example.ken.galleymukey.ui.main.fragment

import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.ObservableArrayList
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.circularreveal.CircularRevealCompat
import com.google.android.material.circularreveal.CircularRevealWidget
import example.ken.galleymukey.R
import example.ken.galleymukey.databinding.FragmentGalleyBinding
import example.ken.galleymukey.ui.main.MainActivity
import example.ken.galleymukey.ui.main.adapter.GalleyAdapter
import example.ken.galleymukey.ui.main.adapter.PhotoAdapter
import example.ken.galleymukey.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.fragment_galley.*
import showmethe.github.kframework.base.BaseFragment

/**
 * example.ken.galleymukey.ui.main.fragment
 * cuvsu
 * 2019/5/18
 **/
class GalleyFragment : BaseFragment<FragmentGalleyBinding, MainViewModel>() {


    val list = ArrayList<Fragment>()
    val texts = ArrayList<String>()
    lateinit var adapter : GalleyAdapter

    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)
    override fun getViewId(): Int = R.layout.fragment_galley

    override fun onBundle(bundle: Bundle) {

    }

    override fun observerUI() {




    }

    override fun init(savedInstanceState: Bundle?) {
        initVp()


    }

    override fun initListener() {


    }


    fun initVp(){
        list.add(PhotoFragment())
        list.add(HotFragment())
        texts.add("Photo")
        texts.add("Hot")

        adapter = GalleyAdapter(texts,list,childFragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 2
        tab.setupWithViewPager(viewPager,false)
    }


}