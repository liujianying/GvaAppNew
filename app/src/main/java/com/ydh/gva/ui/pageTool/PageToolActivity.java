package com.ydh.gva.ui.pageTool;

import android.content.Context;
import android.content.Intent;

import com.ydh.gva.core.HomeCfg;
import com.ydh.gva.core.HomeCfg.Fixation;
import com.ydh.gva.core.NewsEntity;
import com.ydh.gva.ui.activitys.QuickCategoryActivity;
import com.ydh.gva.ui.activitys.QuickCategoryTowActivity;

/**
 * Created by liujianying on 15/5/27.
 */
public class PageToolActivity {

    /**
     * @param context
     * @param clzz
     * @param fixation
     */
    public static void gotoFixationActivity(Context context, Class clzz, Fixation fixation) {
        Intent intent = new Intent(context,clzz );
        intent.putExtra("Fixation", fixation);
        context.startActivity(intent);
    }

    /**
     * @param context
     * @param clzz
     * @param newsEntity
     */
    public static void gotoNewsActivity(Context context, Class clzz, NewsEntity newsEntity) {
        Intent intent = new Intent(context,clzz );
        intent.putExtra("NewsEntity", newsEntity);
        context.startActivity(intent);
    }


    /**
     * @param context
     * @param homeCfg
     */
    public static void gotoQuickCategoryActivity(Context context, HomeCfg.HomeCfgQuick homeCfg) {
        Intent intent = new Intent(context, QuickCategoryActivity.class);
        intent.putExtra("Quick", homeCfg);
        context.startActivity(intent);
    }

    /**
     * @param context
     * @param homeCfg
     */
    public static void gotoQuickCategoryTowActivity(Context context, HomeCfg homeCfg, int posint) {
        if(homeCfg == null || homeCfg.quick2 == null) return;
        Intent intent = new Intent(context, QuickCategoryTowActivity.class);
        intent.putExtra("Quick2", homeCfg.quick2.get(posint));
        context.startActivity(intent);
    }


}
