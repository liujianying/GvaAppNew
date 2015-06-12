package com.ydh.gva.ui.Fresments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ydh.gva.R;
import com.ydh.gva.base.BaseFragment;
import com.ydh.gva.config.ConfigurationUrl;
import com.ydh.gva.core.MerchantsEntity;
import com.ydh.gva.core.YDHData;
import com.ydh.gva.ui.Base.GvaApp;
import com.ydh.gva.ui.adapter.MerchantsListAdapter;
import com.ydh.gva.util.net.url.HttpRequestBody;
import com.ydh.gva.util.net.volley.FsonRequest;
import com.ydh.gva.util.net.volley.Request;
import com.ydh.gva.util.net.volley.Response;
import com.ydh.gva.util.net.volley.VolleyError;

import java.util.List;

import gva.ydh.com.util.AppLog;
import gva.ydh.com.util.ToastUitl;


/**
 * Created by liujianying on 15/6/8.
 * @商城列表
 */
public class ClassifysFragment extends BaseFragment {

    private final int pageSize = 20;
    private int mCurrentPage = 1;
    private boolean hasNext = false;
    private List<MerchantsEntity> list;

    private String type;
    private String typeValue;
    private MerchantsListAdapter mAdapter;
    private PullToRefreshListView mRecyclerView;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.classifys_fragment, container, false);
        mContext = getActivity();
        mRecyclerView =
                (PullToRefreshListView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
                mCurrentPage = 1;
                hasNext = true;
                getMerchants();
            }

            @Override
            public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView) {
                if (hasNext) {
                    mCurrentPage++;
                    getMerchants();
                } else {
                    ToastUitl.showToast(mContext, "已经是最后一页了");
                    mRecyclerView.onRefreshComplete();
                }
            }
        });

        mAdapter = new MerchantsListAdapter(getActivity(), list);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMerchants();
    }

    public void setRequestValue(String type, String typeValue) {
        this.type = type;
        this.typeValue = typeValue;
    }


    /**
     * @获取商场数据
     */
    public void getMerchants() {
        FsonRequest stringRequest = new FsonRequest<>(Request.Method.POST, ConfigurationUrl.COMMONURL,
                YDHData.class,null, HttpRequestBody.Instance().merchants(type, typeValue, mCurrentPage, pageSize), new Response.Listener<YDHData>() {
            @Override
            public void onResponse(YDHData response) {

                mRecyclerView.onRefreshComplete();
                if(response.getResultCode() != 0) {
                    ToastUitl.showToast(mContext, response.getMsg());
                    return;
                }

                list = JSON.parseArray(response.getData(), MerchantsEntity.class);
                if(mCurrentPage == 1) {
                    mAdapter.replaceAll(list);
                }else {
                    mAdapter.addAll(list);
                }

                if(list.size() < pageSize) {
                    hasNext = false;
                }

                AppLog.out(JSON.toJSONString(response));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mRecyclerView.onRefreshComplete();
                AppLog.out(JSON.toJSONString(error));
            }
        });
        GvaApp.requestQueue.add(stringRequest);
    }

}
