package showmethe.github.kframework.picture

import android.content.Intent

object PictureSelector {

    fun findLocalPictrues(data :Intent) : ArrayList<PicturesJo>{
        return data.getParcelableArrayListExtra("PictureSelector")
    }
}