package showmethe.github.kframework.widget.citypicker.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import androidx.databinding.ObservableArrayList;

/**
 * @2Do:
 * @Author M2
 * @Version v ${VERSION}
 * @Date 2017/7/7 0007.
 */
public class ProvinceBean implements Parcelable {

  private String id; /*110101*/

  private String name; /*东城区*/


  private ObservableArrayList<CityBean> cityList;



  @Override
  public String toString() {
    return  name ;
  }

  public String getId() {
    return id == null ? "0" : id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name == null ? "" : name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ObservableArrayList<CityBean> getCityList() {
    return cityList;
  }

  public void setCityList(ObservableArrayList<CityBean> cityList) {
    this.cityList = cityList;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.id);
    dest.writeString(this.name);
    dest.writeTypedList(this.cityList);
  }

  public ProvinceBean() {
  }

  protected ProvinceBean(Parcel in) {
    this.id = in.readString();
    this.name = in.readString();
    this.cityList = (ObservableArrayList<CityBean>) in.createTypedArrayList(CityBean.CREATOR);
  }

  public static final Creator<ProvinceBean> CREATOR = new Creator<ProvinceBean>() {
    @Override
    public ProvinceBean createFromParcel(Parcel source) {
      return new ProvinceBean(source);
    }

    @Override
    public ProvinceBean[] newArray(int size) {
      return new ProvinceBean[size];
    }
  };
}
