package com.example.questionnaire.constants;

public enum QuestionnaireFrontDeskRtnCode {
	SUCCESS("200", "Successful !!"),
	ID_IS_NOT_FOUND("400", "Id is not found !!"),
	TITLE("400", "Title cannot be empty or null !!"),
	DESCRIPTION("400", "Description cannot be empty or null !!"),
	QUESTION_REPEAT("400", "Question repeated !!"),
	QUESTION_IS_EMPTY("400", "Question is empty !!"),
	ANSWER_IS_EMPTY("400", "Answer is empty !!"),
	ERROR("400","Not found !!");
	
	private String code;

	private String message;
	
	private QuestionnaireFrontDeskRtnCode(String code,String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
