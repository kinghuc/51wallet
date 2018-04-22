package com.carefree.coldwallet.ui.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.carefree.coldwallet.MyApplication;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.utils.StatusBarCompat;
import com.carefree.coldwallet.constants.Constants;
import com.carefree.coldwallet.ui.fragments.mainfragments.AssetFragment;
import com.carefree.coldwallet.ui.fragments.mainfragments.MarketFragment;
import com.carefree.coldwallet.ui.fragments.mainfragments.MeFragment;
import com.carefree.coldwallet.ui.fragments.mainfragments.NetworkFragment;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

/*--------------------------------------------------------------------
  文 件 名：MainActivity
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/3/16(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：主界面
---------------------------------------------------------------------*/

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private FragmentManager fragmentManager;
    private AssetFragment assetFragment;//资产界面
    private MarketFragment marketFragment;//行情界面
    private NetworkFragment networkFragment;//网络界面
    private MeFragment meFragment;//我的界面
    private LinearLayout ll_main;
    private LinearLayout ll_zichan;
    private LinearLayout ll_hangqing;
    private LinearLayout ll_wangluo;
    private LinearLayout ll_me;
    private ImageView iv_zichan;
    private ImageView iv_hangqing;
    private ImageView iv_wangluo;
    private ImageView iv_me;
    private int index=0;//当前的界面
    private String verName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.transparencyBar(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        showFragment(index);
        MyApplication.getInstance().addActivity(this);
    }

    private void initView(){
        fragmentManager=getSupportFragmentManager();
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        ll_zichan = (LinearLayout) findViewById(R.id.ll_zichan);
        ll_hangqing = (LinearLayout) findViewById(R.id.ll_hangqing);
        ll_wangluo = (LinearLayout) findViewById(R.id.ll_wangluo);
        ll_me = (LinearLayout) findViewById(R.id.ll_me);
        iv_zichan = (ImageView) findViewById(R.id.iv_zichan);
        iv_hangqing = (ImageView) findViewById(R.id.iv_hangqing);
        iv_wangluo = (ImageView) findViewById(R.id.iv_wangluo);
        iv_me = (ImageView) findViewById(R.id.iv_me);

        ll_zichan.setOnClickListener(this);
        ll_hangqing.setOnClickListener(this);
        ll_wangluo.setOnClickListener(this);
        ll_me.setOnClickListener(this);
    }

    private void initData(){

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 检测更新
//        update();
    }

    @Override
    protected void onDestroy() {
        PgyUpdateManager.unregister();
        super.onDestroy();
    }

    /**
     * 显示当前的fragment
     * @param index
     */
    private void showFragment(int index){
        initBottom();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        hintfragment(fragmentTransaction);
        switch (index){
            case 0:
                iv_zichan.setImageResource(R.mipmap.main_wallet_selected);
                if (assetFragment ==null){
                    assetFragment = new AssetFragment();
                    fragmentTransaction.add(R.id.ll_main,assetFragment);
                } else {
                    fragmentTransaction.show(assetFragment);
                }
                break;
            case 1:
                iv_hangqing.setImageResource(R.mipmap.main_market_selected);
                if (marketFragment ==null){
                    marketFragment = new MarketFragment();
                    fragmentTransaction.add(R.id.ll_main,marketFragment);
                } else {
                    fragmentTransaction.show(marketFragment);
                }
                break;
            case 2:
//                iv_wangluo.setImageResource(R.mipmap.main_network_selected);
//                if (networkFragment ==null){
//                    networkFragment = new NetworkFragment();
//                    fragmentTransaction.add(R.id.ll_main,networkFragment);
//                } else {
//                    fragmentTransaction.show(networkFragment);
//                }
                startActivity(new Intent(Settings.ACTION_SETTINGS));
                break;
            case 3:
                iv_me.setImageResource(R.mipmap.main_me_selected);
                if (meFragment ==null){
                    meFragment = new MeFragment();
                    fragmentTransaction.add(R.id.ll_main,meFragment);
                } else {
                    fragmentTransaction.show(meFragment);
                }
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏所有的fragment
     * @param transaction
     */
    private void hintfragment(FragmentTransaction transaction){
        if(assetFragment!=null){
            transaction.hide(assetFragment);
        }
        if(marketFragment!=null){
            transaction.hide(marketFragment);
        }
        if(meFragment!=null){
            transaction.hide(meFragment);
        }
    }

    /**
     * 初始化底部导航栏
     */
    private void initBottom(){
        iv_zichan.setImageResource(R.mipmap.main_wallet_no_select);
        iv_hangqing.setImageResource(R.mipmap.main_market_no_select);
        iv_wangluo.setImageResource(R.mipmap.main_network_no_select);
        iv_me.setImageResource(R.mipmap.main_me_no_select);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_zichan:
                showFragment(0);
                break;
            case R.id.ll_hangqing:
                showFragment(1);
                break;
            case R.id.ll_wangluo:
                showFragment(2);
                break;
            case R.id.ll_me:
                showFragment(3);
                break;
        }
    }

    private void update() {
        try {
            verName = getPackageManager().
                    getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
        PgyUpdateManager.setIsForced(true);
        PgyUpdateManager.register(MainActivity.this, new UpdateManagerListener() {
            @Override
            public void onNoUpdateAvailable() {

            }
            @Override
            public void onUpdateAvailable(String s) {
                // 将新版本信息封装到AppBean中
                final AppBean appBean = getAppBeanFromString(s);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("版本"+verName+"更新")
                        .setMessage(appBean.getReleaseNote())
                        .setNegativeButton(
                                "确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        startDownloadTask(
                                                MainActivity.this,
                                                appBean.getDownloadURL());
                                    }
                                }).show();
            }
        });
    }

}
