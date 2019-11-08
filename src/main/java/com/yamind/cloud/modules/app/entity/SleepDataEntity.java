package com.yamind.cloud.modules.app.entity;

import java.beans.Transient;
import java.io.Serializable;

public class SleepDataEntity implements Serializable {


    Long id;

    Long userId;

    String serial;

    String sleepDataTime;

    String uploadTime;

    String data;

    String path;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getSleepDataTime() {
        return sleepDataTime;
    }

    public void setSleepDataTime(String sleepDataTime) {
        this.sleepDataTime = sleepDataTime;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String pat) {
        this.path = pat;
    }
}
