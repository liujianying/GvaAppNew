package com.ydh.gva.core;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liujianying on 15/6/11.
 */
public class TagsEntity implements Parcelable {

    public String tagId;
    public String businessId;
    public String tagName;
    public String icon;
    public String idx;
    public String status;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.tagId);
        dest.writeString(this.businessId);
        dest.writeString(this.tagName);
        dest.writeString(this.icon);
        dest.writeString(this.idx);
        dest.writeString(this.status);
    }

    public TagsEntity() {
    }

    protected TagsEntity(Parcel in) {
        this.tagId = in.readString();
        this.businessId = in.readString();
        this.tagName = in.readString();
        this.icon = in.readString();
        this.idx = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<TagsEntity> CREATOR = new Parcelable.Creator<TagsEntity>() {
        public TagsEntity createFromParcel(Parcel source) {
            return new TagsEntity(source);
        }

        public TagsEntity[] newArray(int size) {
            return new TagsEntity[size];
        }
    };
}
