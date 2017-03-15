package com.eeduspace.challenge.model;

import com.eeduspace.challenge.util.CustomDateSerializer;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Date;
import java.util.List;

/**
 * Author: dingran
 * Date: 2016/7/12
 * Description:
 */
public class UserModel {
	/**
	 * 体力值
	 */
	private Integer stamina;
	/**
	 * 排名
	 */
	private List<Long> rank;
    private long friendRank;//好友排名
    private long fightRank;//战斗力排名
    private String token;
    private String refreshToken;
    private String expires;//token的有效时间
    private String userCode;
    private String userName;
    private String mobile;
    private String email;
    private String password;
    private Integer isBlack;
    private Integer userOnlineState;
    private Integer isVip;
    private Date vipStartDate;
    private Date vipEndDate;
    private String registerSource;
    private Long fightValue;
    private Long userPoints;
    private Long first;
    private Long second;
    private Long third;
    private String equipmentType;
    private Integer userInfoDegree;
    private String channelSource;
    private boolean isFriend;
    private Long allFriend;
    private Double userPercent;
    private Double weekStatus;

    public Double getWeekStatus() {
		return weekStatus;
	}

	public void setWeekStatus(Double weekStatus) {
		this.weekStatus = weekStatus;
	}

    public Long getAllFriend() {
		return allFriend;
	}

	public void setAllFriend(Long allFriend) {
		this.allFriend = allFriend;
	}

	public Double getUserPercent() {
		return userPercent;
	}

	public void setUserPercent(Double userPercent) {
		this.userPercent = userPercent;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
    private Date createDate;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date updateDate;

    /*************************用户详细信息************************************/
    private String school;
    private String headImgUrl;
    private Integer sex;
    private String nickName;
    private String challengeNickName;
    private String birthday;
    private String grade;
    private String province;
    private String city;
    private String area;
    private String country;

    //扫码登录所需数据
    private long VIPExpireDays;
    private boolean overdue;//用户会员是否过期  true 已过期  false 未过期
    private boolean challengeOverdue;//用户会员是否过期  true 已过期  false 未过期
    private String scanStatus;
    
    public String getChallengeNickName() {
		return challengeNickName;
	}

	public void setChallengeNickName(String challengeNickName) {
		this.challengeNickName = challengeNickName;
	}

	public boolean isChallengeOverdue() {
		return challengeOverdue;
	}

	public void setChallengeOverdue(boolean challengeOverdue) {
		this.challengeOverdue = challengeOverdue;
	}

	public Integer getStamina() {
		return stamina;
	}

	public void setStamina(Integer stamina) {
		this.stamina = stamina;
	}


    public long getFriendRank() {
        return friendRank;
    }

    public void setFriendRank(long friendRank) {
        this.friendRank = friendRank;
    }

    public long getFightRank() {
        return fightRank;
    }

    public void setFightRank(long fightRank) {
        this.fightRank = fightRank;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsBlack() {
        return isBlack;
    }

    public void setIsBlack(Integer isBlack) {
        this.isBlack = isBlack;
    }

    public Integer getUserOnlineState() {
        return userOnlineState;
    }

    public void setUserOnlineState(Integer userOnlineState) {
        this.userOnlineState = userOnlineState;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public Date getVipStartDate() {
        return vipStartDate;
    }

    public void setVipStartDate(Date vipStartDate) {
        this.vipStartDate = vipStartDate;
    }

    public Date getVipEndDate() {
        return vipEndDate;
    }

    public void setVipEndDate(Date vipEndDate) {
        this.vipEndDate = vipEndDate;
    }

    public String getRegisterSource() {
        return registerSource;
    }

    public void setRegisterSource(String registerSource) {
        this.registerSource = registerSource;
    }


    public Long getFightValue() {
		return fightValue;
	}

	public void setFightValue(Long fightValue) {
		this.fightValue = fightValue;
	}

	public Long getUserPoints() {
		return userPoints;
	}

	public void setUserPoints(Long userPoints) {
		this.userPoints = userPoints;
	}

	public Long getFirst() {
        return first;
    }

    public void setFirst(Long first) {
        this.first = first;
    }

    public Long getSecond() {
        return second;
    }

    public void setSecond(Long second) {
        this.second = second;
    }

    public Long getThird() {
        return third;
    }

    public void setThird(Long third) {
        this.third = third;
    }


    public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}

	public Integer getUserInfoDegree() {
        return userInfoDegree;
    }

    public void setUserInfoDegree(Integer userInfoDegree) {
        this.userInfoDegree = userInfoDegree;
    }

    public String getChannelSource() {
        return channelSource;
    }

    public void setChannelSource(String channelSource) {
        this.channelSource = channelSource;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getVIPExpireDays() {
        return VIPExpireDays;
    }

    public void setVIPExpireDays(long VIPExpireDays) {
        this.VIPExpireDays = VIPExpireDays;
    }

    public boolean isOverdue() {
        return overdue;
    }

    public void setOverdue(boolean overdue) {
        this.overdue = overdue;
    }

    public String getScanStatus() {
        return scanStatus;
    }

    public void setScanStatus(String scanStatus) {
        this.scanStatus = scanStatus;
    }

	public List<Long> getRank() {
		return rank;
	}

	public void setRank(List<Long> rank) {
		this.rank = rank;
	}

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean isFriend) {
        this.isFriend = isFriend;
    }
}
