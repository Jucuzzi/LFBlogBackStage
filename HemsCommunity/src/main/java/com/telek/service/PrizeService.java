package com.telek.service;

public interface PrizeService {
	public String queryHemsCircleQueryAllPrizes(String endTime,String issued,String pageNum,String pageSize,String prizeType,String redeemCode,String startTime);
	public String queryHemsCircleChangeExchangeState(String prizeId);
	public String queryHemsCircleQueryPrizeNum();
	public String queryHemsCircleAddNewPrize(String prizeNum,String prizeType);
}
