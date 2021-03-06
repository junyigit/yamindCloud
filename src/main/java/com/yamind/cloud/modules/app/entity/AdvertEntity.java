package com.yamind.cloud.modules.app.entity;

import java.io.Serializable;

public class AdvertEntity implements Serializable {


    Long id;

    String advertName;

    String advertImgPath;

    String createTime;

    Integer sort;


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdvertName() {
        return advertName;
    }

    public void setAdvertName(String advertName) {
        this.advertName = advertName;
    }

    public String getAdvertImgPath() {
        return advertImgPath;
    }

    public void setAdvertImgPath(String advertImgPath) {
        this.advertImgPath = advertImgPath;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}
