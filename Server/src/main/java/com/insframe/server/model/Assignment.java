package com.insframe.server.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "assignments")
public class Assignment {

	public static final Integer STATE_INITIAL = 0;
	public static final Integer STATE_IN_PROGRESS = 1;
	public static final Integer STATE_FINISHED = 2;

	@Id
	private String id;

	@Indexed(unique = true)
	private String assignmentName;
	private String description;
	private Boolean isTemplate;
	private Boolean hasError;
	private Integer state;
	private List<Task> tasks;
	private Date startDate;
	private Date endDate;
	private List<Attachment> attachments;
	@DBRef
	private User user;
	@DBRef
	private InspectionObject inspectionObject;

	private Integer version;

	public String toDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append("ID: " + this.id + "\n");
		sb.append("Name: " + this.assignmentName + "\n");
		sb.append("Description: " + this.description + "\n");
		if(this.startDate != null) {
			sb.append("Start Date: " + this.startDate + "\n");
		} else {
			sb.append("Start Date: No start date defined. \n");
		}
		
		if(this.endDate != null) {
			sb.append("End Date: " + this.endDate + "\n");
		} else {
			sb.append("End Date: No end date defined. \n");
		}
		
		if (this.inspectionObject != null) {
			sb.append("Inspection Object: "
					+ this.inspectionObject.getObjectName() + "\n");
		} else {
			sb.append("Inspection Object: No inspection object assigned. \n");
		}
		
		if (this.user != null) {
			sb.append("Person assigned: " + this.getUser().getFirstName() + " "
					+ this.getUser().getLastName() + "\n");
		} else {
			sb.append("Person assigned: No person assigned. \n");
		}
		
		if(this.tasks.size() != 0) {
			sb.append("Tasks: \n");
			int i = 1;
			for (Task t : this.tasks) {
				sb.append("\t" + i + "- " + t.getTaskName() + "\n");
				sb.append("\t    State: " + t.getStateToString() + "\n");
				if (t.getState() == Task.STATE_ERROR) {
					sb.append("\t\tError Description: \n");
					sb.append("\t\t" + t.getErrorDescription());
				}
				if (t.getAttachments().size() != 0) {
					sb.append("\t    Attachments:\n");
					for (Attachment a : t.getAttachments()) {
						sb.append("\t\tDescription: " + a.getDescription() + "\n");
						sb.append("\t\tFile type: " + a.getFiletype() + "\n");
						sb.append("\t\tUpload date: " + a.getUploadDate() + "\n");
					}
				} else {
					sb.append("\t    No attachments for this task.\n");
				}
				i++;
			}
		} else {
			sb.append("Tasks: No tasks. \n");
		}
		
		if (this.getAttachments() != null) {
			if(this.getAttachments().size() != 0) {
				sb.append("Attachments: \n");
				for (Attachment a : this.getAttachments()) {
					sb.append("\tDescription: " + a.getDescription() + "\n");
					sb.append("\tFile type: " + a.getFiletype() + "\n");
					sb.append("\tUpload date: " + a.getUploadDate() + "\n");
				}
			} else {
				sb.append("No attachments for this assignment.");
			}
		} else {
			sb.append("No attachments for this assignment.");
		}
		return sb.toString();
	}

	public Assignment() {
		super();
	}

	public Assignment(String assignmentName, String description,
			Boolean isTemplate) {
		super();
		this.assignmentName = assignmentName;
		this.description = description;
		this.isTemplate = isTemplate;
		this.tasks = new ArrayList<Task>();
	}

	public Assignment(String assignmentName, String description,
			Boolean isTemplate, Integer state, List<Task> tasks,
			Date startDate, Date endDate, User user,
			InspectionObject inspectionObject) {
		super();
		this.assignmentName = assignmentName;
		this.description = description;
		this.isTemplate = isTemplate;
		this.state = state;
		this.tasks = tasks;
		this.startDate = startDate;
		this.endDate = endDate;
		this.user = user;
		this.inspectionObject = inspectionObject;
	}

	public Assignment(String assignmentName, String description,
			Boolean isTemplate, Integer state, List<Task> tasks,
			Date startDate, Date endDate, User user,
			InspectionObject inspectionObject, List<Attachment> attachments) {
		super();
		this.assignmentName = assignmentName;
		this.description = description;
		this.isTemplate = isTemplate;
		this.state = state;
		this.tasks = tasks;
		this.startDate = startDate;
		this.endDate = endDate;
		this.user = user;
		this.inspectionObject = inspectionObject;
		this.attachments = attachments;
	}

	@Override
	public String toString() {
		return "InspectionObject [id=" + id + ", assignmentName="
				+ assignmentName + ", description=" + description
				+ ", isTemplate=" + isTemplate + ", state=" + state
				+ ", tasks=" + tasks + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", user=" + user
				+ ", inspectionObject=" + inspectionObject + " attachments="
				+ attachments + "]";
	}

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

	public Task getTask(String id, Boolean addAssignmentVersion) {
		if (tasks != null) {
			for (Task task : tasks) {
				if (task.getId().equals(id)) {
					if (addAssignmentVersion = true) {
						task.setAssignmentVersion(this.version);
					}
					return task;
				}
			}
		}
		return null;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public void updateTask(String taskId, Task task) {
		Task updateTask = this.getTask(taskId, false);
		updateTask.setDescription(task.getDescription());
		updateTask.setErrorDescription(task.getErrorDescription());
		updateTask.setState(task.getState());
		updateTask.setTaskName(task.getTaskName());
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
		if (attachments == null) {
			attachments = new ArrayList<Attachment>();
		}
		attachments.add(new Attachment(gridFsId));
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Boolean getHasError() {
		return hasError;
	}

	public void setHasError(Boolean hasError) {
		this.hasError = hasError;
	}
}
