package example.ken.galleymukey.ui.cate.fragment

import android.animation.Animator
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.databinding.ObservableArrayList
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import example.ken.galleymukey.R
import example.ken.galleymukey.bean.HashTagBean
import example.ken.galleymukey.dialog.HashTagDialog
import example.ken.galleymukey.ui.cate.adapter.CateMenuAdapter
import example.ken.galleymukey.ui.main.vm.MainViewModel

import kotlinx.android.synthetic.main.fragment_cate.*
import showmethe.github.kframework.base.BaseFragment
import showmethe.github.kframework.util.system.KeyBoardUtils
import java.lang.StringBuilder

/**
 * example.ken.galleymukey.ui.cate.fragment
 * cuvsu
 * 2019/5/20
 **/
class CateFragment  : BaseFragment<ViewDataBinding, MainViewModel>() {


    var fragments: List<Fragment>? = null
    var temp = ""//初始对比
    var sb = StringBuilder()

    val dialog = HashTagDialog()
    val list = ArrayList<HashTagBean>()

    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)
    override fun getViewId(): Int = R.layout.fragment_cate

    override fun onBundle(bundle: Bundle) {
    }

    override fun observerUI() {

    }

    override fun init(savedInstanceState: Bundle?) {

        addHashTag()
        replaceFragment(CategoryFragment::class.java.name)



    }

    override fun initListener() {
        ivHashTag.setOnClickListener {
            KeyBoardUtils.hideSoftKeyboard(context)
            dialog.show(childFragmentManager,"")
        }

        edSearch.setOnFocusChangeListener { v, hasFocus ->
            if(hasFocus){
                rotate()
            }
        }

        edSearch.setOnClickListener {
            rotate()
        }

        ivBack.setOnClickListener {
            unRotate()

        }


        dialog.setOnConfirmClickListener {
            viewModel.boolean.value = true
        }

    }


    private  fun rotate(){
        ivBack.animate()
            .alpha(1.0f)
            .rotation(90f)
            .setDuration(300)
            .setInterpolator(AccelerateInterpolator())
            .setListener(object :Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }
            override fun onAnimationEnd(animation: Animator?) {
                ivBack.isEnabled = true
            }
            override fun onAnimationCancel(animation: Animator?) {
            }
            override fun onAnimationStart(animation: Animator?) {
                ivBack.visibility = View.VISIBLE
                ivBack.isEnabled = false
                ivSearch.visibility=View.GONE
                ivHashTag.visibility = View.INVISIBLE
                replaceFragment(SearchFragment::class.java.name)
            }
        }).start()
    }

    private fun unRotate(){
        ivBack.animate()
            .alpha(0.7f)
            .rotation(0f)
            .setDuration(300)
            .setInterpolator(AccelerateInterpolator())
            .setListener(object :Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }
            override fun onAnimationEnd(animation: Animator?) {
                ivBack.visibility = View.GONE
                ivSearch.visibility = View.VISIBLE
                ivHashTag.visibility = View.VISIBLE
                childFragmentManager.popBackStack()
            }
            override fun onAnimationCancel(animation: Animator?) {
            }
            override fun onAnimationStart(animation: Animator?) {
            }
        }).start()
    }


    fun addHashTag(){
        list.add(HashTagBean("http://image1.xyzs.com/upload/a6/1c/1450580015244844/20151224/145089874795426_0.jpg","YHGJKII"))
        list.add(HashTagBean("http://image3.xyzs.com/upload/b9/40/1449104703418440/20151205/144925600471264_0.jpg","12333"))
        list.add(HashTagBean("http://image4.xyzs.com/upload/03/f7/1449978315650125/20151217/145028981364776_0.jpg","JJJCC"))
        list.add(HashTagBean("http://image2.xyzs.com/upload/9f/d5/1450057331616098/20151217/145028980491632_0.jpg","JJJCC"))
        list.add(HashTagBean("http://image4.xyzs.com/upload/03/f7/1449978315650125/20151217/145028981364776_0.jpg","DDD"))
        list.add(HashTagBean("http://image1.xyzs.com/upload/09/b4/1450405158764323/20151224/145090278841619_0.jpg","ADDCCSS"))
        list.add(HashTagBean("http://image1.xyzs.com/upload/1a/b1/1450492649469952/20151224/145090277979009_0.jpg","GGG"))
        dialog.beanList.value = list
    }




    private fun replaceFragment(tag: String) {
        var tempFragment = childFragmentManager.findFragmentByTag(tag)
        val transaction = childFragmentManager.beginTransaction()
        if (tempFragment == null) {
            try {
                tempFragment = Class.forName(tag).newInstance() as Fragment
                transaction.setCustomAnimations(
                    R.anim.slide_bottom_in,
                    R.anim.slide_bottom_out,
                    R.anim.slide_left_in,
                    R.anim.slide_left_out)
                transaction.add(R.id.frameLayout, tempFragment, tag)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fragments  = childFragmentManager.fragments
        if (fragments != null) {
            for (i in fragments!!.indices) {
                val fragment = fragments!![i]
                if (fragment.tag == tag) {
                    transaction.setCustomAnimations(
                        R.anim.slide_bottom_in,
                        R.anim.slide_bottom_out,
                        R.anim.slide_left_in,
                        R.anim.slide_left_out)
                    transaction.show(fragment).addToBackStack(null)
                } else {
                    transaction.setCustomAnimations(
                        R.anim.slide_bottom_in,
                        R.anim.slide_bottom_out,
                        R.anim.slide_left_in,
                        R.anim.slide_left_out)
                    transaction.hide(fragment).addToBackStack(null)
                }
            }
        }
        transaction.commitAllowingStateLoss()
    }

}