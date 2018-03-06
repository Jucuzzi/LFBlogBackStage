package com.telek.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.telek.cache.model.ConfigInfoCache;
import com.telek.cache.model.UserInfoCache;
import com.telek.daoImpl.ReportDaoImpl;
import com.telek.model.CommentEntity;
import com.telek.model.PreferenceEntity;
import com.telek.model.ReportEntity;
import com.telek.model.UserEntity;
import com.telek.service.ICommentService;
import com.telek.service.IUserService;
import com.telek.service.PreferenceService;
import com.telek.util.ConstantVar;
import com.telek.util.Util;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private IUserService userServiceImpl;
	@Autowired
	private ICommentService commentServiceImpl;
	@Autowired
	private PreferenceService preferenceServiceImpl;
	@Autowired
	private ReportDaoImpl reportDaoImpl;
	/**
	 * 修改评论删除状态
	 * 
	 */
	@RequestMapping(value = "/updCommentIsdelete.do", method = RequestMethod.POST)
	public void updateCommentById(HttpServletRequest request,
			HttpServletResponse response) {
		String commentId = request.getParameter("commentId");
		String  isDelete = request.getParameter("isDelete");
		// 返回数据
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(
					commentServiceImpl.updateCommentById(commentId,isDelete));
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除评论
	 * 
	 */
	@RequestMapping(value = "/delcomment.do", method = RequestMethod.POST)
	public void delCommentsListByIds(HttpServletRequest request,
			HttpServletResponse response) {
		String commentIds = request.getParameter("commentIds");
		// 返回数据
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(
					commentServiceImpl.delCommentListByIds(commentIds));
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取评论列表信息-模糊查询
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getCommentList.do", method = RequestMethod.POST)
	public void queryCommentListByName(HttpServletRequest request,
			HttpServletResponse response) {
		String condition = request.getParameter("condition");
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		String time= request.getParameter("time");
		if (time == null || "".equals(time)) {
			time = System.currentTimeMillis()+"";
		}
		// 返回数据
		try {
			response.setCharacterEncoding("utf-8");			
			response.getWriter().write(commentServiceImpl.queryCommentsListByName(pageNumber,condition,time));
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getComments")
	public void getComments(HttpServletRequest request, HttpServletResponse response) {
		HttpServletResponse servletResponse = (HttpServletResponse) response;

//		servletResponse.setHeader("Access-Control-Allow-Origin", "*");
		String informationId = request.getParameter("informationId");
		int pageNum = Integer.parseInt(request.getParameter("pageNum"));
		
		String time= request.getParameter("time");
		if (time == null || "".equals(time)) {
			time = System.currentTimeMillis()+"";
		}
		String selfUserId = Util.getCurrentUser(request).getId();
		List<CommentEntity> list= commentServiceImpl.getByInfomationId(informationId, pageNum,time);
		JSONArray jsonArray = new JSONArray();
		for(int i=0; i<list.size(); i++) {
			CommentEntity commentEntity = list.get(i);
			System.out.println(commentEntity);
			System.out.println(commentEntity.getUserId());
			int preferenceCount = preferenceServiceImpl.getCountByCommentId(commentEntity.getCommentId());
			int isThumbed = preferenceServiceImpl.getCountByCommentIdAndUserId(commentEntity.getCommentId(), selfUserId);
			UserEntity user = userServiceImpl.get(commentEntity.getUserId());
			System.out.println(user);
			user.changeImageUrl();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("commentId", ""+commentEntity.getCommentId());
			jsonObject.put("commentTime",Util.stampToDate(Long.parseLong(commentEntity.getCommentTime()),"yyyy-MM-dd HH:mm:ss"));
			jsonObject.put("commentDetail", ""+commentEntity.getCommentDetail());
			jsonObject.put("informationId", ""+commentEntity.getInformationId());
			jsonObject.put("userId", ""+commentEntity.getUserId());
//			jsonObject.put("userName", ""+UserInfoCache.userInfoMap.get(commentEntity.getUserId()));

			jsonObject.put("preferenceCount", ""+preferenceCount);
			jsonObject.put("isThumbed", ""+isThumbed);
			jsonObject.put("name", ""+user.getNickName());
			
			jsonObject.put("imgPath", ""+user.getUserIconPath());
			jsonObject.put("isSelf",commentEntity.getUserId().equals(Util.getCurrentUser(request).getId()));
			jsonArray.add(jsonObject);
		}
		
		JSONObject jsonObjectAll = new JSONObject();
		jsonObjectAll.put("result", jsonArray);
		//举报列表
		JSONArray reportTypeList = new JSONArray();
		for (Map.Entry<String, String> entry : ConfigInfoCache.reportType.entrySet()) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("reportId",entry.getKey());
			jsonObject.put("reportValue", entry.getValue());
			reportTypeList.add(jsonObject);
		}
		
		jsonObjectAll.put("reportTypeList", reportTypeList);
		
		System.out.println(jsonObjectAll);
		try {
			response.getWriter().write(jsonObjectAll.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 点赞
	 * @param request
	 * @param response
	 */
	@RequestMapping("/thumbsUp")
	public void thumbsUp(HttpServletRequest request, HttpServletResponse response) {
		
		String commentId = request.getParameter("commentId");
		String selfUserId = Util.getCurrentUser(request).getId();
		
		int isThumbed = preferenceServiceImpl.getCountByCommentIdAndUserId(commentId, selfUserId);
		
		if(isThumbed == 0) {
			PreferenceEntity preferenceEntity = new PreferenceEntity(commentId, selfUserId);
			preferenceServiceImpl.save(preferenceEntity);
		}
		
		try {
			JSONObject jsonObjectAll = new JSONObject();
			jsonObjectAll.put("result", "successed");
			response.getWriter().write(jsonObjectAll.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 取消点赞
	 * @param request
	 * @param response
	 */
	@RequestMapping("/cancelThumbsUp")
	public void cancelThumbsUp(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("commentId:"+request.getParameter("commentId"));
		
		String commentId = request.getParameter("commentId");
		String selfUserId = Util.getCurrentUser(request).getId();
		
		preferenceServiceImpl.deleteByCommentIdAndUserId(commentId, selfUserId);
		
		try {
			JSONObject jsonObjectAll = new JSONObject();
			jsonObjectAll.put("result", "successed");
			response.getWriter().write(jsonObjectAll.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 举报
	 * @param request
	 * @param response
	 */
	@RequestMapping("/reported")
	public void reported(HttpServletRequest request, HttpServletResponse response) {
		
		String contentId = request.getParameter("commentId");
		String reportType = request.getParameter("reportType");
		String userId = Util.getCurrentUser(request).getId();
		String contenttype = ConstantVar.REPORT_TYPE_TWO;
		
		ReportEntity reportEntity = new ReportEntity(contenttype, reportType, userId, contentId);
		reportDaoImpl.save(reportEntity);
		
		try {
			JSONObject jsonObjectAll = new JSONObject();
			jsonObjectAll.put("result", "successed");
			response.getWriter().write(jsonObjectAll.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 删除评论
	 * @param commentId
	 */
	@RequestMapping("/deleteComment")
	public void deleteComment(HttpServletRequest request, HttpServletResponse response) {
		
		String commentId = request.getParameter("commentId");
		
		int temp = commentServiceImpl.sofetDelete(commentId);
		
		try {
			JSONObject jsonObjectAll = new JSONObject();
			if (temp == 1) {
				jsonObjectAll.put("result", "successed");
			} else {
				jsonObjectAll.put("result", "failed");
			}
			response.getWriter().write(jsonObjectAll.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 评论
	 * @param request
	 * @param response
	 */
	@RequestMapping("/addComment")
	public void addComment(HttpServletRequest request, HttpServletResponse response) {
		
		String commentDetail = request.getParameter("commentDetail");
		String informationId = request.getParameter("informationId");
		String userId = Util.getCurrentUser(request).getId();
		System.out.println(commentDetail+informationId);
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String time =  System.currentTimeMillis()+"";//为获取当前系统时间
		
		CommentEntity commentEntityTemp = new CommentEntity(time, commentDetail, userId, informationId,0);
		
		commentServiceImpl.save(commentEntityTemp);
		
		int pageNum = 1;
		List<CommentEntity> list= commentServiceImpl.getByInfomationId(informationId, pageNum,time);
		JSONArray jsonArray = new JSONArray();
	
		for(int i=0; i<list.size(); i++) {
			CommentEntity commentEntity = list.get(i);
			
			int preferenceCount = preferenceServiceImpl.getCountByCommentId(commentEntity.getCommentId());
			int isThumbed = preferenceServiceImpl.getCountByCommentIdAndUserId(commentEntity.getCommentId(), commentEntity.getUserId());
System.out.println(commentEntity.getUserId());
			UserEntity user = userServiceImpl.get(commentEntity.getUserId());
			user.changeImageUrl();
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("commentId", ""+commentEntity.getCommentId());
			jsonObject.put("commentTime", Util.stampToDate(Long.parseLong(commentEntity.getCommentTime()),"yyyy-MM-dd HH:mm:ss"));
			jsonObject.put("commentDetail", ""+commentEntity.getCommentDetail());
			jsonObject.put("informationId", ""+commentEntity.getInformationId());
			jsonObject.put("userId", ""+commentEntity.getUserId());
			
			jsonObject.put("preferenceCount", ""+preferenceCount);
			jsonObject.put("isThumbed", ""+isThumbed);
			jsonObject.put("name", ""+user.getNickName());
			jsonObject.put("imgPath", ""+user.getUserIconPath());
			
			jsonArray.add(jsonObject);
		}
		
		JSONObject jsonObjectAll = new JSONObject();
		jsonObjectAll.put("result", jsonArray);
		jsonObjectAll.put("time", time);
		try {
			response.getWriter().write(jsonObjectAll.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
