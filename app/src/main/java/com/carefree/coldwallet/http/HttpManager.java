
package com.carefree.coldwallet.http;

import android.app.Activity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.carefree.coldwallet.constants.Constants;

import java.io.File;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*--------------------------------------------------------------------
  文 件 名：HttpManager
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/3/16(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：访问接口
---------------------------------------------------------------------*/
public class HttpManager {
//网络请求
    private OkHttpClient client;
    //图片数组
    private List<String> imgs;
    //页数
    private int pager = -1;
    //文件
    private String file;
    public void setFile(String file) {
        this.file = file;
    }
    public int getPager() {
        return pager;
    }
    public void setPager(int pager) {
        this.pager = pager;
    }
    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }
    //定义所返回的实例
    private static HttpManager sInstance = null;
    //声明标记
    private String token;
    public void setToken(String token) {
        this.token = token;
    }
    //图片格式类型转换
    private MediaType MEDIA_TYPE_PNG = MediaType.parse("image/jpeg; charset=utf-8");
    //文本文件格式类型转换
    private MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
    public HttpManager() {
        //忽略SSL证书验证
        SSLContext sslContext = null;
        try {
            //安全协议
            sslContext = SSLContext.getInstance("SSL");
            //创建xml证书
            sslContext.init(null, new TrustManager[]{xtm}, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        //验证主机的IP
        HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        //初始化配置okhttp
        client = new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory())
                .hostnameVerifier(DO_NOT_VERIFY)
                .readTimeout(30000, TimeUnit.MILLISECONDS)
                .writeTimeout(30000,TimeUnit.MILLISECONDS)
                .connectTimeout(3000,TimeUnit.MILLISECONDS)
                .build();
    }
    public static synchronized HttpManager getInstance() {
        if (sInstance == null) {
            sInstance = new HttpManager();
        }
        return sInstance;
    }
    /**
     * Created by huangxijun on 2018/03/16.
     * 统一的接口调用方法
     * 注意：如果接口需要到token值直接调用setToken(String token)方法
     *       如果接口需要上传单张图片直接调用 setImgs(List<String> imgs)方法
     *       如果接口需要分页获取数据直接调用 setPager(int pager)方法
     *       如果接口需要上传文件直接调用 setFile(String file)方法
     * @param map
     * @param url
     * @param obj
     * @param callback
     */
    public void AppRequest(Map map, String url, final Object obj, final HttpCallback<Object> callback, final Activity activity) {
        RequestBody body = null;
        Request request = null;
        if (pager >= 0) {
            map.put("page", pager);
            map.put("Size", Constants.KEY_PAGERSIZE);
        }
        if (imgs != null &&imgs.size()==0) {
            //上传表单及参数
             MultipartBody.Builder builder= new MultipartBody.Builder().setType(MultipartBody.FORM);
            builder.addFormDataPart("file", "file", RequestBody.create(MEDIA_TYPE_PNG, ""));
            builder.addFormDataPart("data", JSON.toJSONString(map));
            body=builder.build();
        } else if (imgs != null &&imgs.size()!=0) {
            MultipartBody.Builder builder= new MultipartBody.Builder().setType(MultipartBody.FORM);
            for (int i=0;i<imgs.size();i++){
                builder.addFormDataPart("file", "file", RequestBody.create(MEDIA_TYPE_PNG, new File(imgs.get(i))));
            }
            builder.addFormDataPart("data", JSON.toJSONString(map));
            body=builder.build();
        }else if(file!=null&&!file.isEmpty()){
            MultipartBody.Builder builder= new MultipartBody.Builder().setType(MultipartBody.FORM);
            builder.addFormDataPart("log", "log", RequestBody.create(MEDIA_TYPE_MARKDOWN, new File(file)));
//            builder.addFormDataPart("data", new Gson().toJson(map));
            builder.addFormDataPart("data", JSON.toJSONString(map));
            body=builder.build();
        } else {
            body = new FormBody.Builder()
                    .add("data", JSON.toJSONString(map)).build();
        }
        if (token != null && !"".equals(token)) {
            request = new Request.Builder().url(url).addHeader("Authorization", token).post(body).build();
        } else {
            //通过请求地址和请求体构造Post请求对象Request
            request = new Request.Builder().url(url).post(body).build();
        }
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFail(e.toString());
                    }
                });
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseStr = response.body().string();
                    Log.i("TTT","=======网络请求=="+responseStr);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(JSON.parseObject(responseStr, obj.getClass()));
                        }
                    });
                }else {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFail(response.message());
                        }
                    });
                }
            }
        });
    }
    X509TrustManager xtm = new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
        }
        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            X509Certificate[] x509Certificates = new X509Certificate[0];
            return x509Certificates;
        }
    };
}


