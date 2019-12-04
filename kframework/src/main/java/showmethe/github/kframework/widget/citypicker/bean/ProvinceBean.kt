package showmethe.github.kframework.widget.citypicker.bean

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.ObservableArrayList


data class ProvinceBean(val id: String = "", var name: String = "", var cityList: ObservableArrayList<CityBean>) :
    Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.createTypedArrayList(CityBean.CREATOR) as ObservableArrayList<CityBean>
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(name)
        writeTypedList<CityBean>(cityList)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ProvinceBean> = object : Parcelable.Creator<ProvinceBean> {
            override fun createFromParcel(source: Parcel): ProvinceBean = ProvinceBean(source)
            override fun newArray(size: Int): Array<ProvinceBean?> = arrayOfNulls(size)
        }
    }
}