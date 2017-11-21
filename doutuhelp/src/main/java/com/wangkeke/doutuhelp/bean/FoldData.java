package com.wangkeke.doutuhelp.bean;

import java.io.Serializable;

/**
 * Created by wangkeke on 2017/11/21.
 */

public class FoldData implements Serializable {

    private String title;

    private String imgUrl;

    private String jumpUrl;

    private String date;

    public FoldData() {
    }

    public FoldData(String title, String imgUrl, String jumpUrl, String date) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.jumpUrl = jumpUrl;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }
}
