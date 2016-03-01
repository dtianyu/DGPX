/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgpx.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author C0160
 */
@Startup
@Singleton
public class BaiduTTSBean {

    private String access_token;
    private Integer expires_in;

    public BaiduTTSBean() {
    }

    private void initAccessToken() throws IOException {

        StringBuilder sb = new StringBuilder();
        sb.append("https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials");
        sb.append("&");
        sb.append("client_id=HI1vugSyWg0Gc2GR4RSj2Gwj");
        sb.append("&");
        sb.append("client_secret=ff1aed14438e930fc9a5abb2a14a1714");
        sb.append("&");

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(sb.toString());
        CloseableHttpResponse response = httpclient.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == HttpStatus.SC_OK) {
            try {
                HttpEntity entity = response.getEntity();
                JSONObject resultJsonObject = null;
                try {
                    resultJsonObject = new JSONObject(EntityUtils.toString(entity, "UTF-8"));
                    access_token = resultJsonObject.getString("access_token");
                    expires_in = Integer.parseInt(resultJsonObject.get("expires_in").toString());
                } catch (IOException | ParseException | JSONException ex) {
                    Logger.getLogger(BaiduTTSBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            } finally {
                response.close();
            }
        }

    }

    private boolean isTokenExpire() {
        if (expires_in == 0) {
            return true;
        }
        Calendar c = Calendar.getInstance();
        Calendar e = Calendar.getInstance();
        e.add(Calendar.SECOND, expires_in);
        return e.before(c.getTime());
    }

    public String ttsURL(String value) throws IOException {
        return ttsURL(value, "zh", "1", "7733709");
    }

    public String ttsURL(String texValue, String lanValue, String ctpValue, String cuidValue) throws IOException {
        String tex = texValue;
        String lan = lanValue;
        String ctp = ctpValue;
        String cuid = cuidValue;
        if ((this.access_token == null) || isTokenExpire()) {
            initAccessToken();
        }
        String token = this.access_token;
        try {
            tex = java.net.URLEncoder.encode(tex, "UTF-8");
            tex = java.net.URLEncoder.encode(tex, "UTF-8");
            lan = java.net.URLEncoder.encode(lan, "UTF-8");
            lan = java.net.URLEncoder.encode(lan, "UTF-8");
            ctp = java.net.URLEncoder.encode(ctp, "UTF-8");
            ctp = java.net.URLEncoder.encode(ctp, "UTF-8");
            cuid = java.net.URLEncoder.encode(cuid, "UTF-8");
            cuid = java.net.URLEncoder.encode(cuid, "UTF-8");
            token = java.net.URLEncoder.encode(token, "UTF-8");
            token = java.net.URLEncoder.encode(token, "UTF-8");

            return "http://tsn.baidu.com/text2audio?tex=" + tex + "&lan=" + lan + "&cuid=" + cuid + "&ctp=" + ctp + "&tok=" + token;

        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BaiduTTSBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    /**
     * @return the access_token
     */
    public String getAccess_token() {
        return access_token;
    }

    /**
     * @return the expires_in
     */
    public long getExpires_in() {
        return expires_in;
    }

}
