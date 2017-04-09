package com.example.administrator.shoping;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.shoping.adapter.TablayoutFragmentAdapter;
import com.example.administrator.shoping.base.BaseActivity;
import com.example.administrator.shoping.fragment.One;
import com.example.administrator.shoping.fragment.Three;
import com.example.administrator.shoping.fragment.Two;

import java.util.ArrayList;
import java.util.List;

public class WareListActivity extends BaseActivity implements View.OnClickListener{

    private TabLayout frg_home_tablayout;
    private ViewPager frg_home_viewpager;
    private List<String> list;
    public int data;
    private ImageView back;
    private ImageView change;

    public static final int ACTION_LIST=1;
    public static final int ACTION_GIRD=2;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_list);
        //  frg_home_tablayout = (TabLayout) view.findViewById(R.id.frg_home_tablayout);
        //frg_home_viewpager = (ViewPager) view.findViewById(R.id.frg_home_viewpager);
        frg_home_tablayout = (TabLayout) findViewById(R.id.frg_home_tablayout);
        frg_home_viewpager = (ViewPager) findViewById(R.id.frg_home_viewpager);
        back = (ImageView) findViewById(R.id.back);
        change = (ImageView) findViewById(R.id.change);
        Intent intent = getIntent();
        data = intent.getIntExtra("data", 0);
        change.setOnClickListener(this);
        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                WareListActivity.this.finish();
            }
        });
        change.setTag(ACTION_LIST);
        initData();
    }

    private void initData(){
        list = new ArrayList<>();
        list.add("默认");
        list.add("价格");
        list.add("销量");
        List<Fragment> frgList = new ArrayList<>();
        frgList.add(new One());
        frgList.add(new Two());
        frgList.add(new Three());
        TablayoutFragmentAdapter tablayoutFragmentAdapter = new TablayoutFragmentAdapter(getSupportFragmentManager(), WareListActivity.this, frgList, list);
        frg_home_viewpager.setAdapter(tablayoutFragmentAdapter);
        frg_home_tablayout.setupWithViewPager(frg_home_viewpager);
    }

    @Override
    public void onClick(View v){
        int action = (int) v.getTag();

         if(ACTION_LIST==action){
             change.setTag(ACTION_GIRD);

         }

           /*
        int action = (int) v.getTag();

        if(ACTION_LIST == action){

            mToolbar.setRightButtonIcon(R.drawable.icon_list_32);
            mToolbar.getRightButton().setTag(ACTION_GIRD);

            mWaresAdapter.resetLayout(R.layout.template_grid_wares);


           mRecyclerview_wares.setLayoutManager(new GridLayoutManager(this,2));
            mRecyclerview_wares.setAdapter(mWaresAdapter);

        }
        else if(ACTION_GIRD == action){


            mToolbar.setRightButtonIcon(R.drawable.icon_grid_32);
            mToolbar.getRightButton().setTag(ACTION_LIST);

            mWaresAdapter.resetLayout(R.layout.template_hot_wares);

            mRecyclerview_wares.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerview_wares.setAdapter(mWaresAdapter);
        }
    }*/

    }
}
