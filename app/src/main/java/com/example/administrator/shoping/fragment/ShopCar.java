package com.example.administrator.shoping.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.administrator.shoping.JiesSuan;
import com.example.administrator.shoping.R;
import com.example.administrator.shoping.adapter.CartAdapter;
import com.example.administrator.shoping.bean.ShoppingCart;
import com.example.administrator.shoping.utils.CartProvider;
import com.example.administrator.shoping.view.CNiaoToolBar;

import java.util.List;

/**
 * Created by Administrator on 2017/3/28.
 */

public class ShopCar extends Fragment implements View.OnClickListener{
    private CartAdapter mAdapter;
    private CartProvider cartProvider;
    private RecyclerView mRecyclerView;
    private CheckBox mchecBox;
    private TextView txt_total;
    public static final int ACTION_EDIT = 1;
    public static final int ACTION_CAMPLATE = 2;
    private CNiaoToolBar mToolbar;
    private Button btn_order;
    private Button btn_del;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //  View view = inflater.inflate(, null);
        View view = inflater.inflate(R.layout.si, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        cartProvider = new CartProvider(getContext());
        mchecBox = (CheckBox) view.findViewById(R.id.checkbox_all);
        btn_order = (Button) view.findViewById(R.id.btn_order);
        btn_del = (Button) view.findViewById(R.id.btn_del);
        txt_total = (TextView) view.findViewById(R.id.txt_total);
        mToolbar = (CNiaoToolBar) view.findViewById(R.id.toolbar);
        btn_del.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mAdapter.delCart();
            }
        });
        btn_order.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            startActivity(new Intent(getActivity(), JiesSuan.class));
            }
        });
        changeToolbar();
        show();
        return view;
    }

    private void show(){
        List<ShoppingCart> carts = cartProvider.getAll();
        mAdapter = new CartAdapter(getActivity(), carts, mchecBox, txt_total);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void changeToolbar(){
        mToolbar.hideSearchView();
        mToolbar.showTitleView();
        mToolbar.setTitle("购物车");
        mToolbar.getRightButton().setVisibility(View.VISIBLE);
        mToolbar.setRightButtonText("编辑");
        mToolbar.getRightButton().setOnClickListener(this);
        mToolbar.getRightButton().setTag(ACTION_EDIT);
    }

    @Override
    public void onClick(View v){
        int action = (int) v.getTag();
        if(ACTION_EDIT == action){
            showDelControl();
        }else if(ACTION_CAMPLATE == action){
            hideDelControl();
        }
    }

    private void showDelControl(){
        mToolbar.getRightButton().setText("完成");
        txt_total.setVisibility(View.GONE);
        btn_order.setVisibility(View.GONE);
        btn_del.setVisibility(View.VISIBLE);
        mToolbar.getRightButton().setTag(ACTION_CAMPLATE);
        mAdapter.checkAll_None(false);
        mchecBox.setChecked(false);
    }

    private void hideDelControl(){
        txt_total.setVisibility(View.VISIBLE);
        btn_order.setVisibility(View.VISIBLE);
        btn_del.setVisibility(View.GONE);
        mToolbar.setRightButtonText("编辑");
        mToolbar.getRightButton().setTag(ACTION_EDIT);
        mAdapter.checkAll_None(true);
        mAdapter.showTotalPrice();
        mchecBox.setChecked(true);
    }
}
