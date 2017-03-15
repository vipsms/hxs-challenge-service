package com.eeduspace.challenge.model.request;

/**
 * Author: dingran
 * Date: 2016/7/13
 * Description:
 */
public class FriendRequestModel {
    //好友申请UUid
    private String applyUuid;
    //好友申请状态
    private String applyState;
    //我的code
    private String myUserCode;
    //好友code
    private String friendCode;
    //好友申请来源 搜索、对战
    private String applySource;
    //回执好友申请状态
    private Boolean replyState;

    public String getApplyUuid() {
        return applyUuid;
    }

    public void setApplyUuid(String applyUuid) {
        this.applyUuid = applyUuid;
    }

    public String getApplyState() {
        return applyState;
    }

    public void setApplyState(String applyState) {
        this.applyState = applyState;
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

    public String getApplySource() {
        return applySource;
    }

    public void setApplySource(String applySource) {
        this.applySource = applySource;
    }

    public Boolean getReplyState() {
        return replyState;
    }

    public void setReplyState(Boolean replyState) {
        this.replyState = replyState;
    }
}
