package com.ydh.gva.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ydh.gva.R;
import com.ydh.gva.core.BisEntity;

import java.util.List;

/**
 * Created by liujianying on 15/5/26.
 */
public class LeShopCityListAdapter extends SimpleBaseAdapter<BisEntity> {

    private int selectedPosition = 0;// 选中的位置
    private Context context;

    public LeShopCityListAdapter(Context context,List<BisEntity> list) {
        super(context, list);
        isCache = true;
        this.context = context;
    }

    @Override
    public int getItemResource() {
        return R.layout.areas_hot_item;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {

        LinearLayout ll_bg = holder.getView(R.id.ll_bg);
        TextView tv_name = holder.getView(R.id.city_hot_txt);

        BisEntity cityEntity = (BisEntity) getItem(position);

        tv_name.setText(cityEntity.getBisName());
        tv_name.setTag(cityEntity.getBusinessId());


        if(selectedPosition == position) {
            ll_bg.setBackgroundResource(R.color.title_color);
            tv_name.setBackgroundResource(R.color.title_color);
            tv_name.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            ll_bg.setBackgroundResource(R.drawable.city_select_bg_selector);
            tv_name.setBackgroundResource(R.drawable.core_white_pink_selector);
        }

        return convertView;
    }


    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }


}
