package com.eeduspace.challenge.model;

import java.util.Date;

/**
 *分享
 */
public class ShareModel {
	   private Long id;

		private Date creatDate;

		private String uuid;

		private String eventType;//事件类型：对战挑战

		private String shareType;//分享类型：QQ,朋友圈

		private String equipmentsource;//IOS Andrid

		private String userCode;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Date getCreatDate() {
			return creatDate;
		}

		public void setCreatDate(Date creatDate) {
			this.creatDate = creatDate;
		}

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}

		public String getEventType() {
			return eventType;
		}

		public void setEventType(String eventType) {
			this.eventType = eventType;
		}

		public String getShareType() {
			return shareType;
		}

		public void setShareType(String shareType) {
			this.shareType = shareType;
		}

		public String getEquipmentsource() {
			return equipmentsource;
		}

		public void setEquipmentsource(String equipmentsource) {
			this.equipmentsource = equipmentsource;
		}

		public String getUserCode() {
			return userCode;
		}

		public void setUserCode(String userCode) {
			this.userCode = userCode;
		}

	
    
}
