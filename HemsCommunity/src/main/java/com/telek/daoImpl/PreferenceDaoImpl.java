package com.telek.daoImpl;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository(value="PreferenceDaoImpl")  
public class PreferenceDaoImpl extends DAOImpl {
	
	/**
	 * 查询评论的点赞数
	 * @param commentId
	 * @return
	 */
	public int getCountByCommentId(String commentId) {
		Session session= getSession();
		System.out.println("commentId:"+commentId);
		String sql = "from PreferenceEntity where commentId = ?";
		Query query = session.createQuery(sql);
		List<?> result = query.setString(0, commentId)
			.list();
		session.close();
		return result.size();
	}
	
	/**
	 * 查询评论的点赞数
	 * @param commentId
	 * @return
	 */
	public int getCountByCommentIdAndUserId(String commentId, String userId) {
		Session session= getSession();
		String sql = "from PreferenceEntity where commentId = ? and userId = ?";
		Query query = session.createQuery(sql);
		List<?> result = query
			.setString(0, commentId)
			.setString(1, userId)
			.list();
		session.close();
		return result.size();
	}
	
	/**【
	 * 根据userId与commentId删除点赞记录
	 * @param commentId
	 * @param userId
	 * @return
	 */
	public void deleteByCommentIdAndUserId(String commentId, String userId) {
		Session session= getSession();
		String hql = "Delete FROM PreferenceEntity Where commentId = ? and userId = ?";
        Query q = session.createQuery(hql) ;
        q.setString(0, commentId) ;
        q.setString(1, userId) ;
        q.executeUpdate();
        session.close();
	}
	
}
