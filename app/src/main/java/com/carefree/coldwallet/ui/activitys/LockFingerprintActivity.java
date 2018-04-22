package com.carefree.coldwallet.ui.activitys;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.utils.ToastUtils;
import com.carefree.coldwallet.commons.views.FingerPrinterView;

/*--------------------------------------------------------------------
  文 件 名：LockFingerprintActivity
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/03/30(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：指纹识别界面
---------------------------------------------------------------------*/

public class LockFingerprintActivity extends BaseActivity{
    private FingerPrinterView fingerPrinterView;
    private FingerprintManager manager;
    private KeyguardManager mKeyManager;
    private TextView start;
    FingerprintManager.AuthenticationCallback authenticationCallback;
    CancellationSignal mCancellationSignal;
    private MyReceiver myReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_fingerprint);
        initView();
        initReceiver();//注册广播
    }

    private void initView(){
        fingerPrinterView = (FingerPrinterView) findViewById(R.id.fpv);
        fingerPrinterView.setOnStateChangedListener(new FingerPrinterView.OnStateChangedListener() {
            @Override public void onChange(int state) {
                if (state == FingerPrinterView.STATE_WRONG_PWD) {
                    fingerPrinterView.setState(FingerPrinterView.STATE_NO_SCANING);
                }
            }
        });
        start = (TextView) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    initManager();
                    confirmFinger();
                    startListening(null);
                }
            }
        });
    }

    private class MyReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())){
                if (Build.VERSION.SDK_INT >= 23) {
                    initManager();
                    confirmFinger();
                    startListening(null);
                }
            }
        }
    }

    /**
     * 注册系统广播
     */
    private void initReceiver(){
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(myReceiver,intentFilter);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initManager(){
        manager = getSystemService(FingerprintManager.class);
        mKeyManager = (KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE);
        authenticationCallback = new FingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                ToastUtils.toastutils("指纹识别失败，请稍候再试",LockFingerprintActivity.this);
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                super.onAuthenticationHelp(helpCode, helpString);

            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                fingerPrinterView.setState(FingerPrinterView.STATE_CORRECT_PWD);
                ToastUtils.toastutils("指纹识别成功",LockFingerprintActivity.this);
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                ToastUtils.toastutils("指纹识别失败，请重试",LockFingerprintActivity.this);
                fingerPrinterView.setState(FingerPrinterView.STATE_WRONG_PWD);
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void confirmFinger() {
        //android studio 上，没有这个会报错
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT)
                != PackageManager.PERMISSION_GRANTED) {
            ToastUtils.toastutils("没有指纹识别权限",LockFingerprintActivity.this);
            return;
        }
        //判断硬件是否支持指纹识别
        if (!manager.isHardwareDetected()) {
            ToastUtils.toastutils("没有指纹识别模块",LockFingerprintActivity.this);
            return;
        }
        //判断 是否开启锁屏密码
        if (!mKeyManager.isKeyguardSecure()) {
            ToastUtils.toastutils("没有开启锁屏密码",LockFingerprintActivity.this);
            return;
        }
        //判断是否有指纹录入
        if (!manager.hasEnrolledFingerprints()) {
            ToastUtils.toastutils("没有指纹录入",LockFingerprintActivity.this);
            return;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startListening(FingerprintManager.CryptoObject cryptoObject) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT)
                != PackageManager.PERMISSION_GRANTED) {
            ToastUtils.toastutils("没有指纹识别权限",LockFingerprintActivity.this);
            return;
//            publishSubject.onError(new FPerException(PERMISSION_DENIED_ERROE));
        }
        if (fingerPrinterView.getState() == FingerPrinterView.STATE_SCANING) {
            return;
        } else if (fingerPrinterView.getState() == FingerPrinterView.STATE_CORRECT_PWD
                || fingerPrinterView.getState() == FingerPrinterView.STATE_WRONG_PWD) {
            fingerPrinterView.setState(FingerPrinterView.STATE_NO_SCANING);
        } else {
            fingerPrinterView.setState(FingerPrinterView.STATE_SCANING);
        }
        mCancellationSignal = new CancellationSignal();
        if (manager != null && authenticationCallback != null) {
//            mSelfCompleted = false;
            manager.authenticate(cryptoObject, mCancellationSignal, 0, authenticationCallback, null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCancellationSignal != null){
            mCancellationSignal.cancel();
        }

    }
}
