package com.telek.service;
import org.springframework.stereotype.Service;

public interface StolenEnageService {
	public String queryHemsCircleQueryStolenUserInfo(int userId,int stolenUserId);
	public String queryHemsCircleQueryRedPacketInfo(int userId);
	public String queryHemsCircleExchangeRedPacket(int userId,String exchangeType);

}
