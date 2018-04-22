package com.carefree.coldwallet.ui.activitys;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.utils.ToastUtils;
import com.carefree.coldwallet.commons.zxing.encode.CodeCreator;
import com.carefree.coldwallet.constants.Constants;
import com.google.zxing.WriterException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReceivAddressActivity extends BaseActivity {

    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.et_number)
    EditText et_number;
    @BindView(R.id.contentIv)
    ImageView contentIv;
    @BindView(R.id.btn_copy_address)
    TextView btn_copy_address;
    @BindView(R.id.iv_right)
    ImageView top_right_text;
    @BindView(R.id.iv_left)
    ImageView top_left;
    @BindView(R.id.tv_currency_unit)
    TextView tv_currency_unit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiv_address);
        ButterKnife.bind(this);
        inittopbar();
        initview();
    }

    private void inittopbar() {
        top_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        top_right_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ReceivAddressActivity.this, "开发中", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initview() {
        String contentEtString = getIntent().getStringExtra(Constants.KEY_VALUE);
        if (TextUtils.isEmpty(contentEtString)) {
            Toast.makeText(ReceivAddressActivity.this, "contentEtString不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap bitmap = null;
        try {
            bitmap = CodeCreator.createQRCode(contentEtString, 1000, 1000, null);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        if (bitmap != null) {
            contentIv.setImageBitmap(bitmap);
        }
        tv_address.setText(contentEtString);

    }

    @OnClick({R.id.btn_copy_address})
    void viewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_copy_address:
//                Toast.makeText(ReceivAddressActivity.this, "开发中", Toast.LENGTH_SHORT).show();
                ClipboardManager clip = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                clip.setText(tv_address.getText());
                ToastUtils.toastutils("你已复制到粘贴板",ReceivAddressActivity.this);
                break;
            default:
                break;
        }
    }

}
