package com.telek.service;

import java.util.List;

import com.telek.model.CollectionEntity;
import com.telek.model.CommentEntity;

public interface CollectionService {
	
	public List<CollectionEntity> getByInfomationIdAndUserId(String informationId);
	public boolean addCollectionEntity(CollectionEntity entity);
	public boolean deleteByInformationIdAndUserIds(String informationId, String userId);
	public List<CommentEntity> getByInfomationId(String informationId) ;

	/**
	 * 
	 * 查询资讯的收藏数量和当前用户是否收藏
	 * @author hyj
	 * @date 2017-8-30 下午2:37:30
	 * @param informationId
	 * @param userId
	 * @return
	 *
	 */
	public String queryCollectionsInfo(String informationId,String userId);
	/**
	 * 查询一个用户收藏的资讯列表
	 *
	 * @author hyj
	 * @date 2017-9-6 下午2:07:26
	 * @param userId
	 * @param pageNumber
	 * @return
	 *
	 */
	public String  queryCollectionsByUserId(String userId,int pageNumber, String time);
}
