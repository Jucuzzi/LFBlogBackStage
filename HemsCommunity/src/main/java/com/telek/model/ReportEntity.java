package com.telek.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="t_report")
public class ReportEntity {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	@Column(name = "reportId", length = 32)
	public String reportId;
	@Column(name = "contentType")
	public String contentType;
	@Column(name = "reportType")
	public String reportType;
	@Column(name = "userId")
	public String userId;
	@Column(name = "contentId")
	public String contentId;
	
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getContentId() {
		return contentId;
	}
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	@Override
	public String toString() {
		return "ReportEntity [reportId=" + reportId + ", contentType=" + contentType + ", reportType=" + reportType
				+ ", userId=" + userId + ", contentId=" + contentId + "]";
	}
	public ReportEntity(String contentType, String reportType, String userId, String contentId) {
		super();
		this.contentType = contentType;
		this.reportType = reportType;
		this.userId = userId;
		this.contentId = contentId;
	}
	
	public ReportEntity() {
		super();
	}
	
}
