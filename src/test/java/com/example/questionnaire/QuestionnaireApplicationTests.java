package com.example.questionnaire;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.questionnaire.entity.QAndA;
import com.example.questionnaire.entity.QuestionnaireFrontDesk;
import com.example.questionnaire.respository.QAndADao;
import com.example.questionnaire.service.ifs.QuestionnaireFrontDeskService;
import com.example.questionnaire.vo.QAndAReq;
import com.example.questionnaire.vo.QuestionnaireFrontDeskReq;

@SpringBootTest
class QuestionnaireApplicationTests {
	@Autowired
	private QuestionnaireFrontDeskService questionnaireFrontDeskService;
	@Autowired
	private QAndADao qAndADao;

	// �s�W�ݨ�
	@Test
	public void contextLoads() {

		String date = "2022-12-23";
		LocalDate local_date = LocalDate.parse(date);
		QuestionnaireFrontDeskReq req = new QuestionnaireFrontDeskReq("�A�n���O�ڤ��n", "213", LocalDate.now(), local_date);
		questionnaireFrontDeskService.createQuestionnaireTitle(req);

	}

	// �s�W�ݨ����D
	@Test
	public void contextLoads1() {
		QAndAReq req = new QAndAReq();
		req.setTitle("888");
		QAndA qAndA1 = new QAndA(UUID.randomUUID(), "888", "123123", "123;123;123");
		QAndA qAndA2 = new QAndA(UUID.randomUUID(), "888", "12312", "123;123;123");
		QAndA qAndA3 = new QAndA(UUID.randomUUID(), "888", "12431542523", "123;123;123");
//		qAndA3.setOneOrMore(true);
		List<QAndA> qAndAList = new ArrayList<>();
		qAndAList.add(qAndA1);
		qAndAList.add(qAndA2);
		qAndAList.add(qAndA3);
		req.setqAndAList(qAndAList);

		questionnaireFrontDeskService.createQuestionnaireQAndA(req);
	}

	@Test
	public void contextLoads2() {
		int x = 1;

		String date = "2022-12-23";
		LocalDate local_date = LocalDate.parse(date);
		String date1 = "2022-12-30";
		LocalDate local_date1 = LocalDate.parse(date1);
		QuestionnaireFrontDeskReq req = new QuestionnaireFrontDeskReq();
		req.setId(x);
		req.setTitle("999");
		req.setDescription("14124123");
		req.setStartTime(local_date);
		req.setEndTime(local_date1);
		questionnaireFrontDeskService.updateQuestionnaireTitle(req);
	}

	// ��X��@���D�ק�
	@Test
	public void contextLoads4() {
		QAndAReq req = new QAndAReq();
		req.setUuid(UUID.fromString("5a4199cd-afbe-4c4f-8bca-e00f6f638dd7"));
		req.setTitle("999");
		req.setQuestion("123");
		req.setAnswer("111;111;111");
		req.setOneOrMore(true);
		req.setNecessary(true);
		QAndA qAndA = questionnaireFrontDeskService.updateQuestionnaireQAndA(req);
		System.out.println(qAndA.getUuid() + " , " + qAndA.getTitle() + " , " + qAndA.getQuestion() + " , "
				+ qAndA.getAnswer() + " , " + qAndA.getOneOrMore() + " , " + qAndA.getNecessary());
//		QAndA qAndA1 = qAndADao.findByTitleAndQuestion(req.getTitle(), req.getQuestion());
//		System.out.println(qAndA1.getTitle()+" , "+ qAndA1.getQuestion());
	}

	// �R���ݨ�
	@Test
	public void contextLoads5() {
		QuestionnaireFrontDeskReq req = new QuestionnaireFrontDeskReq();
		req.setId(1);
		req.setTitle("999");
//		qAndADao.deleteByTitle(req.getTitle());
		questionnaireFrontDeskService.deleteQuestionnaireTitle(req);
	}

	// �ҽk�j�M�ݨ�
	@Test
	public void contextLoads6() {
		QuestionnaireFrontDeskReq req = new QuestionnaireFrontDeskReq();
		req.setTitle("��");
		List<QuestionnaireFrontDesk> str = questionnaireFrontDeskService.searchQuestionnaireTitle(req);
		for (QuestionnaireFrontDesk item : str) {
			System.out.println(item.getId() + " , " + item.getTitle() + " , " + item.getDescription() + " , "
					+ item.getStartTime() + " , " + item.getEndTime());
		}

	}

}
