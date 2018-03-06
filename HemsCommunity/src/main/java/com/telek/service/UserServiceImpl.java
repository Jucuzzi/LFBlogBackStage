package com.telek.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.telek.cache.model.UserInfoCache;
import com.telek.daoImpl.PrvlgDAOImpl;
import com.telek.model.UserEntity;
import com.telek.util.ConstantVar;
import com.telek.util.HttpUtil;
import com.telek.util.Util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service("UserServiceImpl")
public class UserServiceImpl implements IUserService {

	// private static final Logger LOG = Logger.getLogger(PrvlgService.class);

	@Autowired
	private PrvlgDAOImpl prvlgDaoImpl;

	// @Transactional
	public boolean save(UserEntity entity) {
		// entity.base64ToImg(ConstantVar.IMG_TYPE_ONE);
		return prvlgDaoImpl.saveUserInfo(entity);
	}

	public UserEntity get(String id) {
		UserEntity user = prvlgDaoImpl.getUserInfoById(id);
		return user;
	}

	public String getUserIconPath(String id) {
		UserEntity user = prvlgDaoImpl.getUserInfoById(id);
		return user.getUserIconPath();
	}

	public String userLogin(UserEntity mUser, HttpServletRequest request) {

		// 登录标识
		boolean flag = false;
		JSONObject backJson = new JSONObject();
		// 遍历用户缓存，查找账号密码匹配的用户
		for (Map.Entry<String, UserEntity> entry : UserInfoCache.userInfoMap.entrySet()) {
			UserEntity user = entry.getValue();
			// 账号密码匹配
			System.out.println("从表中取出了用户" + user);
			if (user.getId() == user.getId()) {
				// 获取用户登录请求的session
				HttpSession session = request.getSession();
				// 将用户对象存入session
				session.setAttribute("loginUser", user);

				// 查找用户信息及相应权限
				backJson.put("result", "successed");
				backJson.put("loginUserId", user.getId());
				backJson.put("loginUserName", user.getName());
				backJson.put("loginUserNickName", user.getNickName());
				// 将该用户所拥有的权限放入session中，供前台拼接成一颗树
				// String jsonarray =
				// queryMenuInfoListByUserId(user.getId()).replaceAll("\"",
				// "#");
				// backJson.put("userOwnMenuPrvlg", jsonarray);

				// 查找用户信息及相应权限
				// JSONObject prvlgJson = loadPrvlgByUser(user.getUserId());
				// 用户菜单及操作权限存入session(页面，供后台过滤)
				// session.setAttribute("prvlgMenuBtnJsp",
				// prvlgJson.getString("prvlgMenuJsp") +
				// prvlgJson.getString("prvlgBtnJsp"));

				// 用户拥有的按钮权限
				// backJson.put("prvlgUserOwnBtn",
				// queryUserOwnBtn(user.getUserId()).replaceAll("\"", "#"));

				// 登录成功
				flag = true;
				// 用户操作中将用户登录记录 add by scc 20161011
				// prvlgDAO.saveOrUpdateUserOpt(
				// new UserOperateInfo("", "登入", userAccount, new Date(),
				// Util.getRemortIP(request)));
				// 跳出循环
				break;
			}
		}

		// 组装格式向云端请求用户数据
		JSONObject obj = new JSONObject();
		obj.put("hemsUserAccount", "15958032222");
		obj.put("hemsUserPWD", "111111");
		obj.put("editType", ConstantVar.SAVE_OR_UPDATE);
		JSONArray toSynchUserArr = new JSONArray();
		toSynchUserArr.add(obj);

		// LOG.info("【userLogin】用户登录：" + userAccount + (flag ? "成功" :
		// "账号或密码错误！"));
		if (!flag) {
			backJson.put(ConstantVar.RESULT, ConstantVar.FAILED);
			backJson.put(ConstantVar.DESC, "账号或密码错误！");
		}
		return backJson.toString();
	}
	
	public boolean setUserIconPath(String userId,String userIconData) {
		for (Map.Entry<String, UserEntity> entry : UserInfoCache.userInfoMap.entrySet()) {
			UserEntity user = entry.getValue();
			if (user.getId() == userId) {
				// 账号密码匹配
				System.out.println("从表中取出了用户" + user);
				user.setUserIconPath(userIconData);
				user.base64ToImg(ConstantVar.IMG_TYPE_ONE);
				return prvlgDaoImpl.saveUserInfo(user);
			}
		}
		return false;
//		entity.base64ToImg(ConstantVar.IMG_TYPE_ONE);
	}

