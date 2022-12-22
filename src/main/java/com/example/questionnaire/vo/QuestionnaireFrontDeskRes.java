package com.example.questionnaire.vo;

import java.util.List;

import com.example.questionnaire.entity.QAndA;
import com.example.questionnaire.entity.QuestionnaireFrontDesk;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionnaireFrontDeskRes {

	private String message;

	private QuestionnaireFrontDesk questionnaireFrontDesk;

	private List<QuestionnaireFrontDesk> questionnaireFrontDeskList;
	
	private List<QAndA> qAndAList;

	public QuestionnaireFrontDeskRes() {

	}

	public QuestionnaireFrontDeskRes(String message) {
		this.message = message;
	}
	
	public QuestionnaireFrontDeskRes(List<QAndA> qAndAList) {
		this.qAndAList = qAndAList;
	}
	
	public QuestionnaireFrontDeskRes(QuestionnaireFrontDesk questionnaireFrontDesk, String message) {
		this.questionnaireFrontDesk = questionnaireFrontDesk;
		this.message = message;
	}
	
	public QuestionnaireFrontDeskRes(String message, List<QuestionnaireFrontDesk> questionnaireFrontDeskList) {
		this.message = message;
		this.questionnaireFrontDeskList = questionnaireFrontDeskList;
	}
	
	public QuestionnaireFrontDeskRes(List<QAndA> qAndAList, String message) {
		this.qAndAList = qAndAList;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public QuestionnaireFrontDesk getQuestionnaireFrontDesk() {
		return questionnaireFrontDesk;
	}

	public void setQuestionnaireFrontDesk(QuestionnaireFrontDesk questionnaireFrontDesk) {
		this.questionnaireFrontDesk = questionnaireFrontDesk;
	}

	public List<QuestionnaireFrontDesk> getQuestionnaireFrontDeskList() {
		return questionnaireFrontDeskList;
	}

	public void setQuestionnaireFrontDeskList(List<QuestionnaireFrontDesk> questionnaireFrontDeskList) {
		this.questionnaireFrontDeskList = questionnaireFrontDeskList;
	}

	public List<QAndA> getqAndAList() {
		return qAndAList;
	}

	public void setqAndAList(List<QAndA> qAndAList) {
		this.qAndAList = qAndAList;
	}

}
