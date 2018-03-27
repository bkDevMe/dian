package com.example.dian.bean;

/**
 * Created by Administrator on 2018/3/18.
 */

public class ActivityBean {

    private int imageId_big;

    private String title;

    private String time;

    private String address;

    private int imageId_small;

    public ActivityBean(int imageId_big, String title, String time, String address, int imageId_small) {
        this.imageId_big = imageId_big;
        this.title = title;
        this.time = time;
        this.address = address;
        this.imageId_small = imageId_small;
    }

    public int getImageId_big() {
        return imageId_big;
    }

    public void setImageId_big(int imageId_big) {
        this.imageId_big = imageId_big;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getImageId_small() {
        return imageId_small;
    }

    public void setImageId_small(int imageId_small) {
        this.imageId_small = imageId_small;
    }

}
