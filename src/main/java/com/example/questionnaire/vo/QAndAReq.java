package com.example.questionnaire.vo;

import java.util.List;
import java.util.UUID;

import com.example.questionnaire.entity.QAndA;
import com.fasterxml.jackson.annotation.JsonInclude;

public class QAndAReq {
	
	private UUID uuid;
	
	private String title;
	
	private String question;
	
	private String answer;
	
	private List<QAndA> qAndAList;
	
	private boolean oneOrMore;
	
	private boolean necessary;

	public QAndAReq() {
		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public List<QAndA> getqAndAList() {
		return qAndAList;
	}

	public void setqAndAList(List<QAndA> qAndAList) {
		this.qAndAList = qAndAList;
	}

	public boolean getOneOrMore() {
		return oneOrMore;
	}

	public void setOneOrMore(boolean oneOrMore) {
		this.oneOrMore = oneOrMore;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public boolean getNecessary() {
		return necessary;
	}

	public void setNecessary(boolean necessary) {
		this.necessary = necessary;
	}
	
}
