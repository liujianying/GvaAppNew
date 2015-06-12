package com.ydh.gva.ui.popuwindow;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.ydh.gva.R;
import com.ydh.gva.core.Classifys;
import com.ydh.gva.core.FloorsEntity;
import com.ydh.gva.core.TagsEntity;
import com.ydh.gva.ui.Fresments.MallPageFragment;
import com.ydh.gva.ui.activitys.FloorsActivity;
import com.ydh.gva.ui.activitys.TagActivity;
import com.ydh.gva.ui.adapter.ClassifysAdapter;
import com.ydh.gva.ui.adapter.FloorsAdapter;
import com.ydh.gva.ui.adapter.TagsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujianying on 15/6/11.
 */
public class BusinessPopuWindow extends BasePopupWindow implements PopupWindow.OnDismissListener ,View.OnClickListener {

    private GridView grid_classifys;
    private GridView grid_tags;
    private GridView grid_floors;
    private ClassifysAdapter classifysAdapter;
    private TagsAdapter tagsAdapter;
    private FloorsAdapter floorsAdapter;
    private List<Classifys> classifys = new ArrayList<>();
    private List<TagsEntity> tagsEntities = new ArrayList<>();
    private List<FloorsEntity> floorsEntities = new ArrayList<>();
    private MallPageFragment mFragment;

    public BusinessPopuWindow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public BusinessPopuWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BusinessPopuWindow(Context context) {
        super(context);
    }

    public BusinessPopuWindow(Context context, int width, int height, MallPageFragment mFragment) {

        super(context, LayoutInflater.from(context).inflate(R.layout.business_popuwindow, null), width, height);
        setOnDismissListener(this);
        this.mFragment = mFragment;
    }




    public void setClassifysList(List<Classifys> classifys) {
        this.classifys = classifys;
        classifysAdapter.replaceAll(classifys);
    }


    public void setTagsList(List<TagsEntity> tagsEntities) {
        this.tagsEntities = tagsEntities;
        tagsAdapter.replaceAll(tagsEntities);
    }

    public void setFloorsList(List<FloorsEntity> floorsList) {
        this.floorsEntities = floorsList;
        floorsAdapter.replaceAll(floorsList);
    }

    @Override
    public void initViews() {
        grid_classifys = (GridView) findViewById(R.id.grid_classifys);
        grid_tags = (GridView) findViewById(R.id.grid_tags);
        grid_floors = (GridView) findViewById(R.id.grid_floors);
        classifysAdapter = new ClassifysAdapter(mContext, classifys);
        tagsAdapter = new TagsAdapter(mContext, tagsEntities);
        floorsAdapter = new FloorsAdapter(mContext, floorsEntities);

        grid_classifys.setAdapter(classifysAdapter);
        grid_tags.setAdapter(tagsAdapter);
        grid_floors.setAdapter(floorsAdapter);
//
        grid_classifys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mFragment.setTable(position);
                dismiss();
            }
        });
        grid_tags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, TagActivity.class);
                intent.putParcelableArrayListExtra("TagsEntity", (ArrayList) tagsEntities);
                intent.putExtra("position", position);
                mContext.startActivity(intent);
                dismiss();
            }
        });
        grid_floors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, FloorsActivity.class);
                intent.putParcelableArrayListExtra("FloorsEntity", (ArrayList) floorsEntities);
                intent.putExtra("position", position);
                mContext.startActivity(intent);
                dismiss();
            }
        });

    }

    @Override
    public void initEvents() {

    }

    @Override
    public void init() {

    }


    @Override
    public void onDismiss() {
        mFragment.setImageAnimation();
    }


    @Override
    public void onClick(View v) {
        onDismiss();
    }
}
