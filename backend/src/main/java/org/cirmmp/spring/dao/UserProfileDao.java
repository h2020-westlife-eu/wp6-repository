package org.cirmmp.spring.dao;

import org.cirmmp.spring.model.UserProfile;

import java.util.List;


public interface UserProfileDao {

	List<UserProfile> findAll();
	
	UserProfile findByType(String type);
	
	UserProfile findById(int id);
}
