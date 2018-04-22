package com.carefree.coldwallet.entity;

/*--------------------------------------------------------------------
  文 件 名：AssetInfo
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/04/20(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：资产实体类
---------------------------------------------------------------------*/

public class AssetInfo {
    private String currency;
    private boolean isSelect;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
