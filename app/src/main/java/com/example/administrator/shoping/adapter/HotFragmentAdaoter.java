package com.example.administrator.shoping.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.shoping.R;
import com.example.administrator.shoping.bean.HoTFragentBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */

public class HotFragmentAdaoter extends RecyclerView.Adapter<HotFragmentAdaoter.ViewHoler>{
    private List<HoTFragentBean.ListBean> listBeen;
    private Context mContext;



    public HotFragmentAdaoter(List<HoTFragentBean.ListBean> listBeen,Context context){
        this.listBeen = listBeen;
        this.mContext = context;
    }

    static class ViewHoler extends RecyclerView.ViewHolder{

        ImageView logo;
        TextView title;
        TextView money;
        Button shap_car;

        public ViewHoler(View itemView){
            super(itemView);
            logo = (ImageView) itemView.findViewById(R.id.logo);
            title = (TextView) itemView.findViewById(R.id.text_title);
            money = (TextView) itemView.findViewById(R.id.money);
            shap_car = (Button) itemView.findViewById(R.id.shop_car);
        }
    }

    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hot_recycler, parent, false);
        ViewHoler viewHoler = new ViewHoler(view);
        return viewHoler;
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position){
        Glide.with(mContext).load(listBeen.get(position).getImgUrl()).into(holder.logo);
        holder.title.setText(listBeen.get(position).getName());
     //   double price = listBeen.get(position).getPrice();
       // holder.money.setText("￥"+Double.toString(price));
        holder.money.setText("￥"+listBeen.get(position).getPrice());
    }

    @Override
    public int getItemCount(){
        return listBeen.size();
    }
   public List<HoTFragentBean.ListBean> getDatas(){

        return  listBeen;
    }
    public void clearData(){

        listBeen.clear();
        notifyItemRangeRemoved(0,listBeen.size());
    }

    public void addData(List<HoTFragentBean.ListBean> datas){

        addData(0,datas);
    }

    public void addData(int position,List<HoTFragentBean.ListBean> datas){

        if(datas !=null && datas.size()>0) {


            listBeen.addAll(datas);
            Log.d("HotFragmentAdaoter", "listBeen.size():" + listBeen.size());
            notifyItemRangeChanged(position, listBeen.size());
        }

    }


}
