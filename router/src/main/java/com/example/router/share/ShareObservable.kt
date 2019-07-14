package com.example.router.share

import androidx.fragment.app.FragmentManager

interface ShareObservable {

    fun updateFragmentManager(fragmentManager: FragmentManager)


    fun popBack(boolean: Boolean)
}