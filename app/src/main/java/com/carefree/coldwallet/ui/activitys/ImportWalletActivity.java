package com.carefree.coldwallet.ui.activitys;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.carefree.coldwallet.R;
import com.carefree.coldwallet.adapter.ImportPagerAdapter;
import com.carefree.coldwallet.ui.fragments.ImportMemoriziWordsFragment;
import com.carefree.coldwallet.ui.fragments.ImportPrivateKeyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImportWalletActivity extends BaseActivity {
    @BindView(R.id.top_right_icon)
    ImageButton top_right_icon;
    @BindView(R.id.top_center_text)
    TextView top_center_text;
    @BindView(R.id.top_left)
    ImageButton top_left;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.mvp)
    ViewPager mvp;
    private List<Fragment> mFragmentList;
    private List<String> mTitleList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_wallet);
        ButterKnife.bind(this);
        initview();
        inittopbar();
    }

    private void inittopbar() {
        top_center_text.setText("导入钱包");
        top_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initview() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new ImportMemoriziWordsFragment());
        mFragmentList.add(new ImportPrivateKeyFragment());
        mTitleList = new ArrayList<>();
        mTitleList.add("助记词");
        mTitleList.add("私钥");
        mvp.setAdapter(new ImportPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList));
        //将tablayout与fragment关联
        tab.setTabMode(TabLayout.MODE_FIXED);
        //将tablayout与fragment关联
        tab.setupWithViewPager(mvp);
    }

}
