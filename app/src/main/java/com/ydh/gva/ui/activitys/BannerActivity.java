package com.ydh.gva.ui.activitys;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.ydh.gva.R;
import com.ydh.gva.base.SwipeActivity;
import com.ydh.gva.core.BannerEntity;

/**
 * Created by liujianying on 15/5/27.
 */
public class BannerActivity extends SwipeActivity {

    private PullToRefreshWebView baner_web_view;
    private BannerEntity bannerEntity;
    private ImageButton btn_back;
    private TextView title_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.banner_activity);

        bannerEntity = getIntent().getParcelableExtra("BannerEntity");

        baner_web_view = $(R.id.baner_web_view);
        title_id = $(R.id.title_id);
        title_id.setVisibility(View.VISIBLE);
        title_id.setText(bannerEntity.getTitle());

        btn_back = $(R.id.btn_back);
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        WebView webView = baner_web_view.getRefreshableView();
        WebSettings webSettings = webView.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //加载需要显示的网页
        webView.loadUrl(bannerEntity.getMoreUrl());
        //设置Web视图
        webView.setWebViewClient(new WebViewClient());
    }
}
