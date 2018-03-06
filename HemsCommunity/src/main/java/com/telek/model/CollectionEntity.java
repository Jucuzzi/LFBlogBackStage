package com.telek.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.telek.util.Util;

import net.sf.json.JSONObject;

/**
 * 咨询收藏信息
 * 
 * @author Xugl
 * 
 * @date 2016-2-2 下午3:13:34
 */
@Entity
@Table(name = "t_collection")

public class CollectionEntity extends BaseModel {
	

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "collection_id", length = 32)
	private String collectionId;// 角色标识

	@Column(name = "user_id", length = 50)
	private String userId;// 角色名称

	@Column(name = "information_id", nullable = true, length = 250)
	private String informationId;// 角色描述
	@Column(name = "collectionTime")
	public String collectionTime;//时间

	public String getCollectionTime() {
		return collectionTime;
	}

	public void setCollectionTime(String collectionTime) {
		this.collectionTime = collectionTime;
	}

	public CollectionEntity() {
		super();
	}

	public String getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getInformationId() {
		return informationId;
	}

	public void setInformationId(String informationId) {
		this.informationId = informationId;
	}



	@Override
	public String toString() {
		return "collection [collectionId=" + collectionId + ", userId=" + userId + ", informationId=" + informationId +
				",collectionTime"+collectionTime+"]";
	}

	public JSONObject getJsonObj() {
		JSONObject obj = new JSONObject();
		obj.put("collectionId", collectionId);
		obj.put("userId", userId);
		obj.put("informationId", informationId);
		obj.put("collectionTime", collectionTime);
		return obj;
	}


}
