package com.ydh.gva.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.ydh.gva.R;
import com.ydh.gva.base.BaseActivity;
import com.ydh.gva.config.ConfigurationUrl;
import com.ydh.gva.config.SelectCity;
import com.ydh.gva.config.SelectCity.SelectCityS;
import com.ydh.gva.config.Session;
import com.ydh.gva.core.YDHData;
import com.ydh.gva.location.ui.PinnedSectionListActivity;
import com.ydh.gva.ui.Base.GvaApp;
import com.ydh.gva.util.net.url.HttpRequestBody;
import com.ydh.gva.util.net.volley.FsonRequest;
import com.ydh.gva.util.net.volley.Request;
import com.ydh.gva.util.net.volley.Response;
import com.ydh.gva.util.net.volley.VolleyError;

import gva.ydh.com.util.ToastUitl;

/**
 * Created by liujianying on 15/5/18.
 */
public class LogoActivity extends BaseActivity {

    private SimpleDraweeView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_logo);
        logo = $(R.id.logo);
        ImageView m = new ImageView(this);
        m.setScaleType(ImageView.ScaleType.CENTER);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getSession();
            }
        }, 100);
    }


    public void getSession() {
        Session.requestSession(new Session.SessionInterfaec() {
            @Override
            public void onSessionSuccess(YDHData response) {

                selectCity();
            }

            @Override
            public void onSessionFailure(VolleyError error) {
                selectCity();
                ToastUitl.showToast(mContext, "Session 获取失败");
            }
        });
    }


    public void selectCity() {

        SelectCityS selectCityS = SelectCity.Instance().getSelectCityS();


        if(selectCityS.bussinessId == null) {
            isSelectCitySuccess(false);
        }else {
            FsonRequest stringRequest = new FsonRequest<>(Request.Method.POST, ConfigurationUrl.COMMONURL,
                    YDHData.class, null, HttpRequestBody.Instance().selectBis(selectCityS.bussinessId), new Response.Listener<YDHData>() {
                @Override
                public void onResponse(YDHData response) {
                    isSelectCitySuccess(response.getResultCode() == 0 ? true : false);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    isSelectCitySuccess(false);
                }
            });
            GvaApp.requestQueue.add(stringRequest);
        }

    }

    private void isSelectCitySuccess(boolean isSuccess) {
        SelectCity.Instance().setIsSelectSuccess(isSuccess);
        startMainActivity();
        finish();
    }


    private void startMainActivity() {

        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
    }

}
