package com.example.administrator.shoping.bean;

import java.io.Serializable;

/**
 * Created by <a href="http://www.cniao5.com">菜鸟窝</a>
 * 一个专业的Android开发在线教育平台
 */
public class ShoppingCart implements Serializable{
    private int count;
    private boolean isChecked = true;

    private int id;
    private int categoryId;
    private int campaignId;
    private String name;
    private String imgUrl;
    private double price;
    private int sale;

    public int getCount(){
        return count;
    }

    public void setCount(int count){
        this.count = count;
    }

    public boolean isChecked(){
        return isChecked;
    }

    public void setChecked(boolean checked){
        isChecked = checked;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getCategoryId(){
        return categoryId;
    }

    public void setCategoryId(int categoryId){
        this.categoryId = categoryId;
    }

    public int getCampaignId(){
        return campaignId;
    }

    public void setCampaignId(int campaignId){
        this.campaignId = campaignId;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getImgUrl(){
        return imgUrl;
    }

    public void setImgUrl(String imgUrl){
        this.imgUrl = imgUrl;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public int getSale(){
        return sale;
    }

    public void setSale(int sale){
        this.sale = sale;
    }
}
