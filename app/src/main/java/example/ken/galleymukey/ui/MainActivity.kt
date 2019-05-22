package example.ken.galleymukey.ui

import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import example.ken.galleymukey.R
import example.ken.galleymukey.ui.cate.fragment.CateFragment
import example.ken.galleymukey.ui.main.fragment.GalleyFragment
import example.ken.galleymukey.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import showmethe.github.kframework.base.BaseActivity

class MainActivity : BaseActivity<ViewDataBinding,MainViewModel>() {


    override fun getViewId(): Int =R.layout.activity_main
    override fun initViewModel(): MainViewModel =createViewModel(MainViewModel::class.java)

    override fun onBundle(bundle: Bundle) {

    }

    override fun observerUI() {
    }

    override fun init(savedInstanceState: Bundle?) {
        initTab()
        switchFragment(0)



    }


    override fun initListener() {



    }




    fun initTab(){
        tab.addTab(tab.newTab().setText("Galley").setIcon(ContextCompat.getDrawable(context,R.mipmap.navi_1)))
        tab.addTab(tab.newTab().setText("Category").setIcon(ContextCompat.getDrawable(context,R.mipmap.category)))
        tab.addTab(tab.newTab().setText("Galley").setIcon(ContextCompat.getDrawable(context,R.mipmap.navi_1)))

        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val pos = tab!!.position
                switchFragment(pos)
            }
        })

    }

    fun switchFragment(position:Int){
        when(position){
            0 ->  replaceFragment(GalleyFragment::class.java.name)
            1 ->  replaceFragment(CateFragment::class.java.name)
     /*
            2 ->  replaceFragment(GalleyFragment::class.java.name)*/
        }
    }

    override fun onBackPressed() {
        if(tab.selectedTabPosition == 1){
            viewModel.cateChildManager?.fragments?.apply {
                if(size > 1){
                    viewModel.catePopBack.value = true
                }else{
                    super.onBackPressed()
                }
            }
        }else{
            super.onBackPressed()
        }
    }


    var fragments: List<Fragment>? = null
    private fun replaceFragment(tag: String) {
        var tempFragment = supportFragmentManager.findFragmentByTag(tag)
        val transaction = supportFragmentManager.beginTransaction()
        if (tempFragment == null) {
            try {
                tempFragment = Class.forName(tag).newInstance() as Fragment
                transaction.setCustomAnimations(R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)

                transaction.add(R.id.frameLayout, tempFragment, tag)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fragments  = supportFragmentManager.fragments
        if (fragments != null) {
            for (i in fragments!!.indices) {
                val fragment = fragments!![i]
                if (fragment.tag == tag) {
                    transaction.setCustomAnimations(R.anim.slide_right_in,
                        R.anim.slide_left_out,
                        R.anim.slide_left_in,
                        R.anim.slide_right_out)
                    transaction.show(fragment)
                } else {
                    transaction.setCustomAnimations(R.anim.slide_right_in,
                        R.anim.slide_left_out,
                        R.anim.slide_left_in,
                        R.anim.slide_right_out)

                    transaction.hide(fragment)
                }
            }
        }
        transaction.commitAllowingStateLoss()
    }


}
