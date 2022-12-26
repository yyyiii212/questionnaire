package com.example.questionnaire.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.questionnaire.entity.Users;
@Repository
public interface UsersDao extends JpaRepository<Users, Integer>{

}
