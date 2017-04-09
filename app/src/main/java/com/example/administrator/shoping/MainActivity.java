package com.example.administrator.shoping;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.shoping.adapter.FragmentAdapter;
import com.example.administrator.shoping.base.BaseActivity;
import com.example.administrator.shoping.fragment.CategoryFragment;
import com.example.administrator.shoping.fragment.HomeFragment;
import com.example.administrator.shoping.fragment.HotFragment;
import com.example.administrator.shoping.fragment.ShopCar;
import com.example.administrator.shoping.fragment.My;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity{


    private ViewPager viewPager;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.vp);
        List<Fragment> list = new ArrayList<>();
        radioGroup = (RadioGroup) findViewById(R.id.rb_layout);
        list.add(new HomeFragment());
       list.add(new HotFragment());
       // list.add(new TextFragment());
        list.add(new CategoryFragment());
        list.add(new ShopCar());
        list.add(new My());


        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), list);
        viewPager = (ViewPager) findViewById(R.id.vp);
        viewPager.setAdapter(adapter);
        radioGroup = (RadioGroup) findViewById(R.id.rb_layout);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled (int position, float positionOffset, int positionOffsetPixels){
            }

            @Override
            public void onPageSelected (int position){
                ((RadioButton) radioGroup.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged (int state){
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i){
                switch(i){
                    case R.id.home:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.hot:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.classification:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.shop:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.my:
                        viewPager.setCurrentItem(4);
                        break;
                }
            }
        });
    }
}