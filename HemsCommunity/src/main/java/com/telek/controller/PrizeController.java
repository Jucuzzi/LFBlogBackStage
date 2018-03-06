package com.telek.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.telek.service.PrizeService;

import net.sf.json.JSONObject;

@Controller
public class PrizeController {
	
	@Autowired
	private PrizeService prizeService;
	
	/**
	 *奖品信息查询
	 * @param request
	 * @param response
	 */
	@RequestMapping("/hemsCircleQueryAllPrizess")
	public void hemsCircleQueryAllPrizess(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("云端请求我进来了");
		
		String endTime = request.getParameter("endTime");
		String issued = request.getParameter("issued");
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		String prizeType = request.getParameter("prizeType");
		String redeemCode = request.getParameter("redeemCode");
		String startTime = request.getParameter("startTime");
		
		String result = prizeService.queryHemsCircleQueryAllPrizes(endTime,issued,pageNum,pageSize,prizeType,redeemCode,startTime);
		try {
			JSONObject jsonObjectAll = new JSONObject();
			jsonObjectAll.put("result", result);
			System.out.println("云端请求的结果0000001111111：   "+result);
			response.getWriter().write(jsonObjectAll.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 *奖品兑换
	 * @param request
	 * @param response
	 */
	@RequestMapping("/hemsCircleChangeExchangeStates")
	public void hemsCircleChangeExchangeStates(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("云端请求我进来了");
		
		String prizeId = request.getParameter("prizeId");
		
		String result = prizeService.queryHemsCircleChangeExchangeState(prizeId);
		try {
			JSONObject jsonObjectAll = new JSONObject();
			jsonObjectAll.put("result", result);
			System.out.println("云端请求的结果0000001111111：   "+result);
			response.getWriter().write(jsonObjectAll.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 *奖品数目信息查询
	 * @param request
	 * @param response
	 */
	@RequestMapping("/hemsCircleQueryPrizeNums")
	public void hemsCircleQueryPrizeNums(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("云端请求我进来了");
		
		String result = prizeService.queryHemsCircleQueryPrizeNum();
		try {
			JSONObject jsonObjectAll = new JSONObject();
			jsonObjectAll.put("result", result);
			System.out.println("云端请求的结果0000001111111：   "+result);
			response.getWriter().write(jsonObjectAll.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 *奖品数目增加
	 * @param request
	 * @param response
	 */
	@RequestMapping("/hemsCircleAddNewPrizes")
	public void hemsCircleAddNewPrizes(HttpServletRequest request, HttpServletResponse response) {
		
		System.out.println("云端请求我进来了");
		
		String prizeNum = request.getParameter("prizeNum");
		String prizeType = request.getParameter("prizeType");
		
		String result = prizeService.queryHemsCircleAddNewPrize(prizeNum,prizeType);
		try {
			JSONObject jsonObjectAll = new JSONObject();
			jsonObjectAll.put("result", result);
			System.out.println("云端请求的结果0000001111111：   "+result);
			response.getWriter().write(jsonObjectAll.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
