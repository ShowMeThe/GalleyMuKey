package com.example.main.ui.mine

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.example.main.R
import com.example.main.ui.mine.vm.MainViewModel
import com.journeyapps.barcodescanner.ScanCameraManager
import kotlinx.android.synthetic.main.activity_scan_qr.*
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.parallaxback.ParallaxBack
import showmethe.github.kframework.util.toast.ToastFactory
import showmethe.github.kframework.util.widget.StatusBarUtil

@ParallaxBack
class ScanQrActivity : BaseActivity<ViewDataBinding,MainViewModel>() {

    lateinit var scanCameraManager: ScanCameraManager

    override fun setTheme() {
        StatusBarUtil.setFullScreen(this)
    }

    override fun getViewId(): Int = R.layout.activity_scan_qr
    override fun initViewModel(): MainViewModel = createViewModel(MainViewModel::class.java)

    override fun onBundle(bundle: Bundle) {

    }

    override fun onLifeCreated(owner: LifecycleOwner) {

    }

    override fun observerUI() {

    }

    override fun init(savedInstanceState: Bundle?) {

        scanCameraManager = ScanCameraManager()
        scanCameraManager.init(this,scanner)
            .start(savedInstanceState)


    }

    override fun initListener() {

        scanCameraManager.setOnBarCodeCallBackListener {
            ToastFactory.createToast(it.contents)
            false
        }


    }


}
