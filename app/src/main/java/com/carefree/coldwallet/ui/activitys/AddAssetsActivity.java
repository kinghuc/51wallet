package com.carefree.coldwallet.ui.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.carefree.coldwallet.MyApplication;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.adapter.AssetsAdapter;
import com.carefree.coldwallet.commons.zxing.common.Constant;
import com.carefree.coldwallet.constants.Constants;
import com.carefree.coldwallet.entity.AssetInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AddAssetsActivity extends BaseActivity {
    private ListView recyc_list;
    private List<AssetInfo> list;
    private AssetsAdapter adapter;
    private TextView top_center_text;
    private ImageView top_left;
    String[] strings = {"ETH","BTC","IOTA","BYTEBALL","IPFS","BCH","EOS","CWC"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assets);
        inittopbar();
        initView();
    }

    private void inittopbar() {
//        top_right_icon = (ImageButton) findViewById(R.id.iv_left);
//        top_right_icon.setImageResource(R.mipmap.sousuo);
//        top_right_icon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(AddAssetsActivity.this,"开发中", Toast.LENGTH_SHORT).show();
//            }
//        });

        top_center_text = (TextView) findViewById(R.id.tv_title);
        top_center_text.setText("添加新资产");
        top_left = (ImageView) findViewById(R.id.iv_left);
        top_left.setVisibility(View.VISIBLE);
        top_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String json =  JSON.toJSONString(adapter.getList());
                MyApplication.getSharedPrefrencesHelper().saveString(Constants.KEY_ASSET_LIST,json);
                Intent intent = new Intent(AddAssetsActivity.this,MainActivity.class);
                intent.putExtra(Constants.KEY_TYPE,1);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initView() {
        recyc_list = (ListView) findViewById(R.id.recyc_list);
        list = new ArrayList<>();
        String json = MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_ASSET_LIST);
        if (json != null && !json.isEmpty()){
            List<AssetInfo> mList = JSON.parseArray(json,AssetInfo.class);
            if (mList != null && mList.size() > 0){
                for (int i = 0;i < mList.size();i++){
                    if (mList.get(i).isSelect()){
                        AssetInfo info = mList.get(i);
                        mList.remove(i);
                        mList.add(0,info);
                    }
                }
            }
            list = mList;
        } else {
            for (int i = 0;i< strings.length;i++){
                AssetInfo info = new AssetInfo();
                info.setCurrency(strings[i]);
                info.setSelect(false);
                list.add(info);
            }
        }
        adapter = new AssetsAdapter(this);
        adapter.setData(list);
        recyc_list.setAdapter(adapter);
    }
}
