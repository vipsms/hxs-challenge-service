package com.eeduspace.challenge.client.service;

import com.eeduspace.challenge.model.request.UserRequestModel;
import com.eeduspace.challenge.responsecode.BaseResponse;
import org.apache.http.client.ClientProtocolException;

import java.io.IOException;

/**
 * 访问UUIMS客户端
 * @author zhuchaowei
 * 2016年5月24日
 * Description
 */
public interface UserClient {
    /**
     * 验证手机号是否已经被注册
     * @param userRequestModel
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public BaseResponse validateByMobile(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * 用户注册
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年5月24日 下午3:27:21
	 * @param userRequestModel
	 * @return
	 * @throws org.apache.http.client.ClientProtocolException
	 * @throws java.io.IOException
	 */
	public BaseResponse userRegister(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * 密码修改
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年5月24日 下午3:27:28
	 * @param userRequestModel
	 * @return
	 * @throws java.io.IOException
	 * @throws org.apache.http.client.ClientProtocolException
	 */
	public BaseResponse updatePassword(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * 用户登录
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年5月24日 下午3:36:44
	 * @param userRequestModel
	 * @return
	 * @throws java.io.IOException
	 * @throws org.apache.http.client.ClientProtocolException
	 */
	public BaseResponse userLogin(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * 找回密码
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年5月25日 上午10:54:34
	 * @param userRequestModel
	 * @return
	 * @throws java.io.IOException
	 * @throws org.apache.http.client.ClientProtocolException
	 */
	public BaseResponse resetPwd(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * 校验手机号
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年5月25日 上午11:52:14
	 * @param userRequestModel
	 * @return
	 * @throws java.io.IOException
	 * @throws org.apache.http.client.ClientProtocolException
	 */
	public BaseResponse checkPhone(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * 用户激活
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年5月25日 下午3:02:10
	 * @param userRequestModel
	 * @return
	 * @throws java.io.IOException
	 * @throws org.apache.http.client.ClientProtocolException
	 */
	public BaseResponse userActivation(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	/**
	 * 修改手机号
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年5月25日 下午5:35:28
	 * @param userRequestModel
	 * @return
	 * @throws java.io.IOException
	 * @throws org.apache.http.client.ClientProtocolException
	 */
	public BaseResponse updateUserInfo(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;

    /**
     * 刷新令牌
     * @param userRequestModel
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
	public BaseResponse refreshToken(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
	
	/**
	 * 根据手机号获取用户信息
	 * 
	 * @author  作者 : gaofengming
			E-mail : gaofengming@e-eduspace.com
	 * @date 创建时间   ：2016年9月1日下午6:32:35  
	 * @param  userRequestModel
	 * @return  BaseResponse
	 */
	public BaseResponse describeByPhone(UserRequestModel userRequestModel) throws ClientProtocolException, IOException;
}
