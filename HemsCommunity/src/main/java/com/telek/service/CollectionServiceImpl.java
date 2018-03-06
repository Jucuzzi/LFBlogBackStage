package com.telek.service;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telek.daoImpl.CollectionDaoImpl;
import com.telek.daoImpl.CommentDaoImpl;
import com.telek.daoImpl.InformationDaoImpl;
import com.telek.model.CollectionEntity;
import com.telek.model.CommentEntity;
import com.telek.model.InformationEntity;


@Service("CollectionServiceImpl")
public class CollectionServiceImpl implements CollectionService{

	@Autowired
	private CollectionDaoImpl collectionDaoImpl;
	@Autowired
	private CommentDaoImpl commentDaoImpl;
	
	@Autowired
	private InformationDaoImpl infarmationDaoImpl;
	/**
	 * 通过资讯id和用户id查询收藏记录
	 * @param id
	 * @return
	 */
	public List<CollectionEntity> getByInfomationIdAndUserId(String informationId) {
		return collectionDaoImpl.queryCollectionByInformationIdAndUserId(informationId);
	}
	
	//新增收藏
	public boolean addCollectionEntity(CollectionEntity entity) {
		if (collectionDaoImpl.queryCollectionNumByInformationId(entity.getInformationId(),entity.getUserId()) > 0) {
			return true;
		} else {
			return collectionDaoImpl.save(entity);
		}
	}
	
	/**【
	 * 根据userId与informationId删除收藏记录
	 * @param informationId
	 * @param userId
	 * @return
	 */
	public boolean deleteByInformationIdAndUserIds(String informationId, String userId) {
		return collectionDaoImpl.deleteByInformationIdAndUserId(informationId, userId);
	}
	
	/**
	 * 通过资讯id和用户id查询评论记录
	 * @param id
	 * @return
	 */
	public List<CommentEntity> getByInfomationId(String informationId) {
		return collectionDaoImpl.queryCommentByInformationId(informationId);
	}
	
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
	public String queryCollectionsInfo(String informationId, String userId) {
		JSONObject backJson = new JSONObject();
		backJson.put("collectNum",collectionDaoImpl.queryCollectionNumByInformationId(informationId));
		backJson.put("isCollect", collectionDaoImpl.queryCollectionNumByInformationId(informationId,userId) > 0 ? true :false);
		backJson.put("commentNum", commentDaoImpl.queryCommentsTotalByInformationId(informationId));
		
		
		return backJson.toString();
	}
	
	public String queryCollectionsByUserId(String userId,int pageNumber,String time){
		JSONObject backJson = new JSONObject();
		List<CollectionEntity> collectionEntities = (List<CollectionEntity>)collectionDaoImpl.queryCollectionsByUserId(userId,pageNumber,10,time);
		JSONArray jsonArray = new JSONArray();
		for (int i = 0; i < collectionEntities.size(); i ++) {
			InformationEntity informationEntity = infarmationDaoImpl.get(collectionEntities.get(i).getInformationId());
			informationEntity.changeImageUrl();
			JSONObject jsb = informationEntity.entityJSONObject();
			jsb.put("collectNum",""+collectionDaoImpl.queryCollectionNumByInformationId(informationEntity.getInformationId()));
			jsonArray.add(jsb);
		}
		backJson.put("collectList",jsonArray);
		backJson.put("time",time);
		return backJson.toString();
	}
}
