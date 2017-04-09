package com.example.administrator.shoping.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.example.administrator.shoping.R;
import com.example.administrator.shoping.bean.ShoppingCart;

import java.util.List;

/**
 * Created by <a href="http://www.cniao5.com">菜鸟窝</a>
 * 一个专业的Android开发在线教育平台
 */
public class WareOrderAdapter extends SimpleAdapter<ShoppingCart>{

    public WareOrderAdapter(Context context, List<ShoppingCart> datas){
        super(context, R.layout.text, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, final ShoppingCart item){
        //        viewHoder.getTextView(R.id.text_title).setText(item.getName());
        //        viewHoder.getTextView(R.id.text_price).setText("￥"+item.getPrice());
      //  SimpleDraweeView draweeView = (SimpleDraweeView) viewHoder.getView(R.id.drawee_view);
        //draweeView.setImageURI(Uri.parse(item.getImgUrl()));
        Glide.with(context).load(item.getImgUrl()).into(viewHoder.getImageView(R.id.drawee_view));
    }

    public float getTotalPrice(){
        float sum = 0;
        if(!isNull())
            return sum;
        for(ShoppingCart cart : datas){
            sum += cart.getCount() * cart.getPrice();
        }
        return sum;
    }

    private boolean isNull(){
        return (datas != null && datas.size() > 0);
    }
}
