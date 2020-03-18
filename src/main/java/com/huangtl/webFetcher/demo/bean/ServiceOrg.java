package com.huangtl.webFetcher.demo.bean;

import java.util.List;

public class ServiceOrg {
    private String name;

//    @ApiModelProperty(value = "机构类型")
    private String typeParent;

//    @ApiModelProperty(value = "养老机构")
    private String typeOrg;

//    @ApiModelProperty(value = "性质")
    private String nature;

//    @ApiModelProperty(value = "成立时间")
    private String establishDate;

//    @ApiModelProperty(value = "占地面积")
    private String areaSize;

//    @ApiModelProperty(value = "床位数量")
    private String bedNum;

//    @ApiModelProperty(value = "员工数")
    private String staffNum;

//    @ApiModelProperty(value = "收住对象")
    private String objectType;

//    @ApiModelProperty(value = "最小价格")
    private String minPrice;

//    @ApiModelProperty(value = "最大价格")
    private String maxPrice;

//    @ApiModelProperty(value = "省份")
    private String province;

//    @ApiModelProperty(value = "城市")
    private String city;

//    @ApiModelProperty(value = "区县")
    private String county;

//    @ApiModelProperty(value = "街道乡镇")
    private String street;

//    @ApiModelProperty(value = "联系电话")
    private String phone;

    private String contact;//联系人
    private String email;//邮箱
    private String fixdPhone;//固话
    private String postalCode;//邮政编码

//    @ApiModelProperty(value = "地址")
    private String address;

//    @ApiModelProperty(value = "环境设施")
    private String facilities;

//    @ApiModelProperty(value = "服务内容")
    private String serviceContent;

//    @ApiModelProperty(value = "膳食介绍")
    private String eatContent;

//    @ApiModelProperty(value = "收费标准")
    private String priceContent;

//    @ApiModelProperty(value = "入住须知")
    private String liveContent;

//    @ApiModelProperty(value = "机构介绍")
    private String orgContent;

//    @ApiModelProperty(value = "主图")
    private String mainPic;

    private List<String> imageList;//图集

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeParent() {
        return typeParent;
    }

    public void setTypeParent(String typeParent) {
        this.typeParent = typeParent;
    }

    public String getTypeOrg() {
        return typeOrg;
    }

    public void setTypeOrg(String typeOrg) {
        this.typeOrg = typeOrg;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(String establishDate) {
        this.establishDate = establishDate;
    }

    public String getAreaSize() {
        return areaSize;
    }

    public void setAreaSize(String areaSize) {
        this.areaSize = areaSize;
    }

    public String getBedNum() {
        return bedNum;
    }

    public void setBedNum(String bedNum) {
        this.bedNum = bedNum;
    }

    public String getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(String staffNum) {
        this.staffNum = staffNum;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(String minPrice) {
        this.minPrice = minPrice;
    }

    public String getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(String maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFacilities() {
        return facilities;
    }

    public void setFacilities(String facilities) {
        this.facilities = facilities;
    }

    public String getServiceContent() {
        return serviceContent;
    }

    public void setServiceContent(String serviceContent) {
        this.serviceContent = serviceContent;
    }

    public String getEatContent() {
        return eatContent;
    }

    public void setEatContent(String eatContent) {
        this.eatContent = eatContent;
    }

    public String getPriceContent() {
        return priceContent;
    }

    public void setPriceContent(String priceContent) {
        this.priceContent = priceContent;
    }

    public String getLiveContent() {
        return liveContent;
    }

    public void setLiveContent(String liveContent) {
        this.liveContent = liveContent;
    }

    public String getOrgContent() {
        return orgContent;
    }

    public void setOrgContent(String orgContent) {
        this.orgContent = orgContent;
    }

    public String getMainPic() {
        return mainPic;
    }

    public void setMainPic(String mainPic) {
        this.mainPic = mainPic;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFixdPhone() {
        return fixdPhone;
    }

    public void setFixdPhone(String fixdPhone) {
        this.fixdPhone = fixdPhone;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public List<String> getImageList() {
        return imageList;
    }

    public void setImageList(List<String> imageList) {
        this.imageList = imageList;
    }

    @Override
    public String toString() {
        return "ServiceOrg{" +
                "name='" + name + '\'' +
                ", typeParent='" + typeParent + '\'' +
                ", typeOrg='" + typeOrg + '\'' +
                ", nature='" + nature + '\'' +
                ", establishDate='" + establishDate + '\'' +
                ", areaSize='" + areaSize + '\'' +
                ", bedNum='" + bedNum + '\'' +
                ", staffNum='" + staffNum + '\'' +
                ", objectType='" + objectType + '\'' +
                ", minPrice='" + minPrice + '\'' +
                ", maxPrice='" + maxPrice + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", street='" + street + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", facilities='" + facilities + '\'' +
                ", serviceContent='" + serviceContent + '\'' +
                ", eatContent='" + eatContent + '\'' +
                ", priceContent='" + priceContent + '\'' +
                ", liveContent='" + liveContent + '\'' +
                ", orgContent='" + orgContent + '\'' +
                ", mainPic='" + mainPic + '\'' +
                '}';
    }
}
