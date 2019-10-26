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
}
