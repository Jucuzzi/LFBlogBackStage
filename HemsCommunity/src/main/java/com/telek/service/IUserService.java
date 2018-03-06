package com.telek.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.telek.model.UserEntity;


public interface IUserService {
	@Transactional
	public boolean save(UserEntity entity);
	public UserEntity get(String id);
	public String userLogin(UserEntity mUser,HttpServletRequest request);
	public String getUserIconPath(String id);
	public UserEntity getHemsUser(String userId,String id);
	
	public String frontStageUserLogin(HttpServletRequest request); 
	public String backStageUserLogin(HttpServletRequest request); 
	public boolean setUserIconPath(String userId,String userIconData);
	public String getUserInfo(String id);
}
