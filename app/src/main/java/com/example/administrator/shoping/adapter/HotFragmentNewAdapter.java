package com.example.administrator.shoping.adapter;

import android.content.Context;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.administrator.shoping.R;
import com.example.administrator.shoping.bean.HoTFragentBean;
import com.example.administrator.shoping.bean.ShoppingCart;
import com.example.administrator.shoping.utils.CartProvider;
import com.example.administrator.shoping.utils.ToastUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class HotFragmentNewAdapter extends SimpleAdapter<HoTFragentBean.ListBean>{
    CartProvider cartProvider;

    public HotFragmentNewAdapter(Context context, List<HoTFragentBean.ListBean> datas){
        super(context, R.layout.hot_recycler, datas);
        cartProvider = new CartProvider(context);
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, final HoTFragentBean.ListBean item){
     /* logo = (ImageView) itemView.findViewById(R.id.logo);
            title = (TextView) itemView.findViewById(R.id.text_title);
            money = (TextView) itemView.findViewById(R.id.money);
            shap_car = (Button) itemView.findViewById(R.id.shop_car);*/
        viewHoder.getTextView(R.id.text_title).setText(item.getName());
        viewHoder.getTextView(R.id.money).setText(Double.toString(item.getPrice()));
        Glide.with(context).load(item.getImgUrl()).into(viewHoder.getImageView(R.id.logo));
        viewHoder.getButton(R.id.shop_car).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

               //cartProvider.put(convertData(item));
                cartProvider.put(item);
                ToastUtils.show(context, "已添加到购物车");
            }
        });
    }

    public ShoppingCart convertData(HoTFragentBean.ListBean item){
        ShoppingCart cart = new ShoppingCart();


        cart.setImgUrl(item.getImgUrl());
        cart.setName(item.getName());
        cart.setCategoryId(item.getCategoryId());
        cart.setSale(item.getSale());
        cart.setPrice(item.getPrice());
        cart.setId(item.getId());
        return cart;
    }



    /* @Override
    protected void convert(BaseViewHolder viewHolder, final Wares wares) {
        SimpleDraweeView draweeView = (SimpleDraweeView) viewHolder.getView(R.id.drawee_view);
        draweeView.setImageURI(Uri.parse(wares.getImgUrl()));

        viewHolder.getTextView(R.id.text_title).setText(wares.getName());

        Button button =viewHolder.getButton(R.id.btn_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                provider.put(convertData(wares));

                ToastUtils.show(context,"已添加到购物车");
            }
        });

    }


    public ShoppingCart convertData(Wares item){

        ShoppingCart cart = new ShoppingCart();

        cart.setId(item.getId());
        cart.setDescription(item.getDescription());
        cart.setImgUrl(item.getImgUrl());
        cart.setName(item.getName());
        cart.setPrice(item.getPrice());

        return cart;
    }
*/ public void  resetLayout(int layoutId){


        this.layoutResId=layoutId;

        notifyItemRangeChanged(0,getDatas().size());


    }
}
