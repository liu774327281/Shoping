package com.example.administrator.shoping.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.administrator.shoping.R;
import com.example.administrator.shoping.adapter.Adapter;
import com.example.administrator.shoping.bean.Banner;
import com.example.administrator.shoping.bean.HomeBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/3/28.
 */

public class HomeFragment extends Fragment{
    private final List<String> list = new ArrayList<>();
    private final List<String> strings = new ArrayList<>();

    String url = "http://112.124.22.238:8081/course_api/banner/query?type=1";
    String url2 = "http://112.124.22.238:8081/course_api/campaign/recommend";
    private RecyclerView recyclerview ;
    private List<HomeBean> homelist=new ArrayList<>();
    private SliderLayout banner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.home_fragment, null);
        banner = (SliderLayout) view.findViewById(R.id.slider);
        recyclerview = (RecyclerView) view.findViewById(R.id.recyclerview);
        OkHttpUtils.get().url(url).build().execute(new StringCallback(){
            @Override
            public void onError(Call call, Exception e, int id){
            }

            @Override
            public void onResponse(String response, int id){
                Gson gson=new Gson();
                List<Banner> info = gson.fromJson(response, new TypeToken<List<Banner>>(){
                }.getType());
                for(Banner banner1 : info){
                    TextSliderView textSliderView= new TextSliderView(getActivity());
                    textSliderView.image(banner1.getImgUrl());
                    textSliderView.description(banner1.getName());
                    textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                    banner.addSlider(textSliderView);


                }

                //设置指示器位置
                banner.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                banner.setCustomAnimation(new DescriptionAnimation());
                //设置转场动画
                banner.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
                banner.setDuration(3000);
            }
        });

        OkHttpUtils.get().url(url2).build().execute(new StringCallback(){

            @Override
            public void onError(Call call, Exception e, int id){
            }

            @Override
            public void onResponse(String response, int id){
                Gson gson = new Gson();
                List<HomeBean> info = gson.fromJson(response, new TypeToken<List<HomeBean>>(){
                }.getType());
                for(HomeBean homeBean : info){
                    homelist.add(homeBean);
                }

            }


        });

        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerview.setLayoutManager(layoutManager);


        Adapter adapter=new Adapter(homelist,getContext());
        recyclerview.setAdapter(adapter);



        return view;

    }



}
