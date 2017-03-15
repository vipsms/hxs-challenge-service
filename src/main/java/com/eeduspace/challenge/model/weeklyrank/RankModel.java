package com.eeduspace.challenge.model.weeklyrank;

import java.sql.Date;

/**
 * 排行榜model 根据学年分组 
 * @author zhuchaowei
 * 2016年7月14日
 * Description
 */
public class RankModel {
	private Long weekFightValue;
	private String subjectCode;
	private String gradeCode;
	private String headImgUrl;
	private Boolean isVip;
	private String nickName;
	private Date vipEndTime;
	private Boolean overdue;
	private String userCode;
	
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public boolean isOverdue() {
	        return overdue;
	    }
	public void setOverdue(Boolean overdue) {
		this.overdue = overdue;
	}
	public Date getVipEndTime() {
		return vipEndTime;
	}
	public void setVipEndTime(Date vipEndTime) {
		this.vipEndTime = vipEndTime;
	}
	public Long getWeekFightValue() {
		return weekFightValue;
	}
	public void setWeekFightValue(Long weekFightValue) {
		this.weekFightValue = weekFightValue;
	}
	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getGradeCode() {
		return gradeCode;
	}
	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public Boolean getIsVip() {
		return isVip;
	}
	public void setIsVip(Boolean isVip) {
		this.isVip = isVip;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}
