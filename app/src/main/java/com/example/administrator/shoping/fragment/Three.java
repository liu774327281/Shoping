package com.example.administrator.shoping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.administrator.shoping.Contants;
import com.example.administrator.shoping.R;
import com.example.administrator.shoping.WareListActivity;
import com.example.administrator.shoping.WrapContentLinearLayoutManager;
import com.example.administrator.shoping.adapter.BaseAdapter;
import com.example.administrator.shoping.adapter.HotFragmentAdaoter;
import com.example.administrator.shoping.adapter.HotFragmentNewAdapter;
import com.example.administrator.shoping.bean.HoTFragentBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by Administrator on 2017/4/2.
 */

public class Three extends Fragment{
    private int currPage = 1;
    private int totalPage = 1;
    private int pageSize = 10;

    private static final int STATE_NORMAL = 0;
    private static final int STATE_REFREH = 1;
    private static final int STATE_MORE = 2;

    private int state = STATE_NORMAL;

    private RecyclerView recyclerView;

    private List<HoTFragentBean.ListBean> list = new ArrayList<>();
    private MaterialRefreshLayout mRefreshLaout;
    private HotFragmentAdaoter adapter;
    private List<HoTFragentBean.ListBean> data;
    private HotFragmentNewAdapter newAdapter;
    private String id;
    public final static int ACTION_LIST = 1;
    public  final static int ACTION_GIRD = 2;
    private ImageView change;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fenlei, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.hot_frag_recyclerview);
        mRefreshLaout = (MaterialRefreshLayout) view.findViewById(R.id.refresh_view);
        initRefreshLayout();
        WareListActivity activity = (WareListActivity) getActivity();
        int aa = activity.data;
        id = String.valueOf(aa);
        change = (ImageView) getActivity().findViewById(R.id.change);
        chang();
        change.setTag(ACTION_LIST);
        getData();
        return view;
    }
    private void chang(){
        change.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                int action = (int) v.getTag();
                Toast.makeText(getContext(), "点击了", Toast.LENGTH_SHORT).show();
                if(ACTION_LIST==action){
                    change.setTag(ACTION_GIRD);
                    recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(newAdapter);
                }if(ACTION_GIRD==action){
                    change.setTag(ACTION_LIST);
                    change.setImageResource(R.mipmap.draw);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
                    recyclerView.setAdapter(newAdapter);
                }
            }
        });
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
                    Toast.makeText(getContext(), "无更多数据", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void refreshData(){
        currPage = 1;
        state = STATE_REFREH;
        getData();
    }

    public void getData(){
        String url = Contants.API.WARES_HOT + "?curPage=" + currPage + "&pageSize=" + pageSize;
        OkHttpUtils.post().url(Contants.API.WARES_CAMPAIN_LIST).addParams("campaignId", id).addParams("orderBy", "1").addParams("curPage", "1").addParams("pageSize", "20").build().execute(new StringCallback(){
            @Override
            public void onError(Call call, Exception e, int id){
            }

            @Override
            public void onResponse(String response, int id){
                Gson gson = new Gson();
                HoTFragentBean info = gson.fromJson(response, new TypeToken<HoTFragentBean>(){
                }.getType());
                data = info.getList();
                list.addAll(data);
                currPage = info.getCurrentPage();
                totalPage = info.getTotalPage();
                showData();
            }
        });
    }

    private void loadMoreData(){
        currPage = ++currPage;
        state = STATE_MORE;
        getData();
    }

    private void showData(){
        //注意 ：不要用list数组 用datas
        //黄色注释中的代码为HotFragmentAdapter的代码 是未封装的adapter
        switch(state){
            case STATE_NORMAL:
                newAdapter = new HotFragmentNewAdapter(getContext(), data);
                recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(newAdapter);
                newAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, int position){
                        Toast.makeText(getContext(), data.get(position).getName(), Toast.LENGTH_SHORT).show();
                    }
                });
                /*adapter = new HotFragmentAdaoter(data, getContext());

             recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
               // recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adapter);*/
                break;
            case STATE_REFREH:
                newAdapter.clear();
                newAdapter.addData(data);
                recyclerView.scrollToPosition(0);
                mRefreshLaout.finishRefresh();

               /* adapter.clearData();
                adapter.addData(data);
                recyclerView.scrollToPosition(0);
                mRefreshLaout.finishRefresh();*/
                break;
            case STATE_MORE:
                newAdapter.addData(newAdapter.getDatas().size(), data);
                recyclerView.scrollToPosition(newAdapter.getDatas().size());
                mRefreshLaout.finishRefreshLoadMore();

               /* adapter.addData(adapter.getDatas().size(), data);
                recyclerView.scrollToPosition(adapter.getDatas().size());
                mRefreshLaout.finishRefreshLoadMore();*/
                break;
        }
    }
}