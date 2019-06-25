package example.ken.galleymukey.ui

import android.animation.Animator
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.bumptech.glide.request.transition.Transition
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.circularreveal.CircularRevealHelper
import com.google.android.material.picker.MaterialDateRangePickerDialogFragment
import com.google.android.material.tabs.TabLayout
import example.ken.galleymukey.R
import example.ken.galleymukey.constant.LiveHelperConstant
import example.ken.galleymukey.constant.RdenConstant
import example.ken.galleymukey.dialog.SelectorDialog
import example.ken.galleymukey.ui.auth.LoginActivity

import example.ken.galleymukey.ui.cart.fragment.CartFragment
import example.ken.galleymukey.ui.cate.fragment.CateFragment
import example.ken.galleymukey.ui.main.LikeActivity
import example.ken.galleymukey.ui.main.fragment.GalleyFragment
import example.ken.galleymukey.ui.main.vm.MainViewModel
import example.ken.galleymukey.ui.mine.ProfileInfoActivity
import example.ken.galleymukey.util.CircularRevealUtils
import kotlinx.android.synthetic.main.activity_goods_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_drawer_item.*
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.glide.DrawableTarget
import showmethe.github.kframework.glide.TGlide
import showmethe.github.kframework.livebus.LiveBusHelper
import showmethe.github.kframework.parallaxbacklayout.ParallaxBack
import showmethe.github.kframework.util.rden.RDEN
import showmethe.github.kframework.util.widget.StatusBarUtil


class MainActivity : BaseActivity<ViewDataBinding,MainViewModel>() {


    val dialog = SelectorDialog()
    val colors = IntArray(3)

    override fun getViewId(): Int =R.layout.activity_main
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

           viewModel.getCustomBg()

    }

    override fun setTheme() {
        StatusBarUtil.fixToolbarScreen(this,toolbar)
    }

    override fun init(savedInstanceState: Bundle?) {

        setContainer()
        setSupportActionBar(bottomBar)
        TGlide.loadCirclePicture(RDEN.get(RdenConstant.avatar,""),ivHead)
        initTab()
        switchFragment(0)


        colors[0] = ContextCompat.getColor(context,R.color.colorPrimary)
        colors[1] = ContextCompat.getColor(context,R.color.colorPrimaryDark)
        colors[2] = ContextCompat.getColor(context,R.color.color_0288d1)



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
            startActivity(bundle,LikeActivity::class.java,it,"title")
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
        switchColor(position)
        when(position){
            0 ->  {
                replaceFragment(GalleyFragment::class.java.name)
                fab.show()
                bottomBar.visibility = View.VISIBLE
            }

            1 ->  {
                replaceFragment(CateFragment::class.java.name)
                fab.hide()
            }
            2 ->  {
                replaceFragment(CartFragment::class.java.name)
                fab.hide()
                bottomBar.visibility = View.VISIBLE
            }
        }
    }


    fun switchColor(position : Int ){
        CircularRevealUtils.circularRevealCenter(inner,colors[position],{
            inner.visibility = View.VISIBLE
        },{
            inner.visibility = View.GONE
            layout.setBackgroundColor(colors[position])
            val colors = ColorStateList.valueOf(colors[position])
            bottomBar.backgroundTintList = colors
            btnLogOut.iconTint = colors
            btnLogOut.setTextColor(colors)
        })
        CircularRevealUtils.revealCenter(bottomBar,colors[position],{},{})
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
