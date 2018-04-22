package com.carefree.coldwallet.ui.fragments.mainfragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.carefree.coldwallet.MyApplication;
import com.carefree.coldwallet.R;
import com.carefree.coldwallet.adapter.HomeAdapter;
import com.carefree.coldwallet.commons.sign.bip39.BIP39;
import com.carefree.coldwallet.commons.sign.bip39.Bip39FormatExceptipn;
import com.carefree.coldwallet.commons.sign.bitaddress.BitAddress;
import com.carefree.coldwallet.commons.sign.ecdsa.ECDSA;
import com.carefree.coldwallet.commons.utils.Dip2Px;
import com.carefree.coldwallet.commons.utils.OnItemClickListener;
import com.carefree.coldwallet.commons.utils.SpaceItemDecoration;
import com.carefree.coldwallet.commons.utils.ToastUtils;
import com.carefree.coldwallet.commons.zxing.android.CaptureActivity;
import com.carefree.coldwallet.commons.zxing.bean.ZxingConfig;
import com.carefree.coldwallet.commons.zxing.common.Constant;
import com.carefree.coldwallet.constants.Constants;
import com.carefree.coldwallet.entity.AssetInfo;
import com.carefree.coldwallet.ui.activitys.AddAssetsActivity;
import com.carefree.coldwallet.ui.activitys.AssetsDetailActivity;
import com.carefree.coldwallet.ui.activitys.CreateWalletActivity;
import com.carefree.coldwallet.ui.activitys.ReceivAddressActivity;

import org.apaches.commons.codec.binary.Hex;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

/*--------------------------------------------------------------------
  文 件 名：AssetFragment
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/3/16(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：资产界面碎片
---------------------------------------------------------------------*/

public class AssetFragment extends Fragment{
    @BindView(R.id.recyc_list)
    RecyclerView recyc_list;
    private List<String> list;
    private HomeAdapter adapter;
    @BindView(R.id.tv_add_asset)
    TextView tv_add_asset;
    @BindView(R.id.wallet_scan)
    ImageView wallet_scan;
    private int REQUEST_CODE_SCAN = 111;
    private Unbinder unbinder;
    @BindView(R.id.wallet_icon)
    ImageView wallet_icon;
    @BindView(R.id.wallet_name)
    TextView wallet_name;
    @BindView(R.id.ll_code_address)
    LinearLayout ll_code_address;
    public AssetFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_asset,container,false);
        unbinder = ButterKnife.bind(this,view);
        initView(view);
        test();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        int type = getActivity().getIntent().getIntExtra(Constants.KEY_TYPE,0);
        if (type != 0){
            list = new ArrayList<>();
            String json = MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_ASSET_LIST);
            List<AssetInfo> mList = JSON.parseArray(json,AssetInfo.class);
            for (int i = 0;i < mList.size();i++){
                if (mList.get(i).isSelect()){
                    list.add(mList.get(i).getCurrency());
                }
            }
            adapter.setData(list);
        }
//        String wallet_id = MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_WALLET_ID);
//        if (TextUtils.isEmpty(wallet_id)) {
//            ll_create_bg.setVisibility(View.VISIBLE);
//            // 前往创建钱包
//        } else {
//            ll_create_bg.setVisibility(View.GONE);
//            String wallet_name_str = MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_WALLET_NAME);
//            wallet_name.setText(wallet_name_str);
//        }
    }


    private void initView(View view) {
        list = new ArrayList<>();
        String json = MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_ASSET_LIST);
        if (json != null){
            List<AssetInfo> mList = JSON.parseArray(json,AssetInfo.class);
            if (mList != null && mList.size() > 0){
                for (int i = 0;i < mList.size();i++){
                    if (mList.get(i).isSelect()){
                        list.add(mList.get(i).getCurrency());
                    }
                }
            }
        }
        recyc_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HomeAdapter(list,getActivity());
        adapter.setListener(new OnItemClickListener<String>() {
            @Override
            public void onClick(String s, View view, int position) {
                Intent intent = new Intent(getActivity(),AssetsDetailActivity.class);
                intent.putExtra(Constants.KEY_VALUE,s);
                startActivity(intent);
            }
        });
        recyc_list.setAdapter(adapter);
        wallet_name.setText(MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_WALLET_NAME));
    }

    @OnClick({R.id.ll_code_address,R.id.tv_add_asset,R.id.wallet_scan,R.id.wallet_icon})
    void viewClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.wallet_scan:
                if (ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //权限还没有授予，需要在这里写申请权限的代码
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 60);
                } else {
                    intent = new Intent(getActivity(), CaptureActivity.class);
                                /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
                                * 也可以不传这个参数
                                * 不传的话  默认都为默认不震动  其他都为true
                                * */
                    ZxingConfig config = new ZxingConfig();
                    config.setPlayBeep(true);
                    config.setShake(true);
                    config.setShowbottomLayout(false);
                    intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                    startActivityForResult(intent, REQUEST_CODE_SCAN);
                }
                break;
            case R.id.tv_add_asset:
                intent = new Intent(getActivity(), AddAssetsActivity.class);
                startActivity(intent);
                break;
            case R.id.wallet_icon:
                break;
            case R.id.ll_code_address:
                String wallet_address = MyApplication.getSharedPrefrencesHelper().getString(Constants.KEY_WALLETSITE);
                if (TextUtils.isEmpty(wallet_address)) {
                    ToastUtils.toastutils("请创建钱包",getActivity());
                    break;
                }
                intent = new Intent(getActivity(), ReceivAddressActivity.class);
                intent.putExtra("wallet_address",wallet_address);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Toast.makeText(getActivity(),content, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_SCAN) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted准许
                Toast.makeText(getActivity(),"已获得授权！",Toast.LENGTH_SHORT).show();
            } else {
                // Permission Denied拒绝
                Toast.makeText(getActivity(),"未获得授权！",Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    public void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    /**
     * 助记词测试
     */
    private void test() {
        try {
            BIP39 bip39 = new BIP39(getActivity());
            byte [] random  = SecureRandom.getSeed(32);
            String privateKey0 = new String(Hex.encodeHex(random));
            String e = bip39.encode(random);
            Log.i("tag",e);
            Log.i("tag",privateKey0);
            String ddd = "knee mother retire inmate play gaze series legend deposit rely engine pact major can retreat apart panda lonely awake diamond field world sad much";
            byte [] data = bip39.decode(ddd);
            String privateKey1 = new String(Hex.encodeHex(data));
            Log.i("tag",privateKey1);
        } catch (Bip39FormatExceptipn e) {
            Log.i("tag","助记词格式错误");
            e.printStackTrace();
        }
    }
}
