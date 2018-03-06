package com.telek.service;



import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.telek.model.CommentEntity;
import com.telek.model.ExchangeEntity;
import com.telek.model.PrizeInfoEntity;

public interface ICommentService {
	@Transactional
	public boolean save(CommentEntity entity);
	public CommentEntity get(int id);
	
	public String queryCommentsListByName(int pageNumber,String condition,String time);
	public String delCommentListByIds(String commentIds);
	public List<CommentEntity> getByInfomationId(String id, int pageNumber, String time);
	public int sofetDelete(String CommentId);
	public String queryPrizeListAll();
	public String UpdatePrize(PrizeInfoEntity prizeInfo);
	public String queryExchangeListAll();
	public String UpdateExchange(ExchangeEntity exchangeInfo);
	public String getMenuInfoById(int prizeId);
	public long getCommentsTotal();
	/**
	 * 修改单条评论删除状态
	 *
	 * @author hyj
	 * @date 2017-8-27 下午12:19:06
	 *
	 */
	public String updateCommentById(String commentId,String isDelete);
}

