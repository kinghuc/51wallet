package com.carefree.coldwallet.ui.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.carefree.coldwallet.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemicTipsActivity extends BaseActivity {

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
        setContentView(R.layout.activity_memic_tips);
        ButterKnife.bind(this);
        inittopbar();
        tv_tips.setText("助记词是明文私钥的另一种表现形式, 最早是由 BIP39 提案提出, 其目的是为了帮助用户记忆复杂的私钥 (64位的哈希值)。助记词一般由12、15、18、21、24个单词构成, 这些单词都取自一个固定词库, 其生成顺序也是按照一定算法而来, 所以用户没必要担心随便输入 12 个单词就会生成一个地址。助记词可以作为私钥的另一种表现形式, 助记词是未经加密的私钥, 没有任何安全性可言, 任何人得到了你的助记词, 可以不费吹灰之力的夺走你的资产。所以在用户在备份助记词之后, 一定要注意三点:1. 尽可能采用物理介质备份, 例如用笔抄在纸上等, 尽可能不要采用截屏或者拍照之后放在联网的设备里, 以防被黑客窃取 2. 多次验证备份的助记词是否正确, 一旦抄错一两个单词, 那么将对后续找回正确的助记词带来巨大的困难; 3. 将备份后的助记词妥善保管, 做好防盗防丢措施。\n" +
                "PS: 用户可以使用备份的助记词, 重新导入 codewallet ,重新找回您的钱包。");
    }

    private void inittopbar() {
        top_center_text.setText("什么是助记词");
        top_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
