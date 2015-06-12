package com.ydh.gva.ui.Fresments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageSwitcher;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.ydh.gva.R;
import com.ydh.gva.base.BaseFragment;
import com.ydh.gva.config.ConfigurationUrl;
import com.ydh.gva.config.SelectCity;
import com.ydh.gva.core.BisEntity;
import com.ydh.gva.core.Classifys;
import com.ydh.gva.core.FloorsEntity;
import com.ydh.gva.core.TagsEntity;
import com.ydh.gva.core.YDHData;
import com.ydh.gva.ui.Base.GvaApp;
import com.ydh.gva.ui.popuwindow.BusinessPopuWindow;
import com.ydh.gva.util.net.url.HttpRequestBody;
import com.ydh.gva.util.net.volley.FsonRequest;
import com.ydh.gva.util.net.volley.Request;
import com.ydh.gva.util.net.volley.Response;
import com.ydh.gva.util.net.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import gva.ydh.com.util.ActivityUtil;
import gva.ydh.com.util.AppLog;
import gva.ydh.com.util.ToastUitl;


/**
 * Created by liujianying on 15/5/12.
 */
public class MallPageFragment extends BaseFragment implements Animation.AnimationListener{

    private List<Classifys> classifys = new ArrayList<>();
    private List<TagsEntity> tagsEntities = new ArrayList<>();
    private List<FloorsEntity> floorsEntities = new ArrayList<>();
    private View contentView;
    private ViewPager viewPager;
    private TabLayout mTabLayout;
    private ImageView show_business_list;
    private Animation operatingAnim;
    private BusinessPopuWindow businessPopuWindow;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.mall_page_fragemnt, null);
        mContext = getActivity();
        EventBus.getDefault().register(this);
        initView();
        return contentView;
    }


    private void initView() {
        operatingAnim = AnimationUtils.loadAnimation(mContext, R.anim.rotate_180);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        operatingAnim.setAnimationListener(this);

        viewPager = $(contentView, R.id.viewpager);
        mTabLayout = $(contentView, R.id.tabs);
        show_business_list = $(contentView, R.id.show_business_list);
        show_business_list.setTag(0);

        businessPopuWindow = new BusinessPopuWindow(mContext, ActivityUtil.getScreenWidth(getActivity()),
                ActivityUtil.getScreenHeight(getActivity()) - ActivityUtil.dp2px(165),this);
        show_business_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                businessPopuWindow.showAsDropDown(show_business_list);
                setImageAnimation();
            }
        });
    }


    /**
     * @启动动画
     */
    public void setImageAnimation() {
        show_business_list.startAnimation(operatingAnim);
    }

    private void initData() {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();

        for (Classifys cfys : classifys) {
            ClassifysFragment cf = new ClassifysFragment();
            cf.setRequestValue("classify", cfys.classifyId);
            fragments.add(cf);
            mTabLayout.addTab(mTabLayout.newTab().setText(cfys.classifyName));
            titles.add(cfys.classifyName);
        }

        FragmentAdapter adapter =
                new FragmentAdapter(getChildFragmentManager(), fragments, titles);
        viewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(viewPager);
        mTabLayout.setTabsFromPagerAdapter(adapter);
    }


    public void setTable(int i) {
        viewPager.setCurrentItem(i);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(SelectCity.Instance().isSelectSuccess()) {
            getClassifys();
            getTags();
            getFloors();
        }

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
                businessPopuWindow.setClassifysList(classifys);
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

    /**
     * @return
     * @商场内定义的标签
     */
    public final void getTags() {
        FsonRequest stringRequest = new FsonRequest<>(Request.Method.POST, ConfigurationUrl.COMMONURL,
                YDHData.class, null, HttpRequestBody.Instance().tags(), new Response.Listener<YDHData>() {
            @Override
            public void onResponse(YDHData response) {
                if (response.getResultCode() != 0) {
                    ToastUitl.showToast(mContext, response.getMsg());
                    return;
                }

                tagsEntities = JSON.parseArray(response.getData(), TagsEntity.class);
                businessPopuWindow.setTagsList(tagsEntities);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppLog.out(JSON.toJSONString(error));
            }
        });
        GvaApp.requestQueue.add(stringRequest);
    }


    /**
     * @return
     * @商场内定义的楼层
     */
    public final void getFloors() {
        FsonRequest stringRequest = new FsonRequest<>(Request.Method.POST, ConfigurationUrl.COMMONURL,
                YDHData.class, null, HttpRequestBody.Instance().floors(), new Response.Listener<YDHData>() {
            @Override
            public void onResponse(YDHData response) {
                if (response.getResultCode() != 0) {
                    ToastUitl.showToast(mContext, response.getMsg());
                    return;
                }
                floorsEntities = JSON.parseArray(response.getData(), FloorsEntity.class);
                businessPopuWindow.setFloorsList(floorsEntities);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppLog.out(JSON.toJSONString(error));
            }
        });
        GvaApp.requestQueue.add(stringRequest);
    }



    /**
     * @param bisEntity
     * @城市列表数据更新
     */
    public void onEventMainThread(BisEntity bisEntity) {
        getClassifys();
        getTags();
        getFloors();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        int tag = (int)show_business_list.getTag();
        if(tag == 0) {
            show_business_list.setImageResource(R.mipmap.filter_close_more);
            show_business_list.setTag(1);
        }else {
            show_business_list.setImageResource(R.mipmap.filter_open_more);
            show_business_list.setTag(0);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

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
