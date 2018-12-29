package com.huangtl.weixin.entity;

import java.util.Date;

/**   
 * 微信用户信息
 *
 */
@SuppressWarnings("serial")
public class HfWeixinUserInfoEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**微信唯一标识*/
	private String unionid;
	/**微信公众号openid*/
	private String openid;
	/**创建日期*/
	private Date createDate;
	/**微信昵称*/
	private String nickName;
	/**语言*/
	private String language;
	/**性别*/
	private Integer gender;
	/**国家*/
	private String country;
	/**省份*/
	private String province;
	/**城市*/
	private String city;
	/**头像路径*/
	private String avatarUrl;
	
	public String getId(){
		return this.id;
	}

	public void setId(String id){
		this.id = id;
	}
	public String getUnionid(){
		return this.unionid;
	}

	public void setUnionid(String unionid){
		this.unionid = unionid;
	}
	public Date getCreateDate(){
		return this.createDate;
	}
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	public String getNickName(){
		return this.nickName;
	}

	public void setNickName(String nickName){
		this.nickName = nickName;
	}
	public String getLanguage(){
		return this.language;
	}

	public void setLanguage(String language){
		this.language = language;
	}
	public Integer getGender(){
		return this.gender;
	}
	public void setGender(Integer gender){
		this.gender = gender;
	}
	public String getCountry(){
		return this.country;
	}
	public void setCountry(String country){
		this.country = country;
	}
	public String getProvince(){
		return this.province;
	}

	public void setProvince(String province){
		this.province = province;
	}
	public String getCity(){
		return this.city;
	}

	public void setCity(String city){
		this.city = city;
	}
	public String getAvatarUrl(){
		return this.avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl){
		this.avatarUrl = avatarUrl;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
}
