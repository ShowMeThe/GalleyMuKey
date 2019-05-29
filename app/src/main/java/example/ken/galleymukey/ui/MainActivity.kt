package example.ken.galleymukey.ui

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.picker.MaterialDateRangePickerDialogFragment
import com.google.android.material.tabs.TabLayout
import example.ken.galleymukey.R

import example.ken.galleymukey.ui.cart.fragment.CartFragment
import example.ken.galleymukey.ui.cate.fragment.CateFragment
import example.ken.galleymukey.ui.main.fragment.GalleyFragment
import example.ken.galleymukey.ui.main.vm.MainViewModel
import example.ken.galleymukey.ui.mine.ProfileInfoActivity
import kotlinx.android.synthetic.main.activity_main.*
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.util.widget.StatusBarUtil

class MainActivity : BaseActivity<ViewDataBinding,MainViewModel>() {


    override fun getViewId(): Int =R.layout.activity_main
    override fun initViewModel(): MainViewModel =createViewModel(MainViewModel::class.java)

    override fun onBundle(bundle: Bundle) {

    }

    override fun observerUI() {
    }

    override fun init(savedInstanceState: Bundle?) {
        StatusBarUtil.fixToolbarScreen(this,toolbar)
        setContainer()
        setSupportActionBar(bottomBar)
        initTab()
        switchFragment(0)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.home ->{
                drawer.openDrawer(Gravity.LEFT)
            }
        }
        return true
    }


    override fun initListener() {

        drawer.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerOpened(drawerView: View) {
                drawerView.isClickable = true

            }
        })

        ivHead.setOnClickListener {
            startActivity(null,ProfileInfoActivity::class.java)
        }


    }


    fun setContainer(){
        val layoutParams = container.layoutParams
        layoutParams.width = (screenWidth*0.75).toInt()
        container.layoutParams = layoutParams
    }


    fun initTab(){
        tab.addTab(tab.newTab().setIcon(ContextCompat.getDrawable(context,R.mipmap.photo)))
        tab.addTab(tab.newTab().setIcon(ContextCompat.getDrawable(context,R.mipmap.category)))
        tab.addTab(tab.newTab().setIcon(ContextCompat.getDrawable(context,R.mipmap.shopping)))

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
            2 ->  replaceFragment(CartFragment::class.java.name)
        }
    }

    override fun onBackPressed() {
        if(!drawer.isDrawerOpen(Gravity.LEFT)){
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
        }else{
            drawer.closeDrawer(Gravity.LEFT)
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
