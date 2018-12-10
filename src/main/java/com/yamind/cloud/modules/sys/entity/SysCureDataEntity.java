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
     * 最小压力
     */
    private float minStress;


    /**
     * 最大压力
     */
    private float maxStress;



    /**
     * 分钟通气量
     */

    private float minusTroughput;

    /**
     * 最大吸气压力
     */
    private float maxInhaleStress;


    /**
     * 吸气压力
     */
    private float inhaleStress;

    /**
     * 呼气压力
     */
    private float exhaleStress;

    /**
     * 呼吸频率
     */
    private float respiratoryRate;



    /**
     * 潮气量
     */
    private float tidalVolume;


    /**
     * 吸气时间
     */
    private float inhaleTime;


    /**
     * 呼气释放
     */
    private int exhaleRelease;

    /**
     * 吸气灵敏度
     */
    private int inhaleSensitivity;

    /**
     * 呼气灵敏度
     */
    private int exhaleSensitivity;



    /**
     * 呼吸暂停次数
     */

    private float aiCount;

    /**
     * 低通气指数
     */

    private float hiCount;

    /**
     * 压力上升坡度
     */
    private int stressUp;

    /**
     * 压力下降坡度
     */
    private int stressDown;

    /**
     * avaps
     */
    private int avaps;



    /**
     * 呼吸暂停时间
     */

    private int continueTime;

    public int getContinueTime() {
        return continueTime;
    }

    public void setContinueTime(int continueTime) {
        this.continueTime = continueTime;
    }



    /**
     * 软件版本号
     */
    private String softVersion;


    public float getRealFlow() {
        return realFlow;
    }

    public void setRealFlow(float realFlow) {
        this.realFlow = realFlow;
    }

    public String getBootSerial() {
        return bootSerial;
    }

    public void setBootSerial(String bootSerial) {
        this.bootSerial = bootSerial;
    }

    public float getInhaleTime() {
        return inhaleTime;
    }

    public float getMinusTroughput() {
        return minusTroughput;
    }

    public void setMinusTroughput(float minusTroughput) {
        this.minusTroughput = minusTroughput;
    }

    public void setInhaleTime(float inhaleTime) {
        this.inhaleTime = inhaleTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public float getCureStress() {
        return cureStress;
    }

    public void setCureStress(float cureStress) {
        this.cureStress = cureStress;
    }

    public float getMinStress() {
        return minStress;
    }

    public void setMinStress(float minStress) {
        this.minStress = minStress;
    }

    public float getMaxStress() {
        return maxStress;
    }

    public void setMaxStress(float maxStress) {
        this.maxStress = maxStress;
    }

    public float getMaxInhaleStress() {
        return maxInhaleStress;
    }

    public void setMaxInhaleStress(float maxInhaleStress) {
        this.maxInhaleStress = maxInhaleStress;
    }

    public float getInhaleStress() {
        return inhaleStress;
    }

    public void setInhaleStress(float inhaleStress) {
        this.inhaleStress = inhaleStress;
    }

    public float getExhaleStress() {
        return exhaleStress;
    }

    public void setExhaleStress(float exhaleStress) {
        this.exhaleStress = exhaleStress;
    }

    public float getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(float respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    public int getExhaleRelease() {
        return exhaleRelease;
    }

    public void setExhaleRelease(int exhaleRelease) {
        this.exhaleRelease = exhaleRelease;
    }

    public int getInhaleSensitivity() {
        return inhaleSensitivity;
    }

    public void setInhaleSensitivity(int inhaleSensitivity) {
        this.inhaleSensitivity = inhaleSensitivity;
    }

    public int getExhaleSensitivity() {
        return exhaleSensitivity;
    }

    public void setExhaleSensitivity(int exhaleSensitivity) {
        this.exhaleSensitivity = exhaleSensitivity;
    }

    public int getStressUp() {
        return stressUp;
    }

    public void setStressUp(int stressUp) {
        this.stressUp = stressUp;
    }

    public int getStressDown() {
        return stressDown;
    }

    public void setStressDown(int stressDown) {
        this.stressDown = stressDown;
    }

    public int getAvaps() {
        return avaps;
    }

    public void setAvaps(int avaps) {
        this.avaps = avaps;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
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

    public float getTidalVolume() {
        return tidalVolume;
    }

    public void setTidalVolume(float tidalVolume) {
        this.tidalVolume = tidalVolume;
    }



}
