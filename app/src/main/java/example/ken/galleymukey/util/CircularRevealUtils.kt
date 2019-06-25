package example.ken.galleymukey.util

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Color
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
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
                }

                override fun onAnimationCancel(p0: Animator?) {

                }

                override fun onAnimationStart(p0: Animator?) {
                    onStart.invoke()
                }
            })
            animatorSet.duration = 500
            animatorSet.interpolator = AccelerateDecelerateInterpolator()
            animatorSet.start()
        }
    }
}
