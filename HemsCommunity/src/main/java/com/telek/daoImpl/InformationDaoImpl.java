package com.telek.daoImpl;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.telek.model.InformationEntity;
@Repository(value="InfarmationDaoImpl")  
public class InformationDaoImpl extends DAOImpl {
	
	public InformationEntity get(String id) {
		Session session= getSession();
		InformationEntity informationEntity = (InformationEntity) session.get(InformationEntity.class, id);
		session.close();
		return informationEntity;
	}
	
	public InformationEntity addEntity(InformationEntity informationEntity) {
		Session session= getSession();
		session.save(informationEntity);
		session.close();
		return informationEntity;
	}
	
	public List<?> queryInformation(int pageNumber,
			String condition, String time) {
		Session session= getSession();
		String sql = "from InformationEntity where releaseTime <= :time and title like :title or author like :author order by releaseTime desc";
		Query query = session.createQuery(sql);
		query.setFirstResult((pageNumber-1)*10);//设置起始行
		query.setMaxResults(10);//每页条数
		List<?> result = query
	   .setString("time", time)
       .setString("title", "%"+condition+"%")
       .setString("author", "%"+condition+"%")
        .list();
		session.close();
		return result;
	}
	/**
	 * 按类型分页查找未删除资讯
	 *
	 * @author hyj
	 * @date 2017-8-29 下午5:37:14
	 *
	 */
	public List<?> queryInformationByType(int pageNumber,String type, String time) {
		Session session= getSession();
		String sql = "from InformationEntity where  releaseTime <= :time and isDelete != 1 and type = :type order by releaseTime desc";
		Query query = session.createQuery(sql);
		query.setFirstResult((pageNumber-1)*10);//设置起始行
		query.setMaxResults(10);//每页条数
		List<?> result = query
	    .setString("time", time)
        .setString("type", type)
        .list();
		session.close();
		return result;
	}
	public List<?> queryInformationTotalNum(int pageNumber,
			String condition, String time) {
		Session session= getSession();
		String sql = "from InformationEntity where releaseTime <= :time and title like :title or author like :author order by releaseTime desc";
		List<?> result = session.createQuery(sql)
		.setString("time", time)
        .setString("title", "%"+condition+"%")
        .setString("author", "%"+condition+"%")
        .list();
		session.close();
		return result;
	}

	public boolean updateInformationDelete(String id,String isDelete) {
		Session session= getSession();
		String sql = "update InformationEntity set isDelete = :isDelete where informationId = :id";
		Query query = session.createQuery(sql)
			 	   .setString("isDelete", isDelete)
				   .setString("id", id);
		query.executeUpdate();
		session.close();
		return true;
	}
}
