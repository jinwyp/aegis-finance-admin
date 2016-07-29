package com.yimei.domain.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by xiangyang on 16/5/16.
 */

public class BankSite implements Serializable {
    private Integer id;
    private String provinceName;
    private Integer provinceCode;
    private String cityName;
    private Integer cityCode;
    private Integer bankCode;
    private String bankName;
    private Long childBankCode;
    private String childBankName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(Integer provinceCode) {
        this.provinceCode = provinceCode;
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Integer getBankCode() {
        return bankCode;
    }

    public void setBankCode(Integer bankCode) {
        this.bankCode = bankCode;
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Long getChildBankCode() {
        return childBankCode;
    }

    public void setChildBankCode(Long childBankCode) {
        this.childBankCode = childBankCode;
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getChildBankName() {
        return childBankName;
    }

    public void setChildBankName(String childBankName) {
        this.childBankName = childBankName;
    }
}
