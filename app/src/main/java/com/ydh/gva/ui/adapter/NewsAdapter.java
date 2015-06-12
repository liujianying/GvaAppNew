package com.ydh.gva.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.view.View;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.ydh.gva.R;
import com.ydh.gva.base.widget.WLImageView;
import com.ydh.gva.core.NewsEntity;

import java.util.List;

/**
 * Created by liujianying on 15/5/26.
 */
public class NewsAdapter extends SimpleBaseAdapter<NewsEntity> {


    public NewsAdapter(Context context, List<NewsEntity> list) {
        super(context, list);


    }

    @Override
    public int getItemResource() {
        return R.layout.new_item;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {

        WLImageView wl_new = holder.getView(R.id.wl_new);
        NewsEntity newsEntity = (NewsEntity) getItem(position);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(controllerListener).setUri(Uri.parse(newsEntity.moreImg))
                .build();
        wl_new.setController(controller);


        return convertView;
    }

    ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
        @Override
        public void onFinalImageSet(
                String id,
                @Nullable ImageInfo imageInfo,
                @Nullable Animatable anim) {
            if (imageInfo == null) {
                return;
            }
            QualityInfo qualityInfo = imageInfo.getQualityInfo();
            FLog.d("Final image received! " +
                            "Size %d x %d",
                    "Quality level %d, good enough: %s, full quality: %s",
                    imageInfo.getWidth(),
                    imageInfo.getHeight(),
                    qualityInfo.getQuality(),
                    qualityInfo.isOfGoodEnoughQuality(),
                    qualityInfo.isOfFullQuality());
        }

        @Override
        public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
        }

        @Override
        public void onFailure(String id, Throwable throwable) {
            FLog.e(getClass(), throwable, "Error loading %s", id);
        }
    };
}


