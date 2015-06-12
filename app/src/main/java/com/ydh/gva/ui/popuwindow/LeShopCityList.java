package com.ydh.gva.ui.popuwindow;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ydh.gva.R;
import com.ydh.gva.config.ConfigurationUrl;
import com.ydh.gva.config.SelectCity;
import com.ydh.gva.config.SelectCity.SelectCityS;
import com.ydh.gva.core.BisEntity;
import com.ydh.gva.core.YDHData;
import com.ydh.gva.ui.Base.GvaApp;
import com.ydh.gva.ui.Fresments.HomePageFragment;
import com.ydh.gva.ui.adapter.LeShopCityListAdapter;
import com.ydh.gva.util.net.url.HttpRequestBody;
import com.ydh.gva.util.net.volley.FsonRequest;
import com.ydh.gva.util.net.volley.Request;
import com.ydh.gva.util.net.volley.Response;
import com.ydh.gva.util.net.volley.VolleyError;

import java.util.List;

import de.greenrobot.event.EventBus;
import gva.ydh.com.loadview.LoadView;
import gva.ydh.com.util.AppLog;
import gva.ydh.com.util.ToastUitl;


/**
 * Created by liujianying on 14/10/21.
 */
public class LeShopCityList extends BasePopupWindow implements PopupWindow.OnDismissListener, LoadView.ToRequest {

    private RelativeLayout city_list_rl;
    private TextView city_name;
    private List<BisEntity> bisEntity;
    private LeShopCityListAdapter le;
    private GridView city_hot_grid;
    private View view_bg;
    private HomePageFragment homePage;
    private LoadView loadView;
    private int selection = -1;
    private int city_id = -1;
    private boolean isRefresh = true;


    public LeShopCityList(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LeShopCityList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LeShopCityList(Context context) {
        super(context);
    }

    public LeShopCityList(Context context, int width, int height, View view_bg, HomePageFragment homePage) {

        super(context, LayoutInflater.from(context).inflate(R.layout.city_list_popuwindow, null), width, height);

        this.homePage = homePage;
        this.view_bg = view_bg;
        setOnDismissListener(this);
        setFocusable(true);
        setBackgroundDrawable(null);
        setOutsideTouchable(false);
        setCity_id();
    }


    public void setCity_id() {
        SelectCityS  ss = SelectCity.Instance().getSelectCityS();
        if (ss.cityID != this.city_id) {
            selection = -1;
            this.city_id = ss.cityID;
            city_name.setText("当前城市:" + ss.cityName);
            getBisData(city_id);
        }
    }

    @Override
    public void initViews() {
        city_hot_grid = (GridView) findViewById(R.id.city_hot_grid);
        city_list_rl = (RelativeLayout) findViewById(R.id.city_list_rl);
        city_name = (TextView) findViewById(R.id.city_name);
        loadView = (LoadView) findViewById(R.id.load_view);
        loadView.setToRequestLinstener(this);
        loadView.setLoadSucessView(null);
        loadView.show();

        le = new LeShopCityListAdapter(mContext, bisEntity);

        city_list_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRefresh = true;
                homePage.startPinnedSection();
            }
        });
        city_hot_grid.setAdapter(le);
        le.setSelectedPosition(selection);
    }


    @Override
    public void initEvents() {

        city_hot_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {

                isRefresh = selection == arg2 ? false : true;
                selection = arg2;
                le.setSelectedPosition(selection);
                if(isRefresh){
                    selectCity(bisEntity.get(selection).getBusinessId());
                }else {
                    dismiss();
                }

            }
        });
    }

    @Override
    public void init() {

    }

    /**
     * @获取商城列表
     */
    public void getBisData(int region_cd) {
        loadView.show();
        String region_cd_string = ((region_cd / 100)) * 100 + "";
        FsonRequest stringRequest = new FsonRequest<>(Request.Method.POST, ConfigurationUrl.COMMONURL,
                YDHData.class, null, HttpRequestBody.Instance().bisList(region_cd_string), new Response.Listener<YDHData>() {
            @Override
            public void onResponse(YDHData response) {
                if (response.getResultCode() != 0) {
                    ToastUitl.showToast(mContext, response.getMsg());
                    return;
                }
                bisEntity = JSON.parseArray(response.getData(), BisEntity.class);
                if (bisEntity.size() == 0) {
                    loadView.closed(LoadView.LoadResponse.NoData, "该城市暂无商城，点击重试!");
                } else {
                    le.replaceAll(bisEntity);
                    loadView.closed(LoadView.LoadResponse.Success);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                AppLog.out(JSON.toJSONString(error));
                if (error != null && error.getMessage() != null && error.getMessage().contains("java.net.ConnectException")) {
                    loadView.closed(LoadView.LoadResponse.NoNetWork);
                } else {
                    loadView.closed(LoadView.LoadResponse.Fail);
                }

            }
        });
        GvaApp.requestQueue.add(stringRequest);
    }


    public void selectCity(final String cityId) {
        loadView.show();
        FsonRequest stringRequest = new FsonRequest<>(Request.Method.POST, ConfigurationUrl.COMMONURL,
                YDHData.class, null, HttpRequestBody.Instance().selectBis(cityId), new Response.Listener<YDHData>() {
            @Override
            public void onResponse(YDHData response) {
                if (response.getResultCode() != 0) {
                    ToastUitl.showToast(mContext, response.getMsg());
                    return;
                }

                SelectCityS selectCityS = SelectCity.Instance().getSelectCityS();
                selectCityS.bussinessId = cityId;
                selectCityS.bussinessName = bisEntity.get(selection).getBisName();
                SelectCity.Instance().setSelectCityS(selectCityS);
                loadView.closed(LoadView.LoadResponse.Success);
                dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                AppLog.out(JSON.toJSONString(error));
                if (error != null && error.getMessage() != null && error.getMessage().contains("java.net.ConnectException")) {
                    loadView.closed(LoadView.LoadResponse.NoNetWork);
                } else {
                    loadView.closed(LoadView.LoadResponse.Fail);
                }

            }
        });
        GvaApp.requestQueue.add(stringRequest);
    }


    @Override
    public void showAsDropDown(View anchor) {
        view_bg.setVisibility(View.VISIBLE);
        super.showAsDropDown(anchor);

    }

    @Override
    public void onDismiss() {

        if (isRefresh && bisEntity != null && bisEntity.size() >= 1 && selection >= -1)
            EventBus.getDefault().post(bisEntity.get(selection));
        isRefresh = false;
        view_bg.setVisibility(View.GONE);
    }

    @Override
    public void OnClickListener() {
        getBisData(city_id);
    }
}
