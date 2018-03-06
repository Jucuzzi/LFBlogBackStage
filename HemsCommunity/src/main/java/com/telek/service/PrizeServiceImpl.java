package com.telek.service;

import org.springframework.stereotype.Service;

import com.telek.util.ConstantVar;
import com.telek.util.HttpUtil;
import com.telek.util.Util;

import net.sf.json.JSONObject;

@Service("PrizeServiceImpl")
public class PrizeServiceImpl implements PrizeService {
	
	/**
	 * 查询奖品信息接口
	 */
	public String queryHemsCircleQueryAllPrizes(String endTime,String issued,String pageNum,String pageSize,String prizeType,String redeemCode,String startTime) {

		JSONObject jsonObj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		paramObj.put("endTime", "" + endTime);
		paramObj.put("issued", "" + issued);
		paramObj.put("pageNum", "" + pageNum);
		paramObj.put("pageSize", "" + pageSize);
		paramObj.put("prizeType", "" + prizeType);
		paramObj.put("redeemCode", "" + redeemCode);
		paramObj.put("startTime", "" + startTime);
		jsonObj.put(ConstantVar.METHOD_NAME, "hemsCircleQueryAllPrizes");
		jsonObj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(jsonObj);
		result = Util.checkCloudResponseValid(result);
		return result;
		
	}
	
	/**
	 * 奖品兑换接口
	 */
	public String queryHemsCircleChangeExchangeState(String prizeId){

		JSONObject jsonObj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		paramObj.put("prizeId", "" + prizeId);
		jsonObj.put(ConstantVar.METHOD_NAME, "hemsCircleChangeExchangeState");
		jsonObj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(jsonObj);
		result = Util.checkCloudResponseValid(result);
		return result;
		
	}
	
	/**
	 * 奖品数目信息接口
	 */
	public String queryHemsCircleQueryPrizeNum(){

		JSONObject jsonObj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		jsonObj.put(ConstantVar.METHOD_NAME, "hemsCircleQueryPrizeNum");
		jsonObj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(jsonObj);
		result = Util.checkCloudResponseValid(result);
		return result;
		
	}
	
	/**
	 * 增加奖品数目接口
	 */
	public String queryHemsCircleAddNewPrize(String prizeNum,String prizeType){

		JSONObject jsonObj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		
		paramObj.put("prizeNum", "" + prizeNum);
		paramObj.put("prizeType", "" + prizeType);
		
		jsonObj.put(ConstantVar.METHOD_NAME, "hemsCircleAddNewPrize");
		jsonObj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(jsonObj);
		result = Util.checkCloudResponseValid(result);
		return result;
		
	}

}
