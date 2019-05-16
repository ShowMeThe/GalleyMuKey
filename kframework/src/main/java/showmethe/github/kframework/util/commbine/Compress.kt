package showmethe.github.kframework.util.commbine

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.graphics.Matrix
import java.io.FileDescriptor


/**
 * showmethe.github.kframework.util.commbine
 * cuvsu
 * 2019/3/3
 **/
class Compress {

     companion object {

         fun get() : Compress{
             val instant by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { Compress() }
             return instant
         }
     }

    private constructor()

    fun compressResource(res: Resources, resId: Int, reqWidth: Int, reqHeight: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }

    fun compressDescriptor(fd: FileDescriptor, reqWidth: Int, reqHeight: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFileDescriptor(fd, null, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFileDescriptor(fd, null, options)
    }

    fun compressResource(bitmap: Bitmap, reqWidth: Int, reqHeight: Int): Bitmap {

        val width = bitmap.width
        val height = bitmap.height
        val scaleWidth = reqWidth.toFloat() / width
        val scaleHeight = reqHeight.toFloat() / height
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options,
                                      reqWidth: Int, reqHeight: Int): Int {
        if (reqWidth == 0 || reqHeight == 0) {
            return 1
        }
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

}