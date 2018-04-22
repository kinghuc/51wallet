package com.carefree.coldwallet.ui.activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.carefree.coldwallet.MyApplication;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.constants.Constants;
import com.pgyersdk.javabean.AppBean;
import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;

/*--------------------------------------------------------------------
  文 件 名：VersionsActivity
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/04/18(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：版本界面
---------------------------------------------------------------------*/

public class VersionsActivity extends BaseActivity implements View.OnClickListener{
    private TextView title;
    private ImageView iv_left;
    private TextView current_versions;
    private TextView new_versions;
    private TextView tv_newVersions;
    private TextView updataBtn;
    private String verName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_versions);
        initView();
    }

    private void initView(){
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(getString(R.string.version_updata));
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(this);
        current_versions = (TextView) findViewById(R.id.current_versions_numuber);
        new_versions = (TextView) findViewById(R.id.new_versions_numuber);
        tv_newVersions = (TextView) findViewById(R.id.new_versions);
        updataBtn = (TextView) findViewById(R.id.updata);
        updataBtn.setOnClickListener(this);

        try {
            verName = getPackageManager().
                    getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String currently = MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_CURRENTLY_VERSIONS);
        current_versions.setText(currently);
        new_versions.setText(verName);
        if (!verName.equals(currently)){
            updataBtn.setBackground(getResources().getDrawable(R.drawable.circular_bead_blue_10px));
            updataBtn.setEnabled(true);
            tv_newVersions.setText(getString(R.string.new_versions));
            new_versions.setVisibility(View.VISIBLE);
        } else {
            updataBtn.setBackground(getResources().getDrawable(R.drawable.circular_bead_gray_10px));
            updataBtn.setEnabled(false);
            tv_newVersions.setText(getString(R.string.the_current_is_the_latest_version));
            new_versions.setVisibility(View.GONE);
        }
    }

    private void update() {
        //设置是否强制更新。true为强制更新；false为不强制更新（默认值）。
        PgyUpdateManager.setIsForced(true);
        PgyUpdateManager.register(VersionsActivity.this, new UpdateManagerListener() {
            @Override
            public void onNoUpdateAvailable() {

            }
            @Override
            public void onUpdateAvailable(String s) {
                // 将新版本信息封装到AppBean中
                final AppBean appBean = getAppBeanFromString(s);
                startDownloadTask(
                        VersionsActivity.this,
                        appBean.getDownloadURL());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
            case R.id.updata:
                update();
                break;
            default:
                break;
        }
    }
}
