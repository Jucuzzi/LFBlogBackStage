package com.telek.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_preference")
public class PreferenceEntity {
	
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "fabulousId", length = 32)
	public String fabulousId;
	@Column(name = "userId")
	public String userId;
	@Column(name = "commentId")
	public String commentId;
	
	@Override
	public String toString() {
		return "PreferenceEntity [fabulousId=" + fabulousId + ", userId=" + userId + ", commentId=" + commentId + "]";
	}
	
	public PreferenceEntity(String commentId, String userId) {
		super();
		this.commentId = commentId;
		this.userId = userId;
	}
	
	public PreferenceEntity() {
		super();
	}
	
}
