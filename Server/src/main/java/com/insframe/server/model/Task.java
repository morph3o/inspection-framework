package com.insframe.server.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


public class Task {
	public static final Integer STATE_INITIAL = 0;
	public static final Integer STATE_IN_PROGRESS = 1;
	public static final Integer STATE_FINISHED = 2;
	private String taskName;
	private String description;
	private Integer state;
	private List<Task> attachments;
	
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
		return "InspectionObject [taskName=" + taskName + ", description="
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
}
