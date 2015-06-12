package com.ydh.gva.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ydh.gva.R;
import com.ydh.gva.base.widget.WLImageView;
import com.ydh.gva.core.MerchantsEntity;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by liujianying on 15/6/8.
 */
public class MerchantsListAdapter extends SimpleBaseAdapter<MerchantsEntity> {

    public MerchantsListAdapter(Context context, List<MerchantsEntity> list) {
        super(context, list);
    }


    @Override
    public int getItemResource() {
        return R.layout.list_item_card_detail;
    }

    @Override
    public View getItemView(int position, View convertView, ViewHolder holder) {

        MerchantsEntity merchantsEntity = (MerchantsEntity)getItem(position);
        TextView row_title = holder.getView(R.id.row_title);
        TextView myclass_classmateprogress_text = holder.getView(R.id.myclass_classmateprogress_text);
        TextView myclass_myprogress_text = holder.getView(R.id.myclass_myprogress_text);
        TextView credit = holder.getView(R.id.credit);

        WLImageView wl = holder.getView(R.id.row_iv);
        wl.setImageURI(merchantsEntity.merchantImg);
        row_title.setText(merchantsEntity.merchantName);
        myclass_classmateprogress_text.setText(merchantsEntity.merchantTitle);
        myclass_myprogress_text.setText(merchantsEntity.merchantIntro);
        credit.setText("地址:" + merchantsEntity.merchantAddr);
        return convertView;
    }


}
