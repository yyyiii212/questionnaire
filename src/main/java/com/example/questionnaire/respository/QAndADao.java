package com.example.questionnaire.respository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.questionnaire.entity.QAndA;

public interface QAndADao extends JpaRepository<QAndA, UUID>{
	
	public List<QAndA> findAllByTitle(String title);
	
	public QAndA findByQuestion(String question);
	
	public QAndA findByTitleAndQuestion(String title, String question);

}
