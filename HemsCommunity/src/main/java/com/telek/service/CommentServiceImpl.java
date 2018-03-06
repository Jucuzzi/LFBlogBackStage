package com.telek.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.Cache;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.telek.cache.model.ConfigInfoCache;
import com.telek.cache.model.UserInfoCache;
import com.telek.daoImpl.CommentDaoImpl;
import com.telek.daoImpl.InformationDaoImpl;
import com.telek.daoImpl.PrizeInfoImpl;
import com.telek.daoImpl.ReportDaoImpl;
import com.telek.model.CommentEntity;
import com.telek.model.ExchangeEntity;
import com.telek.model.PrizeInfoEntity;
import com.telek.model.ReportEntity;
import com.telek.model.UserEntity;
import com.telek.model.UserRoleRel;
import com.telek.util.ConstantVar;
import com.telek.util.Util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("CommentServiceImpl")
public class CommentServiceImpl implements ICommentService{
	
	@Autowired
	private CommentDaoImpl commentDaoImpl;
	@Autowired
	private ReportDaoImpl reportDaoImpl;
	@Autowired
	private PreferenceService preferenceServiceImpl;
	@Transactional
	public boolean save(CommentEntity entity) {
		return commentDaoImpl.save(entity);
	}
	
	public String queryCommentsListByName(int pageNumber,String condition,String time) {
		JSONObject backJson = new JSONObject();
		List<CommentEntity> commentList = (List<CommentEntity>)commentDaoImpl.queryCommentListByParam(pageNumber,10,condition,time);

		JSONArray commentArr = new JSONArray();
		for (CommentEntity comment : commentList) {
			JSONObject commentObj = comment.getJsonObj();	
			
			commentObj.put("userName", UserInfoCache.userInfoMap.get(""+comment.getUserId()).getName());
			List<ReportEntity> reportList = (List<ReportEntity>)reportDaoImpl.getReportList(ConstantVar.REPORT_TYPE_TWO, comment.getCommentId());
			commentObj.put("reportCount", reportList.size());
			
			JSONArray reportArr = new JSONArray();
			for (Map.Entry<String, String> entry : ConfigInfoCache.reportType.entrySet()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("reportType",entry.getKey());
				jsonObject.put("reportValue", entry.getValue());
				jsonObject.put("num", 0);
				reportArr.add(jsonObject);
			}	
			for (int j = 0; j < reportList.size(); j++) {
				for (int i = 0; i < reportArr.size(); i++) {
					if ((reportList.get(j).getReportType()+"").equals(reportArr.getJSONObject(i).getString("reportType"))) {
						reportArr.getJSONObject(i).put("num",reportArr.getJSONObject(i).getInt("num")+1);
						break;
					}
				}
			}
			String reportContent = "";
			for (int i = 0; i < reportArr.size(); i++) {
				JSONObject jsonObject = reportArr.getJSONObject(i);
				reportContent += jsonObject.getString("reportValue") + ":" + jsonObject.getInt("num")+";";
			}
			commentObj.put("reportContent",reportContent);
			
			//点赞
			commentObj.put("fabulousCount",preferenceServiceImpl.getCountByCommentId(comment.getCommentId()));
			
			commentArr.add(commentObj);
		}	
		backJson.put(ConstantVar.TOTAL, commentDaoImpl.queryCommentsTotal());
		backJson.put(ConstantVar.ROOT, commentArr);
		System.out.println(commentArr);
		return backJson.toString();
	}
	
	public String delCommentListByIds(String commentIds) {

		String[] commentIdArr = commentIds.split(",");

		for (String commentId : commentIdArr) {
			System.out.println("=======================");
			commentDaoImpl.delURRelByCommentId(Integer.parseInt(commentId),"1");
		}
		return ConstantVar.SUCCESSED_RESULT;
	}
	/**
	 * 修改单条评论删除状态
	 *
	 * @author hyj
	 * @date 2017-8-27 下午12:19:06
	 *
	 */
	public String updateCommentById(String commentId,String isDelete) {
		commentDaoImpl.delURRelByCommentId(Integer.parseInt(commentId),isDelete);
		return ConstantVar.SUCCESSED_RESULT;
	}
	/**
	 * 通过主键Id查找评论
	 * @param id
	 * @return
	 */
	public CommentEntity get(int id) {
		String str = "from CommentEntity where id = "+id;
		List<CommentEntity> list = (List<CommentEntity>) commentDaoImpl.findByHql(str);
		if (list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 通过资讯id和页码查找对象
	 * @param id
	 * @return
	 */
	public List<CommentEntity> getByInfomationId(String id, int pageNumber,String time) {
		return commentDaoImpl.queryComments(id, pageNumber, 10,time);
	}
	/**
	 * 通过资讯id和页码查找对象
	 * @param id
	 * @return
	 */
	public long getCommentsTotal() {
		return commentDaoImpl.queryCommentsTotal();
	}
	/**
	 * 软删除评论
	 */
	public int sofetDelete(String commentId) {	
		return commentDaoImpl.softDelete(commentId);
	}
	
	/**
	 * 获取全部奖品
	 * 
	 * @return
	 * @author Xugl @2017-3-17 下午1:29:00
	 */
	public String queryPrizeListAll() {
		JSONObject backJson = new JSONObject();
		List<PrizeInfoEntity> prizeInfoList = commentDaoImpl.queryPrizeListAll();
		JSONArray prizeInfoArray = new JSONArray();
		for (PrizeInfoEntity prizeInfo : prizeInfoList) {
			prizeInfoArray.add(prizeInfo.getJsonObj());
		}
		backJson.put(ConstantVar.TOTAL, prizeInfoArray.size());
		backJson.put(ConstantVar.ROOT, prizeInfoArray);
		return backJson.toString();
	}
	
	
	/**
	 * 更新奖品信息
	 * 
	 */
	
	public String UpdatePrize(PrizeInfoEntity prizeInfo) {
		System.out.println("---------------------");
		commentDaoImpl.updatePrizeInfo(prizeInfo);
		System.out.println("---------------------");
		return ConstantVar.SUCCESSED_RESULT;
	}
	
	
	/**
	 * 根据菜单id查询奖品信息
	 * 
	 */
	public String getMenuInfoById(int prizeId) {
		return commentDaoImpl.getPrieInfoById(prizeId).getJsonObj().toString();
	}
	
	
	/**
	 * 获取所有兑换券
	 */
	public String queryExchangeListAll() {
		JSONObject backJson = new JSONObject();
		List<ExchangeEntity> exchangeList = commentDaoImpl.queryExchangeListAll();
		JSONArray exchangeArray = new JSONArray();
		for (ExchangeEntity exchangeInfo : exchangeList) {
			exchangeArray.add(exchangeInfo.getJsonObj());
		}
		backJson.put(ConstantVar.TOTAL, exchangeArray.size());
		backJson.put(ConstantVar.ROOT, exchangeArray);
		return backJson.toString();
	}
	/**
	 * 更新兑换券信息
	 * 
	 */
	
	public String UpdateExchange(ExchangeEntity exchangeInfo) {
		commentDaoImpl.updateExchangeInfo(exchangeInfo);
		System.out.println("---------------------");
		return ConstantVar.SUCCESSED_RESULT;
	}
}

