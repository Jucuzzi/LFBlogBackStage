package com.telek.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.telek.util.FilesUtil;

import net.sf.json.JSONObject;



@Entity
@Table(name="t_user")
public class UserEntity extends BaseModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "id", length = 32)
	private String id;// 系统权限标识
	
	@Column(name = "name")
	public String name;
	@Column(name = "nickName")
	public String nickName;
	@Column(name = "userIconPath")
	public String userIconPath;
	@Column(name = "userType")
	public String userType;
	@Column(name = "userId")
	public String userId;
	@Column(name = "pictureId")
	public String pictureId;
	@Column(name = "userName")
	public String userName;
	@Column(name = "password")
	public String password;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserIconPath() {
		return userIconPath;
	}
	public void setUserIconPath(String userIconPath) {
		this.userIconPath = userIconPath;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPictureId() {
		return pictureId;
	}
	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}
	public UserEntity() {
		super();
	}
	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", userid=" + userId +",name=" + name + ", nickName=" + nickName
				+ ", userIconPath=" + userIconPath + ", userType=" + userType + ", pictureId" + pictureId + "]";
	}
	public UserEntity(String name, String nickName, String userIconPath, String userType) {
		super();
		this.name = name;
		this.nickName = nickName;
		this.userIconPath = userIconPath;
		this.userType = userType;
	}
	
	public JSONObject jsonObj() {
		JSONObject obj = new JSONObject();
		obj.put("id", id);
		obj.put("name", name);
		obj.put("nickName", nickName);
		obj.put("userIconPath", userIconPath);
		obj.put("userType", userType);
		obj.put("userId",userId);
		obj.put("pictureId",pictureId);
		obj.put("userName", userName);
		obj.put("password", password);
		return obj;
	}
	
	/**
	 * base64转为png图片，并存下路径-------存储数据后手动执行一次
	 *
	 * @author hyj
	 * @date 2017-8-22 下午5:50:01
	 *
	 */
	public void base64ToImg(int Type){
		this.userIconPath = FilesUtil.base64ToImgPath(Type, this.userIconPath);
	}
	/**
	 * 获取完整图片路径---在数据查询完后手动执行一次
	 *
	 * @author hyj
	 * @date 2017-8-22 下午5:50:46
	 *
	 */
	public void changeImageUrl(){
		this.userIconPath = FilesUtil.getImageUrl(this.userIconPath);
	}
}
