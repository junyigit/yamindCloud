package com.yamind.cloud.modules.sys.entity;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;

public class SysParamaterSetEntity implements Serializable {


    private long id;

    //序列号
    private String serialId;

    //日期(只保留7天的设置信息)
    private String useDate;

    //机器型号
    private String bootType;

    //模式
    private String mode;

    //治疗压力
    private Float cureStress;

    //开始压力
    private Float startStress;

    //延迟时间
    private Integer  delayTime;

    //呼气释放
    private Integer exhaleRelease;

    //最大吸气压力
    private Float maxInhaleStress;

    //最小吸气压力
    private Float minInhaleStress;

    //最小压力
    private Float minStress;

    //最大压力
    private Float maxStress;

    //吸气压力
    private Float inhaleStress;

    //目标潮气量
    private int tidalVolume;

    //呼气压力
    private Float exhaleStress;

    //吸气灵敏度
    private Integer inhaleSensitivity;

    //呼气灵敏度
    private Integer exhaleSensitivity;

    //压力上升坡度
    private Integer stressUp;

    //压力下降坡度  删除掉
   // private Integer stressDown;

    //软件版本号
    private String softVersion;

    private Integer avaps;

    //最大吸气时间
    private Float maxInhaleTime;

    //最小吸气时间
    private Float minInhaleTime;

    //智能启动
    private Integer aiStart;

    //呼吸频率
    private Float respiratoryRate;

    //吸气时间
    private Float inhaleTime;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSerialId() {
        return serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public String getUseDate() {
        return useDate;
    }

    public void setUseDate(String useDate) {
        this.useDate = useDate;
    }

    public String getBootType() {
        return bootType;
    }

    public void setBootType(String bootType) {
        this.bootType = bootType;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Float getCureStress() {
        return cureStress;
    }

    public void setCureStress(Float cureStress) {
        this.cureStress = cureStress;
    }

    public Float getStartStress() {
        return startStress;
    }

    public void setStartStress(Float startStress) {
        this.startStress = startStress;
    }

    public Integer getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(Integer delayTime) {
        this.delayTime = delayTime;
    }

    public Integer getExhaleRelease() {
        return exhaleRelease;
    }

    public void setExhaleRelease(Integer exhaleRelease) {
        this.exhaleRelease = exhaleRelease;
    }

    public Float getMaxInhaleStress() {
        return maxInhaleStress;
    }

    public void setMaxInhaleStress(Float maxInhaleStress) {
        this.maxInhaleStress = maxInhaleStress;
    }

    public Float getMinInhaleStress() {
        return minInhaleStress;
    }

    public void setMinInhaleStress(Float minInhaleStress) {
        this.minInhaleStress = minInhaleStress;
    }

    public Float getMinStress() {
        return minStress;
    }

    public void setMinStress(Float minStress) {
        this.minStress = minStress;
    }

    public Float getMaxStress() {
        return maxStress;
    }

    public void setMaxStress(Float maxStress) {
        this.maxStress = maxStress;
    }

    public Float getInhaleStress() {
        return inhaleStress;
    }

    public void setInhaleStress(Float inhaleStress) {
        this.inhaleStress = inhaleStress;
    }

    public int getTidalVolume() {
        return tidalVolume;
    }

    public void setTidalVolume(int tidalVolume) {
        this.tidalVolume = tidalVolume;
    }

    public Float getExhaleStress() {
        return exhaleStress;
    }

    public void setExhaleStress(Float exhaleStress) {
        this.exhaleStress = exhaleStress;
    }

    public Integer getInhaleSensitivity() {
        return inhaleSensitivity;
    }

    public void setInhaleSensitivity(Integer inhaleSensitivity) {
        this.inhaleSensitivity = inhaleSensitivity;
    }

    public Integer getExhaleSensitivity() {
        return exhaleSensitivity;
    }

    public void setExhaleSensitivity(Integer exhaleSensitivity) {
        this.exhaleSensitivity = exhaleSensitivity;
    }

    public Integer getStressUp() {
        return stressUp;
    }

    public void setStressUp(Integer stressUp) {
        this.stressUp = stressUp;
    }

    public Integer getAvaps() {
        return avaps;
    }

    public void setAvaps(Integer avaps) {
        this.avaps = avaps;
    }

    public Float getMaxInhaleTime() {
        return maxInhaleTime;
    }

    public void setMaxInhaleTime(Float maxInhaleTime) {
        this.maxInhaleTime = maxInhaleTime;
    }

    public Float getMinInhaleTime() {
        return minInhaleTime;
    }

    public void setMinInhaleTime(Float minInhaleTime) {
        this.minInhaleTime = minInhaleTime;
    }

    public Integer getAiStart() {
        return aiStart;
    }

    public void setAiStart(Integer aiStart) {
        this.aiStart = aiStart;
    }

    public Float getRespiratoryRate() {
        return respiratoryRate;
    }

    public void setRespiratoryRate(Float respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    public Float getInhaleTime() {
        return inhaleTime;
    }

    public void setInhaleTime(Float inhaleTime) {
        this.inhaleTime = inhaleTime;
    }

    public String getSoftVersion() {
        return softVersion;
    }

    public void setSoftVersion(String softVersion) {
        this.softVersion = softVersion;
    }




}
