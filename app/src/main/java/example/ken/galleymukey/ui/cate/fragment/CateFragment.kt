package example.ken.galleymukey.ui.cate.fragment

import android.animation.Animator
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.inputmethod.EditorInfo
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import example.ken.galleymukey.R
import example.ken.galleymukey.dialog.HashTagDialog
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


    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)
    override fun getViewId(): Int = R.layout.fragment_cate

    override fun onBundle(bundle: Bundle) {
    }

    override fun observerUI() {

        viewModel.catePopBack.observe(this, Observer {
            it?.apply {
                if(this){
                    edSearch.setText("")
                    unRotate()
                }
            }
        })

        viewModel.hashTag.observe(this, Observer {
            it?.apply {
                dialog.beanList.value = this
            }
        })

    }

    override fun init(savedInstanceState: Bundle?) {

       viewModel.getHashTag()
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


        edSearch.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                viewModel.searchContent.value = edSearch.text.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        edSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                KeyBoardUtils.closeKeyboard(context,edSearch)
            }
            false
        }


        dialog.setOnConfirmClickListener {
            viewModel.keyword.value = it
            dialog.dismiss()
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
                transaction.add(R.id.frameLayout, tempFragment, tag
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        viewModel.cateChildManager = childFragmentManager
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