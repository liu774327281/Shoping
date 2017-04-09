package com.example.administrator.shoping.bean;

/**
 * Created by Administrator on 2017/3/30.
 */

public class RecyclerLeftBean{

    /**
     * id : 1
     * name : 热门推荐
     * sort : 1
     */

    private int id;
    private String name;
    private int sort;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getSort(){
        return sort;
    }

    public void setSort(int sort){
        this.sort = sort;
    }
}
