package com.example.router.dialog

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.router.R
import kotlinx.android.synthetic.main.dialog_camera_album.view.*
import showmethe.github.kframework.dialog.SimpleDialogFragment
import showmethe.github.kframework.dialog.WindowParam

@WindowParam(gravity = Gravity.CENTER,noAnim = true)
class CameraAlbumDialog : SimpleDialogFragment() {

    override fun build(savedInstanceState: Bundle?) {
        buildDialog {
            R.layout.dialog_camera_album
        }.onView { view ->
            view.apply {
                val viewTreeObserver = view.viewTreeObserver
                if (viewTreeObserver.isAlive) {
                    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {

                            ViewAnimationUtils.createCircularReveal(rlAlbum,rlAlbum.width/2,rlAlbum.height/2,0f,
                                rlAlbum.width.toFloat()
                            ).setDuration(1000).start()

                            ViewAnimationUtils.createCircularReveal(rlCamera,rlCamera.width/2,rlCamera.height/2,0f,
                                rlCamera.width.toFloat()
                            ).setDuration(1000).start()

                            view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        }
                    })
                }


                rlCamera.setOnClickListener {
                    onCameraAlbum?.invoke(0)
                    dialog?.dismiss()
                }

                rlAlbum.setOnClickListener {
                    onCameraAlbum?.invoke(1)
                    dialog?.dismiss()
                }


            }
        }
    }


    private var  onCameraAlbum :((position:Int)->Unit)? = null

    fun setOnCameraAlbumListener(onCameraAlbum :((position:Int)->Unit)){
        this.onCameraAlbum = onCameraAlbum
    }

}