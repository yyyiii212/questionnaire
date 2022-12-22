package com.example.questionnaire.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.questionnaire.constants.QuestionnaireFrontDeskRtnCode;
import com.example.questionnaire.entity.QAndA;
import com.example.questionnaire.entity.QuestionnaireFrontDesk;
import com.example.questionnaire.respository.QAndADao;
import com.example.questionnaire.respository.QuestionnaireFrontDeskDao;
import com.example.questionnaire.service.ifs.QuestionnaireFrontDeskService;
import com.example.questionnaire.vo.QAndAReq;
import com.example.questionnaire.vo.QuestionnaireFrontDeskReq;
import com.example.questionnaire.vo.QuestionnaireFrontDeskRes;

@Service
public class QuestionnaireFrontDeskServiceImpl implements QuestionnaireFrontDeskService {

	@Autowired
	private QuestionnaireFrontDeskDao frontDeskDao;
	@Autowired
	private QAndADao qAndADao;

	// 新增問卷標題
	@Override
	public QuestionnaireFrontDesk createQuestionnaireTitle(QuestionnaireFrontDeskReq req) {

		QuestionnaireFrontDesk front = frontDeskDao.findByTitle(req.getTitle());
		if (front != null) {
			return null;
		}

		QuestionnaireFrontDesk questionnaireFrontDesk = new QuestionnaireFrontDesk(req.getTitle(), req.getDescription(),
				req.getStartTime(), req.getEndTime());

		frontDeskDao.save(questionnaireFrontDesk);

		return questionnaireFrontDesk;
	}
	
	//判斷問題有無重複
	private boolean checkQAndA(List<QAndA> list) {
		List<String> strList = new ArrayList<>();
		for(QAndA item : list) {
			strList.add(item.getQuestion());
		}
		Set<String> set = new HashSet<>(strList);
		return strList.size() == set.size();
	}

	//新增問題與答案(如果資料庫已有問卷的題目與答案就先刪掉 再新增
	@Override
	public QuestionnaireFrontDeskRes createQuestionnaireQAndA(QAndAReq req) {
		//先找資料庫有無這張問卷
		QuestionnaireFrontDesk questionnaireFrontDesk = frontDeskDao.findByTitle(req.getTitle());
		if(questionnaireFrontDesk == null) {
			return null;
		}
		
		List<QAndA> qAndAListTitle = qAndADao.findAllByTitle(req.getTitle());
		if(!qAndAListTitle.isEmpty()) {
			deleteQuestionnaireQAndA(req);
		}

		if (req.getqAndAList().isEmpty()) {
			new QuestionnaireFrontDeskRes(QuestionnaireFrontDeskRtnCode.SUCCESS.getMessage());
		}
		//判斷新增的問題有無重複(有重複就回傳null
		List<QAndA> qAndAList = req.getqAndAList();
		if(checkQAndA(qAndAList) == false) {
			return null;
		}
		
		for (QAndA item : qAndAList) {
			// answer字串切割
			String cmower = item.getAnswer();
			String[] parts = cmower.split(";");
			StringBuffer sBuffer = new StringBuffer();
			for (int i = 0; i < parts.length; i++) {
				sBuffer.append(parts[i] + ";");
			}
			String str = sBuffer.substring(0, sBuffer.length() - 1).toString();
			
			QAndA qAndA = new QAndA(UUID.randomUUID(), item.getTitle(), item.getQuestion(), str);
			if (item.getOneOrMore() == true) {
				qAndA.setOneOrMore(true);
			}
			if (item.getNecessary() == true) {
				qAndA.setNecessary(true);
			}
			qAndADao.save(qAndA);
		}
		return new QuestionnaireFrontDeskRes(qAndAList);
	}

	// 修改問卷標題
	@Override
	public QuestionnaireFrontDesk updateQuestionnaireTitle(QuestionnaireFrontDeskReq req) {
		Optional<QuestionnaireFrontDesk> frontOp = frontDeskDao.findById(req.getId());

		QuestionnaireFrontDesk front = frontDeskDao.findByTitle(req.getTitle());
		if (front != null) {
			return null;
		}

		QuestionnaireFrontDesk questionnaireFrontDesk = frontOp.get();

		List<QAndA> qAndAList = qAndADao.findAllByTitle(questionnaireFrontDesk.getTitle());
		for (QAndA item : qAndAList) {
			item.setTitle(req.getTitle());
			qAndADao.save(item);
		}

		questionnaireFrontDesk.setTitle(req.getTitle());
		questionnaireFrontDesk.setDescription(req.getDescription());
		questionnaireFrontDesk.setStartTime(req.getStartTime());
		questionnaireFrontDesk.setEndTime(req.getEndTime());
		frontDeskDao.save(questionnaireFrontDesk);

		return questionnaireFrontDesk;
	}

