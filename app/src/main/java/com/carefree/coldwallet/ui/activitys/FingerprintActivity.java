package com.carefree.coldwallet.ui.activitys;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.carefree.coldwallet.R;
import com.carefree.coldwallet.adapter.FingerprintAdapter;
import com.carefree.coldwallet.commons.utils.Dip2Px;
import com.carefree.coldwallet.commons.views.swipemenu.SwipeMenu;
import com.carefree.coldwallet.commons.views.swipemenu.SwipeMenuCreator;
import com.carefree.coldwallet.commons.views.swipemenu.SwipeMenuItem;
import com.carefree.coldwallet.commons.views.swipemenu.SwipeMenuListView;

import org.bouncycastle.LICENSE;

import java.util.ArrayList;
import java.util.List;

/*--------------------------------------------------------------------
  文 件 名：FingerprintActivity
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/04/18(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：指纹界面
---------------------------------------------------------------------*/

public class FingerprintActivity extends BaseActivity implements View.OnClickListener{
    private TextView title;
    private TextView addBtn;
    private ImageView iv_left;
    private SwipeMenuListView menuListView;
    private FingerprintAdapter adapter;
    private List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);
        initView();
        initData();
    }

    private void initView(){
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(getString(R.string.fingerprint));
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(this);
        menuListView = (SwipeMenuListView) findViewById(R.id.menuList);
        adapter = new FingerprintAdapter(this);
        addBtn = (TextView) findViewById(R.id.add_fingerprint);
        addBtn.setOnClickListener(this);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.RED));
                // set item width
                openItem.setWidth(Dip2Px.getDip2Px(FingerprintActivity.this,50));
                // set item title
                openItem.setTitle(getString(R.string.delete));
                // set item title fontsize
                openItem.setTitleSize(16);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);
            }
        };

        menuListView.setMenuCreator(creator);

        menuListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index){
                    case 0:
                        list.remove(position);
                        adapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });
    }

    private void initData(){
        list.add("指纹1");
        list.add("指纹2");
        adapter.setData(list);
        menuListView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
            case R.id.add_fingerprint:
                break;
            default:
                break;
        }
    }
}
