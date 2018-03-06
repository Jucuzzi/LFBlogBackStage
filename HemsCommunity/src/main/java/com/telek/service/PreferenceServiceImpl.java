package com.telek.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telek.daoImpl.PreferenceDaoImpl;
import com.telek.model.PreferenceEntity;

@Service("PreferenceServiceImpl")
public class PreferenceServiceImpl implements PreferenceService {

	@Autowired
	private PreferenceDaoImpl preferenceDaoImpl;
	
	/**
	 * 查询评论的点赞数
	 * @param commentId
	 * @return
	 */
	public int getCountByCommentId(String commentId) {
		return preferenceDaoImpl.getCountByCommentId(commentId);
	}
	
	/**
	 * 查询评论的点赞数
	 * @param commentId
	 * @return
	 */
	public int getCountByCommentIdAndUserId(String commentId, String userId) {
		return preferenceDaoImpl.getCountByCommentIdAndUserId(commentId, userId);
	}
	
	/**
	 * 根据userId与commentId删除点赞记录
	 * @param commentId
	 * @param userId
	 * @return
	 */
	public void deleteByCommentIdAndUserId(String commentId, String userId) {
		preferenceDaoImpl.deleteByCommentIdAndUserId(commentId, userId);
	}
	
	/**
	 * 保存
	 * @param preferenceEntity
	 * @return
	 */
	public boolean save(PreferenceEntity preferenceEntity) {
		return preferenceDaoImpl.save(preferenceEntity);
	}
	
}
