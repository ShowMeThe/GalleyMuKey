package showmethe.github.kframework.picture;

import android.os.Parcel;
import android.os.Parcelable;

public class PicturesJo implements Parcelable {

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getCompress() {
        return compress;
    }

    public void setCompress(String compress) {
        this.compress = compress;
    }

    private String origin;
    private String compress;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.origin);
        dest.writeString(this.compress);
    }

    public PicturesJo() {
    }

    protected PicturesJo(Parcel in) {
        this.origin = in.readString();
        this.compress = in.readString();
    }

    public static final Parcelable.Creator<PicturesJo> CREATOR = new Parcelable.Creator<PicturesJo>() {
        @Override
        public PicturesJo createFromParcel(Parcel source) {
            return new PicturesJo(source);
        }

        @Override
        public PicturesJo[] newArray(int size) {
            return new PicturesJo[size];
        }
    };
}
