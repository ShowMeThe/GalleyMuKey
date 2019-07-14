package showmethe.github.kframework.picture

import android.content.Intent

object PictureSelector {

    fun findLocalPictures(data :Intent) : ArrayList<PicturesJo>{
        return data.getParcelableArrayListExtra("PictureSelector")
    }
}