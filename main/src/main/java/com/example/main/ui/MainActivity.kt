package com.example.main.ui

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.database.source.DataSourceBuilder
import com.example.database.source.dto.PhotoWallDto

import com.example.main.R
import com.google.android.material.tabs.TabLayout

import com.example.router.constant.LiveHelperConstant
import com.example.router.constant.RdenConstant
import com.example.router.dialog.SelectorDialog
import com.example.main.ui.auth.LoginActivity


import com.example.main.ui.mine.ProfileInfoActivity
import com.example.main.ui.mine.ScanQrActivity
import com.example.main.ui.mine.WalletActivity
import com.example.main.ui.mine.vm.MainViewModel
import com.example.router.dialog.CameraAlbumDialog
import com.example.router.router.RouteServiceManager
import com.example.router.router.RouterService
import com.example.router.util.CircularRevealUtils
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_drawer_item.*
import showmethe.github.kframework.base.BaseActivity

import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.livebus.LiveBusHelper
import showmethe.github.kframework.picture.CameraActivity
import showmethe.github.kframework.picture.PictureSelector
import showmethe.github.kframework.picture.PictureSelectorActivity
import showmethe.github.kframework.util.rden.RDEN
import showmethe.github.kframework.util.widget.StatusBarUtil


class MainActivity : BaseActivity<ViewDataBinding, MainViewModel>() {


    private val dialog = SelectorDialog()
    private val colors = IntArray(3)
    private var cartFragment: Fragment? = null
    private var homeFragment: Fragment? = null
    private var cateFragment: Fragment? = null
    private var iProvider: RouterService? = null


    private val pickDialog = CameraAlbumDialog()

