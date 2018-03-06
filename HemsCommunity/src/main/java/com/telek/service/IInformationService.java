package com.telek.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.telek.model.InformationEntity;



public interface IInformationService {
	/**
	 * 资讯添加
	 *
	 * @author hyj
	 * @date 2017-8-29 下午5:29:54
	 *
	 */
	@Transactional
	public boolean addEntity(InformationEntity entity);
	/**
	 * 通过Id查询资讯
	 *
	 * @author hyj
	 * @date 2017-8-29 下午5:30:09
	 *
	 */
	public  InformationEntity get(String id);
	
	/**
	 * 模糊分页查询
	 *
	 * @author hyj
	 * @date 2017-8-29 下午5:30:45
	 *
	 */
	public  List<InformationEntity> queryInformation(int pageNumber,String condition,String time);
	/**
	 * 
	 *类型分页查询
	 * @author hyj
	 * @date 2017-8-29 下午5:31:03
	 *
	 */
	public String queryInformationByType(int pageNumber,String type, String time);
	
	/**
	 * 修改资讯删除状态
	 *
	 * @author hyj
	 * @date 2017-8-29 下午5:31:25
	 *
	 */
	public  boolean updateInformationDelete(String id,String delete);
	/**
	 * 查询资讯总条数
	 *
	 * @author hyj
	 * @date 2017-8-29 下午5:31:43
	 *
	 */
	public int queryInformationTotalNum(int pageNumber,String condition, String time);

}
