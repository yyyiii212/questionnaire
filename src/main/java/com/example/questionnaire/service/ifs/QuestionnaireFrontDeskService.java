package com.example.questionnaire.service.ifs;

import java.util.List;

import com.example.questionnaire.entity.QAndA;
import com.example.questionnaire.entity.QuestionnaireFrontDesk;
import com.example.questionnaire.vo.QAndAReq;
import com.example.questionnaire.vo.QuestionnaireFrontDeskReq;
import com.example.questionnaire.vo.QuestionnaireFrontDeskRes;

public interface QuestionnaireFrontDeskService {
	
	public QuestionnaireFrontDesk createQuestionnaireTitle(QuestionnaireFrontDeskReq req);
	
	public QuestionnaireFrontDeskRes checkQAndA(QAndAReq req);
	
	public QuestionnaireFrontDeskRes createQuestionnaireQAndA(QAndAReq req);
	
	public QuestionnaireFrontDesk updateQuestionnaireTitle(QuestionnaireFrontDeskReq req);
	
	public QAndA updateQuestionnaireQAndA(QAndAReq req);
	
	public QuestionnaireFrontDeskRes deleteQuestionnaireTitle(QuestionnaireFrontDeskReq req);
	
	public List<QuestionnaireFrontDesk> searchAll(QuestionnaireFrontDeskReq req);
	
	public List<QuestionnaireFrontDesk> searchQuestionnaireTitle(QuestionnaireFrontDeskReq req);
	
	public List<QAndA> searchQuestionnaireQAndA(QAndAReq req);
}
