package com.ydh.gva.ui.activitys;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.ydh.gva.R;
import com.ydh.gva.base.SwipeActivity;
import com.ydh.gva.core.FloorsEntity;
import com.ydh.gva.ui.Fresments.ClassifysFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujianying on 15/6/12.
 * @楼层标签
 */
public class FloorsActivity extends SwipeActivity{


    private List<FloorsEntity> floorsEntities = new ArrayList<>();
    private ViewPager floors_viewpager;
    private TabLayout tag_floors;
    private ImageButton btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.floors);
        initView();
    }

    private void initView() {
        floorsEntities = getIntent().getParcelableArrayListExtra("FloorsEntity");
        tag_floors = $(R.id.tag_floors);
        floors_viewpager = $(R.id.floors_viewpager);
        btn_back = $(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        for (FloorsEntity cfys : floorsEntities) {
            ClassifysFragment cf = new ClassifysFragment();
            cf.setRequestValue("tag", cfys.businessId);
            fragments.add(cf);
            tag_floors.addTab(tag_floors.newTab().setText(cfys.floorId));
            titles.add(cfys.floorName);
        }

        FragmentAdapter adapter =
                new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        floors_viewpager.setAdapter(adapter);
        tag_floors.setupWithViewPager(floors_viewpager);
        tag_floors.setTabsFromPagerAdapter(adapter);
        floors_viewpager.setCurrentItem(getIntent().getIntExtra("position", 0));
    }

    public class FragmentAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragments;
        private List<String> mTitles;

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }
    }


}
