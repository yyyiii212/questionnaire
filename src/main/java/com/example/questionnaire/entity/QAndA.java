package com.example.questionnaire.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "q_and_a")
public class QAndA {
	
	@Id
	@Column(name = "uuid")
	@Type(type = "uuid-char")
	private UUID uuid;

	@Column(name = "title")
	private String title;
	
	@Column(name = "question")
	private String question;
	
	@Column(name = "answer")
	private String answer;
	
	@Column(name = "one_or_more")
	private boolean oneOrMore;
	
	@Column(name = "necessary")
	private boolean necessary;
	
	public  QAndA() {
		
	}

	public QAndA(UUID uuid, String title, String question, String answer) {
		this.uuid = uuid;
		this.title = title;
		this.question = question;
		this.answer = answer;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
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

	public boolean getOneOrMore() {
		return oneOrMore;
	}

	public void setOneOrMore(boolean oneOrMore) {
		this.oneOrMore = oneOrMore;
	}

	public boolean getNecessary() {
		return necessary;
	}

	public void setNecessary(boolean necessary) {
		this.necessary = necessary;
	}

}
