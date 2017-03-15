package com.eeduspace.challenge.persist.po;

import java.util.Date;
/**
 * 周榜定榜详情 
 *
 */
public class WeeklyRanked {
    private Long id;

    private String uuid;
    //周获取战斗力
    private Long weekFight;
    //周排名
    private Double weekRank;
    //用户标识
    private String userCode;
    //昵称
    private String nickName;
    //周获取积分
    private Long weekIntegral;
    //周信息榜uuid
    private String weeklyChartUuid;

    private Date createDate;

    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }



	public Double getWeekRank() {
        return weekRank;
    }

    public void setWeekRank(Double weekRank) {
        this.weekRank = weekRank;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public Long getWeekFight() {
		return weekFight;
	}

	public void setWeekFight(Long weekFight) {
		this.weekFight = weekFight;
	}

	public Long getWeekIntegral() {
		return weekIntegral;
	}

	public void setWeekIntegral(Long weekIntegral) {
		this.weekIntegral = weekIntegral;
	}

	public String getWeeklyChartUuid() {
        return weeklyChartUuid;
    }

    public void setWeeklyChartUuid(String weeklyChartUuid) {
        this.weeklyChartUuid = weeklyChartUuid;
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
}