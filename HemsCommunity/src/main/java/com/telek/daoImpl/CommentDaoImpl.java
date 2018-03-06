package com.telek.daoImpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.telek.cache.service.CacheService;
import com.telek.dao.IDAO;
import com.telek.model.ButtonInfo;
import com.telek.model.CommentEntity;
import com.telek.model.ExchangeEntity;
import com.telek.model.InformationEntity;
import com.telek.model.PrizeInfoEntity;
import com.telek.model.UserEntity;
import com.telek.util.ConstantVar;
import com.telek.util.PageModel;
import com.telek.util.Util;
@Repository(value="CommentDaoImpl")  
public class CommentDaoImpl extends DAOImpl {
	private static final Logger LOG = Logger.getLogger(PrvlgDAOImpl.class);
	@Autowired
	private IDAO iDAO;
	@Autowired
	private CacheService cacheService;
	/**
	 * 根据informationId全部评论总数
	 * @param informationId 资讯ID
	 * @return 
	 */
	public long queryCommentsTotalByInformationId(String informationId) {
		Session session= getSession();
		String sql = "select count(*) from CommentEntity where informationId = ? and isDelete != 1";
		Query query = session.createQuery(sql);
		query.setString(0, informationId);
		long total = ((Long)query.uniqueResult()).intValue();
		session.close();
		return total;
	}
	/**
	 * 全部评论总数
	 * @param commentId
	 * @return
	 */
	public long queryCommentsTotal() {
		Session session= getSession();
		String sql = "select count(*) from CommentEntity";
		Query query = session.createQuery(sql);
		long total = ((Long)query.uniqueResult()).intValue();
		session.close();
		return total;
	}
	/**
	 * 根据页数和页码及资讯id查询评论
	 * @param informationId
	 * @param pageNumber
	 * @param num
	 * @return
	 */
	public List<CommentEntity> queryComments(String informationId, int pageNumber, int num, String time) {
		Session session= getSession();
 		String sql = "from CommentEntity where informationId = ? and commentTime <= ? and isDelete != 1 order by commentTime desc";
		Query query = session.createQuery(sql);
		query.setFirstResult((pageNumber-1)*num);//设置起始行
		query.setMaxResults(num);//每页条数
		List<?> result = query
			.setString(0, informationId)
			.setString(1, time)
			.list();
		session.close();
		return (List<CommentEntity>) result;
	}
	
	/**
	 * 根据commentId查询评论
	 * @param commentId
	 * @return
	 */
	public CommentEntity queryCommentsBycommentId(int commentId) {
		Session session= getSession();
		String sql = "from CommentEntity where commentId = ?";
		Query query = session.createQuery(sql);
		List<?> result = query
			.setInteger(0, commentId)
			.list();
		session.close();
		if (result.size() > 0) {
			return (CommentEntity) result.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * 软删除评论
	 * @param commentId
	 * @return
	 */
	public int softDelete(String commentId) {
		
		Session session= getSession();
		String hql = "update CommentEntity set isDelete = 1 Where commentId = ?";
        Query q = session.createQuery(hql) ;
        q.setString(0, commentId) ;
        int result = q.executeUpdate();
        session.close();
        
        return result;
        
	}
	
//	public Integer save(CommentEntity entity) {
//		setSessionFactory(sessionFactory);
//		
//		return (Integer) getSession().save(entity);
//	}
	
	@SuppressWarnings("unchecked")
	public List<?> queryCommentListByParam(int pageNumber,int everyPageNum,String condition,String time) {
		Session session= getSession();
		String sql = "from CommentEntity where commentDetail like :commentDetail and commentTime <= :time order by commentTime desc";
		Query query = session.createQuery(sql);
		query.setFirstResult((pageNumber-1)*everyPageNum);//设置起始行
		query.setMaxResults(everyPageNum);//每页条数
		List<?> result = query
        .setString("commentDetail", "%"+condition+"%")
        .setString("time", time)
        .list();
		session.close();
		return result;
	}
	
	public void delURRelByCommentId(int commentId,String isDelete) {
		Session session= getSession();
		String sql = "update CommentEntity set isDelete = :isDelete where commentId = :id";
		Query query = session.createQuery(sql)
			 	   .setString("isDelete", isDelete)
				   .setInteger("id", commentId);
		query.executeUpdate();
		session.close();
	}
	/*
	 * 奖品管理
	 */
	public List<PrizeInfoEntity> queryPrizeListAll() {
		return (List<PrizeInfoEntity>) iDAO.findByHql("from PrizeInfoEntity order by prizeId ");
	}
	
	/*
	 * 根据id去获取奖品
	 */
	public PrizeInfoEntity getPrieInfoById(int prizeId) {
		Object obj = iDAO.getObject(PrizeInfoEntity.class, prizeId);
		return obj == null ? null : (PrizeInfoEntity) obj;
	}
	
	public boolean updatePrizeInfo(PrizeInfoEntity prizeInfo) {
		try {
			Object obj = cacheService.updCacheData(prizeInfo, ConstantVar.UPD_DATA);
			return obj != null;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return false;
		}
	}
	/*
	 * 兑换券管理
	 */
	public List<ExchangeEntity> queryExchangeListAll() {
		return (List<ExchangeEntity>) iDAO.findByHql("from ExchangeEntity order by prizeId");
	}
	
	public boolean updateExchangeInfo(ExchangeEntity exchangeInfo) {
		try {
			Object obj = cacheService.updCacheData(exchangeInfo, ConstantVar.UPD_DATA);
			return obj != null;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return false;
		}
	}
}

