package com.carefree.coldwallet.ui.fragments.mainfragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carefree.coldwallet.MyApplication;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.commons.utils.ToastUtils;
import com.carefree.coldwallet.commons.views.RoundImageView;
import com.carefree.coldwallet.constants.Constants;
import com.carefree.coldwallet.ui.activitys.AddExchangeActivity;
import com.carefree.coldwallet.ui.activitys.MineWalletActivity;
import com.carefree.coldwallet.ui.activitys.NoticeActivity;
import com.carefree.coldwallet.ui.activitys.PersonalMessageActivity;
import com.carefree.coldwallet.ui.activitys.ReceivAddressActivity;
import com.carefree.coldwallet.ui.activitys.SettingActivity;

/*--------------------------------------------------------------------
  文 件 名：MeFragment
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/3/16(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：我的界面碎片
---------------------------------------------------------------------*/

public class MeFragment extends Fragment implements View.OnClickListener{
    private View view;
    private LinearLayout ll_wallet;
    private LinearLayout ll_qrcode;
    private LinearLayout ll_notice;
    private LinearLayout ll_navigation;
    private LinearLayout ll_message;
    private LinearLayout ll_setting;
    private TextView wallet_address;
    private RoundImageView wallet_icon;
    public MeFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_me,container,false);
        initView();
        initData();
        return view;
    }

    private void initView(){
        wallet_address = view.findViewById(R.id.wallet_address);
        wallet_icon = view.findViewById(R.id.wallet_icon);
        ll_wallet = view.findViewById(R.id.ll_me_wallet);
        ll_qrcode = view.findViewById(R.id.ll_me_qr_code);
        ll_navigation = view.findViewById(R.id.ll_navigation);
        ll_notice = view.findViewById(R.id.ll_notice);
        ll_message = view.findViewById(R.id.ll_message);
        ll_setting = view.findViewById(R.id.ll_setting);

        ll_wallet.setOnClickListener(this);
        ll_qrcode.setOnClickListener(this);
        ll_navigation.setOnClickListener(this);
        ll_notice.setOnClickListener(this);
        ll_message.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
    }

    private void initData(){
        wallet_address.setText(MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_WALLETSITE));
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.ll_me_wallet:
                intent = new Intent(getActivity(),MineWalletActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_me_qr_code:
                String wallet_address = MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_WALLETSITE);
                if (TextUtils.isEmpty(wallet_address)) {
                    ToastUtils.toastutils("请创建钱包",getActivity());
                    break;
                }
                intent = new Intent(getActivity(), ReceivAddressActivity.class);
                intent.putExtra("wallet_address",wallet_address);
                startActivity(intent);
                break;
            case R.id.ll_navigation:
                Intent exchangeIntent = new Intent(getActivity(), AddExchangeActivity.class);
                startActivity(exchangeIntent);
                break;
            case R.id.ll_notice:
                Intent noticeIntent = new Intent(getActivity(), NoticeActivity.class);
                startActivity(noticeIntent);
                break;
            case R.id.ll_message:
                Intent messIntent = new Intent(getActivity(), PersonalMessageActivity.class);
                startActivity(messIntent);
                break;
            case R.id.ll_setting:
//                ToastUtils.toastutils("开发中",getActivity());
                Intent setIntent = new Intent(getActivity(), SettingActivity.class);
                startActivity(setIntent);
                break;
        }
    }
}
