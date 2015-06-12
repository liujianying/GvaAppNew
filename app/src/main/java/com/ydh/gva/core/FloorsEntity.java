package com.ydh.gva.core;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liujianying on 15/6/11.
 */
public class FloorsEntity implements Parcelable {

    public String floorId;
    public String businessId;
    public String floorName;
    public String floorDesc;
    public String idx;
    public String status;
    public String memo;
    public String icon;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.floorId);
        dest.writeString(this.businessId);
        dest.writeString(this.floorName);
        dest.writeString(this.floorDesc);
        dest.writeString(this.idx);
        dest.writeString(this.status);
        dest.writeString(this.memo);
        dest.writeString(this.icon);
    }

    public FloorsEntity() {
    }

    protected FloorsEntity(Parcel in) {
        this.floorId = in.readString();
        this.businessId = in.readString();
        this.floorName = in.readString();
        this.floorDesc = in.readString();
        this.idx = in.readString();
        this.status = in.readString();
        this.memo = in.readString();
        this.icon = in.readString();
    }

    public static final Parcelable.Creator<FloorsEntity> CREATOR = new Parcelable.Creator<FloorsEntity>() {
        public FloorsEntity createFromParcel(Parcel source) {
            return new FloorsEntity(source);
        }

        public FloorsEntity[] newArray(int size) {
            return new FloorsEntity[size];
        }
    };
}
