package com.example.administrator.shoping;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.shoping.adapter.FullyLinearLayoutManager;
import com.example.administrator.shoping.adapter.WareOrderAdapter;
import com.example.administrator.shoping.bean.ShoppingCart;
import com.example.administrator.shoping.utils.CartProvider;
import com.example.administrator.shoping.utils.JSONUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

public class JiesSuan extends AppCompatActivity implements View.OnClickListener{
    @ViewInject(R.id.txt_order)
    private TextView txtOrder;

    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerView;


    @ViewInject(R.id.rl_alipay)
    private RelativeLayout mLayoutAlipay;

    @ViewInject(R.id.rl_wechat)
    private RelativeLayout mLayoutWechat;

    @ViewInject(R.id.rl_bd)
    private RelativeLayout mLayoutBd;


    @ViewInject(R.id.rb_alipay)
    private RadioButton mRbAlipay;

    @ViewInject(R.id.rb_webchat)
    private RadioButton mRbWechat;

    @ViewInject(R.id.rb_bd)
    private RadioButton mRbBd;

    @ViewInject(R.id.btn_createOrder)
    private Button mBtnCreateOrder;

    @ViewInject(R.id.txt_total)
    private TextView mTxtTotal;

    /**
     * 银联支付渠道
     */
    private static final String CHANNEL_UPACP = "upacp";
    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    /**
     * 支付支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";
    /**
     * 百度支付渠道
     */
    private static final String CHANNEL_BFB = "bfb";
    /**
     * 京东支付渠道
     */
    private static final String CHANNEL_JDPAY_WAP = "jdpay_wap";

    private CartProvider cartProvider;

    private WareOrderAdapter mAdapter;

    private HashMap<String,RadioButton> channels = new HashMap<>(3);


    private String orderNum;
    private String payChannel = CHANNEL_ALIPAY;
    private float amount;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jies_suan);
        ViewUtils.inject(this);
        showData();
        init();

    }
    private void init(){



        channels.put(CHANNEL_ALIPAY,mRbAlipay);
        channels.put(CHANNEL_WECHAT,mRbWechat);
        channels.put(CHANNEL_BFB,mRbBd);

        mLayoutAlipay.setOnClickListener(this);
        mLayoutWechat.setOnClickListener(this);
        mLayoutBd.setOnClickListener(this);



        amount = mAdapter.getTotalPrice();
        mTxtTotal.setText("应付款： ￥"+amount);
    }



    public void showData(){

        cartProvider = new CartProvider(this);
        mAdapter = new WareOrderAdapter(this,cartProvider.getAll());

      FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(this);
       layoutManager.setOrientation(GridLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View v){
        selectPayChannle(v.getTag().toString());
    }


    public void selectPayChannle(String paychannel){


        for (Map.Entry<String,RadioButton> entry:channels.entrySet()){

            payChannel = paychannel;
            RadioButton rb = entry.getValue();
            if(entry.getKey().equals(paychannel)){

                boolean isCheck = rb.isChecked();
                rb.setChecked(!isCheck);

            }
            else
                rb.setChecked(false);
        }


    }
    @OnClick(R.id.btn_createOrder)
    public void createNewOrder(View view){

        postNewOrder();
    }
    private void postNewOrder(){

        final List<ShoppingCart> carts = mAdapter.getDatas();
        List<WareItem> items = new ArrayList<>(carts.size());
        for(ShoppingCart c : carts){
            WareItem item = new WareItem(c.getId(),c.getPrice());
            items.add(item);
        }
        String item_json = JSONUtil.toJSON(items);
        Map<String, String> params = new HashMap<>(5);
        params.put("user_id", "22");
        params.put("item_json", item_json);
        params.put("pay_channel", payChannel);
        params.put("amount", (int) amount + "");
        params.put("addr_id", 1 + "");
        mBtnCreateOrder.setEnabled(false);
        OkHttpUtils.post().url(Contants.API.ORDER_CREATE).params(params).build().execute(new StringCallback(){
            @Override
            public void onError(Call call, Exception e, int id){
                Toast.makeText(JiesSuan.this, "没成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id){
                mBtnCreateOrder.setEnabled(true);
                Log.d("JiesSuan", response);
                Toast.makeText(JiesSuan.this, response, Toast.LENGTH_SHORT).show();
                Toast.makeText(JiesSuan.this, response, Toast.LENGTH_SHORT).show();
            }
        });
    }
        class WareItem{
            private int ware_id;
            private double amount;

            public int getWare_id(){
                return ware_id;
            }

            public void setWare_id(int ware_id){
                this.ware_id = ware_id;
            }

            public double getAmount(){
                return amount;
            }

            public void setAmount(double amount){
                this.amount = amount;
            }

            public WareItem(int ware_id, double amount){
                this.ware_id = ware_id;
                this.amount = amount;

            }
        }
}
