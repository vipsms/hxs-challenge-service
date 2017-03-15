package com.eeduspace.challenge.client.model.response;

import java.util.List;

import com.eeduspace.challenge.model.paper.QuestionEntity;

public class QuestionResponseModel {
	private List<QuestionEntity> datas;

	public List<QuestionEntity> getDatas() {
		return datas;
	}

	public void setDatas(List<QuestionEntity> datas) {
		this.datas = datas;
	}
}
