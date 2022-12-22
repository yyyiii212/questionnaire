package com.example.questionnaire.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.questionnaire.entity.QuestionnaireFrontDesk;

public interface QuestionnaireFrontDeskDao extends JpaRepository<QuestionnaireFrontDesk, Integer>{
	
	public QuestionnaireFrontDesk findByTitle(String title);
}
