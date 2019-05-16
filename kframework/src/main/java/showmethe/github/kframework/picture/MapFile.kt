package showmethe.github.kframework.picture

import android.annotation.SuppressLint

import java.io.File
import java.util.ArrayList
import androidx.databinding.ObservableArrayList
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.functions.Predicate
import showmethe.github.kframework.picture.MapFile.list

/**
 * showmethe.github.kframework.picture
 * cuvsu
 * 2019/4/5
 */
object MapFile {


    fun list(file: File): ObservableSource<File> {
        return if (file.isDirectory) {
            Observable.fromArray(*file.listFiles()).flatMap { file -> list(file) }
        } else {
            Observable.just(file)
        }
    }


}
