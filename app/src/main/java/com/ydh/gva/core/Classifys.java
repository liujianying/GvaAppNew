package com.ydh.gva.core;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liujianying on 15/6/8.
 * @商场内定义的分类
 */
public class Classifys implements Parcelable {
    public String icon;
    public String idx;
    public String status;
    public String businessId;
    public String classifyId;
    public String classifyName;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.icon);
        dest.writeString(this.idx);
        dest.writeString(this.status);
        dest.writeString(this.businessId);
        dest.writeString(this.classifyId);
        dest.writeString(this.classifyName);
    }

    public Classifys() {
    }

    protected Classifys(Parcel in) {
        this.icon = in.readString();
        this.idx = in.readString();
        this.status = in.readString();
        this.businessId = in.readString();
        this.classifyId = in.readString();
        this.classifyName = in.readString();
    }

    public static final Parcelable.Creator<Classifys> CREATOR = new Parcelable.Creator<Classifys>() {
        public Classifys createFromParcel(Parcel source) {
            return new Classifys(source);
        }

        public Classifys[] newArray(int size) {
            return new Classifys[size];
        }
    };
}
