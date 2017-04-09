package com.example.administrator.shoping.adapter;

import android.content.Context;

import com.example.administrator.shoping.R;
import com.example.administrator.shoping.bean.RecyclerLeftBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class LeftAdapter extends SimpleAdapter<RecyclerLeftBean>{
    public LeftAdapter(Context context,  List<RecyclerLeftBean> datas){
        super(context, R.layout.recy_left, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, RecyclerLeftBean item){
        viewHoder.getTextView(R.id.recy_left_text).setText(item.getName());
    }

}
