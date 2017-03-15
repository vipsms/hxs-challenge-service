package com.eeduspace.challenge.client.service.impl;


import com.eeduspace.challenge.client.service.UserClient;
import com.eeduspace.challenge.model.request.UserRequestModel;
import com.eeduspace.challenge.responsecode.BaseResponse;
import com.eeduspace.uuims.comm.util.HTTPClientUtils;
import com.eeduspace.uuims.comm.util.base.encrypt.Digest;
import com.google.gson.Gson;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class UserClientImpl implements UserClient {
	private final Logger logger = LoggerFactory.getLogger(UserClient.class);
	private Gson gson=new Gson();
	@Value("${iwrong.uuims.server.url}")
	private String serverUrl;
	
	@Value("${iwrong.accessKey}")
	private String accessKey;
	@Value("${iwrong.secretKey}")
	private String secretKey;
	@Value("${iwrong.productType}")
	private String productType;

    @Override
    public BaseResponse validateByMobile(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
        userRequestModel.setType("phone");
        userRequestModel.setPhone(userRequestModel.getMobile());
        String action="validate";
        String timestamp=System.currentTimeMillis()+"";
        String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
        String signature="";
        try {
            signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String urlInfo="user?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("validate request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("validate reponse:{}",responseString);
        BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

        return baseResponse;
    }

    @Override
	public BaseResponse userRegister(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
        userRequestModel.setProductType(productType);
        String action="create";
		String timestamp=System.currentTimeMillis()+"";
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("register request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("register reponse:{}",responseString);
        BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);
        //如果用户已经存在则进行激活操作
        if(baseResponse!=null && "Resource.Duplicate".equals(baseResponse.getCode())){
            baseResponse = this.userActivation(userRequestModel);
        }
        return baseResponse;
	}
	@Override
	public BaseResponse updatePassword(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		userRequestModel.setOldPassword(userRequestModel.getOldPassword());
		userRequestModel.setPassword(userRequestModel.getPassword());
		String action="edit_password";
		String timestamp=System.currentTimeMillis()+"";
		String userAccessKey=userRequestModel.getUserAccessKey();
		String token=userRequestModel.getToken();
		String userSecretKey=userRequestModel.getUserSecretKey();
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((userAccessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), userSecretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+userAccessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature+"&token="+token;
        String url=serverUrl.concat(urlInfo);
        logger.debug("updatePwd request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("updatePwd reponse:{}",responseString);
        BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

        return baseResponse;
	}
	@Override
	public BaseResponse userLogin(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		userRequestModel.setProductType(productType);
		if(userRequestModel.getEquipmentType()==null||userRequestModel.getEquipmentType().equals("")){
			userRequestModel.setEquipmentType("Web");
		}
		String action="login";
		String timestamp=System.currentTimeMillis()+"";
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String urlInfo="login?action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5;
	    String url=serverUrl.concat(urlInfo);
        logger.debug("userLogin request:{}",url);
	    String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("userLogin reponse:{}",responseString);
	    BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

        return baseResponse;
	}
	@Override
	public BaseResponse resetPwd(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		String action="reset_password";
		String timestamp=System.currentTimeMillis()+"";
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("resetPwd request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("resetPwd reponse:{}",responseString);
	    BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

		return baseResponse;
	}
	@Override
	public BaseResponse checkPhone(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		String action="validate";
		String timestamp=System.currentTimeMillis()+"";
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("user checkPhone request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("user checkPhone reponse:{}",responseString);
	    BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

		return baseResponse;
	}
	@Override
	public BaseResponse userActivation(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		String action="activation";
		String timestamp=System.currentTimeMillis()+"";
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("user activation  request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("user activation  reponse:{}",responseString);
	    BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

		return baseResponse;
	}
	@Override
	public BaseResponse updateUserInfo(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		String action="update";
		String timestamp=System.currentTimeMillis()+"";
		String userAccessKey=userRequestModel.getUserAccessKey();
		String token=userRequestModel.getToken();
		String userSecretKey=userRequestModel.getUserSecretKey();
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((userAccessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), userSecretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+userAccessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature+"&token="+token;
        String url=serverUrl.concat(urlInfo);
        logger.debug("updateUserInfo request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("updateUserInfo reponse:{}",responseString);
        BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

        return baseResponse;
	}

    @Override
    public BaseResponse refreshToken(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
        String action="refresh_token";
        String timestamp=System.currentTimeMillis()+"";
        logger.debug("request body:{}",gson.toJson(userRequestModel));
        String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));

        String urlInfo="token?&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5;
        String url=serverUrl.concat(urlInfo);
        logger.debug("updateUserInfo request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("updateUserInfo reponse:{}",responseString);
        BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

        return baseResponse;
    }

	@Override
	public BaseResponse describeByPhone(UserRequestModel userRequestModel) throws ClientProtocolException, IOException {
		String action="describe_by_phone";
		String timestamp=System.currentTimeMillis()+"";
		logger.debug("request body:{}",gson.toJson(userRequestModel));
		String bodyMD5=Digest.md5Digest(gson.toJson(userRequestModel));
		String signature="";
        try {
			signature = Digest.getSignature((accessKey+"\n"+action+"\n"+timestamp+"\n"+ bodyMD5).getBytes(), secretKey.getBytes());
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        String urlInfo="user?accessKey="+accessKey+"&action="+action+"&timestamp="+timestamp+"&bodyMD5="+bodyMD5+"&signature="+signature;
        String url=serverUrl.concat(urlInfo);
        logger.debug("describeByPhone request:{}",url);
        String responseString= HTTPClientUtils.httpPostRequestJson(url, gson.toJson(userRequestModel));
        logger.debug("describeByPhone reponse:{}",responseString);
	    BaseResponse baseResponse=gson.fromJson(responseString, BaseResponse.class);

		return baseResponse;
	}

}
