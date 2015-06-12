package com.ydh.gva.ui.adapter;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;

import gva.ydh.com.util.ActivityUtil;

/**
 * Created by liujianying on 15/5/26.
 */
public class BaseController<WLImageView, INFO> extends BaseControllerListener<INFO> {
    private int screeWidth;
    private WLImageView wlImageView;

    public BaseController(com.ydh.gva.base.widget.WLImageView simpleDraweeView,Activity activity) {
        this.wlImageView = wlImageView;
        screeWidth = ActivityUtil.getScreenWidth(activity);
    }

    public BaseController() {

    }

    public void onFinalImageSet(String id, @Nullable INFO imageInfo, @Nullable Animatable animatable) {

        ImageInfo imageInfoS = (ImageInfo)imageInfo;
        ((View)wlImageView).setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, screeWidth*imageInfoS.getWidth()/imageInfoS.getHeight()));
    }


}
