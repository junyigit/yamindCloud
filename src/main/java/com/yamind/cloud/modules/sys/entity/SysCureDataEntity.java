package com.yamind.cloud.modules.sys.entity;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;

public class SysCureDataEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 治疗ID
     */
    private Integer id;


    /**
     * '治疗日期'
     */
    private String cureTime;


    /**
     * 治疗模式
     */
    private String mode;


    /**
     * 机器序列号
     */
    private String bootSerial;


    /**
     * 实时流量
     */
    private float realFlow;


    /**
     * 治疗压力
     */
    private float cureStress;


    /**
     * 呼吸频率
     */
    private float respiratoryRate;



    /**
     * 潮气量
     */
    private float tidalVolume;

    /**
     * 分钟通气量
     */

    private float minusTroughput;


    /**
     * 呼吸暂停次数
     */

    private float aiCount;

    /**
     * 低通气指数
     */

    private float hiCount;


    /**
     * 软件版本号
     */
    private String softVersion;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCureTime() {
        return cureTime;
    }

    public void setCureTime(String cureTime) {
        this.cureTime = cureTime;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getBootSerial() {
        return bootSerial;
    }

    public void setBootSerial(String bootSerial) {
        this.bootSerial = bootSerial;
    }

    public float getRealFlow() {
        return realFlow;
    }

    public void setRealFlow(float realFlow) {
        this.realFlow = realFlow;
    }

    public float getCureStress() {
        return cureStress;
    }

    public void setCureStress(float cureStress) {
        this.cureStress = cureStress;
    }

    public float getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(float respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    public float getTidalVolume() {
        return tidalVolume;
    }

    public void setTidalVolume(float tidalVolume) {
        this.tidalVolume = tidalVolume;
    }

    public float getMinusTroughput() {
        return minusTroughput;
    }

    public void setMinusTroughput(float minusTroughput) {
        this.minusTroughput = minusTroughput;
    }

    public float getAiCount() {
        return aiCount;
    }

    public void setAiCount(float aiCount) {
        this.aiCount = aiCount;
    }

    public float getHiCount() {
        return hiCount;
    }

    public void setHiCount(float hiCount) {
        this.hiCount = hiCount;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }








}
