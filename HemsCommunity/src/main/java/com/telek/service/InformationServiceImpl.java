package com.telek.service;


import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telek.daoImpl.CollectionDaoImpl;
import com.telek.daoImpl.InformationDaoImpl;
import com.telek.model.InformationEntity;
import com.telek.util.ConstantVar;
import com.telek.util.FilesUtil;
import com.telek.util.Util;


@Service("InformationServiceImpl")
public class InformationServiceImpl implements IInformationService{
	
	@Autowired
	private InformationDaoImpl infarmationDaoImpl;
	@Autowired
	private CollectionDaoImpl collectionDaoImpl;
	
//	@Transactional
	public boolean addEntity(InformationEntity entity) {
		entity.base64ToImg(ConstantVar.IMG_TYPE_TWO);
		return infarmationDaoImpl.save(entity);
	}
	
	public InformationEntity get(String id) {
		InformationEntity entity = infarmationDaoImpl.get(id);
		entity.changeImageUrl();
		entity.setReleaseTime(Util.formatDate(new Date(Long.parseLong(entity.getReleaseTime())), "yyyyMMddHHmmss"));
		return entity;
	}



	@SuppressWarnings("unchecked")
	public List<InformationEntity> queryInformation(int pageNumber,
			String condition,String time) {
		// TODO Auto-generated method stub
		List<InformationEntity> list = (List<InformationEntity>)infarmationDaoImpl.queryInformation(pageNumber, condition,time);
		
		for (int i = 0; i<list.size(); i++) {
			list.get(i).changeImageUrl();
			list.get(i).setReleaseTime(Util.formatDate(new Date(Long.parseLong(list.get(i).getReleaseTime())), "yyyyMMddHHmmss"));
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public String queryInformationByType(int pageNumber,
			String type, String time) {
		// TODO Auto-generated method stub
		JSONObject backJson = new JSONObject();
		List<InformationEntity> list = (List<InformationEntity>)infarmationDaoImpl.queryInformationByType(pageNumber, type, time);
		JSONArray array = new JSONArray();
		for (int i = 0; i<list.size(); i++) {
			list.get(i).changeImageUrl();
			list.get(i).setReleaseTime(Util.formatDate(new Date(Long.parseLong(list.get(i).getReleaseTime())), "yyyyMMddHHmmss"));
			JSONObject jsb = list.get(i).entityJSONObject();
			jsb.put("collectNum",""+collectionDaoImpl.queryCollectionNumByInformationId(list.get(i).getInformationId()));
			array.add(jsb);
		}
		backJson.put("infoList", array);
		backJson.put("time", time);
		return backJson.toString();
	}
	@SuppressWarnings("unchecked")
	public int queryInformationTotalNum(int pageNumber,
			String condition, String time) {
		// TODO Auto-generated method stub
		return ((List<InformationEntity>)infarmationDaoImpl.queryInformationTotalNum(pageNumber, condition, time)).size();
	}

	public boolean updateInformationDelete(String id,String isDelete) {
		// TODO Auto-generated method stub
		return infarmationDaoImpl.updateInformationDelete(id,isDelete);
	}
	
}
