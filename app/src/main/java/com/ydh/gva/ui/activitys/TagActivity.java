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
import com.ydh.gva.core.Classifys;
import com.ydh.gva.core.TagsEntity;
import com.ydh.gva.ui.Fresments.ClassifysFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liujianying on 15/6/11.
 * @
 */
public class TagActivity  extends SwipeActivity {

    private List<TagsEntity> tagsEntities = new ArrayList<>();
    private ViewPager tag_viewpager;
    private TabLayout tag_tabs;
    private ImageButton btn_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tag_activity);
        initView();
    }

    private void initView() {
        tagsEntities = getIntent().getParcelableArrayListExtra("TagsEntity");
        tag_tabs = $(R.id.tag_tabs);
        tag_viewpager = $(R.id.tag_viewpager);
        btn_back = $(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        for (TagsEntity cfys : tagsEntities) {
            ClassifysFragment cf = new ClassifysFragment();
            cf.setRequestValue("tag", cfys.businessId);
            fragments.add(cf);
            tag_tabs.addTab(tag_tabs.newTab().setText(cfys.tagName));
            titles.add(cfys.tagName);
        }

        FragmentAdapter adapter =
                new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        tag_viewpager.setAdapter(adapter);
        tag_tabs.setupWithViewPager(tag_viewpager);
        tag_tabs.setTabsFromPagerAdapter(adapter);
        tag_viewpager.setCurrentItem(getIntent().getIntExtra("position", 0));
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
