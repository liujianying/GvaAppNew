package com.ydh.gva.ui.adapter;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.ydh.gva.base.widget.WLImageView;

/**
 * Created by liujianying on 15/5/26.
 */
public class AutoSizeImage extends WLImageView implements ImageInfo {
    public AutoSizeImage(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public AutoSizeImage(Context context) {
        super(context);
    }

    public AutoSizeImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoSizeImage(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public QualityInfo getQualityInfo() {
        return null;
    }
}
