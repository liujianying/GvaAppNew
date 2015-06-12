package com.ydh.gva.core;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liujianying on 15/5/26.
 * @最新资讯
 */
public class NewsEntity implements Parcelable {

    public String boxItemId;
    public String boxId;
    public String title;
    public String moreImg;
    public String urlType;
    public String moreUrl;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.boxItemId);
        dest.writeString(this.boxId);
        dest.writeString(this.title);
        dest.writeString(this.moreImg);
        dest.writeString(this.urlType);
        dest.writeString(this.moreUrl);
    }

    public NewsEntity() {
    }

    private NewsEntity(Parcel in) {
        this.boxItemId = in.readString();
        this.boxId = in.readString();
        this.title = in.readString();
        this.moreImg = in.readString();
        this.urlType = in.readString();
        this.moreUrl = in.readString();
    }

    public static final Parcelable.Creator<NewsEntity> CREATOR = new Parcelable.Creator<NewsEntity>() {
        public NewsEntity createFromParcel(Parcel source) {
            return new NewsEntity(source);
        }

        public NewsEntity[] newArray(int size) {
            return new NewsEntity[size];
        }
    };
}
