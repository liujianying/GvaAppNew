package com.ydh.gva.ui.activitys;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.ydh.gva.R;
import com.ydh.gva.base.SwipeActivity;
import com.ydh.gva.config.ConfigurationUrl;
import com.ydh.gva.core.BisEntity;
import com.ydh.gva.core.Classifys;
import com.ydh.gva.core.YDHData;
import com.ydh.gva.ui.Base.GvaApp;
import com.ydh.gva.ui.Fresments.ClassifysFragment;
import com.ydh.gva.util.net.url.HttpRequestBody;
import com.ydh.gva.util.net.volley.FsonRequest;
import com.ydh.gva.util.net.volley.Request;
import com.ydh.gva.util.net.volley.Response;
import com.ydh.gva.util.net.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import gva.ydh.com.util.AppLog;
import gva.ydh.com.util.ToastUitl;

/**
 * Created by liujianying on 15/6/10.
 */
public class TestAc extends SwipeActivity {

    private List<Classifys> classifys = new ArrayList<>();
    private ViewPager viewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.mall_page_fragemnt);
        initView();
        getClassifys();
    }


    private void initView() {
        viewPager = $( R.id.viewpager);
        mTabLayout = $( R.id.tabs);

    }

    private void initData() {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        classifys.add(new Classifys());
        classifys.add(new Classifys());
        classifys.add(new Classifys());
        for (Classifys cfys : classifys) {
            ClassifysFragment cf = new ClassifysFragment();
            cf.setRequestValue("tag", cfys.classifyId);
            fragments.add(cf);
            mTabLayout.addTab(mTabLayout.newTab().setText(cfys.classifyName));
            titles.add(cfys.classifyName);
        }


        FragmentAdapter adapter =
                new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(viewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }



    /**
     * @return
     * @商场内定义的分类
     */
    public final void getClassifys() {
        FsonRequest stringRequest = new FsonRequest<>(Request.Method.POST, ConfigurationUrl.COMMONURL,
                YDHData.class, null, HttpRequestBody.Instance().classifys(), new Response.Listener<YDHData>() {
            @Override
            public void onResponse(YDHData response) {
                if (response.getResultCode() != 0) {
                    ToastUitl.showToast(mContext, response.getMsg());
                    return;
                }

                classifys = JSON.parseArray(response.getData(), Classifys.class);
                initData();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppLog.out(JSON.toJSONString(error));
            }
        });
        GvaApp.requestQueue.add(stringRequest);
    }




    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
