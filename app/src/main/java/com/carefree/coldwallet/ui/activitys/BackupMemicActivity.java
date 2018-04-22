package com.carefree.coldwallet.ui.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.carefree.coldwallet.MyApplication;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.sign.bip39.BIP39;
import com.carefree.coldwallet.commons.sign.bip39.Bip39FormatExceptipn;
import com.carefree.coldwallet.commons.utils.BackupMemicDialog;
import com.carefree.coldwallet.commons.utils.StatusBarCompat;
import com.carefree.coldwallet.commons.utils.ToastUtils;
import com.carefree.coldwallet.commons.zxing.common.Constant;
import com.carefree.coldwallet.constants.Constants;


import org.bouncycastle.util.encoders.Hex;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BackupMemicActivity extends BaseActivity {
    private TextView title;
    private ImageView iv_left;
    @BindView(R.id.tv_memic)
    TextView tv_memic;
    private String backupMemic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StatusBarCompat.setStatusBarColor(this, Color.WHITE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backup_memic);
        initView();
        ButterKnife.bind(this);
        // 通过私钥来生成助记词
        generateMemic();
    }

    private void initView(){
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(getString(R.string.backups_memic));
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setVisibility(View.VISIBLE);


    }

    @OnClick({R.id.btn_complete,R.id.iv_right})
    void viewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_complete:
                showLoadding("加载中");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showDialog();
                    }
                },1000);
                break;
            case R.id.iv_left:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 弹出询问框
     */
    private void showDialog(){
        BackupMemicDialog dialog = new BackupMemicDialog(this);
        dialog.show();
        dialog.setOnConfirmClickListener(new BackupMemicDialog.OnConfirmClickListener() {
            @Override
            public void onConfirmClick(View view) {
                toSkip();
                finish();
                MyApplication.getInstance().closeActivitys();
            }
        });
    }
    /**
     * 跳转确认助记词界面
     */
    private void toSkip(){
        Intent intent = new Intent(BackupMemicActivity.this,AffirmMemicActivity.class);
        intent.putExtra(Constants.KEY_VALUE,backupMemic);
        startActivity(intent);
        finish();
    }

    private void generateMemic() {
        try {
            String privateKey = getIntent().getStringExtra(Constants.KEY_VALUE);
            byte [] random = Hex.decode(privateKey);
            BIP39 bip39 = new BIP39(BackupMemicActivity.this);
            String memic = bip39.encode(random);
            backupMemic = memic;
            Log.i("s",memic);
            tv_memic.setText(memic);
        } catch (Bip39FormatExceptipn e) {
            e.printStackTrace();
            ToastUtils.toastutils("Invalid data length for mnemonic",BackupMemicActivity.this);
        }

    }
}
