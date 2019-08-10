package showmethe.github.kframework.picture

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.camera.core.CameraX
import androidx.camera.core.ImageCapture
import androidx.camera.core.VideoCapture
import androidx.camera.view.CameraView
import androidx.core.content.FileProvider
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import kotlinx.android.synthetic.main.activity_camera.*
import showmethe.github.kframework.R
import showmethe.github.kframework.adapter.BaseRecyclerViewAdapter
import showmethe.github.kframework.base.BaseActivity
import showmethe.github.kframework.util.widget.StatusBarUtil
import java.io.File

@SuppressLint("MissingPermission")
class CameraActivity : BaseActivity<ViewDataBinding, PictureViewModel>() {

    private var photoPath = ""


    override fun setTheme() {
        StatusBarUtil.setFullScreen(this)
    }
    override fun getViewId(): Int = R.layout.activity_camera
    override fun initViewModel(): PictureViewModel = createViewModel(PictureViewModel::class.java)
    override fun onBundle(bundle: Bundle) {
    }

    override fun onLifeCreated(owner: LifecycleOwner) {

    }

    override fun observerUI() {
        cameraView.apply {
            bindToLifecycle(this@CameraActivity)
            cameraLensFacing = CameraX.LensFacing.BACK


        }



    }


    override fun init(savedInstanceState: Bundle?) {







    }

    override fun initListener() {


        capture.setOnRecordListener(object : RecordButton.OnRecordListener(){
            override fun onShortClick() {
                cameraView.takePicture(photoPath(),object : ImageCapture.OnImageSavedListener {
                    override fun onImageSaved(file: File) {
                        showToast("Save at ${file.path}")
                    }

                    override fun onError(useCaseError: ImageCapture.UseCaseError, message: String, cause: Throwable?) {

                    }
                })
            }

            override fun OnRecordStartClick() {

                cameraView.startRecording(videoPath(),object : VideoCapture.OnVideoSavedListener{
                    override fun onVideoSaved(file: File?) {
                        showToast("Save at ${file?.path}")
                    }

                    override fun onError(
                        useCaseError: VideoCapture.UseCaseError?,
                        message: String?,
                        cause: Throwable?
                    ) {
                    }
                })


            }

            override fun OnFinish() {
                if( cameraView.isRecording){
                    cameraView.stopRecording()
                }
            }

        })

        ivChange.setOnClickListener {
            if(cameraView.cameraLensFacing == CameraX.LensFacing.FRONT){
                cameraView.cameraLensFacing = CameraX.LensFacing.BACK
            }else{
                cameraView.cameraLensFacing = CameraX.LensFacing.FRONT
            }

        }

    }


    private fun videoPath() : File{
        val dir = File(Environment.getExternalStorageDirectory().toString() + File.separator + "/Pictures")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        photoPath = dir.path + "/" + System.currentTimeMillis() + ".mp4"
        return File(photoPath)
    }

    private fun photoPath() : File{
        val dir = File(Environment.getExternalStorageDirectory().toString() + File.separator + "/Pictures")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        photoPath = dir.path + "/" + System.currentTimeMillis() + ".jpg"
        return File(photoPath)
    }

    override fun onDestroy() {
        super.onDestroy()
        CameraX.unbindAll()
    }

}
