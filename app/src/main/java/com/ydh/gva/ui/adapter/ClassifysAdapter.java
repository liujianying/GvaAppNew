package com.ydh.gva.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ydh.gva.R;
import com.ydh.gva.core.Classifys;

import java.util.List;

/**
 * Created by liujianying on 15/6/11.
 */
public class ClassifysAdapter extends SimpleBaseAdapter<Classifys> {

    public ClassifysAdapter(Context context, List<Classifys> list) {
        super(context, list);
        isCache = true;
    }

    @Override
    public int getItemResource() {
        return R.layout.business_category;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {
        Classifys classifys = (Classifys)getItem(position);
        TextView textView = holder.getView(R.id.bus_category_text);
        textView.setText(classifys.classifyName);
        return convertView;
    }

}
