package com.carefree.coldwallet.commons.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/*--------------------------------------------------------------------
  文 件 名：SpaceItemDecoration
  作 　 者：HuangXiJun (黄夕君)
  创建日期：V1.0,  2018/03/29(版本号+逗号＋日期，注：日期格式：YYYY－MMM－DD，即月用英文表示，尽量减少异意)
  模块功能：RecyclerView item间距
---------------------------------------------------------------------*/

public class SpaceItemDecoration extends RecyclerView.ItemDecoration{
    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(parent.getChildPosition(view) != 0)
            outRect.top = space;
    }
}
