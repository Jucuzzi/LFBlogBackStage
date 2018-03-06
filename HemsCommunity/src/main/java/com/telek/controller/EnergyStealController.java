package com.telek.controller;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.telek.cache.model.UserInfoCache;
import com.telek.model.UserEntity;
import com.telek.service.IUserService;
import com.telek.util.ConstantVar;
import com.telek.util.HttpUtil;
import com.telek.util.Util;

import net.sf.json.JSONObject;

@Controller
public class EnergyStealController {
	
	@Autowired
	private IUserService userServiceImpl;
	
	
	/**
	 * 查询谷电能量主界面
	 * 输出参数：
	 * String AllValleyEnergy 当月谷电总能量
	 * String enviromentLevel 环境等级（窗外等级)
	 * Array friends 好友列表（可偷用户列表）
	 *       String AllValleyEnergy 本月谷电能量
	 *       String canBeSteal 是否有能量可以被偷
	 *       String pictureId 用户头像ID
	 *       String userId 用户ID
	 *       String userName 用户名
	 * Array generatedEnergy 
	 * 		 String ValleyEnergy 剩余的谷电能量
	 * 		 String applianceTypeCode 电器类型
	 *       String canBeSteal 是否可以被偷（1可以0不可以）
	 *       String generatedId 产生的能量Id
	 * String hasRedPacket 是否有红包可以领取
	 * String honorWallLevel 荣誉墙等级
	 * Array thieves 偷取能量的用户数组
	 *       String date 偷取的时间
	 *       String stealEnergy 偷取的能量
	 *       String userId 用户ID
	 *       String userName 用户名
	 * */
	@RequestMapping("/getValleyEnergyMain")
	public void getValleyEnergyMain(HttpServletRequest request, HttpServletResponse response) {
		/* 组装数据发送云端请求 */
		JSONObject obj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		paramObj.put("userId", Util.getCurrentUser(request).getUserId());
		obj.put(ConstantVar.METHOD_NAME, "hemsCircleQueryValleyEnergyMain");
		obj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(obj);
		com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(result);
		if (jsonObj.containsKey("friends")) {
			JSONArray jsonArr = (JSONArray)jsonObj.get("friends") ;
			boolean existFlag = false;
			for (int i = 0; i < jsonArr.size(); i++) {
				com.alibaba.fastjson.JSONObject friend = (com.alibaba.fastjson.JSONObject) jsonArr.get(i);
				for (Map.Entry<String, UserEntity> entry : UserInfoCache.userInfoMap.entrySet()) {
					UserEntity user = entry.getValue();
					if (user.getUserId().equals(friend.get("userId"))) {
						existFlag = true;
						friend.put("userIconPath", user.getUserIconPath());
						break;
					}
				}
				if (!existFlag) {
					userServiceImpl.save(userServiceImpl.getHemsUser(friend.getString("userId"), null));
				}
			}
		}
		
		for (Map.Entry<String, UserEntity> entry : UserInfoCache.userInfoMap.entrySet()) {
			UserEntity user = entry.getValue();
			if (user.getUserId().equals(Util.getCurrentUser(request).getUserId())) {
				jsonObj.put("userIconPath", user.getUserIconPath());
				break;
			}
		}
		// 封装数据打包给前台
		JSONObject jsonObjectAll = new JSONObject();
		jsonObjectAll.put("result", jsonObj);
		try {
			response.getWriter().write(jsonObjectAll.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 查询更多动态界面
	 * 输出参数：
	 * Array root 
	 *       String date 被偷取的时间
	 *       String pictureId 用户头像id
	 *       String stealEnergy 偷取的能量
	 *       String userId 用户ID
	 *       String userName 用户名
	 * String total 记录总条数（两天内）
	 * */
	@RequestMapping("/getStealedList")
	public void getStealedList(HttpServletRequest request, HttpServletResponse response) {
		/* 组装数据发送云端请求 */
		JSONObject obj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		paramObj.put("userId", Util.getCurrentUser(request).getUserId());
		obj.put(ConstantVar.METHOD_NAME, "hemsCircleQueryStealUser");
		obj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(obj);
		result = Util.checkCloudResponseValid(result);
		
		com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(result);
		JSONArray jsonArr = (JSONArray)jsonObj.get("root");
		com.alibaba.fastjson.JSONObject todaySteal = (com.alibaba.fastjson.JSONObject) jsonArr.get(0);
		JSONArray todayStealList = (JSONArray)todaySteal.get("stealUser");
		boolean existFlag = false;
		if (todayStealList.size()!=0) {
			for (int i = 0; i < todayStealList.size(); i++) {
				com.alibaba.fastjson.JSONObject stealler = (com.alibaba.fastjson.JSONObject) todayStealList.get(i);
				for (Map.Entry<String, UserEntity> entry : UserInfoCache.userInfoMap.entrySet()) {
					UserEntity user = entry.getValue();
					if (user.getUserId().equals(stealler.get("userId"))) {
						existFlag = true;
						stealler.put("userIconPath", user.getUserIconPath());
						break;
					}
				}
				if (!existFlag) {
					UserEntity newUser = userServiceImpl.getHemsUser(stealler.getString("userId"), null);
					if (newUser.userId!=null) {
						userServiceImpl.save(newUser);
						stealler.put("userIconPath", newUser.getUserIconPath());
					}
				}
			}
		}
		
		
		com.alibaba.fastjson.JSONObject yesterdaySteal = (com.alibaba.fastjson.JSONObject) jsonArr.get(1);
		JSONArray yesterdayStealList = (JSONArray)yesterdaySteal.get("stealUser");
		boolean existFlag2 = false;
		if (yesterdayStealList.size()!=0) {
			for (int i = 0; i < yesterdayStealList.size(); i++) {
				com.alibaba.fastjson.JSONObject stealler = (com.alibaba.fastjson.JSONObject) yesterdayStealList.get(i);
				for (Map.Entry<String, UserEntity> entry : UserInfoCache.userInfoMap.entrySet()) {
					UserEntity user = entry.getValue();
					if (user.getUserId().equals(stealler.get("userId"))) {
						existFlag2 = true;
						stealler.put("userIconPath", user.getUserIconPath());
						break;
					}
				}
				if (!existFlag2) {
					UserEntity newUser = userServiceImpl.getHemsUser(stealler.getString("userId"), null);
					if (newUser.userId!=null) {
						userServiceImpl.save(newUser);
						stealler.put("userIconPath", newUser.getUserIconPath());
					}
				}
			}
		}
		
		
		JSONObject jsonObjectAll = new JSONObject();
		jsonObjectAll.put("result", jsonObj);
		try {
			response.getWriter().write(jsonObjectAll.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询收集记录界面
	 * 输出参数：
	 * Array root 
	 *       String date 被偷取的时间
	 *       String energy 获取到的能量
	 *       String id 谷电能量id
	 * String total 记录总条数
	 * */
	@RequestMapping("/getStealRecord")
	public void getStealRecord(HttpServletRequest request, HttpServletResponse response) {
		/* 组装数据发送云端请求 */
		JSONObject obj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		paramObj.put("userId", Util.getCurrentUser(request).getUserId());
		paramObj.put("id",request.getParameter("lastRecordId"));
		System.out.println(paramObj);
		obj.put(ConstantVar.METHOD_NAME, "hemsCircleQueryValleyEnergyRecord");
		obj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(obj);
		result = Util.checkCloudResponseValid(result);
		JSONObject jsonObjectAll = new JSONObject();
		jsonObjectAll.put("result", result);
		try {
			response.getWriter().write(jsonObjectAll.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询月度排行榜界面
	 * 输出参数：
	 * String AllValleyEnergy 用户自己的谷电能量
	 * String rank 用户自己的排名
	 * Array topUserList 前30名用户 
	 *       String AllValleyEnergy 总的古典能量
	 *       String pictureId 用户头像Id
	 *       String userId 用户ID
	 *       String userName 用户名
	 * String total 记录总条数
	 * */
	@RequestMapping("/getMonthContributionRank")
	public void getMonthContributionRank(HttpServletRequest request, HttpServletResponse response) {
		/* 组装数据发送云端请求 */
		JSONObject obj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		paramObj.put("userId", Util.getCurrentUser(request).getUserId());
		obj.put(ConstantVar.METHOD_NAME, "hemsCircleQueryMonthContributionRank");
		obj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(obj);
		result = Util.checkCloudResponseValid(result);
		
		com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(result);
		JSONArray jsonArr = (JSONArray)jsonObj.get("topUserList") ;
		boolean existFlag = false;
		if (jsonArr!=null) {
			for (int i = 0; i < jsonArr.size(); i++) {
				com.alibaba.fastjson.JSONObject friend = (com.alibaba.fastjson.JSONObject) jsonArr.get(i);
				for (Map.Entry<String, UserEntity> entry : UserInfoCache.userInfoMap.entrySet()) {
					UserEntity user = entry.getValue();
					if (user.getUserId().equals(friend.get("userId"))) {
						existFlag = true;
						friend.put("userIconPath", user.getUserIconPath());
						break;
					}
				}
				if (!existFlag) {
					UserEntity newUser = userServiceImpl.getHemsUser(friend.getString("userId"), null);
					userServiceImpl.save(newUser);
					friend.put("userIconPath", newUser.getUserIconPath());
				}
			}
		}
		
		for (Map.Entry<String, UserEntity> entry : UserInfoCache.userInfoMap.entrySet()) {
			UserEntity user = entry.getValue();
			if (user.getUserId().equals(Util.getCurrentUser(request).getUserId())) {
				jsonObj.put("userIconPath", user.getUserIconPath());
				break;
			}
		}
		com.alibaba.fastjson.JSONObject topUser = (com.alibaba.fastjson.JSONObject)jsonObj.get("topUser") ;
		for (Map.Entry<String, UserEntity> entry : UserInfoCache.userInfoMap.entrySet()) {
			UserEntity user = entry.getValue();
			if (user.getUserId().equals(topUser.get("userId"))) {
				topUser.put("userIconPath", user.getUserIconPath());
				break;
			}
		}
		
		
		JSONObject jsonObjectAll = new JSONObject();
		jsonObjectAll.put("result", jsonObj);
		try {
			response.getWriter().write(jsonObjectAll.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询总排行榜界面
	 * 输出参数：
	 * String AllValleyEnergy 用户自己的谷电能量
	 * String rank 用户自己的排名
	 * Array topUserList 前30名用户 
	 *       String AllValleyEnergy 总的古典能量
	 *       String pictureId 用户头像Id
	 *       String userId 用户ID
	 *       String userName 用户名
	 * Array topUserList 前30名用户 
	 *       String AllValleyEnergy 总的古典能量
	 *       String pictureId 用户头像Id
	 *       String userId 用户ID
	 *       String userName 用户名
	 * String total 记录总条数
	 * */
	@RequestMapping("/getTotalContributionRank")
	public void getTotalContributionRank(HttpServletRequest request, HttpServletResponse response) {
		/* 组装数据发送云端请求 */
		JSONObject obj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		paramObj.put("userId", Util.getCurrentUser(request).getUserId());
		obj.put(ConstantVar.METHOD_NAME, "hemsCircleQueryCumulativContributionRank");
		obj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(obj);
		result = Util.checkCloudResponseValid(result);
		
		com.alibaba.fastjson.JSONObject jsonObj = JSON.parseObject(result);
		JSONArray jsonArr = (JSONArray)jsonObj.get("topUserList") ;
		boolean existFlag = false;
		for (int i = 0; i < jsonArr.size(); i++) {
			com.alibaba.fastjson.JSONObject friend = (com.alibaba.fastjson.JSONObject) jsonArr.get(i);
			for (Map.Entry<String, UserEntity> entry : UserInfoCache.userInfoMap.entrySet()) {
				UserEntity user = entry.getValue();
				if (user.getUserId().equals(friend.get("userId"))) {
					existFlag = true;
					friend.put("userIconPath", user.getUserIconPath());
					break;
				}
			}
			if (!existFlag) {
				UserEntity newUser = userServiceImpl.getHemsUser(friend.getString("userId"), null);
				userServiceImpl.save(newUser);
				friend.put("userIconPath", newUser.getUserIconPath());
			}
		}
		for (Map.Entry<String, UserEntity> entry : UserInfoCache.userInfoMap.entrySet()) {
			UserEntity user = entry.getValue();
			if (user.getUserId().equals(Util.getCurrentUser(request).getUserId())) {
				jsonObj.put("userIconPath", user.getUserIconPath());
				break;
			}
		}
		com.alibaba.fastjson.JSONObject topUser = (com.alibaba.fastjson.JSONObject)jsonObj.get("topUser") ;
		for (Map.Entry<String, UserEntity> entry : UserInfoCache.userInfoMap.entrySet()) {
			UserEntity user = entry.getValue();
			if (user.getUserId().equals(topUser.get("userId"))) {
				topUser.put("userIconPath", user.getUserIconPath());
				break;
			}
		}
		
		
		JSONObject jsonObjectAll = new JSONObject();
		jsonObjectAll.put("result", jsonObj);
		try {
			response.getWriter().write(jsonObjectAll.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 偷取能量功能
	 * 输出参数：
	 * String remainEnergy 剩余的能量，如果是自己收集，则可以全部收取完
	 * String stealEnergy 偷取的能量
	 * */
	@RequestMapping("/stealValleyEnergy")
	public void stealValleyEnergy(HttpServletRequest request, HttpServletResponse response) {
		/* 组装数据发送云端请求 */
		JSONObject obj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		paramObj.put("userId", Util.getCurrentUser(request).getUserId());
		String generatedId = request.getParameter("generatedId");
		paramObj.put("generatedId", generatedId);
		obj.put(ConstantVar.METHOD_NAME, "hemsCircleStealValleyEnergy");
		obj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(obj);
		result = Util.checkCloudResponseValid(result);
		JSONObject jsonObjectAll = new JSONObject();
		jsonObjectAll.put("result", result);
		try {
			response.getWriter().write(jsonObjectAll.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
