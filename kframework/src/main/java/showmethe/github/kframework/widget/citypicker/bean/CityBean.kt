package showmethe.github.kframework.widget.citypicker.bean

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.ObservableArrayList

data class CityBean(val id: String = "", var name: String = "",var cityList: ObservableArrayList<DistrictBean>) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.createTypedArrayList(CREATOR) as ObservableArrayList<DistrictBean>
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
        writeTypedList<DistrictBean>(cityList)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CityBean> = object : Parcelable.Creator<CityBean> {
            override fun createFromParcel(source: Parcel): CityBean = CityBean(source)
            override fun newArray(size: Int): Array<CityBean?> = arrayOfNulls(size)
        }
    }
}