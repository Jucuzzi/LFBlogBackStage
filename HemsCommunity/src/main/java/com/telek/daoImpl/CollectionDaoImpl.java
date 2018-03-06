package com.telek.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.telek.cache.service.CacheService;
import com.telek.dao.IDAO;
import com.telek.model.CollectionEntity;
import com.telek.model.CommentEntity;

@Repository(value="CollectionDaoImpl")  

public class CollectionDaoImpl extends DAOImpl {
	
	private static final Logger LOG = Logger.getLogger(PrvlgDAOImpl.class);
	@Autowired
	private IDAO iDAO;
	@Autowired
	private CacheService cacheService;
	
	public long queryCollectionNumByInformationId(String informationId,String userId) {		
		Session session= getSession();
		String sql = "select count(*) from CollectionEntity where informationId = ? and userId = ?";
		Query query = session.createQuery(sql);
		query.setString(0, informationId);
		query.setString(1, userId);
		long total = ((Long)query.uniqueResult()).intValue();
		session.close();
		return total;
	}
	
	/**
	 * 根据informationId查询收藏
	 * @param 
	 * @return
	 */
	public long queryCollectionNumByInformationId(String informationId) {		
		Session session= getSession();
		String sql = "select count(*) from CollectionEntity where informationId = ?";
		Query query = session.createQuery(sql);
		query.setString(0, informationId);
		long total = ((Long)query.uniqueResult()).intValue();
		session.close();
		return total;
	}
	
	/**
	 * 根据informationId查询收藏
	 * @param 
	 * @return
	 */
	public List<CollectionEntity> queryCollectionByInformationIdAndUserId(String informationId) {
		Session session= getSession();
		String sql = "from CollectionEntity where informationId = ?";
		Query query = session.createQuery(sql);
		List<?> result = query
			.setString(0, informationId)
			.list();
		session.close();
		return (List<CollectionEntity>) result;
	}

	public CollectionEntity addEntity(CollectionEntity collectionEntity) {
		Session session= getSession();
		session.save(collectionEntity);
		session.close();
		return collectionEntity;
	}
	
	/**【
	 * 根据userId与informationId删除收藏记录
	 * @param informationId
	 * @param userId
	 * @return
	 */
	public boolean deleteByInformationIdAndUserId(String informationId, String userId) {
		Session session= getSession();
		String hql = "Delete FROM CollectionEntity Where informationId = ? and userId = ?";
        Query q = session.createQuery(hql) ;
        q.setString(0, informationId) ;
        q.setString(1, userId) ;
        q.executeUpdate();
        session.close();
        return true;
	}
	
	/**
	 * 根据informationId 查询评论
	 * @param 
	 * @return
	 */
	public List<CommentEntity> queryCommentByInformationId(String informationId) {
		Session session= getSession();
		String sql = "from CommentEntity where informationId = ?";
		Query query = session.createQuery(sql);
		List<?> result = query
			.setString(0, informationId)
			.list();
		session.close();
		return (List<CommentEntity>) result;
	}
	/**
	 * 通过用户Id查找收藏资讯
	 *
	 * @author hyj
	 * @date 2017-9-6 下午2:10:49
	 * @param id
	 * @param pageNumber 页序号
	 * @param maxNum 每页最大条数
	 * @return
	 *
	 */
	public List<?> queryCollectionsByUserId(String id,int pageNumber,int maxNum, String time) {
		Session session= getSession();
		String sql = "from CollectionEntity where collectionTime <= ? and  user_id = ? order by collectionTime desc";

		Query query = session.createQuery(sql);
		query.setString(0, time);
		query.setString(1, id);
		query.setFirstResult((pageNumber-1)*maxNum);//设置起始行
		query.setMaxResults(maxNum);//每页条数
		List<?> result = query.list();
		session.close();
		return (List<?>) result;
	}

}
