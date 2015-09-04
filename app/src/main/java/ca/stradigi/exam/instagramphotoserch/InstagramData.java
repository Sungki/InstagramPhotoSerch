package ca.stradigi.exam.instagramphotoserch;

import android.os.Parcel;
import android.os.Parcelable;

public class InstagramData implements Parcelable{

    private String username;
    private String caption;
    private String imgThumnailUrl;
    private String imgStandardUrl;

    public InstagramData() {
    }

    public InstagramData(Parcel in) {
        readFromParcel(in);
    }

    public InstagramData(String username, String caption, String imgThumnailUrl, String imgStandardUrl) {
        this.username = username;
        this.caption = caption;
        this.imgThumnailUrl = imgThumnailUrl;
        this.imgStandardUrl = imgStandardUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getCaption() {
        return caption;
    }

    public String getImgThumnailUrl() {
        return imgThumnailUrl;
    }

    public String getImgStandardUrl() {
        return imgStandardUrl;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setImgThumnailUrl(String imgThumnailUrl) {
        this.imgThumnailUrl = imgThumnailUrl;
    }

    public void setImgStandardUrl(String imgStandardUrl) {
        this.imgStandardUrl = imgStandardUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(caption);
        dest.writeString(imgThumnailUrl);
        dest.writeString(imgStandardUrl);
    }

    private void readFromParcel(Parcel in) {
        username = in.readString();
        caption = in.readString();
        imgThumnailUrl = in.readString();
        imgStandardUrl = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public InstagramData createFromParcel(Parcel in) {
            return new InstagramData(in);
        }

        public InstagramData[] newArray(int size) {
            return new InstagramData[size];
        }
    };

}
