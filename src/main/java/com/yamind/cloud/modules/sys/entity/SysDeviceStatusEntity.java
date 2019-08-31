package com.yamind.cloud.modules.sys.entity;

import java.io.Serializable;

public class SysDeviceStatusEntity implements Serializable {


    /**
     * 状态id
     */
    private Long id;


    /**
     * 序列号
     */
    private String serialId;



    /**
     * 开始时间
     */

    private String startTime;



    /**
     * 结束时间
     */
    private String stopTime;


    /**
     * 状态
     * @return
     */

    private Integer status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
