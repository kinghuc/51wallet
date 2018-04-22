package com.carefree.coldwallet.constants;

import android.os.Environment;

/*--------------------------------------------------------------------
  文 件 名：Constants常量定义集合类
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/3/16(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：定义App中使用的常量集合在此类中定义
---------------------------------------------------------------------*/
public class Constants {
    //用户名
    public static String KEY_USERNAME="key_userName";
    //密码
    public static String KEY_PASSWORD="key_password";
    // 钱包id
    public static String KEY_WALLET_ID="key_wallet_id";
    // 钱包名
    public static String KEY_WALLET_NAME="key_wallet_name";
    // 钱包路径
    public static String KEY_WALLET_path="key_wallet_path";
//    //钱包密码
//    public static String KEY_WALLET_PASSWORD="key_wallet_password";
    //交易密码
    public static String KEY_DEALPASSWORD="key_dealpassword";
    //钱包地址
    public static String KEY_WALLETSITE="key_walletsite";
    //当前版本
    public static String KEY_CURRENTLY_VERSIONS="";
    // 文件存储路径
    public static String YYW_FILE_PATH = Environment.getExternalStorageDirectory()
            + "/com.carefree.coldwallet/";
    // 私钥文件本地存储名称
    public static String KEY_PRIVATEKEY_NAME= YYW_FILE_PATH + "privateKey.dat";
    //执久性文件存储文件名
    public static final String SHARED_NAME="coldwallet";
    //刷新
    public static final int KEY_UPDATA=0;
    //分页获取数据，每一页最大个数
    public static int KEY_PAGERSIZE=20;
    //资产列表
    public static String KEY_ASSET_LIST = "asset_list";
    //intent传类型
    public static String KEY_TYPE = "key_type";
    //intent传值id
    public static String KEY_ID = "id";
    //intent传值标题
    public static String KEY_TITLE = "title";
    //intent传图片链接
    public static String KEY_IMG = "img";
    //intent传时间
    public static String KEY_TIME = "time";
    //intent传值
    public static String KEY_VALUE = "values";
    //是否第一次进入程序
    public static String KEY_THEFIRST="firstInto";
    //是否登录
    public static String KEY_ISLOGIN="isLogin";
    //是否有钱包
    public static String KEY_ISWALLET="isWallet";


}
