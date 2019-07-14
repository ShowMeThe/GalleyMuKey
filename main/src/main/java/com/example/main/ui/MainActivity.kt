package com.example.main.ui

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

import com.example.main.R
import com.google.android.material.tabs.TabLayout

import com.example.router.constant.LiveHelperConstant
import com.example.router.constant.RdenConstant
import com.example.router.dialog.SelectorDialog
import com.example.main.ui.auth.LoginActivity


import com.example.main.ui.mine.ProfileInfoActivity
import com.example.main.ui.mine.WalletActivity
import com.example.main.ui.mine.vm.MainViewModel
import com.example.router.router.RouteServiceManager
import com.example.router.router.RouterService
import com.example.router.util.CircularRevealUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_drawer_item.*
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.livebus.LiveBusHelper
import showmethe.github.kframework.util.rden.RDEN
import showmethe.github.kframework.util.widget.StatusBarUtil


class MainActivity : BaseActivity<ViewDataBinding, MainViewModel>() {


    val dialog = SelectorDialog()
    val colors = IntArray(3)
    var cartFragment : Fragment? = null
    var homeFragment : Fragment? = null
    var cateFragment : Fragment? = null
    var iProvider : RouterService? =null


    override fun getViewId(): Int = R.layout.activity_main
    override fun initViewModel(): MainViewModel =createViewModel(MainViewModel::class.java)
    override fun onBundle(bundle: Bundle) {

    }

    override fun observerUI() {

        viewModel.customBg.observe(this, Observer {
            it?.apply {
                TGlide.load(this,topBg)
            }
        })


    }


    override fun onLifeCreated(owner: LifecycleOwner) {
        viewModel.repository.init(owner)
    }

    override fun setTheme() {
        StatusBarUtil.fixToolbarScreen(this,toolbar)
    }

    override fun init(savedInstanceState: Bundle?) {
        iProvider = RouteServiceManager.provide(RouterService::class.java)

        setContainer()
        setSupportActionBar(bottomBar)
        TGlide.loadCirclePicture(RDEN.get(RdenConstant.avatar,""),ivHead)
        initTab()
        replaceFragment(iProvider!!.getHomeFragment().javaClass.name)



        colors[0] = ContextCompat.getColor(context,R.color.colorPrimary)
        colors[1] = ContextCompat.getColor(context,R.color.colorPrimaryDark)
        colors[2] = ContextCompat.getColor(context,R.color.color_0288d1)


        cartFragment = iProvider?.getCartFragment()
        homeFragment = iProvider?.getHomeFragment()
        cateFragment = iProvider?.getCateFragment()



        viewModel.getCustomBg()

    }




    override fun isLiveEventBusHere(): Boolean = true
    override fun onEventComing(helper: LiveBusHelper) {
        when(helper.code){
            LiveHelperConstant.onAvatarUpdate ->{
                TGlide.loadCirclePicture(RDEN.get(RdenConstant.avatar,""),ivHead)
            }

        }
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
            R.id.cart->{
                iProvider?.startToCartActivity()
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

        llLogout.setOnClickListener {
            RDEN.put(RdenConstant.hasLogin,false)
            startActivity(null,LoginActivity::class.java)
            finishAfterTransition()
        }

        ivChange.setOnClickListener {
            dialog.show(supportFragmentManager,"")
        }

        dialog.setOnTapImageListner {
            viewModel.setCustomBg(it)
        }

        tvAddLike.setOnClickListener {
            val bundle  = Bundle()
            bundle.putString("title",tvAddLike.text.toString())
            val compat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, it, "title")
            iProvider!!.startLikeActivity(bundle,compat)
        }

        tvWallet.setOnClickListener {
            startActivity(null,WalletActivity::class.java)
        }


    }


   private fun setContainer(){
        val layoutParams = container.layoutParams
        layoutParams.width = (screenWidth*0.75).toInt()
        container.layoutParams = layoutParams
    }


    private  fun initTab(){

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
        switchColor(position)
        when(position){
            0 ->  {
                replaceFragment(homeFragment!!.javaClass.name)

                fab.show()
                bottomBar.visibility = View.VISIBLE
            }

            1 ->  {
                replaceFragment(cateFragment!!.javaClass.name)

                fab.hide()
            }
            2 ->  {
                replaceFragment(cartFragment!!.javaClass.name)
                fab.hide()
                bottomBar.visibility = View.VISIBLE
            }
        }
    }


    private fun switchColor(position : Int ){
        val color = ColorStateList.valueOf(colors[position])
        CircularRevealUtils.circularRevealCenter(inner,colors[position],{
        },{
            layout.setBackgroundColor(colors[position])
            btnLogOut.iconTint = color
            btnLogOut.setTextColor(color)
        })
        CircularRevealUtils.revealCenter(bottomBar,colors[position],{
        },{
            bottomBar.backgroundTintList = color
        })
    }


    override fun onBackPressed() {
        if(!drawer.isDrawerOpen(Gravity.LEFT)){
            if(tab.selectedTabPosition == 1){
                viewModel.cateChildManager?.fragments?.apply {
                    if(size > 1){
                        viewModel.catePopBack(true)
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
    private fun replaceFragment(tag: String,id : Int = R.id.frameLayout) {
        var tempFragment = supportFragmentManager.findFragmentByTag(tag)
        val transaction = supportFragmentManager.beginTransaction()
        if (tempFragment == null) {
            try {
                tempFragment = Class.forName(tag).newInstance() as Fragment
                transaction.setCustomAnimations(R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out)

                transaction.add(id, tempFragment, tag)
                    .setMaxLifecycle(tempFragment, Lifecycle.State.RESUMED)

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
                    transaction.show(fragment).setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
                } else {
                    transaction.setCustomAnimations(R.anim.slide_right_in,
                        R.anim.slide_left_out,
                        R.anim.slide_left_in,
                        R.anim.slide_right_out)

                    transaction.hide(fragment).setMaxLifecycle(fragment, Lifecycle.State.STARTED)
                }
            }
        }
        transaction.commitAllowingStateLoss()
    }


}
