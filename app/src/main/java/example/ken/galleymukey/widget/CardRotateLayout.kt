package example.ken.galleymukey.widget

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnticipateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView


class CardRotateLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var firstView : View? = null
    private var secondView : View? = null
    private var hasExpand = false
    private var animatorA : ObjectAnimator? = null
    private var alphaA : ObjectAnimator? = null
    private var animatorB: ObjectAnimator? = null
    private var alphaB : ObjectAnimator? = null
    private val  set = AnimatorSet()
    private var rotateTime = 4


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        firstView = getChildAt(0)
        secondView = getChildAt(1)

        if(childCount>2){
            throw Exception("Parent layout can only contain two child layout")
        }else if(firstView!! is CardView || secondView!! is CardView ){
            throw Exception("Parent layout do not support CardView or MaterialCardView")
        }


    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        firstView = getChildAt(0)
        secondView = getChildAt(1)

        firstView?.bringToFront()
        createRotateAnim()
        firstView?.setOnClickListener {
            firstView?.isEnabled = false
            if(!hasExpand){
                set.start()
            }else{
                set.reverse()
            }
            hasExpand  = !hasExpand
            onDetachListener?.invoke(hasExpand)
        }
    }




    /**
     * 控制顶层和底层两个界面显示状态 否则默认都一起可见
     */
    fun setCurrentExpand(boolean: Boolean){
        hasExpand = boolean
        if(hasExpand){
            firstView?.alpha = 0f
            secondView?.alpha = 1f
            secondView?.bringToFront()
        }else{
            firstView?.alpha = 1f
            secondView?.alpha = 0f
            firstView?.bringToFront()
        }
    }

    private var onDetachListener :((expand : Boolean)->Unit)? =  null

    fun setOnDetachListener(onDetachListener :((expand : Boolean)->Unit)){
        this.onDetachListener = onDetachListener
    }

    private fun createRotateAnim(){
        if(animatorA == null){
            animatorA = ObjectAnimator.ofFloat(firstView!!,"rotationX",0.0f,180f * rotateTime)
            alphaA = ObjectAnimator.ofFloat(firstView!!,"alpha",1.0f,0f)
        }
        if(animatorB == null){
            animatorB = ObjectAnimator.ofFloat(secondView!!,"rotationX",0.0f,180f * rotateTime)
            alphaB = ObjectAnimator.ofFloat(secondView!!,"alpha",0.0f,1f)
        }
        set.playTogether(animatorA,animatorB,alphaA,alphaB)
        set.duration = 1500
        set.interpolator = AnticipateInterpolator()
        set.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                firstView?.isEnabled = true
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })
    }



}