package com.ydh.gva.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.ydh.gva.R;
import com.ydh.gva.circlebutton.CircleButton;
import com.ydh.gva.core.HomeCfg.HomeCfgQuick;
import com.ydh.gva.ui.Base.GvaApp;
import com.ydh.gva.util.net.volley.BitmapCache;
import com.ydh.gva.util.net.volley.RequestQueue;
import com.ydh.gva.util.net.volley.toolbox.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujianying on 15/5/24.
 */
public class CategoryAdapter extends SimpleBaseAdapter<HomeCfgQuick> {

    private List<Integer> bgColors = new ArrayList<Integer>();
    private ImageLoader mImageLoader;
    private AdapterView.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(
            AdapterView.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public CategoryAdapter(Context context, List<HomeCfgQuick> data) {
        super(context, data);
        initColors(context);
        // 初始化Volley图片Loader
        mImageLoader = new ImageLoader(GvaApp.requestQueue, new BitmapCache());
    }

    private void initColors(Context context) {
        Resources res = context.getResources();
        bgColors.clear();
        bgColors.add(res.getColor(R.color.app_specialty_home_type_01));
        bgColors.add(res.getColor(R.color.app_specialty_home_type_02));
        bgColors.add(res.getColor(R.color.app_specialty_home_type_03));
        bgColors.add(res.getColor(R.color.app_specialty_home_type_04));
        bgColors.add(res.getColor(R.color.app_specialty_home_type_05));
        bgColors.add(res.getColor(R.color.app_specialty_home_type_06));
        bgColors.add(res.getColor(R.color.app_specialty_home_type_07));
        bgColors.add(res.getColor(R.color.app_specialty_home_type_08));
    }


    @Override
    public int getItemResource() {
        return R.layout.home_page_category;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {

        final CircleButton item_pic = holder.getView(R.id.item_pic);
        TextView item_text = holder.getView(R.id.item_text);
        HomeCfgQuick homeCfgQuick = (HomeCfgQuick) getItem(position);
        item_pic.setColor(bgColors.get(position));
        item_text.setText(homeCfgQuick.title);
        // 利用Volley加载图片
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(item_pic, 0, 0);
        mImageLoader.get(homeCfgQuick.moreImg, listener);

        item_pic.setOnClickListener(mOnClickListener);
        item_pic.setTag(position);
        return convertView;
    }


    private View.OnClickListener mOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null && v != null) {
                int position = (Integer) v.getTag();
                mOnItemClickListener.onItemClick(null, v, position, v.getId());
            }
        }
    };
}
