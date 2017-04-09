package com.example.administrator.shoping.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.shoping.LoginActivity;
import com.example.administrator.shoping.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/3/28.
 */

public class My extends Fragment{

    private CircleImageView icon_head;
    private TextView text_username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //  View view = inflater.inflate(, null);
        View view = inflater.inflate(R.layout.wu, null);
        icon_head = (CircleImageView) view.findViewById(R.id.img_head);
        text_username = (TextView) view.findViewById(R.id.txt_username);
        icon_head.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                intent();
            }
        });
        text_username.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
intent();
           }
        });
        return view;
    }

    private void intent(){
        startActivity(new Intent(getContext(), LoginActivity.class));
    }
}
