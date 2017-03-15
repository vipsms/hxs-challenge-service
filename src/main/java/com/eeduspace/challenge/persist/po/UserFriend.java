package com.eeduspace.challenge.persist.po;

import java.util.Date;

/**
 *Author: dingran
 * Date: 2016/7/13
 * 用户好友关系表
 */
public class UserFriend {
    private Long id;
    //我的code
    private String myUserCode;
    //好友code
    private String friendCode;

    private Date createDate;

    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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