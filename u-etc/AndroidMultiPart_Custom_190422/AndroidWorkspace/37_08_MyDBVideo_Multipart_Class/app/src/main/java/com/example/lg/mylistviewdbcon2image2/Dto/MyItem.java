package com.example.lg.mylistviewdbcon2image2.Dto;

/**
 * Created by LG on 2017-02-08.
 */

public class MyItem {
    public String id;
    public String name;
    public String date;
    public String image1;
    public String uploadType;
    public String videoimage;

    public MyItem(String id, String name, String date, String image1, String uploadType, String videoimage) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.image1 = image1;
        this.uploadType = uploadType;
        this.videoimage = videoimage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public String getVideoimage() {
        return videoimage;
    }

    public void setVideoimage(String videoimage) {
        this.videoimage = videoimage;
    }
}
