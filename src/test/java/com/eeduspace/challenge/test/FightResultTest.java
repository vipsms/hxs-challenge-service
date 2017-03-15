package com.eeduspace.challenge.test;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.eeduspace.challenge.client.model.response.QuestionResponseModel;
import com.eeduspace.challenge.client.service.BaseResourceClient;
import com.eeduspace.challenge.model.BaseResourceModel;
import com.eeduspace.challenge.model.paper.QuestionEntity;
import com.eeduspace.challenge.persist.po.FightResult;
import com.eeduspace.challenge.responsecode.BaseResponse;
import com.eeduspace.challenge.service.FightResultService;
import com.eeduspace.uuims.comm.util.base.json.GsonUtil;
import com.google.gson.Gson;

@ContextConfiguration(locations = {"classpath*:*/**/spring-*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class FightResultTest {
	 private Gson gson=new Gson();
    private final Logger logger = LoggerFactory.getLogger(FightResultTest.class);
	@Inject
	private FightResultService fightResultServiceImpl;
	@Inject
	private BaseResourceClient baseResourceClient;
	@Test
	public void testSave(){
		Random r=new Random();
		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < 50; i++) {
				FightResult fightResult=new FightResult();
				fightResult.setPaperUuid("123123"+j);
				fightResult.setGetScore(Double.valueOf(r.nextInt(100)));
				fightResult.setUserCode("us"+i);
//				fightResultServiceImpl.save(fightResult);
			}
		}
	}
	@Test
	public void getPaper(){
		BaseResourceModel baseResourceModel=new BaseResourceModel();
		try {
			baseResourceModel.setBookTypeCode("ddc14b2e6ab2459f9d209815d6bb6c9c");
			baseResourceModel.setGradeCode("23");
			baseResourceModel.setStageCode("2");
			baseResourceModel.setSubjectCode("5");
			QuestionResponseModel questionResponseModel=baseResourceClient.getPapper(baseResourceModel);
			System.out.println("================================");
			System.out.println(gson.toJson(questionResponseModel));
			//QuestionResponseModel questionResponseModel=GsonUtil.fromObjectJson(gson.toJson(baseResponse), "result", "datas", QuestionResponseModel.class);
			System.out.println("-------------------------------------");
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testFindUserHighestPaper(){
		Gson gson=new Gson();
	}
	/**
	 * 获取试卷
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月16日 下午2:48:24
	 */
	@Test
	public void testGetHighestPaper(){
		//Gson gson=new Gson();
		String userCode="us3";
		String subjectCode="17";
		String gradeCode="112";
		List<FightResult> list= fightResultServiceImpl.findUserHighestPaper(userCode, subjectCode, gradeCode);
		System.out.println(gson.toJson(list));
	}
	
	/**
	 * 计算挑战结果
	 * Author： zhuchaowei
	 * e-mail:zhuchaowei@e-eduspace.com
	 * 2016年7月16日 下午3:37:11
	 */
	@Test
	public void computeChallengeFightResult(){
		//fightResultServiceImpl.computeChallengeFightResult("123123", "5ed0308d86df4d06a3d8fa2da27518d7", "82560cf51e48492a996b69b0bd6287c0", "us3", "us4", "us444", "88", 100l);
	}
}

