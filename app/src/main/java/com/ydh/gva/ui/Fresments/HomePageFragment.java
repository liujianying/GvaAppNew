package com.ydh.gva.ui.Fresments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.ydh.gva.R;
import com.ydh.gva.autoview.MyAutoView;
import com.ydh.gva.base.BaseFragment;
import com.ydh.gva.base.widget.MyListView;
import com.ydh.gva.base.widget.WLImageView;
import com.ydh.gva.config.ConfigurationUrl;
import com.ydh.gva.config.SelectCity;
import com.ydh.gva.config.SelectCity.SelectCityS;
import com.ydh.gva.core.BannerEntity;
import com.ydh.gva.core.BisEntity;
import com.ydh.gva.core.HomeCfg;
import com.ydh.gva.core.HomeCfg.HomeCfgQuick2;
import com.ydh.gva.core.NewsEntity;
import com.ydh.gva.core.YDHData;
import com.ydh.gva.location.ui.PinnedSectionListActivity;
import com.ydh.gva.ui.Base.GvaApp;
import com.ydh.gva.ui.Test11;
import com.ydh.gva.ui.activitys.BannerActivity;
import com.ydh.gva.ui.activitys.FixationActivity;
import com.ydh.gva.ui.activitys.NewsActivity;
import com.ydh.gva.ui.activitys.QuickCategoryActivity;
import com.ydh.gva.ui.activitys.TestAc;
import com.ydh.gva.ui.adapter.CategoryAdapter;
import com.ydh.gva.ui.adapter.FixationAdapter;
import com.ydh.gva.ui.adapter.NewsAdapter;
import com.ydh.gva.ui.pageTool.PageToolActivity;
import com.ydh.gva.ui.popuwindow.LeShopCityList;
import com.ydh.gva.util.net.url.HttpRequestBody;
import com.ydh.gva.util.net.volley.FsonRequest;
import com.ydh.gva.util.net.volley.Request;
import com.ydh.gva.util.net.volley.Response;
import com.ydh.gva.util.net.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;

import de.greenrobot.event.EventBus;
import gva.ydh.com.rippleviewlibrary.RippleView;
import gva.ydh.com.util.ActivityUtil;
import gva.ydh.com.util.AppLog;
import gva.ydh.com.util.ToastUitl;

/**
 * Created by liujianying on 15/5/12.
 */
public class HomePageFragment extends BaseFragment implements View.OnClickListener {

    private final static int CITY_CODE = 1;
    private View contentView;
    private TextView city_text;
    private TextView city_name;
    private List<BannerEntity> bannerEntity;
    private List<NewsEntity> newsEntityList;
    private HomeCfg homeCfgs;
    private MyAutoView homePage_autoView;
    private MyListView list_fixation;
    private MyListView list_news;
    private NewsAdapter newsAdapter;

    private WLImageView iv_pic_left;
    private WLImageView iv_pic_top;
    private WLImageView iv_pic_bottom;

    private RelativeLayout rl_city;
    private TextView title_left;
    private TextView sub_title_left;
    private TextView title_top;
    private TextView sub_title_top;
    private TextView title_bottom;
    private TextView sub_title_bottom;
    private View view_bg;
    private GridView grid_category;
    private ImageButton btn_searchLeShop;

    private LeShopCityList leShopCityList;

    private PullToRefreshScrollView scroll_all;
    private SelectCityS selectCity;


    private RippleView layout_left;

    private RippleView layout_top;

