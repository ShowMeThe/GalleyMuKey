package com.example.ken.kmvvm.ItemAnimator

import android.view.View
import androidx.core.view.ViewCompat



/**
 * com.example.ken.kmvvm.ItemAnimator
 *
 * 2019/9/2
 **/
class ViewHelper {

    companion object{
        fun clear(v: View) {
            v.alpha = 1f
            v.translationX = 0f
            v.translationY = 0f
            ViewCompat.animate(v).setInterpolator(null).startDelay = 0
        }
    }
}