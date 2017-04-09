package com.example.administrator.shoping.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.administrator.shoping.Contants;
import com.example.administrator.shoping.R;
import com.example.administrator.shoping.ShopShow;
import com.example.administrator.shoping.WrapContentLinearLayoutManager;
import com.example.administrator.shoping.adapter.BaseAdapter;
import com.example.administrator.shoping.adapter.HotFragmentAdaoter;
import com.example.administrator.shoping.adapter.HotFragmentNewAdapter;
import com.example.administrator.shoping.bean.HoTFragentBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by liujie on 2017/1/23.
 */

public class HotFragment extends Fragment implements Serializable{

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //  View view = inflater.inflate(, null);
        View view = inflater.inflate(R.layout.hotfragment, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.hot_frag_recyclerview);
        mRefreshLaout = (MaterialRefreshLayout) view.findViewById(R.id.refresh_view);
        initRefreshLayout();
        getData();
        return view;
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
        OkHttpUtils.get().url(url).build().execute(new StringCallback(){
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

                newAdapter = new HotFragmentNewAdapter(getContext(),data);

                recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
               recyclerView.setAdapter(newAdapter);
                newAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, int position){
                      HoTFragentBean.ListBean listBean = data.get(position);
                        Intent intent = new Intent(getActivity(), ShopShow.class);
                   intent.putExtra(Contants.WARE,listBean);

                        startActivity(intent);
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
