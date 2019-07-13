package com.example.router.util

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import com.google.android.material.circularreveal.CircularRevealCompat
import com.google.android.material.circularreveal.CircularRevealWidget
import kotlin.math.sqrt

object CircularRevealUtils {


    fun <T> circularRevealCenter(circularRevealWidget: T, color: Int,onStart:()->Unit,onFinish : ()->Unit) where T : View, T : CircularRevealWidget {
        circularRevealWidget.post {
            val viewWidth = circularRevealWidget.width
            val viewHeight = circularRevealWidget.height

            val viewDiagonal = sqrt((viewWidth * viewWidth + viewHeight * viewHeight).toDouble()).toInt()

            val animatorSet = AnimatorSet()
            animatorSet.playTogether(
                CircularRevealCompat.createCircularReveal(
                    circularRevealWidget,
                    (viewWidth / 2).toFloat(), (viewHeight / 2).toFloat(), 4f, (viewDiagonal / 2).toFloat()
                ),
                ObjectAnimator.ofArgb(
                    circularRevealWidget,
                    CircularRevealWidget.CircularRevealScrimColorProperty.CIRCULAR_REVEAL_SCRIM_COLOR,
                    Color.TRANSPARENT, color
                )
            )
            animatorSet.addListener(object : Animator.AnimatorListener{
                override fun onAnimationRepeat(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    onFinish.invoke()
                    if(animatorSet.isRunning || animatorSet.isStarted){
                        animatorSet.cancel()
                    }
                }

                override fun onAnimationCancel(p0: Animator?) {

                }

                override fun onAnimationStart(p0: Animator?) {
                    onStart.invoke()
                }
            })
            animatorSet.duration = 450
            animatorSet.interpolator = AccelerateDecelerateInterpolator()
            animatorSet.start()
        }
    }



    fun <T> revealCenter(widger: T, color: Int,onStart:()->Unit,onFinish : ()->Unit) where T : View {
        widger.post {
            val viewWidth = widger.width
            val viewHeight = widger.height

            val viewDiagonal = sqrt((viewWidth * viewWidth + viewHeight * viewHeight).toDouble()).toInt()
            val animator = ObjectAnimator.ofArgb(
                Color.TRANSPARENT, color)
            animator.addUpdateListener {
                widger.backgroundTintList = ColorStateList.valueOf(it.animatedValue as Int)
            }
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(
                ViewAnimationUtils.createCircularReveal(
                    widger,
                    (viewWidth / 2), (viewHeight / 2), 4f, (viewDiagonal / 2).toFloat()
                ),animator)
            animatorSet.addListener(object : Animator.AnimatorListener{
                override fun onAnimationRepeat(p0: Animator?) {

                }

                override fun onAnimationEnd(p0: Animator?) {
                    onFinish.invoke()
                    if(animatorSet.isRunning || animatorSet.isStarted){
                        animatorSet.cancel()
                    }
                }

                override fun onAnimationCancel(p0: Animator?) {

                }

                override fun onAnimationStart(p0: Animator?) {
                    onStart.invoke()
                }
            })
            animatorSet.duration = 450
            animatorSet.interpolator = AccelerateDecelerateInterpolator()
            animatorSet.start()
        }
    }


}
