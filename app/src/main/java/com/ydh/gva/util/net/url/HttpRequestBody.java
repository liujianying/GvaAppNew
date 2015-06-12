package com.ydh.gva.util.net.url;

import com.ydh.gva.config.Session;
import com.ydh.gva.util.config.SystemVal;
import com.ydh.gva.util.encryption.AESCrypto;
import com.ydh.gva.util.encryption.MD5Util;

import org.json.JSONException;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;

public class HttpRequestBody {


    public static HttpRequestBody httpRequestBody = null;

    public static HttpRequestBody Instance() {

        if (httpRequestBody == null) {
            synchronized (HttpRequestBody.class) {
                if (httpRequestBody == null) httpRequestBody = new HttpRequestBody();
            }
        }
        return httpRequestBody;
    }

    private HttpRequestBody() {

    }

    /**
     * 加密data数据
     *
     * @param data
     * @return
     */
    private static String encrypt(String data) {

        return AESCrypto.encrypt(SystemVal.getLoginInfo().getKey(), data.toString());

    }




    /**
     * 请求登录接口
     *
     * @param userName
     * @param password
     * @return
     * @throws JSONException
     */
    public String requestManagerLoginBody(String userName, String password) throws JSONException {
        JSONObject data = new JSONObject();
        data.put("userName", userName);
        data.put("password", MD5Util.getMD5(password));
        JSONObject request = new JSONObject();
        request.put("data", data);
        request.put("encryptType", 2);
        return request.toString();
    }


    /**
     * @return
     * @获取session
     * @createSession
     */
    public final HashMap<String, String> createSession() {
        HashMap<String, String> map = new HashMap<>();
        map.put("act", "createSession");
        map.put("session", "");
        map.put("request", "{}");

        return map;
    }



    /**
     * @param isEncryptType
     * @return
     * @公共session
     */
    public HashMap<String, String> publicMap(int isEncryptType, String interfaceStr) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("session", Session.Instance().getSessionString());
        hashMap.put("encryptType", isEncryptType+ "");
        hashMap.put("act", interfaceStr);
        return hashMap;
    }



    /**
     * @return
     * @商场列表
     */
    public final HashMap<String, String> bisList(String data) {
        HashMap<String, String> hashMap = publicMap(0, "bisList");
        JSONObject json = new JSONObject();
        json.put("data", data);
        hashMap.put("request", json.toJSONString());
        return hashMap;
    }


    /**
     * @return
     * @选择商场
     */
    public final HashMap<String, String> selectBis(String data) {
        HashMap<String, String> hashMap = publicMap(0, "selectBis");
        JSONObject json = new JSONObject();
        json.put("data", data);
        hashMap.put("request", json.toJSONString());
        return hashMap;
    }

    /**
     * @return
     * @滚动广告
     */
    public final HashMap<String, String> banner() {
        HashMap<String, String> hashMap = publicMap(0, "banner");
        hashMap.put("request", "{}");
        return hashMap;
    }

    /**
     * @最新资讯
     * @return
     */
    public final HashMap<String, String> news() {
        HashMap<String, String> hashMap = publicMap(0, "news");
        hashMap.put("request", "{}");
        return hashMap;
    }

      /**
     * @首页配置
     * @return
     */
    public final HashMap<String, String> homeCfg() {
        HashMap<String, String> hashMap = publicMap(0, "homeCfg");
        hashMap.put("request", "{}");
        return hashMap;
    }


    /**
     * @商场内定义的分类
     * @return
     */
    public final HashMap<String, String> classifys() {
        HashMap<String, String> hashMap = publicMap(0, "classifys");
        hashMap.put("request", "{}");
        return hashMap;
    }


    /**
     * @商场内定义的标签
     * @return
     */
    public final HashMap<String, String> tags() {
        HashMap<String, String> hashMap = publicMap(0, "tags");
        hashMap.put("request", "{}");
        return hashMap;
    }



    /**
     * @商场内定义的楼层
     * @return
     */
    public final HashMap<String, String> floors() {
        HashMap<String, String> hashMap = publicMap(0, "floors");
        hashMap.put("request", "{}");
        return hashMap;
    }


    /**
     * @商户列表(分类/标签/楼层)
     * @return
     */
    public final HashMap<String, String> merchants(String type, String typeValue, int pageCurrent, int pageSize) {
        HashMap<String, String> hashMap = publicMap(0, "merchants");
        JSONObject json = new JSONObject();
        json.put("type", type);
        json.put("typeValue", typeValue);
        json.put("pageCurrent", pageCurrent);
        json.put("pageSize", pageSize);
        hashMap.put("request", json.toJSONString());
        return hashMap;
    }

}