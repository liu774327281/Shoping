package com.example.administrator.shoping.adapter;

import android.content.Context;

import com.example.administrator.shoping.bean.ShoppingCart;

import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */

public class TextAdapter extends SimpleAdapter<ShoppingCart>{
    public TextAdapter(Context context, int layoutResId, List<ShoppingCart> datas){
        super(context, layoutResId, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, ShoppingCart item){
    }
}
