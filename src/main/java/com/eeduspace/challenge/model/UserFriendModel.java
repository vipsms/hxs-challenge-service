package com.eeduspace.challenge.model;

/**
 * Author: dingran
 * Date: 2016/7/21
 * Description:
 */
public class UserFriendModel {
    private String type;//类型： 好友申请（apply）、回执添加好友请求(reply)
    private String result;//回执结果
    private String friendCode;
    private String friendNickName;
    private String friendHeadImgUrl;
    private String applyUuid;
    private boolean isFriend;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFriendCode() {
        return friendCode;
    }

    public void setFriendCode(String friendCode) {
        this.friendCode = friendCode;
    }

    public String getFriendNickName() {
        return friendNickName;
    }

    public void setFriendNickName(String friendNickName) {
        this.friendNickName = friendNickName;
    }

    public String getFriendHeadImgUrl() {
        return friendHeadImgUrl;
    }

    public void setFriendHeadImgUrl(String friendHeadImgUrl) {
        this.friendHeadImgUrl = friendHeadImgUrl;
    }

    public String getApplyUuid() {
        return applyUuid;
    }

    public void setApplyUuid(String applyUuid) {
        this.applyUuid = applyUuid;
    }

    public boolean isFriend() {
        return isFriend;
    }

    public void setFriend(boolean isFriend) {
        this.isFriend = isFriend;
    }
}
