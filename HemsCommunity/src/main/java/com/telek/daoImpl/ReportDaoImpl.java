package com.telek.daoImpl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.telek.model.ReportEntity;

@Repository(value="ReportDaoImpl")  
public class ReportDaoImpl extends DAOImpl {
	
	public List<?> getReportList(String type,String id) {
		Session session= getSession();
		String sql = "from ReportEntity where contentId = :contentId and contentType = :type";
		List<?> result = session.createQuery(sql)
        .setString("contentId", id)
        .setString("type", type)
        .list();
		session.close();
		return result;
	}
	

	
}
