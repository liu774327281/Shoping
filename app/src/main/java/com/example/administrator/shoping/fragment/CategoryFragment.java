package com.example.administrator.shoping.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.administrator.shoping.Contants;
import com.example.administrator.shoping.R;
import com.example.administrator.shoping.adapter.BaseAdapter;
import com.example.administrator.shoping.adapter.LeftAdapter;
import com.example.administrator.shoping.adapter.RightAdapter;
import com.example.administrator.shoping.bean.Banner;
import com.example.administrator.shoping.bean.RecyclerLeftBean;
import com.example.administrator.shoping.bean.RightRecyclerBanner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/3/28.
 */

public class CategoryFragment extends Fragment{

    private RecyclerView recyclerview_left;
    private SliderLayout banner;
    String url = "http://112.124.22.238:8081/course_api/banner/query?type=1";
    private int currPage = 1;
    private int totalPage = 1;
    private int pageSize = 10;

    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFREH = 1;
    private static final int STATE_MORE = 2;

    private int state = STATE_NORMAL;
    private List<RightRecyclerBanner.ListBean> list;
    private RecyclerView rightRecycler;
    private RightAdapter adapter;
    private long category_id = 1;
    private MaterialRefreshLayout mRefreshLaout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = null;
        getRightData(category_id);
        if(view == null){
            view = inflater.inflate(R.layout.fragment_category, null);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if(parent != null){
            parent.removeView(view);
        }
        recyclerview_left = (RecyclerView) view.findViewById(R.id.recycler_left);
        banner = (SliderLayout) view.findViewById(R.id.slider);
        rightRecycler = (RecyclerView) view.findViewById(R.id.recyclerview_wares);
        mRefreshLaout = (MaterialRefreshLayout) view.findViewById(R.id.refresh);
        getBanner();
        getleftdata();
        //getRightData(category_id);
        initRefreshLayout();
        return view;
    }

    @Override
    public void onStart(){
        super.onStart();
        if(adapter!=null){
            Log.d("CategoryFragment", "list.size():" + list.size());

            rightRecycler.setAdapter(adapter);
            rightRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
            rightRecycler.setItemAnimator(new DefaultItemAnimator());
        }
        else {
            Log.d("CategoryFragment", "(adapter!=null");
        }

    }

    private void initRefreshLayout(){
        mRefreshLaout.setLoadMore(true);
        mRefreshLaout.setMaterialRefreshListener(new MaterialRefreshListener(){
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout){
                refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout){
                if(currPage <= totalPage)
                    loadMoreData();
                else{
                    //                    Toast.makeText()
                    mRefreshLaout.finishRefreshLoadMore();
                }
            }
        });
    }

    private void refreshData(){
        currPage = 1;
        state = STATE_REFREH;
        getRightData(category_id);
    }

    private void loadMoreData(){
        currPage = ++currPage;
        state = STATE_MORE;
        getRightData(category_id);
    }

    public void getleftdata(){
        OkHttpUtils.get().url(Contants.API.CATEGORY_LIST).build().execute(new StringCallback(){
            @Override
            public void onError(Call call, Exception e, int id){
            }

            @Override
            public void onResponse(String response, int id){
                Gson gson = new Gson();
                List<RecyclerLeftBean> info = gson.fromJson(response, new TypeToken<List<RecyclerLeftBean>>(){
                }.getType());
                showLetf(info);
                category_id = info.get(0).getId();
                getRightData(category_id);
            }
        });
    }

    private void showLetf(final List<RecyclerLeftBean> info){
        recyclerview_left.setLayoutManager(new LinearLayoutManager(getContext()));
        LeftAdapter leftAdapter = new LeftAdapter(getActivity(), info);
        leftAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position){
                category_id = info.get(position).getId();
                currPage = 1;
                state = STATE_NORMAL;
                getRightData(category_id);
                Toast.makeText(getContext(), info.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerview_left.setAdapter(leftAdapter);
    }

    public void getBanner(){
        OkHttpUtils.get().url(url).build().execute(new StringCallback(){
            @Override
            public void onError(Call call, Exception e, int id){
            }

            @Override
            public void onResponse(String response, int id){
                Gson gson = new Gson();
                List<Banner> info = gson.fromJson(response, new TypeToken<List<Banner>>(){
                }.getType());
                for(Banner banner1 : info){
                    DefaultSliderView textSliderView = new DefaultSliderView(getActivity());
                    textSliderView.image(banner1.getImgUrl());
                    textSliderView.description(banner1.getName());
                    textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                    banner.addSlider(textSliderView);
                }
                //设置指示器位置
                banner.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
                banner.setCustomAnimation(new DescriptionAnimation());
                //设置转场动画
                banner.setPresetTransformer(SliderLayout.Transformer.RotateDown);
                banner.setDuration(3000);
            }
        });
    }

    public void getRightData(long categoryId){
        String url = Contants.API.WARES_LIST + "?categoryId=" + categoryId + "&curPage=" + currPage + "&pageSize=" + pageSize;
        //String url="http://112.124.22.238:8081/course_api/wares/list?categoryId=1&curPage=1&pageSize=10";
        OkHttpUtils.get().url(url).build().execute(new StringCallback(){
            @Override
            public void onError(Call call, Exception e, int id){
            }

            @Override
            public void onResponse(String response, int id){
                Gson gson = new Gson();
                RightRecyclerBanner info = gson.fromJson(response, new TypeToken<RightRecyclerBanner>(){
                }.getType());
                list = info.getList();
                currPage = info.getCurrentPage();
                totalPage = info.getTotalPage();
                showRight(list);
            }
        });
    }

    private void showRight(final List<RightRecyclerBanner.ListBean> datas){
        switch(state){
            case STATE_NORMAL:
                if(adapter == null){
                    adapter = new RightAdapter(getContext(), datas);
                    adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener(){
                        @Override
                        public void onItemClick(View view, int position){
                            Toast.makeText(getContext(), datas.get(position).getName(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    rightRecycler.setAdapter(adapter);
                    rightRecycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    rightRecycler.setItemAnimator(new DefaultItemAnimator());
                }else{
                    adapter.clear();
                    adapter.addData(datas);
                }
                break;
            case STATE_REFREH:
                adapter.clear();
                adapter.addData(datas);
                rightRecycler.scrollToPosition(0);
                mRefreshLaout.finishRefresh();
                break;
            case STATE_MORE:
                adapter.addData(adapter.getDatas().size(), datas);
                rightRecycler.scrollToPosition(adapter.getDatas().size());
                mRefreshLaout.finishRefreshLoadMore();
                break;
        }
    }

}