	public String frontStageUserLogin(HttpServletRequest request) {
		JSONObject backJson = new JSONObject();
		// 遍历用户缓存，找到这个用户ID在数据库中是否存在
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		for (Map.Entry<String, UserEntity> entry : UserInfoCache.userInfoMap.entrySet()) {
			UserEntity user = entry.getValue();
			if (user.getUserName() == username &&user.getPassword() == password) {
				// 把这个用户信息存入session
				HttpSession session = request.getSession();
				session.setAttribute("loginUser", user);
				backJson.put("login", "successed");
				backJson.put("userId", user.getId());
				UserEntity loginUser = (UserEntity)session.getAttribute("loginUser");
				System.out.println("本次登录的用户是"+loginUser);
				return backJson.toString();
			}
		}
		backJson.put("login", "failed");
		return backJson.toString();
	}

	public String backStageUserLogin(HttpServletRequest request) {
		JSONObject backJson = new JSONObject();
		// 遍历用户缓存，找到这个用户ID在数据库中是否存在
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		if (username.equals("telekadmin") && password.equals("telek7890-")){
			UserEntity user = new UserEntity("wlf", "dtc", "www.baidu.com", "1");
			// 把这个用户信息存入session
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", user);
			backJson.put("login", "successed");
			UserEntity loginUser = (UserEntity)session.getAttribute("loginUser");
			System.out.println("本次登录的用户是"+loginUser);
		} else {
			backJson.put("login", "failed");
		}
		return backJson.toString();
	}

	/**
	 * 同步云端赫马用户信息
	 * 
	 * @author 王力丰
	 * @date 2016年2月24日 下午1:54:37
	 * @param obj
	 * @return
	 */
	public UserEntity getHemsUser(String userId, String id) {
		JSONObject obj = new JSONObject();
		JSONObject paramObj = new JSONObject();
		paramObj.put("userId", userId);
		obj.put(ConstantVar.METHOD_NAME, "hemsCircleGetUserInfo");
		obj.put(ConstantVar.PARAMETER, paramObj);
		String result = HttpUtil.doPost(obj);
		result = Util.checkCloudResponseValid(result);
		UserEntity user = new UserEntity();
		user.setUserId(userId);
		user.setUserType("2");
		if (id != null) {
			user.setId(id);
		}
		if (result != ConstantVar.NET_ERROR && result != ConstantVar.SYSTEM_ERROR && result != ConstantVar.NULL_ERROR) {
			@SuppressWarnings("unchecked")
			Map<String, String> userInfo = (Map<String, String>) JSON.parse(result);
			user.setName(userInfo.get("userName"));
			user.setNickName(userInfo.get("nickName"));
			user.setPictureId(userInfo.get("pictureId"));
			if (userInfo.get("pictureId") == null || userInfo.get("pictureId").equals("")) {
				user.setUserIconPath("userImg.png");
			} else {
				// 向云端获取头像并进行保存
				JSONObject obj2 = new JSONObject();
				JSONObject paramObj2 = new JSONObject();
				paramObj2.put("pictureId", userInfo.get("pictureId"));
				obj2.put(ConstantVar.METHOD_NAME, "hemsCircleGetUserPhoto");
				obj2.put(ConstantVar.PARAMETER, paramObj2);
				String result2 = HttpUtil.doPost(obj2);
				result2 = Util.checkCloudResponseValid(result2);
				Map<String, String> map = (Map<String, String>) JSON.parse(result2);
				System.out.println("map的值为" + map);
				user.setUserIconPath(map.get("picture"));
				user.base64ToImg(1);
			}
		}
		return user;
	}
	
	public String getUserInfo(String id){
		JSONObject backJson = new JSONObject();
		UserEntity user = prvlgDaoImpl.getUserInfoById(id);
		if (user == null) {
			backJson.put("failed", "failed");
		} else {
			backJson = user.jsonObj();
		} 
		return backJson.toString();
	}

}
