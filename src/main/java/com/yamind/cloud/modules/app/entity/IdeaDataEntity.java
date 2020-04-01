package com.yamind.cloud.modules.app.entity;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class IdeaDataEntity implements Serializable {

    private Long id;

    private Long userId;

    private String userName;

    private String userPhone;

    private Integer ideaType;

    private String createTime;

    private String content;

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

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public Integer getIdeaType() {
        return ideaType;
    }

    public void setIdeaType(Integer ideaType) {
        this.ideaType = ideaType;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



}
