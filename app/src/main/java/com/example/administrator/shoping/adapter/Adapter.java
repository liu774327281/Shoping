package com.example.administrator.shoping.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.shoping.R;
import com.example.administrator.shoping.WareListActivity;
import com.example.administrator.shoping.bean.HomeBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder>{

    private static int VIEW_TYPE_L = 0;
    private static int VIEW_TYPE_R = 1;

    private LayoutInflater mInflater;

    private List<HomeBean> mDatas;

    private Context mContext;

    public Adapter(List<HomeBean> datas, Context context){
        mDatas = datas;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type){
        mInflater = LayoutInflater.from(viewGroup.getContext());
        if(type == VIEW_TYPE_R){
            return new ViewHolder(mInflater.inflate(R.layout.card, viewGroup, false));
        }
        return new ViewHolder(mInflater.inflate(R.layout.card2, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        holder.textTitle.setText(mDatas.get(position).getTitle());
        Glide.with(mContext).load(mDatas.get(position).getCpOne().getImgUrl()).into(holder.imageViewBig);
        Glide.with(mContext).load(mDatas.get(position).getCpTwo().getImgUrl()).into(holder.imageViewSmallTop);
        Glide.with(mContext).load(mDatas.get(position).getCpThree().getImgUrl()).into(holder.imageViewSmallBottom);
    }

    @Override
    public int getItemCount(){
        if(mDatas == null || mDatas.size() <= 0)
            return 0;
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position){
        if(position % 2 == 0){
            return VIEW_TYPE_R;
        }else
            return VIEW_TYPE_L;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textTitle;
        ImageView imageViewBig;
        ImageView imageViewSmallTop;
        ImageView imageViewSmallBottom;

        public ViewHolder(View itemView){
            super(itemView);
            textTitle = (TextView) itemView.findViewById(R.id.text_title);
            imageViewBig = (ImageView) itemView.findViewById(R.id.imgview_big);
            imageViewSmallTop = (ImageView) itemView.findViewById(R.id.imgview_small_top);
            imageViewSmallBottom = (ImageView) itemView.findViewById(R.id.imgview_small_bottom);
            imageViewBig.setOnClickListener(this);
            imageViewSmallTop.setOnClickListener(this);
            imageViewSmallBottom.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            switch(v.getId()){
                case R.id.imgview_big:
                    Intent intent = new Intent(mContext, WareListActivity.class);
                    intent.putExtra("data", mDatas.get(getLayoutPosition()).getCpOne().getId());
                    Toast.makeText(mContext, "mDatas.get(getLayoutPosition()).getCpOne().getId():" + mDatas.get(getLayoutPosition()).getCpOne().getId(), Toast.LENGTH_SHORT).show();
                    mContext.startActivity(intent);
                    break;
                case R.id.imgview_small_top:
                    Intent intent2 = new Intent(mContext, WareListActivity.class);
                    intent2.putExtra("data", mDatas.get(getLayoutPosition()).getCpTwo().getId());
                    Toast.makeText(mContext, "mDatas.get(getLayoutPosition()).getCpTwo().getId():" + mDatas.get(getLayoutPosition()).getCpTwo().getId(), Toast.LENGTH_SHORT).show();
                    mContext.startActivity(intent2);
                    break;
                case R.id.imgview_small_bottom:
                    Intent intent3 = new Intent(mContext, WareListActivity.class);
                    intent3.putExtra("data", mDatas.get(getLayoutPosition()).getCpThree().getId());
                    Toast.makeText(mContext, "mDatas.get(getLayoutPosition()).getCpThree().getId():" + mDatas.get(getLayoutPosition()).getCpThree().getId(), Toast.LENGTH_SHORT).show();
                    mContext.startActivity(intent3);
                    break;
            }
        }
    }
}
