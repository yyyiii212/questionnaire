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

	// �s�W�ݨ����D
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
	
	//�P�_���D���L����
	private boolean checkQAndA(List<QAndA> list) {
		List<String> strList = new ArrayList<>();
		for(QAndA item : list) {
			strList.add(item.getQuestion());
		}
		Set<String> set = new HashSet<>(strList);
		return strList.size() == set.size();
	}

	//�s�W���D�P����(�p�G��Ʈw�w���ݨ����D�ػP���״N���R�� �A�s�W
	@Override
	public QuestionnaireFrontDeskRes createQuestionnaireQAndA(QAndAReq req) {
		//�����Ʈw���L�o�i�ݨ�
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
		//�P�_�s�W�����D���L����(�����ƴN�^��null
		List<QAndA> qAndAList = req.getqAndAList();
		if(checkQAndA(qAndAList) == false) {
			return null;
		}
		
		for (QAndA item : qAndAList) {
			// answer�r�����
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

	// �ק�ݨ����D
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

	// �ק�ݨ�(���D����
	@Override
	public QAndA updateQuestionnaireQAndA(QAndAReq req) {
		// ��X��@���D
		Optional<QAndA> qAndAOp = qAndADao.findById(req.getUuid());
		QAndA qAndA = qAndAOp.get();

		// ��DB�O�_�w�s�b�ۦP���D�P�P���D
		QAndA qAndA1 = qAndADao.findByTitleAndQuestion(qAndA.getTitle(), req.getQuestion());
		if (qAndA1 != null) {
			if (qAndA.getQuestion().equals(req.getQuestion())) {
				// �ק�
				qAndA.setQuestion(req.getQuestion());
				// ���ε���(����;����)
				String cmower = req.getAnswer();
				String[] parts = cmower.split(";");
				StringBuffer sBuffer = new StringBuffer();
				// ���Χ��r�꭫��
				for (int i = 0; i < parts.length; i++) {
					sBuffer.append(parts[i] + ";");
				}
				// �૬
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

	// �R���ݨ�
	@Override
	public QuestionnaireFrontDeskRes deleteQuestionnaireTitle(QuestionnaireFrontDeskReq req) {

		if (req.getId() != 0) {
			Optional<QuestionnaireFrontDesk> questionnaireFrontDeskOp = frontDeskDao.findById(req.getId());
			QuestionnaireFrontDesk questionnaireFrontDesk = questionnaireFrontDeskOp.get();
			frontDeskDao.delete(questionnaireFrontDesk);
			//�R���ݨ����D��
			List<QAndA> qAndAList = qAndADao.findAllByTitle(questionnaireFrontDesk.getTitle());
			qAndADao.deleteAll(qAndAList);
			return new QuestionnaireFrontDeskRes(QuestionnaireFrontDeskRtnCode.SUCCESS.getMessage());
		}
		return null;
	}

	// �R���ݨ����D
	private QuestionnaireFrontDeskRes deleteQuestionnaireQAndA(QAndAReq req) {
		
		List<QAndA> qAndAList = qAndADao.findAllByTitle(req.getTitle());
		qAndADao.deleteAll(qAndAList);
		return new QuestionnaireFrontDeskRes(QuestionnaireFrontDeskRtnCode.SUCCESS.getMessage());
	}
	
	//�C�X�Ҧ��ݨ�
	@Override
	public List<QuestionnaireFrontDesk> searchAll(QuestionnaireFrontDeskReq req) {
		
//		List<QuestionnaireFrontDesk> questionnaireFrontDeskList = frontDeskDao.findAll();
		return frontDeskDao.findAll();
	}
	
	@Override
	public List<QuestionnaireFrontDesk> searchQuestionnaireTitle(QuestionnaireFrontDeskReq req) {
		//�C�X�Ҧ��ݨ�
		List<QuestionnaireFrontDesk> frontDeskList = frontDeskDao.findAll();
		
		List<QuestionnaireFrontDesk> findFrontDeskList = new ArrayList<>();
		//��X���]�t��J�r�ꪺ���D
		for(QuestionnaireFrontDesk item : frontDeskList) {
			if(item.getTitle().contains(req.getTitle())) {
				QuestionnaireFrontDesk questionnaireFrontDesk = new QuestionnaireFrontDesk(item.getTitle(), item.getDescription(), item.getStartTime(), item.getEndTime());
				findFrontDeskList.add(questionnaireFrontDesk);
			}
		}
		return findFrontDeskList;
	}

	// �C�X�ݨ��̪����D
	@Override
	public List<QAndA> searchQuestionnaireQAndA(QAndAReq req) {

		return qAndADao.findAllByTitle(req.getTitle());
	}
}
