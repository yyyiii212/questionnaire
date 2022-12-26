package com.example.questionnaire;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.example.questionnaire.entity.Users;
import com.example.questionnaire.respository.QAndADao;
import com.example.questionnaire.respository.UsersDao;
import com.example.questionnaire.service.ifs.QuestionnaireFrontDeskService;
import com.example.questionnaire.vo.QAndAReq;
import com.example.questionnaire.vo.QuestionnaireFrontDeskReq;

@SpringBootTest
class QuestionnaireApplicationTests {
	@Autowired
	private QuestionnaireFrontDeskService questionnaireFrontDeskService;
	@Autowired
	private QAndADao qAndADao;
	@Autowired
	private UsersDao usersDao;

	// 新增問卷
	@Test
	public void contextLoads() {

		String date = "2022-12-23";
		LocalDate local_date = LocalDate.parse(date);
		QuestionnaireFrontDeskReq req = new QuestionnaireFrontDeskReq("你好但是我不好", "213", LocalDate.now(), local_date);
		questionnaireFrontDeskService.createQuestionnaireTitle(req);

	}

	// 新增問卷問題
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

	// 找出單一問題修改
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

	// 刪除問卷
	@Test
	public void contextLoads5() {
		QuestionnaireFrontDeskReq req = new QuestionnaireFrontDeskReq();
		req.setId(1);
		req.setTitle("999");
//		qAndADao.deleteByTitle(req.getTitle());
		questionnaireFrontDeskService.deleteQuestionnaireTitle(req);
	}

	// 模糊搜尋問卷
	@Test
	public void contextLoads6() {
		QuestionnaireFrontDeskReq req = new QuestionnaireFrontDeskReq();
		req.setTitle("我");
		List<QuestionnaireFrontDesk> str = questionnaireFrontDeskService.searchQuestionnaireTitle(req);
		for (QuestionnaireFrontDesk item : str) {
			System.out.println(item.getId() + " , " + item.getTitle() + " , " + item.getDescription() + " , "
					+ item.getStartTime() + " , " + item.getEndTime());
		}

	}
	
	@Test
	public void contextLoads7() {
		QAndAReq req = new QAndAReq();
		QAndA qAndA = new QAndA();
		qAndA.setQuestion("111");
		qAndA.setAnswer("123");
		QAndA qAndA1 = new QAndA();
		qAndA1.setQuestion("222");
		qAndA1.setAnswer("");
		QAndA qAndA2 = new QAndA();
		qAndA2.setQuestion("333");
		qAndA2.setAnswer("111,222,333");
		List<QAndA> list = new ArrayList<>();
		list.add(qAndA);
		list.add(qAndA1);
		list.add(qAndA2);
		Map<String, String> map = new HashMap<>();
		
		for (QAndA item : list) {
			// answer字串切割
			if(item.getAnswer().contains(",")) {
				String cmower = item.getAnswer();
				String[] parts = cmower.split(",");
				StringBuffer sBuffer = new StringBuffer();
				for (int i = 0; i < parts.length; i++) {
					sBuffer.append(parts[i] + ";");
				}
				String str = sBuffer.substring(0, sBuffer.length() - 1).toString();
				
				map.put(item.getQuestion(), str);
			}else {
				map.put(item.getQuestion(), item.getAnswer());
			}
		}
		Users users = new Users("hi", "0912345678", "213@gmail.com", 23, LocalDateTime.now());
		users.setGender("男");
		users.setTitle("99999");
		users.setUsersAnswer(map.toString().substring(1, map.toString().length()-1));
		usersDao.save(users);
	}

}
