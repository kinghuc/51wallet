package com.carefree.coldwallet.ui.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.carefree.coldwallet.MyApplication;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.adapter.PersonalMessageAdapter;
import com.carefree.coldwallet.constants.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AssetsDetailActivity extends AppCompatActivity {
//    @BindView(R.id.top_right_icon)
//    ImageButton top_right_icon;
    @BindView(R.id.tv_title)
    TextView top_center_text;
    @BindView(R.id.iv_left)
    ImageView top_left;
    @BindView(R.id.ll_receivables)
    LinearLayout ll_receivables;
    @BindView(R.id.ll_transfer_accounts)
    LinearLayout ll_transfer_accounts;
    @BindView(R.id.recyc_list)
    ListView recyc_list;
    private TextView tv_coinname;
    private TextView tv_coinname_value;
    private TextView tv_cny_value;
    private PersonalMessageAdapter adapter;
    private List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_detail);
        ButterKnife.bind(this);
        inittopbar();
    }

    private void inittopbar() {
//        top_right_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(AssetsDetailActivity.this,"开发中", Toast.LENGTH_SHORT).show();
//            }
//        });
        tv_coinname = (TextView) findViewById(R.id.tv_coinname);
        tv_coinname_value = (TextView) findViewById(R.id.tv_coinname_value);
        tv_cny_value = (TextView) findViewById(R.id.tv_cny_value);
        top_center_text.setText(getIntent().getStringExtra(Constants.KEY_VALUE));
        top_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter = new PersonalMessageAdapter(this);
        list.add(" ");
        list.add(" ");
        list.add(" ");
        list.add(" ");
        list.add(" ");
        list.add(" ");
        adapter.setData(list);
        recyc_list.setAdapter(adapter);
    }

    @OnClick({R.id.ll_receivables,R.id.ll_transfer_accounts})
    void viewClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_transfer_accounts:
                intent = new Intent(AssetsDetailActivity.this,TransferAccountsActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_receivables:
                String wallet_address = MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_WALLETSITE);
                intent = new Intent(AssetsDetailActivity.this, ReceivAddressActivity.class);
                intent.putExtra(Constants.KEY_VALUE,wallet_address);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
