package com.carefree.coldwallet.entity;

/*--------------------------------------------------------------------
  文 件 名：TransferInfo
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/04/20(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：转账实体类
---------------------------------------------------------------------*/

import java.io.Serializable;

public class TransferInfo implements Serializable{
    private String currency;
    private String address;
    private String money;
    private String cost;
    private String remark;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
