package com.telek.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.telek.model.CollectionEntity;
import com.telek.model.CommentEntity;
import com.telek.model.InformationEntity;
import com.telek.service.CollectionService;
import com.telek.util.ConstantVar;
import com.telek.util.Util;

import net.sf.json.JSONObject;

@Controller
public class CollectionController {
	
	@Autowired
	private CollectionService collectionService;
	
	//查询某一条咨询收藏
	@RequestMapping ( "/queryCollections" )
	public void queryCollections (HttpServletRequest request,HttpServletResponse response) {
		// 返回数据
		try {
			String informationId = request.getParameter("informationId");
			List<CollectionEntity> list = collectionService.getByInfomationIdAndUserId(informationId);
			JSONObject backJson = new JSONObject();
			backJson.put("infoList", list);
			response.getWriter().write(backJson.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping ( "/addCollections" )
	public void addCollections (HttpServletRequest request,HttpServletResponse response,CollectionEntity collection) {
		// 返回数据
		try {
			collection.setUserId(Util.getCurrentUser(request).getId());
			collection.setCollectionTime(System.currentTimeMillis()+"");
			if(collectionService.addCollectionEntity(collection)){
				response.getWriter().write(ConstantVar.SUCCESSED_RESULT);
			} else {
				response.getWriter().write(ConstantVar.FAILED_RESULT);
			}
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping ( "/queryCollectionsInfo" )
	public void queryCollectionsInfo (HttpServletRequest request,HttpServletResponse response) {
		// 返回数据
		
		try {
			String informationId = request.getParameter("informationId");
			String userId = Util.getCurrentUser(request).getId();
			response.getWriter().write(collectionService.queryCollectionsInfo(informationId,userId));
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 查询收藏列表
	 *
	 * @author hyj
	 * @date 2017-9-6 下午2:04:19
	 * @param request
	 * @param response
	 *
	 */
	@RequestMapping ( "/queryCollectionsByUser" )
	public void queryCollectionsByUser (HttpServletRequest request,HttpServletResponse response) {
		// 返回数据
		try {
			int pageNumber = Integer.parseInt(request.getParameter("pageNumber") == null ? "1" : request.getParameter("pageNumber"));
			String userId = Util.getCurrentUser(request).getId();
			String time= request.getParameter("time");
			if (time == null || "".equals(time)) {
				time = System.currentTimeMillis()+"";
			}
			response.getWriter().write(collectionService.queryCollectionsByUserId(userId,pageNumber,time));
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//删除收藏
	@RequestMapping ( "/collectionDelete" )
	public void collectionDelete (HttpServletRequest request,HttpServletResponse response) {
		// 返回数据
		try {
			String informationId = request.getParameter("informationId");
			String  userId =  Util.getCurrentUser(request).getId();
			if (collectionService.deleteByInformationIdAndUserIds(informationId, userId)){
				JSONObject backJson = new JSONObject();
				backJson.put("informationId", ""+informationId);
				backJson.put("result", "successed");
				response.getWriter().write(backJson.toString());
			} else {
				response.getWriter().write(ConstantVar.FAILED_RESULT);
			}
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//查询某一条咨询评论
		@RequestMapping ( "/queryComments" )
		public void queryComments (HttpServletRequest request,HttpServletResponse response) {
			// 返回数据
			try {
				String informationId = request.getParameter("informationId");
				List<CommentEntity> list = collectionService.getByInfomationId(informationId);
				JSONObject backJson = new JSONObject();
				backJson.put("infoList", list);
				response.getWriter().write(backJson.toString());
				response.getWriter().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
