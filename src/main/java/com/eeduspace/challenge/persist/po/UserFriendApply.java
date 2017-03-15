package com.eeduspace.challenge.persist.po;

import java.util.Date;

/**
 * Author: dingran
 * Date: 2016/7/13
 * Description:用户申请添加好友表
 */
public class UserFriendApply {

    private Long id;

    private String uuid;
    //我的code
    private String myUserCode;
    //好友code
    private String friendCode;
    //状态：已发送消息、未发送消息、已同意、未同意
    private String applyState;
    //申请来源 ：搜索、对战、挑战
    private String applySource;

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

    public String getMyUserCode() {
        return myUserCode;
    }

    public void setMyUserCode(String myUserCode) {
        this.myUserCode = myUserCode;
    }

    public String getFriendCode() {
        return friendCode;
    }

    public void setFriendCode(String friendCode) {
        this.friendCode = friendCode;
    }

    public String getApplyState() {
        return applyState;
    }

    public void setApplyState(String applyState) {
        this.applyState = applyState;
    }

    public String getApplySource() {
        return applySource;
    }

    public void setApplySource(String applySource) {
        this.applySource = applySource;
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
