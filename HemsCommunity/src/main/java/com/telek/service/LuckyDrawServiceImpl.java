package com.telek.service;

import net.sf.json.JSONObject;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.telek.cache.model.UserInfoCache;
import com.telek.model.UserEntity;
import com.telek.util.ConstantVar;
import com.telek.util.HttpUtil;
import com.telek.util.Util;

@Service("LuckyDrawServiceImpl")
public class LuckyDrawServiceImpl implements LuckyDrawService {

	@Autowired
	IUserService userServiceImpl;
	/**
	 * 查询中奖主页面信息接口
	 */
	public String hemsCircleQueryPrizeDrawMain(int userId) {

		JSONObject jsonObj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		paramObj.put("userId", "" + userId);
		jsonObj.put(ConstantVar.METHOD_NAME, "hemsCircleQueryPrizeDrawMain");
		jsonObj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(jsonObj);
		result = Util.checkCloudResponseValid(result);
		return result;
		
	}
	
	/**
	 * 查询中奖历史记录接口
	 */
	public String hemsCircleQueryPrizeDrawHistory(int userId) {

		JSONObject jsonObj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		paramObj.put("userId", "" + userId);
		jsonObj.put(ConstantVar.METHOD_NAME, "hemsCircleQueryPrizeDrawHistory");
		jsonObj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(jsonObj);
		result = Util.checkCloudResponseValid(result);
		return result;
		
	}
	
	/**
	 * 抽奖接口
	 */
	public String hemsCirclePrizeDraw(int userId) {

		JSONObject jsonObj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		paramObj.put("userId", "" + userId);
		jsonObj.put(ConstantVar.METHOD_NAME, "hemsCirclePrizeDraw");
		jsonObj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(jsonObj);
		result = Util.checkCloudResponseValid(result);
		return result;
		
	}
	/*
	 * 签到接口
	 */
	public String queryHemsCircleSignIn(int userId) {

		JSONObject jsonObj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		paramObj.put("userId", "" + userId);
		jsonObj.put(ConstantVar.METHOD_NAME, "hemsCircleSignIn");
		jsonObj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(jsonObj);
		result = Util.checkCloudResponseValid(result);
		return result;
		
	}
	/*
	 * 查询积分主界面接口
	 */
	public String queryemsCircleQueryPointsMain(int userId) {

		JSONObject jsonObj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		paramObj.put("userId", "" + userId);
		jsonObj.put(ConstantVar.METHOD_NAME, "hemsCircleQueryPointsMain");
		jsonObj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(jsonObj);
		result = Util.checkCloudResponseValid(result);
		
		com.alibaba.fastjson.JSONObject jsonOBJ = JSON.parseObject(result);
		if (jsonOBJ.containsKey("signInList")) {
			JSONArray jsonArr = (JSONArray)jsonOBJ.get("signInList") ;
			boolean existFlag = false;
			if (jsonArr.size()!=0) {
				for (int i = 0; i < jsonArr.size(); i++) {
					com.alibaba.fastjson.JSONObject signUser = (com.alibaba.fastjson.JSONObject) jsonArr.get(i);
					for (Map.Entry<String, UserEntity> entry : UserInfoCache.userInfoMap.entrySet()) {
						UserEntity user = entry.getValue();
						if (user.getUserId().equals(signUser.get("userId"))) {
							existFlag = true;
							signUser.put("userIconPath", user.getUserIconPath());
							break;
						}
					}
					if (!existFlag) {
						userServiceImpl.save(userServiceImpl.getHemsUser(signUser.getString("userId"), null));
					}
				}
			}
		}
		
		return jsonOBJ.toJSONString();	
	}
	
	/*
	 * 查询签到历史
	 */
	public String queryemsHemsCircleQuerySignInState(int userId) {

		JSONObject jsonObj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		paramObj.put("userId", "" + userId);
		jsonObj.put(ConstantVar.METHOD_NAME, "hemsCircleQuerySignInState");
		jsonObj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(jsonObj);
		result = Util.checkCloudResponseValid(result);
		return result;	
	}

}
