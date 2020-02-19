package com.yamind.cloud.modules.sys.entity;

import java.io.Serializable;

public class SysCureDataBoeEntity implements Serializable {


    /**
     * ID
     */
    private Integer id;




    /**
     * 机器序列号
     */
    private String bootSerial;



    /**
     * '治疗日期'
     */
    private String cureTime;


    /**
     * 漏气量
     */

    private float leakage;


    /**
     * 治疗压力
     */
    private float cureStress;

    /**
     * 呼吸暂停次数
     */
    private float aiCount;


    /**
     * 低通气指数
     */
    private float hiCount;


    /**
     * 潮气量
     */
    private int tidalVolume;


    /**
     * 呼吸频率
     */
    private float respiratoryRate;

    //吸气压力
    private Float inhaleStress;

    //呼气压力
    private Float exhaleStress;

    //分钟通气量
    private Float minuThroughput;


    public Float getMinuThroughput() {
        return minuThroughput;
    }

    public void setMinuThroughput(Float minuThroughput) {
        this.minuThroughput = minuThroughput;
    }

    public Float getInhaleStress() {
        return inhaleStress;
    }

    public void setInhaleStress(Float inhaleStress) {
        this.inhaleStress = inhaleStress;
    }

    public Float getExhaleStress() {
        return exhaleStress;
    }

    public void setExhaleStress(Float exhaleStress) {
        this.exhaleStress = exhaleStress;
    }




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

    public float getCureStress() {
        return cureStress;
    }

    public void setCureStress(float cureStress) {
        this.cureStress = cureStress;
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
    public String getBootSerial() {
        return bootSerial;
    }

    public void setBootSerial(String bootSerial) {
        this.bootSerial = bootSerial;
    }
    public float getLeakage() {
        return leakage;
    }

    public void setLeakage(float leakage) {
        this.leakage = leakage;
    }

    public int getTidalVolume() {
        return tidalVolume;
    }

    public void setTidalVolume(int tidalVolume) {
        this.tidalVolume = tidalVolume;
    }

    public float getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(float respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

}
