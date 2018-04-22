package com.carefree.coldwallet.ui.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.carefree.coldwallet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrivatekeyTipsActivity extends BaseActivity {

    @BindView(R.id.tv_tips)
    TextView tv_tips;
    @BindView(R.id.top_right_icon)
    ImageButton top_right_icon;
    @BindView(R.id.top_center_text)
    TextView top_center_text;
    @BindView(R.id.top_left)
    ImageButton top_left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privatekey_tips);
        ButterKnife.bind(this);
        inittopbar();
        tv_tips.setText("我们常说, 你对钱包中资金的控制取决于相应私钥的所有权和控制权。在区块链交易中, 私钥用于生成支付货币所必须的签名, 以证明资金的所有权。私钥必须始终保持机密, 因为一旦泄露给第三方, 相当于该私钥保护下的资产也拱手相让了。\n" +
                "存储在用户钱包中的私钥完全独立, 可由用户的钱包软件生成并管理, 无需区块链或者网络连接。用户的钱包地址由公钥经过 椭圆曲线算法计算得到的。 私钥的样式为 64 位 16 进制的哈希值字符串, 例如: 56f759ece75f0ab1b783893cbe390288978d4d4ff24dd233245b4285fcc31cf6\n" +
                "PS: 用户可以使用明文私钥导入  重新找回你的钱包。");
    }

    private void inittopbar() {
        top_center_text.setText("什么是私钥");
        top_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
