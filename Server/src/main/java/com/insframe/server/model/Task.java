package com.insframe.server.model;

import java.util.ArrayList;
import java.util.List;

public class Task {
	public static final Integer STATE_INITIAL = 0;
	public static final Integer STATE_OKAY = 1;
	public static final Integer STATE_ERROR = 2;
	
	private String id;
	private String taskName;
	private String description;
	private String errorDescription;
	private Integer state;
	private List<Attachment> attachments;

	public Task() {
		super();
	}
	
	public Task(String taskName, String description, Integer state) {
		this.taskName = taskName;
		this.description = description;
		this.state = state;
	}
	
	@Override
	public String toString(){
		return "InspectionObject [taskId =" + id + ", taskName=" + taskName + ", description="
				+ description + ", state=" + state + "]";
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public List<String> listAttachmentIds() {
		List<String> attachmentIds = new ArrayList<String>();
		for (Attachment attachment : attachments) {
			attachmentIds.add(attachment.getGridFsId());
		}
		return attachmentIds;
	}
	
	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	
	public void addAttachment(String gridFsId) {
		if(attachments == null){
			attachments = new ArrayList<Attachment>();
		}
		attachments.add(new Attachment(gridFsId));
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
}
