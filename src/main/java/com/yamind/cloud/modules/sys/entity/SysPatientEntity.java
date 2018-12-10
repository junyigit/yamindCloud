package com.yamind.cloud.modules.sys.entity;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
public class SysPatientEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 患者id
     */
    private Long id;

    /**
     * 名称
     */
    private String name;


    /**
     * 患者年龄
     */

    private int age;

    /**
     * 身高
     */

    private float height;


    /**
     * 体重
     */

    private float weight;



    /**
     * 理想身高
     */

    private float bmi;

    /**
     * 理想体重
     */

    private float pbw;


    /**
     * 机器序列号
     */

    private String serialNum;


    /**
     * 病历号
     */

    private String medicalRecord;


    /**
     * 诊断
     */
    private String diagnose;



    /**
     * 医生姓名
     */
    private String doctor;


    /**
     * 身份证
     */
    private String card;

    /**
     * 家庭住址
     */
    private String homeAddres;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public float getPbw() {
        return pbw;
    }

    public void setPbw(float pbw) {
        this.pbw = pbw;
    }

    public String getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(String medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getHomeAddres() {
        return homeAddres;
    }

    public void setHomeAddres(String homeAddres) {
        this.homeAddres = homeAddres;
    }


    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

}
