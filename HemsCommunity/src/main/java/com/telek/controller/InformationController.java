package com.telek.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.telek.model.InformationEntity;
import com.telek.service.IInformationService;
import com.telek.util.ConstantVar;
import com.telek.util.Util;
@Controller
public class InformationController{

	@Autowired
	private IInformationService iInformationService;
	@RequestMapping ( "/addinformation" )
	public void addInformation (HttpServletRequest request,HttpServletResponse response,InformationEntity info) {
		// 返回数据
		try {
			info.setReleaseTime(System.currentTimeMillis()+"");
			if(iInformationService.addEntity(info)){
				response.getWriter().write(ConstantVar.SUCCESSED_RESULT);
			} else {
				response.getWriter().write(ConstantVar.FAILED_RESULT);
			}
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping ( "/queryinformation" )
	public void queryInformation (HttpServletRequest request,HttpServletResponse response) {
		// 返回数据
		try {
			int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			String condition = request.getParameter("condition");
			String time= request.getParameter("time");
			if (time == null || "".equals(time)) {
				time = System.currentTimeMillis()+"";
			}
			List<InformationEntity> list = iInformationService.queryInformation(pageNumber, condition, time);
			JSONObject backJson = new JSONObject();
			backJson.put("total", ""+iInformationService.queryInformationTotalNum(pageNumber, condition, time));
			backJson.put("infoList", list);
			backJson.put("time", time);
			System.out.print(backJson.toString());
			response.getWriter().write(backJson.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping ( "/queryinformationByType" )
	public void queryInformationByType (HttpServletRequest request,HttpServletResponse response) {
		// 返回数据
		try {
			int pageNumber = Integer.parseInt(request.getParameter("pageNumber") == null ? "1" : request.getParameter("pageNumber"));
			String type = request.getParameter("type");
			String time= request.getParameter("time");
			if (time == null || "".equals(time)) {
				time = System.currentTimeMillis()+"";
			}
			response.getWriter().write(iInformationService.queryInformationByType(pageNumber, type,time));
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping ( "/queryinformationById" )
	public void queryInformationById (HttpServletRequest request,HttpServletResponse response) {
		// 返回数据
		try {
			String informationId = request.getParameter("informationId");
			InformationEntity informationEntity= iInformationService.get(informationId);
			response.getWriter().write(informationEntity.entityJSONObject().toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@RequestMapping ( "/updateInformationIsDelete" )
	public void updateInformationIsDelete (HttpServletRequest request,HttpServletResponse response) {
		// 返回数据
		try {
			String informationId = request.getParameter("informationId");
			String  isDelete = request.getParameter("isDelete");
			if (iInformationService.updateInformationDelete(informationId, isDelete)){
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
}
