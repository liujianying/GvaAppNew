package com.ydh.gva.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ydh.gva.R;
import com.ydh.gva.base.widget.WLImageView;
import com.ydh.gva.core.HomeCfg.Fixation;

import java.util.List;

import gva.ydh.com.util.ActivityUtil;

/**
 * Created by liujianying on 15/5/25.
 */
public class FixationAdapter extends SimpleBaseAdapter<Fixation>{

    private int screeWidth;

    public FixationAdapter(Context context, List<Fixation> data) {
        super(context, data);
        screeWidth = ActivityUtil.getScreenWidth((Activity) context)*9/16;
    }

    @Override
    public int getItemResource() {
        return R.layout.fixation_item;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {

        WLImageView wl_img = holder.getView(R.id.wl_img);
        Fixation fixation = (Fixation) getItem(position);
        wl_img.setImageURI(fixation.moreImg);
        wl_img.setLayoutParams(new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, screeWidth));
        return convertView;
    }
}
