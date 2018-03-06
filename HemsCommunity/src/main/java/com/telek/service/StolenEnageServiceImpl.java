package com.telek.service;

import org.springframework.stereotype.Service;

import com.telek.util.ConstantVar;
import com.telek.util.HttpUtil;
import com.telek.util.Util;

import net.sf.json.JSONObject;

@Service("StolenEnageServiceImpl")
public class StolenEnageServiceImpl implements StolenEnageService{
	
	/*
	 * 查询被偷能量者信息
	 */
	public String queryHemsCircleQueryStolenUserInfo(int userId,int stolenUserId) {

		JSONObject jsonObj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		paramObj.put("userId", "" + userId);
		paramObj.put("stolenUserId", "" + stolenUserId);
		jsonObj.put(ConstantVar.METHOD_NAME, "hemsCircleQueryStolenUserInfo");
		jsonObj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(jsonObj);
		result = Util.checkCloudResponseValid(result);
		return result;
	}
	
	/*
	 * 查询领取红包信息
	 */
	public String queryHemsCircleQueryRedPacketInfo(int userId) {

		JSONObject jsonObj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		paramObj.put("userId", "" + userId);
		jsonObj.put(ConstantVar.METHOD_NAME, "hemsCircleQueryRedPacketInfo");
		jsonObj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(jsonObj);
		result = Util.checkCloudResponseValid(result);
		return result;
	}
	
	/*
	 * 领取红包
	 */
	public String queryHemsCircleExchangeRedPacket(int userId,String exchangeType) {

		JSONObject jsonObj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		paramObj.put("userId", "" + userId);
		paramObj.put("exchangeType", "" + exchangeType);
		jsonObj.put(ConstantVar.METHOD_NAME, "hemsCircleExchangeRedPacket");
		jsonObj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(jsonObj);
		result = Util.checkCloudResponseValid(result);
		return result;
	}

}
