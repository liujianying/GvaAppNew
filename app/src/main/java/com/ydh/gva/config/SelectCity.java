package com.ydh.gva.config;

import com.alibaba.fastjson.JSON;
import com.ydh.gva.ui.Base.GvaApp;

import gva.ydh.com.util.SharedPreUtil;

/**
 * Created by liujianying on 15/6/10.
 * @城市选择
 */
public class SelectCity {

    private String SelectCity = "SelectCity";
    private  boolean isSelectSuccess = false;
    public static SelectCity selectCity;

    public static SelectCity Instance() {
        if (selectCity == null) {
            selectCity = new SelectCity();
        }
        return selectCity;
    }

    private SelectCity() {}

    /**
     * @获取选择城市
     */
    public SelectCityS getSelectCityS() {
        return JSON.parseObject(SharedPreUtil.get(GvaApp.wlApp, SelectCity, "{}"), SelectCityS.class);
    }
    /**
     * @选择城市
     */
    public void setSelectCityS(SelectCityS selectCityS) {
        SharedPreUtil.set(GvaApp.wlApp, SelectCity, JSON.toJSONString(selectCityS));
    }

    /**
     * @选择城市是否选择成功
     */
    public boolean isSelectSuccess() {
        return isSelectSuccess;
    }
    /**
     * @选择城市是否选择成功
     */
    public void setIsSelectSuccess(boolean isSelectSuccess) {
        this.isSelectSuccess = isSelectSuccess;
    }


    public static class SelectCityS {
        private SelectCityS(){}
        public String bussinessId;
        public String cityName;
        public String bussinessName;
        public int cityID;
    }
}