	// 修改問卷(問題答案
	@Override
	public QAndA updateQuestionnaireQAndA(QAndAReq req) {
		// 找出單一問題
		Optional<QAndA> qAndAOp = qAndADao.findById(req.getUuid());
		QAndA qAndA = qAndAOp.get();

		// 看DB是否已存在相同標題與與問題
		QAndA qAndA1 = qAndADao.findByTitleAndQuestion(qAndA.getTitle(), req.getQuestion());
		if (qAndA1 != null) {
			if (qAndA.getQuestion().equals(req.getQuestion())) {
				// 修改
				qAndA.setQuestion(req.getQuestion());
				// 切割答案(按照;切割)
				String cmower = req.getAnswer();
				String[] parts = cmower.split(";");
				StringBuffer sBuffer = new StringBuffer();
				// 切割完字串重組
				for (int i = 0; i < parts.length; i++) {
					sBuffer.append(parts[i] + ";");
				}
				// 轉型
				String str = sBuffer.substring(0, sBuffer.length() - 1).toString();

				qAndA.setAnswer(str);
				qAndA.setOneOrMore(req.getOneOrMore());
				qAndA.setNecessary(req.getNecessary());
				qAndADao.save(qAndA);

				return qAndA;
			}
			return null;
		}
		return null;
	}

	// 刪除問卷
	@Override
	public QuestionnaireFrontDeskRes deleteQuestionnaireTitle(QuestionnaireFrontDeskReq req) {

		if (req.getId() != 0) {
			Optional<QuestionnaireFrontDesk> questionnaireFrontDeskOp = frontDeskDao.findById(req.getId());
			QuestionnaireFrontDesk questionnaireFrontDesk = questionnaireFrontDeskOp.get();
			frontDeskDao.delete(questionnaireFrontDesk);
			//刪除問卷的題目
			List<QAndA> qAndAList = qAndADao.findAllByTitle(questionnaireFrontDesk.getTitle());
			qAndADao.deleteAll(qAndAList);
			return new QuestionnaireFrontDeskRes(QuestionnaireFrontDeskRtnCode.SUCCESS.getMessage());
		}
		return null;
	}

	// 刪除問卷問題
	private QuestionnaireFrontDeskRes deleteQuestionnaireQAndA(QAndAReq req) {
		
		List<QAndA> qAndAList = qAndADao.findAllByTitle(req.getTitle());
		qAndADao.deleteAll(qAndAList);
		return new QuestionnaireFrontDeskRes(QuestionnaireFrontDeskRtnCode.SUCCESS.getMessage());
	}
	
	//列出所有問卷
	@Override
	public List<QuestionnaireFrontDesk> searchAll(QuestionnaireFrontDeskReq req) {
		
//		List<QuestionnaireFrontDesk> questionnaireFrontDeskList = frontDeskDao.findAll();
		return frontDeskDao.findAll();
	}
	
	@Override
	public List<QuestionnaireFrontDesk> searchQuestionnaireTitle(QuestionnaireFrontDeskReq req) {
		//列出所有問卷
		List<QuestionnaireFrontDesk> frontDeskList = frontDeskDao.findAll();
		
		List<QuestionnaireFrontDesk> findFrontDeskList = new ArrayList<>();
		//找出有包含輸入字串的標題
		for(QuestionnaireFrontDesk item : frontDeskList) {
			if(item.getTitle().contains(req.getTitle())) {
				QuestionnaireFrontDesk questionnaireFrontDesk = new QuestionnaireFrontDesk(item.getTitle(), item.getDescription(), item.getStartTime(), item.getEndTime());
				findFrontDeskList.add(questionnaireFrontDesk);
			}
		}
		return findFrontDeskList;
	}

	// 列出問卷裡的問題
	@Override
	public List<QAndA> searchQuestionnaireQAndA(QAndAReq req) {

		return qAndADao.findAllByTitle(req.getTitle());
	}
}
