package com.ydh.gva.core;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by liujianying on 15/5/22.
 */
public class HomeCfg {

    public List<HomeCfgQuick> quick;
    public List<HomeCfgQuick2> quick2;
    public List<Fixation> fixation;

    public static class Fixation implements Parcelable {
        public String boxId;
        public String subTitle;
        public String moreImg;
        public String title;
        public String moreUrl;
        public String urlType;
        public String boxItemId;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.boxId);
            dest.writeString(this.subTitle);
            dest.writeString(this.moreImg);
            dest.writeString(this.title);
            dest.writeString(this.moreUrl);
            dest.writeString(this.urlType);
            dest.writeString(this.boxItemId);
        }

        public Fixation() {
        }

        private Fixation(Parcel in) {
            this.boxId = in.readString();
            this.subTitle = in.readString();
            this.moreImg = in.readString();
            this.title = in.readString();
            this.moreUrl = in.readString();
            this.urlType = in.readString();
            this.boxItemId = in.readString();
        }

        public static final Parcelable.Creator<Fixation> CREATOR = new Parcelable.Creator<Fixation>() {
            public Fixation createFromParcel(Parcel source) {
                return new Fixation(source);
            }

            public Fixation[] newArray(int size) {
                return new Fixation[size];
            }
        };
    }

    public static class HomeCfgQuick implements Parcelable {
        public String boxItemId;
        public String boxId;
        public String title;
        public String subTitle;
        public String moreImg;
        public String urlType;
        public String moreUrl;
        public String idx;
        public String ver;
        public String status;
        public String memo;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.boxItemId);
            dest.writeString(this.boxId);
            dest.writeString(this.title);
            dest.writeString(this.subTitle);
            dest.writeString(this.moreImg);
            dest.writeString(this.urlType);
            dest.writeString(this.moreUrl);
            dest.writeString(this.idx);
            dest.writeString(this.ver);
            dest.writeString(this.status);
            dest.writeString(this.memo);
        }

        public HomeCfgQuick() {
        }

        private HomeCfgQuick(Parcel in) {
            this.boxItemId = in.readString();
            this.boxId = in.readString();
            this.title = in.readString();
            this.subTitle = in.readString();
            this.moreImg = in.readString();
            this.urlType = in.readString();
            this.moreUrl = in.readString();
            this.idx = in.readString();
            this.ver = in.readString();
            this.status = in.readString();
            this.memo = in.readString();
        }

        public static final Parcelable.Creator<HomeCfgQuick> CREATOR = new Parcelable.Creator<HomeCfgQuick>() {
            public HomeCfgQuick createFromParcel(Parcel source) {
                return new HomeCfgQuick(source);
            }

            public HomeCfgQuick[] newArray(int size) {
                return new HomeCfgQuick[size];
            }
        };
    }

    public static class HomeCfgQuick2 implements Parcelable {
        public String boxId;
        public String moreImg;
        public String title;
        public String moreUrl;
        public String boxItemId;
        public String urlType;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.boxId);
            dest.writeString(this.moreImg);
            dest.writeString(this.title);
            dest.writeString(this.moreUrl);
            dest.writeString(this.boxItemId);
            dest.writeString(this.urlType);
        }

        public HomeCfgQuick2() {
        }

        private HomeCfgQuick2(Parcel in) {
            this.boxId = in.readString();
            this.moreImg = in.readString();
            this.title = in.readString();
            this.moreUrl = in.readString();
            this.boxItemId = in.readString();
            this.urlType = in.readString();
        }

        public static final Parcelable.Creator<HomeCfgQuick2> CREATOR = new Parcelable.Creator<HomeCfgQuick2>() {
            public HomeCfgQuick2 createFromParcel(Parcel source) {
                return new HomeCfgQuick2(source);
            }

            public HomeCfgQuick2[] newArray(int size) {
                return new HomeCfgQuick2[size];
            }
        };
    }

}
