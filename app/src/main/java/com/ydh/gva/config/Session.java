package com.ydh.gva.config;

import com.ydh.gva.core.YDHData;
import com.ydh.gva.ui.Base.GvaApp;
import com.ydh.gva.util.net.url.HttpRequestBody;
import com.ydh.gva.util.net.volley.FsonRequest;
import com.ydh.gva.util.net.volley.Request;
import com.ydh.gva.util.net.volley.Response;
import com.ydh.gva.util.net.volley.VolleyError;

import gva.ydh.com.util.SharedPreUtil;

/**
 * Created by liujianying on 15/5/19.
 *
 * @用户Session保存
 */
public class Session {

    private final static String SESSIONS = "SESSIONS";

    private static Session session = null;

    private String sessionString = null;
    public static Session Instance() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    private Session() {}

    public String getSessionString() {
        return SharedPreUtil.get(GvaApp.wlApp, SESSIONS, null);
    }

    public void setSessionString(String sessionString) {

        SharedPreUtil.set(GvaApp.wlApp, SESSIONS, sessionString);
        this.sessionString = sessionString;
    }



    /**
     * @请求session
     */
    public static void requestSession(final SessionInterfaec sessionInterfaec) {

        FsonRequest stringRequest = new FsonRequest<>(Request.Method.POST, ConfigurationUrl.COMMONURL,
                YDHData.class, null, HttpRequestBody.Instance().createSession(), new Response.Listener<YDHData>() {
            @Override
            public void onResponse(YDHData response) {

                if(response.getResultCode() == 0) {
                    Session session = Session.Instance();
                    session.setSessionString(response.getData());
                    if (sessionInterfaec != null)
                        sessionInterfaec.onSessionSuccess(response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                AppLog.I("TAG", JSON.toJSONString(error));
                if (sessionInterfaec != null)
                    sessionInterfaec.onSessionFailure(error);
            }
        });
        GvaApp.requestQueue.add(stringRequest);
    }




    public interface SessionInterfaec {

        public void onSessionSuccess(YDHData response);

        public void onSessionFailure(VolleyError error);

    }


}
