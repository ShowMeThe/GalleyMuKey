package showmethe.github.kframework.util.extras

import android.animation.Animator
import android.text.Editable
import android.text.TextWatcher
import android.view.animation.Animation

open class  SimpleTextWatcher : TextWatcher{
    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

}

open class SimpeAnimatorListener : Animator.AnimatorListener{
    override fun onAnimationRepeat(p0: Animator?) {
    }

    override fun onAnimationEnd(p0: Animator?) {
    }

    override fun onAnimationCancel(p0: Animator?) {
    }

    override fun onAnimationStart(p0: Animator?) {
    }

}


open class SimpleAnimationListener : Animation.AnimationListener{
    override fun onAnimationRepeat(p0: Animation?) {
    }

    override fun onAnimationEnd(p0: Animation?) {
    }

    override fun onAnimationStart(p0: Animation?) {
    }

}