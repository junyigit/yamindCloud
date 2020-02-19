package com.yamind.cloud.modules.app.entity;

import java.io.Serializable;

public class DeviceOtaEntity implements Serializable {


    Long id;

    Integer productType;

    String version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
