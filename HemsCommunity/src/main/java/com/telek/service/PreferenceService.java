package com.telek.service;

import org.springframework.transaction.annotation.Transactional;

import com.telek.model.PreferenceEntity;
import com.telek.model.UserEntity;

public interface PreferenceService {
	@Transactional
	public boolean save(PreferenceEntity preferenceEntity);
	public int getCountByCommentId(String commentId);
	public int getCountByCommentIdAndUserId(String commentId, String userId);
	public void deleteByCommentIdAndUserId(String commentId, String userId);
}
