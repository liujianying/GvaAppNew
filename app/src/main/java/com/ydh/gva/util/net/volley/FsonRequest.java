package com.ydh.gva.util.net.volley;

import com.alibaba.fastjson.JSON;
import com.ydh.gva.core.YDHData;
import com.ydh.gva.util.net.volley.toolbox.HttpHeaderParser;

import java.util.HashMap;
import java.util.Map;

import gva.ydh.com.util.AppLog;

/**
 * Created by liujianying on 15/5/18.
 */
public class FsonRequest<T> extends Request<T> {

    private final Class<T> clazz;
    private final Map<String, String> headers;
    private final Map<String, String> params;
    private final Response.Listener<T> listener;

    public FsonRequest(int method,
                       String url,
                       Class<T> clazz,
                       Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);

        this.clazz = clazz;
        this.headers = new HashMap();
        this.params = new HashMap();
        this.listener = listener;
    }

    public FsonRequest(int method,
                       String url,
                       Class<T> clazz,
                       Map<String, String> headers,
                       Map<String, String> params,
                       Response.Listener<T> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, errorListener);

        this.clazz = clazz;
        this.headers = headers;
        this.params = params;
        this.listener = listener;
    }

    public FsonRequest addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public FsonRequest addParam(String key, String value) {
        params.put(key, value);
        return this;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params != null ? params : super.getParams();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {

        try {
            String json = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));


            AppLog.E("Response", json);
            YDHData ydhData = (YDHData)JSON.parseObject(json, clazz);
            ydhData.fsonEnncryptToString();
            return Response.success((T)ydhData, HttpHeaderParser.parseCacheHeaders(response));
        }catch (Exception e) {
            return Response.error(new ParseError(e));
        }

    }
}
