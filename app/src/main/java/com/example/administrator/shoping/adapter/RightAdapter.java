package com.example.administrator.shoping.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.example.administrator.shoping.R;
import com.example.administrator.shoping.bean.RightRecyclerBanner;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class RightAdapter extends SimpleAdapter<RightRecyclerBanner.ListBean>{

    public RightAdapter(Context context, List<RightRecyclerBanner.ListBean> datas){
        super(context, R.layout.template_grid_wares, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, RightRecyclerBanner.ListBean item){
        Glide.with(context).load(item.getImgUrl()).into(viewHoder.getImageView(R.id.logo));
        viewHoder.getTextView(R.id.text_title).setText(item.getName());
        viewHoder.getTextView(R.id.text_price).setText("￥"+Double.toString(item.getPrice()));
    }
}
