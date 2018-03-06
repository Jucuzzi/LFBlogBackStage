package com.telek.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.telek.model.InformationEntity;
import com.telek.model.UserEntity;
import com.telek.service.IUserService;
import com.telek.util.ConstantVar;


@Controller
public class UserController {
	@Autowired
	private IUserService userService;
	
	/**
	 * 前台用户登录
	 * 
	 * @param request
	 * @param response
	 * @author 王力丰 @2017-8-29 下午5:55:19
	 */
	@RequestMapping(value = "/frontStageUserlogin")
	public void userLogin(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("进行前台登录");
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(userService.frontStageUserLogin(request));
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 后台用户登录
	 * 
	 * @param request
	 * @param response
	 * @author 王力丰 @2017-8-29 下午5:55:19
	 */
	@RequestMapping(value ="/backstageUserLogin")
	public void saveUser(HttpServletRequest request, HttpServletResponse response, UserEntity u) {
		System.out.println("进行后台登录");
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(userService.backStageUserLogin(request));
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping (value ="/uploadUserIcon")
	public void uploadUserIcon (HttpServletRequest request,HttpServletResponse response,String userId,String userIconData) {
		System.out.println("进行头像更换");
		// 返回数据
		try {
			if(userService.setUserIconPath(userId,userIconData)){
				response.getWriter().write(ConstantVar.SUCCESSED_RESULT);
			} else {
				response.getWriter().write(ConstantVar.FAILED_RESULT);
			}
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping (value ="/queryUserInfo")
	public void queryUserInfo (HttpServletRequest request,HttpServletResponse response,String userId) {
		System.out.println("获取用户信息");
		// 返回数据
		try {
			if(userService.get(userId) !=null){
				response.getWriter().write(userService.getUserInfo(userId));
			} else {
				response.getWriter().write(ConstantVar.FAILED_RESULT);
			}
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