    private RippleView layout_bottom;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        contentView = inflater.inflate(R.layout.home_page_fragment, null);
        mContext = getActivity();
        initView();
        getHomeConfig();
        leShopCityList = new LeShopCityList(mContext, ActivityUtil.getScreenWidth(getActivity()), ActivityUtil.dp2px(275), view_bg, this);
        return contentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getSelect();
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * @初始化View
     */
    private void initView() {
        btn_searchLeShop = $(contentView, R.id.btn_searchLeShop);
        btn_searchLeShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TestAc.class));
            }
        });
        city_text = $(contentView, R.id.city_text);
        city_name = $(contentView, R.id.city_name);
        iv_pic_left = $(contentView, R.id.iv_pic_left);
        iv_pic_top = $(contentView, R.id.iv_pic_top);
        iv_pic_bottom = $(contentView, R.id.iv_pic_bottom);
        title_left = $(contentView, R.id.title_left);
        sub_title_left = $(contentView, R.id.sub_title_left);
        title_top = $(contentView, R.id.title_top);
        sub_title_top = $(contentView, R.id.sub_title_top);
        title_bottom = $(contentView, R.id.title_bottom);
        sub_title_bottom = $(contentView, R.id.sub_title_bottom);
        grid_category = $(contentView, R.id.grid_category);
        homePage_autoView = $(contentView, R.id.homePage_autoView);
        list_fixation = $(contentView, R.id.list_fixation);
        list_news = $(contentView, R.id.list_news);
        rl_city = $(contentView, R.id.rl_city);
        view_bg = $(contentView, R.id.view_bg);
        scroll_all = $(contentView, R.id.scroll_all);
        layout_left = $(contentView, R.id.layout_left);
        layout_top = $(contentView, R.id.layout_top);
        layout_bottom = $(contentView, R.id.layout_bottom);


        layout_left.setOnClickListener(this);
        layout_top.setOnClickListener(this);
        layout_bottom.setOnClickListener(this);
        rl_city.setOnClickListener(this);


        scroll_all.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getHomeConfig();
                refreshUi(SelectCity.Instance().getSelectCityS().bussinessName);
            }
        });

        newsAdapter = new NewsAdapter(mContext, newsEntityList);
        list_news.addHeaderView(View.inflate(mContext, R.layout.home_page_list_head, null));
        list_news.setAdapter(newsAdapter);

        homePage_autoView.setOnImageClickListener(new MyAutoView.OnImageViewClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                if (bannerEntity == null) return;
                Intent intent = new Intent(mContext, BannerActivity.class);
                intent.putExtra("BannerEntity", bannerEntity.get(position));
                startActivity(intent);

            }
        });


        list_fixation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PageToolActivity.gotoFixationActivity(mContext, FixationActivity.class, homeCfgs.fixation.get(i));
            }
        });


        list_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PageToolActivity.gotoNewsActivity(mContext, NewsActivity.class, newsEntityList.get(i - 1));
            }
        });
    }


    /**
     * 分类item点击
     */
    private AdapterView.OnItemClickListener mTypeOnItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            if (homeCfgs == null || homeCfgs.quick == null) return;
            PageToolActivity.gotoQuickCategoryActivity(mContext, homeCfgs.quick.get(position));
        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (data == null) return;
           /* 用户选择城市的回调 */
        Bundle bundle = data.getExtras();

        if (CITY_CODE == resultCode && bundle != null) {
            String cityName = bundle.getString("city");
            city_text.setText(cityName);
            int r_cd = bundle.getInt("city_id");
            /**保存选择的城市*/
            SelectCityS selectCityS = SelectCity.Instance().getSelectCityS();
            selectCityS.cityID = r_cd;
            selectCityS.cityName = cityName;
            SelectCity.Instance().setSelectCityS(selectCityS);
            leShopCityList.setCity_id();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startPinnedSection() {
        startActivityForResult(new Intent(mContext, PinnedSectionListActivity.class), 0);
    }


    /**
     * @获取最新资讯
     */
    private void getNews() {
        FsonRequest stringRequest = new FsonRequest<>(Request.Method.POST, ConfigurationUrl.COMMONURL,
                YDHData.class, null, HttpRequestBody.Instance().news(), new Response.Listener<YDHData>() {
            @Override
            public void onResponse(YDHData response) {
                if (response.getResultCode() != 0) {
                    ToastUitl.showToast(mContext, response.getMsg());
                    return;
                }
                AppLog.out(JSON.toJSONString(response));
                newsEntityList = JSON.parseArray(response.getData(), NewsEntity.class);
                if (newsEntityList != null && newsEntityList.size() == 0) {
                    list_news.setVisibility(View.GONE);
                } else {
                    list_news.setVisibility(View.VISIBLE);
                }
                newsAdapter.replaceAll(newsEntityList);

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
     * @获取首页广告
     */
    private void getBannerList() {
        FsonRequest stringRequest = new FsonRequest<>(Request.Method.POST, ConfigurationUrl.COMMONURL,
                YDHData.class, null, HttpRequestBody.Instance().banner(), new Response.Listener<YDHData>() {
            @Override
            public void onResponse(YDHData response) {

                if (response.getResultCode() == 0) {
                    bannerEntity = JSON.parseArray(response.getData(), BannerEntity.class);
                    setBannerUi();
                } else {
                    ToastUitl.showToast(mContext, response.getMsg());
                }

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
     * @获取首页配置
     */
    private void getHomeConfig() {
        FsonRequest stringRequest = new FsonRequest<>(Request.Method.POST, ConfigurationUrl.COMMONURL,
                YDHData.class, null, HttpRequestBody.Instance().homeCfg(), new Response.Listener<YDHData>() {
            @Override
            public void onResponse(YDHData response) {
                scroll_all.onRefreshComplete();
                if (response.getResultCode() != 0) {
                    ToastUitl.showToast(mContext, response.getMsg());
                    return;
                }

                homeCfgs = JSON.parseObject(response.getData(), HomeCfg.class);

                if (homeCfgs == null) return;

                CategoryAdapter categoryAdapter = new CategoryAdapter(mContext, homeCfgs.quick);
                grid_category.setAdapter(categoryAdapter);
                categoryAdapter.setOnItemClickListener(mTypeOnItemClickListener);

                if (homeCfgs.quick2 != null) {

                    HomeCfgQuick2 q0 = homeCfgs.quick2.get(0);
                    HomeCfgQuick2 q1 = homeCfgs.quick2.get(1);
                    HomeCfgQuick2 q2 = homeCfgs.quick2.get(2);
                    title_left.setText(q0.title);
                    iv_pic_left.setImageURI(q0.moreImg);
                    title_top.setText(q1.title);
                    iv_pic_top.setImageURI(q1.moreImg);
                    title_bottom.setText(q2.title);
                    iv_pic_bottom.setImageURI(q2.moreImg);
                }

                if (homeCfgs.fixation != null) {
                    FixationAdapter fixationAdapter = new FixationAdapter(mContext, homeCfgs.fixation);
                    list_fixation.setAdapter(fixationAdapter);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AppLog.out(JSON.toJSONString(error));
            }
        });
        GvaApp.requestQueue.add(stringRequest);
    }


    private void setBannerUi() {
        List<String> list = new ArrayList<>();
        for (BannerEntity bEntity : bannerEntity) {
            list.add(bEntity.getMoreImg());
        }
        homePage_autoView.setVisibility(list != null && list.size() >= 1 ? View.VISIBLE : View.GONE);
        homePage_autoView.setDataForUrl(list,
                com.ydh.gva.autoview.R.mipmap.bg_leshop_home_ad);
    }

    public void refreshUi(String bisName) {

        homePage_autoView.setVisibility(View.GONE);
        list_news.setVisibility(View.GONE);
        city_name.setText(bisName);
        getBannerList();
        getNews();
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.rl_city:
                leShopCityList.showAsDropDown(rl_city);
                break;
            case R.id.homePage_autoView:
                break;
            case R.id.layout_left:
                PageToolActivity.gotoQuickCategoryTowActivity(mContext, homeCfgs, 0);
                break;
            case R.id.layout_top:
                PageToolActivity.gotoQuickCategoryTowActivity(mContext, homeCfgs, 1);
                break;
            case R.id.layout_bottom:
                PageToolActivity.gotoQuickCategoryTowActivity(mContext, homeCfgs, 2);
                break;
        }
    }

    /**
     * @param bisEntity
     * @城市列表数据更新
     */
    public void onEventMainThread(BisEntity bisEntity) {
        refreshUi(bisEntity.getBisName());
    }

    /**
     * @登录后请求默认商城列表是否选择成功
     */
    public void getSelect() {
        this.selectCity = SelectCity.Instance().getSelectCityS();
        city_text.setText(selectCity.cityName);
        if (SelectCity.Instance().isSelectSuccess()) {
            refreshUi(selectCity.bussinessName);
            leShopCityList.setCity_id();
        } else {
            startActivityForResult(new Intent(getActivity(), PinnedSectionListActivity.class), 0);
            leShopCityList.showAsDropDown(rl_city);
        }

    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
