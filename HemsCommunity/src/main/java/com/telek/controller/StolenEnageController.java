package com.telek.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.telek.cache.model.UserInfoCache;
import com.telek.model.UserEntity;
import com.telek.service.StolenEnageService;
import com.telek.util.ConstantVar;
import com.telek.util.HttpUtil;
import com.telek.util.Util;

import net.sf.json.JSONObject;
@Controller
public class StolenEnageController {
	@Autowired
	private StolenEnageService stolenEnageService;
	
	/**
	 * 被偷能量者信息查询
	 * @param request
	 * @param response
	 */
	@RequestMapping("/hemsCircleQueryStolenUserInfos")
	public void hemsCircleQueryStolenUserInfos(HttpServletRequest request, HttpServletResponse response) {
		
		String stolenUserId = request.getParameter("stolenUserId");	
		//向云端请求数据
		JSONObject jsonObj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		paramObj.put("userId", Util.getCurrentUser(request).getUserId());
		paramObj.put("stolenUserId", stolenUserId);
		
		
		jsonObj.put(ConstantVar.METHOD_NAME, "hemsCircleQueryStolenUserInfo");
		jsonObj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(jsonObj);
		result = Util.checkCloudResponseValid(result);
		
		com.alibaba.fastjson.JSONObject jsonOBJ = JSON.parseObject(result);
		for (Map.Entry<String, UserEntity> entry : UserInfoCache.userInfoMap.entrySet()) {
			UserEntity user = entry.getValue();
			if (user.getUserId().equals(Util.getCurrentUser(request).getUserId())) {
				jsonOBJ.put("userIconPath", user.getUserIconPath());
			} else if (user.getUserId().equals(stolenUserId)) {
				jsonOBJ.put("stolenUserIconPath", user.getUserIconPath());
			}
		}
		
		try {
			JSONObject jsonObjectAll = new JSONObject();
			jsonObjectAll.put("result", jsonOBJ);
			response.getWriter().write(jsonObjectAll.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 领取红包信息查询
	 * @param request
	 * @param response
	 */
	@RequestMapping("/hemsCircleQueryRedPacketInfos")
	public void hemsCircleQueryRedPacketInfos(HttpServletRequest request, HttpServletResponse response) {
		
		JSONObject jsonObj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		paramObj.put("userId", Util.getCurrentUser(request).getUserId());
		jsonObj.put(ConstantVar.METHOD_NAME, "hemsCircleQueryRedPacketInfo");
		jsonObj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(jsonObj);
		result = Util.checkCloudResponseValid(result);
		
		try {
			JSONObject jsonObjectAll = new JSONObject();
			jsonObjectAll.put("result", result);
			response.getWriter().write(jsonObjectAll.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 领取红包
	 * @param request
	 * @param response
	 */
	@RequestMapping("/hemsCircleExchangeRedPackets")
	public void hemsCircleExchangeRedPackets(HttpServletRequest request, HttpServletResponse response) {
		
		String userId = Util.getCurrentUser(request).getUserId();
		String exchangeType = request.getParameter("exchangeType");	
		JSONObject jsonObj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		paramObj.put("userId", "" + userId);
		paramObj.put("exchangeType", "" + exchangeType);
		jsonObj.put(ConstantVar.METHOD_NAME, "hemsCircleExchangeRedPacket");
		jsonObj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(jsonObj);
		result = Util.checkCloudResponseValid(result);
		
		try {
			JSONObject jsonObjectAll = new JSONObject();
			jsonObjectAll.put("result", result);
			response.getWriter().write(jsonObjectAll.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
