
package com.carefree.coldwallet.http;

/*--------------------------------------------------------------------
  文 件 名：HttpCallback
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/3/16(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：网络访问回调接口
---------------------------------------------------------------------*/
public interface HttpCallback<T> {

    void onSuccess(T data);

    void onFail(String msg);
}
