package showmethe.github.kframework.picture

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore

import java.io.File
import java.io.IOException
import java.util.Objects

import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import io.reactivex.subjects.PublishSubject


/**
 * PackageName: example.ewhale.com.demo
 * Author : jiaqi Ye
 * Date : 2018/9/19
 * Time : 9:55
 */
class MediaFragment : Fragment() {

    private var videoTempFile: File? = null
    private var photoTempFile: File? = null
    private val mSubjects = PublishSubject.create<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true

    }

    enum class MediaType {
        VIDEO, PHOTO
    }

    fun mediaCapture(type: MediaType): PublishSubject<String> {
        when (type) {
            MediaFragment.MediaType.PHOTO -> startPhotoCapture()
            MediaFragment.MediaType.VIDEO -> startVideoRecorder()
        }
        return mSubjects
    }

    private fun startVideoRecorder() {

        val openVideoIntent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        openVideoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val dir = File(Environment.getExternalStorageDirectory().toString() + File.separator + "/Pictures")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        videoTempFile = File(dir.path + "/" + System.currentTimeMillis() + ".mp4")
        if (!videoTempFile!!.exists()) {
            try {
                videoTempFile!!.delete()
                videoTempFile!!.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        val fileVUri = FileProvider.getUriForFile(Objects.requireNonNull<FragmentActivity>(activity), activity!!.packageName + ".provider", videoTempFile!!)
        openVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileVUri)
        startActivityForResult(openVideoIntent, VIDEO_CAPTURE)
    }


    private fun startPhotoCapture() {
        val intent_photo = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val fileUri: Uri
        val dir = File(Environment.getExternalStorageDirectory().toString() + File.separator + "/Pictures")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        photoTempFile = File(dir.path + "/" + System.currentTimeMillis() + ".jpg")
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = FileProvider.getUriForFile(Objects.requireNonNull<FragmentActivity>(activity), activity!!.packageName + ".provider", photoTempFile!!)
        } else {
            fileUri = Uri.fromFile(photoTempFile)
        }
        intent_photo.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent_photo.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
        startActivityForResult(intent_photo, PHOTO_CAPTURE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            VIDEO_CAPTURE -> {
                mSubjects.onNext(videoTempFile!!.path)
                mSubjects.onComplete()
            }
            PHOTO_CAPTURE -> {
                mSubjects.onNext(photoTempFile!!.path)
                mSubjects.onComplete()
            }
        }

    }

    companion object {

        private val VIDEO_CAPTURE = 5000
        private val PHOTO_CAPTURE = 6000
    }

}
