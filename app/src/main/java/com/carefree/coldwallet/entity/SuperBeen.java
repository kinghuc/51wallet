package com.carefree.coldwallet.entity;

import java.io.Serializable;

/*--------------------------------------------------------------------
  文 件 名：SuperBeen
  作 　 者：HuangXiJun (黄夕君)
  版本日期：V1.0,  2018/3/20(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：是所有实体类的超级父类
---------------------------------------------------------------------*/
public class SuperBeen implements Serializable{
    /**
     * 请求接口成功或失败要返回的值
     * code==0000为访问成功
     */
    private String retCode;
   //请求接口失败时要返回的值
    private String retMsg;
  //请求需登录调用接口要传入的身份
    private String token;

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
