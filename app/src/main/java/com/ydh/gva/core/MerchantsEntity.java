package com.ydh.gva.core;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liujianying on 15/6/8.
 * @merchants
 */
public class MerchantsEntity implements Parcelable {
//    "merchantId": "1",
//            "businessId": "1",
//            "merchantType": "1",
//            "merchantName": "商业1商户1",
//            "merchantTitle": "雅诗兰黛, 来自内心的爱",
//            "merchantImg": "1",
//            "merchantIntro": "雅诗兰黛, 来自内心的爱.更多内容.",
//            "merchantSite": "外键到位置表",
//            "merchantAddr": "王府井百货 1F",
//            "merchantTel": "0591-38051117",
//            "merchantShopHours": "10:00-22:00",
//            "idx": 1,
//            "ver": 1,
//            "status": "1",
//            "memo": "1"
    public String merchantId;
    public String businessId;
    public String merchantType;
    public String merchantName;
    public String merchantTitle;
    public String merchantImg;
    public String merchantIntro;
    public String merchantAddr;
    public String merchantTel;
    public String merchantShopHours;
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
        dest.writeString(this.merchantId);
        dest.writeString(this.businessId);
        dest.writeString(this.merchantType);
        dest.writeString(this.merchantName);
        dest.writeString(this.merchantTitle);
        dest.writeString(this.merchantImg);
        dest.writeString(this.merchantIntro);
        dest.writeString(this.merchantAddr);
        dest.writeString(this.merchantTel);
        dest.writeString(this.merchantShopHours);
        dest.writeString(this.idx);
        dest.writeString(this.ver);
        dest.writeString(this.status);
        dest.writeString(this.memo);
    }

    public MerchantsEntity() {
    }

    protected MerchantsEntity(Parcel in) {
        this.merchantId = in.readString();
        this.businessId = in.readString();
        this.merchantType = in.readString();
        this.merchantName = in.readString();
        this.merchantTitle = in.readString();
        this.merchantImg = in.readString();
        this.merchantIntro = in.readString();
        this.merchantAddr = in.readString();
        this.merchantTel = in.readString();
        this.merchantShopHours = in.readString();
        this.idx = in.readString();
        this.ver = in.readString();
        this.status = in.readString();
        this.memo = in.readString();
    }

    public static final Parcelable.Creator<MerchantsEntity> CREATOR = new Parcelable.Creator<MerchantsEntity>() {
        public MerchantsEntity createFromParcel(Parcel source) {
            return new MerchantsEntity(source);
        }

        public MerchantsEntity[] newArray(int size) {
            return new MerchantsEntity[size];
        }
    };
}