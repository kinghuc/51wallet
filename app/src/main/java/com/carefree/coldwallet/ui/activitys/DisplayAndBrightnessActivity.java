package com.carefree.coldwallet.ui.activitys;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.views.RangeSeekBar;

/*--------------------------------------------------------------------
  文 件 名：DisplayAndBrightnessActivity
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/04/18(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：显示与亮度界面
---------------------------------------------------------------------*/
public class DisplayAndBrightnessActivity extends BaseActivity implements View.OnClickListener{
    private TextView title;
    private ImageView iv_left;
    private RangeSeekBar seekBar;
    private ToggleButton toggleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_and_brightness);
        initView();
    }
    private void initView(){
        title = (TextView) findViewById(R.id.tv_title);
        title.setText(getString(R.string.display_and_brightness));
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_left.setVisibility(View.VISIBLE);
        iv_left.setOnClickListener(this);
        seekBar = (RangeSeekBar) findViewById(R.id.seekbar);
        seekBar.setValue(50);
        toggleButton = (ToggleButton) findViewById(R.id.toggleBtn);

        seekBar.setOnRangeChangedListener(new RangeSeekBar.OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
                Log.d("huangxijun","min:" + min + "," + "max:" + max);
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_left:
                finish();
                break;
        }
    }
}
