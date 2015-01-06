package com.insframe.server.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.insframe.server.data.repository.GridFsRepository;

@Document(collection="assignments")
public class Assignment {
	
	@Id
	private String id;

	@Indexed(unique=true)
	private String assignmentName;
	private String description;
	private Boolean isTemplate;
	private List<Task> tasks;
	private Date startDate;
	private Date endDate;
	@DBRef
	private User user;
	@DBRef
	private InspectionObject inspectionObject; 
	private List<String> attachmentIds;
	
	public Assignment() {
		super();
	}
	
	public Assignment(String assignmentName, String description, Boolean isTemplate) {
		super();
		this.assignmentName = assignmentName;
		this.description = description;
		this.isTemplate = isTemplate;
	}
	
	public Assignment(String assignmentName, String description, Boolean isTemplate, List<Task> tasks,
						Date startDate, Date endDate, User user, InspectionObject inspectionObject) {
		super();
		this.assignmentName = assignmentName;
		this.description = description;
		this.isTemplate = isTemplate;
		this.tasks = tasks;
		this.startDate = startDate;
		this.endDate = endDate;
		this.user = user;
		this.inspectionObject = inspectionObject;
	}
	
	@Override
	public String toString(){
		return "InspectionObject [id=" + id + ", assignmentName=" + assignmentName + ", description="
				+ description + ", isTemplate=" + isTemplate + ", tasks=" + tasks + ", startDate=" + startDate 
				+ ", endDate=" + endDate + ", user=" + user + ", inspectionObject=" + inspectionObject 
				+ " attachments=" + attachmentIds + "]";
	}
	
	public void addAttachment(String gridFsId) {
		if(attachmentIds == null){
			attachmentIds = new ArrayList<String>();
		}
		attachmentIds.add(gridFsId);
	}
	
//	public void addAttachment(InputStream inputStream, String fileName, String contentType, FileMetaData metaData) {
//		GridFsRepository gridFsDao = new GridFsRepositoryImpl();
//		if(attachmentIds == null){
//			attachmentIds = new ArrayList<String>();
//		}
//		attachmentIds.add(gridFsDao.store(inputStream, fileName, contentType, metaData));
//	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getAssignmentName() {
		return assignmentName;
	}

	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}

	public Boolean getIsTemplate() {
		return isTemplate;
	}

	public void setIsTemplate(Boolean isTemplate) {
		this.isTemplate = isTemplate;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public InspectionObject getInspectionObject() {
		return inspectionObject;
	}

	public void setInspectionObject(InspectionObject inspectionObject) {
		this.inspectionObject = inspectionObject;
	}
}
