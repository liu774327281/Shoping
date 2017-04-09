package com.example.administrator.shoping;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.administrator.shoping.bean.HoTFragentBean;
import com.example.administrator.shoping.utils.CartProvider;
import com.example.administrator.shoping.utils.ToastUtils;
import com.example.administrator.shoping.view.CNiaoToolBar;

import java.io.Serializable;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import dmax.dialog.SpotsDialog;

public class ShopShow extends AppCompatActivity{
    private SpotsDialog mDialog;
    private WebView webView;
    HoTFragentBean.ListBean listbean;
    WebAppInterface mAppInterfce;
    private CartProvider cartProvider;
    private WebSettings settings;
    private CNiaoToolBar mToobar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_show);
        webView = (WebView) findViewById(R.id.web);
        Serializable serializable = getIntent().getSerializableExtra(Contants.WARE);
        if(serializable == null){
            this.finish();
        }
        cartProvider = new CartProvider(this);
        settings = webView.getSettings();
        mDialog = new SpotsDialog(this, "loading....");
        mDialog.show();
        listbean = (HoTFragentBean.ListBean) serializable;
        initWebView();
        initToobar();
    }

    private void initToobar(){
        mToobar = (CNiaoToolBar) findViewById(R.id.toolbar);
        mToobar.setRightButtonText("分享");
        mToobar.setRightButtonOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showShare();
            }
        });
    }

    private void showShare(){
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(this);
    }

    private void initWebView(){
        settings.setJavaScriptEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setAppCacheEnabled(true);
        webView.loadUrl(Contants.API.WARES_DETAIL);
        mAppInterfce = new WebAppInterface();
        webView.addJavascriptInterface(mAppInterfce, "appInterface");
        webView.setWebViewClient(new WC());
    }

    class WC extends WebViewClient{

        @Override
        public void onPageFinished(WebView view, String url){
            super.onPageFinished(view, url);
            if(mDialog != null && mDialog.isShowing())
                mDialog.dismiss();
            mAppInterfce.showDetail();
        }
    }

    class WebAppInterface{

        @JavascriptInterface
        public void showDetail(){
            runOnUiThread(new Runnable(){
                @Override
                public void run(){
                    webView.loadUrl("javascript:showDetail(" + listbean.getId() + ")");
                }
            });
        }

        @JavascriptInterface
        public void buy(long id){
            cartProvider.put(listbean);
            ToastUtils.show(ShopShow.this, "已添加到购物车");
        }

        @JavascriptInterface
        public void addFavorites(long id){
            // addToFavorite();
        }
    }
}
