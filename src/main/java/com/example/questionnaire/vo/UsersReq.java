package com.example.questionnaire.vo;

public class UsersReq {

	private String name;
	
	private String gender;

	private String cellphone;

	private String email;

	private int age;

	private String usersAnswer;

	private String title;

	public UsersReq() {

	}

	public UsersReq(String name, String cellphone, String email, int age, String usersAnswer) {
		this.name = name;
		this.cellphone = cellphone;
		this.email = email;
		this.age = age;
		this.usersAnswer = usersAnswer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getUsersAnswer() {
		return usersAnswer;
	}

	public void setUsersAnswer(String usersAnswer) {
		this.usersAnswer = usersAnswer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