    override fun getViewId(): Int = R.layout.activity_main
    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)
    override fun onBundle(bundle: Bundle) {

    }

    override fun observerUI() {

        viewModel.customBg.observe(this, Observer {
            it?.apply {
                TGlide.load(this, topBg)
            }
        })


    }


    override fun onLifeCreated(owner: LifecycleOwner) {
        viewModel.repository.init(owner)
    }

    override fun setTheme() {
        StatusBarUtil.fixToolbarScreen(this, toolbar)
    }

    override fun init(savedInstanceState: Bundle?) {
        iProvider = RouteServiceManager.provide(RouterService::class.java)

        setContainer()
        setSupportActionBar(bottomBar)
        TGlide.loadCirclePicture(RDEN.get(RdenConstant.avatar, ""), ivHead)
        initTab()
        replaceFragment(iProvider!!.getHomeFragment().javaClass.name)



        colors[0] = ContextCompat.getColor(context, R.color.colorPrimary)
        colors[1] = ContextCompat.getColor(context, R.color.colorPrimaryDark)
        colors[2] = ContextCompat.getColor(context, R.color.color_0288d1)


        cartFragment = iProvider?.getCartFragment()
        homeFragment = iProvider?.getHomeFragment()
        cateFragment = iProvider?.getCateFragment()



        viewModel.getCustomBg()


    }


    override fun isLiveEventBusHere(): Boolean = true
    override fun onEventComing(helper: LiveBusHelper) {
        when (helper.code) {
            LiveHelperConstant.onAvatarUpdate -> {
                TGlide.loadCirclePicture(RDEN.get(RdenConstant.avatar, ""), ivHead)
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                drawer.openDrawer(GravityCompat.START)
            }
            R.id.cart -> {
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
            startActivity(null, ProfileInfoActivity::class.java)
        }

        llLogout.setOnClickListener {
            RDEN.put(RdenConstant.hasLogin, false)
            startActivity(null, LoginActivity::class.java)
            finishAfterTransition()
        }

        ivChange.setOnClickListener {
            dialog.show(supportFragmentManager, "")
        }

        dialog.setOnTapImageListner {
            viewModel.setCustomBg(it)
        }

        tvAddLike.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("title", tvAddLike.text.toString())
            iProvider!!.startLikeActivity(bundle)
        }

        tvWallet.setOnClickListener {
            startActivity(null, WalletActivity::class.java)
        }


        tvScan.setOnClickListener {
            startActivity(null, ScanQrActivity::class.java)
        }


        fab.setOnClickListener {
            pickDialog.show(supportFragmentManager, "camera")
        }

        pickDialog.setOnCameraAlbumListener {
            when (it) {
                0 -> startToCamera()
                1 -> startToAlbum()
            }
        }


    }


    private fun startToCamera(){
        startActivity(null,CameraActivity::class.java)
    }


    private fun startToAlbum() {
        startForResult(null, 5000, PictureSelectorActivity::class.java) { _, _, data ->
            data?.apply {
                val list = PictureSelector.findLocalPictures(data)
                val datas = ArrayList<String>()
                list.forEach {
                    datas.add(it.compress)
                }
                val bean = PhotoWallDto()
                bean.id = System.currentTimeMillis().toInt()
                bean.imageTop = datas
                bean.avatar = RDEN.get(RdenConstant.avatar, "")
                bean.username = RDEN.get(RdenConstant.account, "")
                bean.like = false
                DataSourceBuilder.getPhotoWall().addPhotoBean(bean)
            }
        }
    }


    private fun setContainer() {
        val layoutParams = container.layoutParams
        layoutParams.width = (screenWidth * 0.75).toInt()
        container.layoutParams = layoutParams
    }


    private fun initTab() {

        tab.addTab(tab.newTab().setIcon(ContextCompat.getDrawable(context, R.mipmap.photo)))
        tab.addTab(tab.newTab().setIcon(ContextCompat.getDrawable(context, R.mipmap.category)))
        tab.addTab(tab.newTab().setIcon(ContextCompat.getDrawable(context, R.mipmap.shopping)))

        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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


    fun switchFragment(position: Int) {
        switchColor(position)
        when (position) {
            0 -> {
                replaceFragment(homeFragment!!.javaClass.name)

                fab.show()
                bottomBar.visibility = View.VISIBLE
            }

            1 -> {
                replaceFragment(cateFragment!!.javaClass.name)

                fab.hide()
            }
            2 -> {
                replaceFragment(cartFragment!!.javaClass.name)
                fab.hide()
                bottomBar.visibility = View.VISIBLE
            }
        }
    }


    private fun switchColor(position: Int) {
        val color = ColorStateList.valueOf(colors[position])
        CircularRevealUtils.circularRevealCenter(inner, colors[position], {
        }, {
            layout.setBackgroundColor(colors[position])
            btnLogOut.iconTint = color
            btnLogOut.setTextColor(color)
        })
        CircularRevealUtils.revealCenter(bottomBar, colors[position], {
        }, {
            bottomBar.backgroundTintList = color
        })
    }


    override fun onBackPressed() {
        if (!drawer.isDrawerOpen(GravityCompat.START)) {
            if (tab.selectedTabPosition == 1) {
                viewModel.cateChildManager?.fragments?.apply {
                    if (size > 1) {
                        viewModel.catePopBack(true)
                    } else {
                        super.onBackPressed()
                    }
                }
            } else {
                val intent = Intent(Intent.ACTION_MAIN)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.addCategory(Intent.CATEGORY_HOME)
                startActivity(intent)
            }
        } else {
            drawer.closeDrawer(GravityCompat.START)
        }
    }


    var fragments: List<Fragment>? = null
    private fun replaceFragment(tag: String, id: Int = R.id.frameLayout) {
        var tempFragment = supportFragmentManager.findFragmentByTag(tag)
        val transaction = supportFragmentManager.beginTransaction()
        if (tempFragment == null) {
            try {
                tempFragment = Class.forName(tag).newInstance() as Fragment
                transaction.setCustomAnimations(
                    R.anim.slide_right_in,
                    R.anim.slide_left_out,
                    R.anim.slide_left_in,
                    R.anim.slide_right_out
                )

                transaction.add(id, tempFragment, tag)
                    .setMaxLifecycle(tempFragment, Lifecycle.State.RESUMED)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fragments = supportFragmentManager.fragments
        if (fragments != null) {
            for (i in fragments!!.indices) {
                val fragment = fragments!![i]
                if (fragment.tag == tag) {
                    transaction.setCustomAnimations(
                        R.anim.slide_right_in,
                        R.anim.slide_left_out,
                        R.anim.slide_left_in,
                        R.anim.slide_right_out
                    )
                    transaction.show(fragment)
                } else {
                    transaction.setCustomAnimations(
                        R.anim.slide_right_in,
                        R.anim.slide_left_out,
                        R.anim.slide_left_in,
                        R.anim.slide_right_out
                    )
                    transaction.hide(fragment)
                }
            }
        }
        transaction.commitAllowingStateLoss()
    }


}
