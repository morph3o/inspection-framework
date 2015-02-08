package com.insframe.server.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

public class Task {
	public static final Integer STATE_INITIAL = 0;
	/**
	 * Do we need an in progress status? because when a user starts a task he/she should finish it and not leave
	 * tasks in progress.
	 * */
	public static final Integer STATE_IN_PROGRESS = 1; 
	public static final Integer STATE_FINISHED = 2;
	
	private String id;
	private String taskName;
	private String description;
	private Integer state;
	private List<String> attachmentIds;

	public Task() {
		super();
		this.setId(ObjectId.get().toString());
	}
	
	public Task(String taskName, String description, Integer state) {
		this.setId(ObjectId.get().toString());
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
	
	public List<String> getAttachmentIds() {
		return attachmentIds;
	}

	public void setAttachmentIds(List<String> attachmentIds) {
		this.attachmentIds = attachmentIds;
	}
	
	public void addAttachment(String gridFsId) {
		if(attachmentIds == null){
			attachmentIds = new ArrayList<String>();
		}
		attachmentIds.add(gridFsId);
	}
}
