package com.example.questionnaire.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.questionnaire.entity.QAndA;
import com.example.questionnaire.entity.Users;
import com.example.questionnaire.respository.QAndADao;
import com.example.questionnaire.respository.UsersDao;
import com.example.questionnaire.service.ifs.UsersService;
import com.example.questionnaire.vo.UsersReq;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	private UsersDao usersDao;
	@Autowired
	private QAndADao qAndADao;

	// 新增使用者資料
	@Override
	public Users createUsers(UsersReq req) {

		List<QAndA> list = qAndADao.findAllByTitle(req.getTitle());
		Map<String, String> map = new HashMap<>();
		for (QAndA item : list) {
			if (item.getAnswer().contains(",")) {
				String cmower = item.getAnswer();
				String[] parts = cmower.split(",");
				StringBuffer sBuffer = new StringBuffer();
				for (int i = 0; i < parts.length; i++) {
					sBuffer.append(parts[i] + ";");
				}
				String str = sBuffer.substring(0, sBuffer.length() - 1).toString();

				map.put(item.getQuestion(), str);
			} else {
				map.put(item.getQuestion(), item.getAnswer());
			}
		}

		Users users = new Users(req.getName(), req.getCellphone(), req.getEmail(), req.getAge(), LocalDateTime.now());
		users.setTitle(req.getTitle());
		users.setUsersAnswer(map.toString().substring(1, map.toString().length() - 1));
		usersDao.save(users);

		return users;
	}

}
