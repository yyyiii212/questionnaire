package com.example.questionnaire.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.questionnaire.constants.QuestionnaireFrontDeskRtnCode;
import com.example.questionnaire.entity.QAndA;
import com.example.questionnaire.entity.QuestionnaireFrontDesk;
import com.example.questionnaire.service.ifs.QuestionnaireFrontDeskService;
import com.example.questionnaire.vo.QAndAReq;
import com.example.questionnaire.vo.QuestionnaireFrontDeskReq;
import com.example.questionnaire.vo.QuestionnaireFrontDeskRes;

@RestController
public class QuestionnaireFrontDeskController {

	@Autowired
	private QuestionnaireFrontDeskService questionnaireFrontDeskService;
	
	//檢查問卷標題與描述內容
	private QuestionnaireFrontDeskRes checkQuestionnaire(@RequestBody QuestionnaireFrontDeskReq req) {
		if (!StringUtils.hasText(req.getTitle())) {
			return new QuestionnaireFrontDeskRes(QuestionnaireFrontDeskRtnCode.TITLE.getMessage());
		} else if (!StringUtils.hasText(req.getDescription())) {
			return new QuestionnaireFrontDeskRes(QuestionnaireFrontDeskRtnCode.DESCRIPTION.getMessage());
		}
		return null;
	}
	
//	//檢查
//	private QuestionnaireFrontDeskRes checkQuestionnaire(@RequestBody QuestionnaireFrontDeskReq req) {
//		if (!StringUtils.hasText(req.getTitle())) {
//			return new QuestionnaireFrontDeskRes(QuestionnaireFrontDeskRtnCode.TITLE.getMessage());
//		} else if (!StringUtils.hasText(req.getDescription())) {
//			return new QuestionnaireFrontDeskRes(QuestionnaireFrontDeskRtnCode.DESCRIPTION.getMessage());
//		}
//		return null;
//	}
	
	//新增問卷
	@PostMapping(value = "/api/create_questionnaire_title")
	public QuestionnaireFrontDeskRes createQuestionnaireTitle(@RequestBody QuestionnaireFrontDeskReq req) {
		
		QuestionnaireFrontDeskRes check = checkQuestionnaire(req);
		if (check != null) {
			return check;
		}
		QuestionnaireFrontDesk questionnaireFrontDesk = questionnaireFrontDeskService.createQuestionnaireTitle(req);
		if(questionnaireFrontDesk == null) {
			return new QuestionnaireFrontDeskRes(QuestionnaireFrontDeskRtnCode.ERROR.getMessage());
		}
		return new QuestionnaireFrontDeskRes(questionnaireFrontDesk, QuestionnaireFrontDeskRtnCode.SUCCESS.getMessage());
	}
	
	//新增問卷(問題,答案,單選多選,是否必填
	@PostMapping(value = "/api/create_questionnaire_qAndA")
	public QuestionnaireFrontDeskRes createQuestionnaireQAndA(@RequestBody QAndAReq req) {
		
		QuestionnaireFrontDeskRes res = questionnaireFrontDeskService.createQuestionnaireQAndA(req);
		if(res == null) {
			return new QuestionnaireFrontDeskRes(QuestionnaireFrontDeskRtnCode.ERROR.getMessage());
		}
		return new QuestionnaireFrontDeskRes(QuestionnaireFrontDeskRtnCode.SUCCESS.getMessage());
	}
	
	//修改問卷
	@PostMapping(value = "/api/update_questionnaire_title")
	public QuestionnaireFrontDeskRes updateQuestionnaireTitle(@RequestBody QuestionnaireFrontDeskReq req) {
		
		QuestionnaireFrontDeskRes check = checkQuestionnaire(req);
		if (check != null) {
			return check;
		}
		QuestionnaireFrontDesk questionnaireFrontDesk = questionnaireFrontDeskService.updateQuestionnaireTitle(req);
		if(questionnaireFrontDesk == null) {
			return new QuestionnaireFrontDeskRes(QuestionnaireFrontDeskRtnCode.ERROR.getMessage());
		}
		return new QuestionnaireFrontDeskRes(QuestionnaireFrontDeskRtnCode.SUCCESS.getMessage());
	}
	
	//修改問卷(問題,答案,單選多選,是否必填
	public QAndA updateQuestionnaireQAndA(@RequestBody QAndAReq req) {
		return null;
	}
	
	//刪除問卷(包含問題
	@PostMapping(value = "/api/delete_questionnaire_title")
	public QuestionnaireFrontDeskRes deleteQuestionnaireTitle(@RequestBody QuestionnaireFrontDeskReq req) {
		
		QuestionnaireFrontDeskRes res = questionnaireFrontDeskService.deleteQuestionnaireTitle(req);
		if(res == null) {
			return new QuestionnaireFrontDeskRes(QuestionnaireFrontDeskRtnCode.ID_IS_NOT_FOUND.getMessage());
		}
		return new QuestionnaireFrontDeskRes(QuestionnaireFrontDeskRtnCode.SUCCESS.getMessage());
	}
	
	//列出全部問卷
	@PostMapping(value = "/api/search_all")
	public QuestionnaireFrontDeskRes searchAll(@RequestBody QuestionnaireFrontDeskReq req) {
		
		List<QuestionnaireFrontDesk> questionnaireFrontDeskList = questionnaireFrontDeskService.searchAll(req);
		if(questionnaireFrontDeskList == null) {
			return new QuestionnaireFrontDeskRes(QuestionnaireFrontDeskRtnCode.ERROR.getMessage());
		}
				
		return new QuestionnaireFrontDeskRes(QuestionnaireFrontDeskRtnCode.SUCCESS.getMessage(), questionnaireFrontDeskList);
	}
	
	//
	@PostMapping(value = "/api/search_questionnaire_title")
	public QuestionnaireFrontDeskRes searchQuestionnaireTitle(@RequestBody QuestionnaireFrontDeskReq req) {
		
		List<QuestionnaireFrontDesk> questionnaireFrontDeskList = questionnaireFrontDeskService.searchQuestionnaireTitle(req);
		if(questionnaireFrontDeskList == null) {
			return new QuestionnaireFrontDeskRes(QuestionnaireFrontDeskRtnCode.ERROR.getMessage());
		}
				
		return new QuestionnaireFrontDeskRes(QuestionnaireFrontDeskRtnCode.SUCCESS.getMessage(), questionnaireFrontDeskList);
	}
	
	//列出問卷裡全部問題
		@PostMapping(value = "/api/search_questionnaire_qAndA")
	public QuestionnaireFrontDeskRes searchQuestionnaireQAndA(@RequestBody QAndAReq req) {
			List< QAndA>  qAndAList = questionnaireFrontDeskService.searchQuestionnaireQAndA(req);
			if(qAndAList == null) {
				return new QuestionnaireFrontDeskRes(QuestionnaireFrontDeskRtnCode.ERROR.getMessage());
			}
					
			return new QuestionnaireFrontDeskRes(qAndAList, QuestionnaireFrontDeskRtnCode.SUCCESS.getMessage());
	}
}
