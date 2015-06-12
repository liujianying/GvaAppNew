package com.ydh.gva.ui.Fresments;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ydh.gva.R;
import com.ydh.gva.base.BaseFragment;

/**
 * Created by liujianying on 15/5/12.
 */
public class MorePageFragment extends BaseFragment {

    View contentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        contentView = inflater.inflate(R.layout.more_page_fagment, null);
        mContext = getActivity();
        CollapsingToolbarLayout collapsingToolbar =
                $(contentView, R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("个人中心");
        return contentView;
    }
}
