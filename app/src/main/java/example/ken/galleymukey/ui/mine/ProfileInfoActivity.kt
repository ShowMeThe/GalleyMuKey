package example.ken.galleymukey.ui.mine

import android.animation.Animator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import example.ken.galleymukey.R
import example.ken.galleymukey.ui.cate.fragment.SearchFragment
import example.ken.galleymukey.ui.mine.fragment.InfoFragment
import example.ken.galleymukey.ui.mine.fragment.ResetFragment
import example.ken.galleymukey.ui.mine.vm.ProfileInfoViewModel

import kotlinx.android.synthetic.main.include_title_bar.*
import showmethe.github.kframework.base.BaseActivity

class ProfileInfoActivity : BaseActivity<ViewDataBinding,ProfileInfoViewModel>() {



    override fun getViewId(): Int = R.layout.activity_profile_info
    override fun initViewModel(): ProfileInfoViewModel = createViewModel(ProfileInfoViewModel::class.java)
    override fun onBundle(bundle: Bundle) {
    }

    override fun observerUI() {

        viewModel.switchToReset.observe(this, Observer {
            it?.apply {
                if(this){
                    ivRight.visibility = View.GONE
                    viewModel.currentType = true
                    replaceFragment(ResetFragment::class.java.name)
                    tvTitle.text = "Update Password"
                }
            }
        })


    }

    override fun init(savedInstanceState: Bundle?) {
        tvTitle.text = "Profile"


        replaceFragment(InfoFragment::class.java.name)




    }

    override fun initListener() {
        rotate()


        ivRight.setOnClickListener {
            if(viewModel.currentType){
                viewModel.updateController.value = true
            }else{

            }
        }



        ivBack.setOnClickListener {
            if(supportFragmentManager.fragments.size>=2){
                viewModel.currentType = false
                supportFragmentManager.popBackStack()
                ivRight.visibility = View.VISIBLE
                tvTitle.text = "Profile"
            }else{
                finishAfterTransition()
            }
        }

    }



    override fun onBackPressed() {
        if(supportFragmentManager.fragments.size>= 2){
            viewModel.currentType = false
            supportFragmentManager.popBackStack()
            ivRight.visibility = View.VISIBLE
            tvTitle.text = "Profile"
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
                    transaction.show(fragment).addToBackStack(null)
                } else {
                    transaction.setCustomAnimations(R.anim.slide_right_in,
                        R.anim.slide_left_out,
                        R.anim.slide_left_in,
                        R.anim.slide_right_out)

                    transaction.hide(fragment).addToBackStack(null)
                }
            }
        }
        transaction.commitAllowingStateLoss()
    }




    private  fun rotate(){
        ivBack.animate()
            .alpha(1.0f)
            .rotation(90f)
            .setDuration(300)
            .setInterpolator(AccelerateInterpolator()).setStartDelay(500)
        ivBack.setOnClickListener {
            finishAfterTransition()
        }
    }

}
