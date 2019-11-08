package com.yamind.cloud.modules.app.entity;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class IdeaDataEntity implements Serializable {

    private Long id;

    private Long userId;

    private String userName;

    private String phone;

    private Integer type;

    private Integer time;

    private String content;

//    private List<String> imgList;
//    public List<String> getImgList() {
//        return imgList;
//    }
//
//    public void setImgList(List<String> imgList) {
//        this.imgList = imgList;
//    }



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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }


}
