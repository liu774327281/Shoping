package com.example.administrator.shoping.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.shoping.R;
import com.example.administrator.shoping.bean.ShoppingCart;
import com.example.administrator.shoping.utils.CartProvider;
import com.example.administrator.shoping.view.NumberAddSubView;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */

public class CartAdapter extends SimpleAdapter<ShoppingCart> implements BaseAdapter.OnItemClickListener{

    private CheckBox checkBox;
    private TextView textView;
    private CartProvider cartProvider;

    public CartAdapter(Context context, List<ShoppingCart> datas, final CheckBox checkBox, TextView tv){
        super(context, R.layout.template_cart, datas);
        cartProvider = new CartProvider(context);
        setCheckBox(checkBox);
        setOnItemClickListener(this);
        setTextView(tv);
        showTotalPrice();
    }

    public void setTextView(TextView textview){
        this.textView = textview;
    }

    @Override
    protected void convert(BaseViewHolder viewHoder, final ShoppingCart item){
        viewHoder.getTextView(R.id.text_title).setText(item.getName());
        viewHoder.getTextView(R.id.text_price).setText("￥" + item.getPrice());
        Glide.with(context).load(item.getImgUrl()).into(viewHoder.getImageView(R.id.drawee_view));
        CheckBox checkBox = (CheckBox) viewHoder.getView(R.id.checkbox);
        checkBox.setChecked(item.isChecked());

        NumberAddSubView numberAddSubView = (NumberAddSubView) viewHoder.getView(R.id.num_control);
        numberAddSubView.setValue(item.getCount());
        numberAddSubView.setOnButtonClickListener(new NumberAddSubView.OnButtonClickListener(){
            @Override
            public void onButtonAddClick(View view, int value){
                item.setCount(value);
                cartProvider.update(item);
                showTotalPrice();
            }

            @Override
            public void onButtonSubClick(View view, int value){
                item.setCount(value);
                cartProvider.update(item);
                showTotalPrice();
            }
        });
    }

    public void showTotalPrice(){
        float total = getTotalPrice();
        textView.setText(Html.fromHtml("合计 ￥<span style='color:#eb4f38'>" + total + "</span>"), TextView.BufferType.SPANNABLE);
    }

    private float getTotalPrice(){
        float sum = 0;
        if(!isNull())
            return sum;
        for(ShoppingCart cart : datas){
            if(cart.isChecked())
                sum += cart.getCount() * cart.getPrice();
        }
        return sum;
    }

    private boolean isNull(){
        return (datas != null && datas.size() > 0);
    }

    @Override
    public void onItemClick(View view, int position){
        ShoppingCart cart = getItem(position);
        cart.setChecked(!cart.isChecked());
        notifyItemChanged(position);
        checkListen();
        showTotalPrice();
    }

    private void checkListen(){
        int count = 0;
        int checkNum = 0;
        if(datas != null){
            count = datas.size();
            for(ShoppingCart cart : datas){
                if(!cart.isChecked()){
                    checkBox.setChecked(false);
                    break;
                }else{
                    checkNum = checkNum + 1;
                }
            }
            if(count == checkNum){
                checkBox.setChecked(true);
            }
        }
    }
    public void setCheckBox(CheckBox ck){
        this.checkBox = ck;

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAll_None(checkBox.isChecked());
                showTotalPrice();

            }
        });
    }

    public void checkAll_None(boolean isChecked){


        if(!isNull())
            return ;


        int i=0;
        for (ShoppingCart cart :datas){
            cart.setChecked(isChecked);
            notifyItemChanged(i);
            i++;
        }


    }

    public void delCart(){


        if(!isNull())
            return ;

        //        for (ShoppingCart cart : datas){
        //
        //            if(cart.isChecked()){
        //                int position = datas.indexOf(cart);
        //                cartProvider.delete(cart);
        //                datas.remove(cart);
        //                notifyItemRemoved(position);
        //            }
        //        }


        for(Iterator iterator = datas.iterator(); iterator.hasNext();){

            ShoppingCart cart = (ShoppingCart) iterator.next();
            if(cart.isChecked()){
                int position = datas.indexOf(cart);
                cartProvider.delete(cart);
                iterator.remove();
                notifyItemRemoved(position);
            }

        }




    }
}
