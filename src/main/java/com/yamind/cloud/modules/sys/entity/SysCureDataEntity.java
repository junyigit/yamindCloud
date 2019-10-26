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
    private float realFlow1;
    private float realFlow2;
    private float realFlow3;
    private float realFlow4;
    private float realFlow5;


    /**
     * 漏气量
     */
    private float leakage;

    /**
     * 治疗压力
     */
    private float cureStress1;
    private float cureStress2;
    private float cureStress3;
    private float cureStress4;


    private float cureStress5;



    /**
     * 呼吸频率
     */
    private float respiratoryRate;


    /**
     * 潮气量
     */
    private int tidalVolume;

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



    /**
     * 吸呼比
     * @return
     */

    private float xhb;


    //吸气压力
    private Float inhaleStress;


    //呼气压力
    private Float exhaleStress;

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

    public float getCureStress1() {
        return cureStress1;
    }

    public void setCureStress1(float cureStress1) {
        this.cureStress1 = cureStress1;
    }

    public float getCureStress2() {
        return cureStress2;
    }

    public void setCureStress2(float cureStress2) {
        this.cureStress2 = cureStress2;
    }

    public float getCureStress3() {
        return cureStress3;
    }

    public void setCureStress3(float cureStress3) {
        this.cureStress3 = cureStress3;
    }

    public float getCureStress4() {
        return cureStress4;
    }

    public void setCureStress4(float cureStress4) {
        this.cureStress4 = cureStress4;
    }

    public float getCureStress5() {
        return cureStress5;
    }

    public void setCureStress5(float cureStress5) {
        this.cureStress5 = cureStress5;
    }


    public float getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(float respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    public int getTidalVolume() {
        return tidalVolume;
    }

    public void setTidalVolume(int tidalVolume) {
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

    public float getLeakage() {
        return leakage;
    }

    public void setLeakage(float leakage) {
        this.leakage = leakage;
    }

    public float getXhb() {
        return xhb;
    }

    public void setXhb(float xhb) {
        this.xhb = xhb;
    }

    public float getRealFlow1() {
        return realFlow1;
    }

    public void setRealFlow1(float realFlow1) {
        this.realFlow1 = realFlow1;
    }

    public float getRealFlow2() {
        return realFlow2;
    }

    public void setRealFlow2(float realFlow2) {
        this.realFlow2 = realFlow2;
    }

    public float getRealFlow3() {
        return realFlow3;
    }

    public void setRealFlow3(float realFlow3) {
        this.realFlow3 = realFlow3;
    }

    public float getRealFlow4() {
        return realFlow4;
    }

    public void setRealFlow4(float realFlow4) {
        this.realFlow4 = realFlow4;
    }

    public float getRealFlow5() {
        return realFlow5;
    }

    public void setRealFlow5(float realFlow5) {
        this.realFlow5 = realFlow5;
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

}
