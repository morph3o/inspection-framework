package com.insframe.server.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="inspection_objects")
public class InspectionObject {
	
	@Id
	private String id;

	@Indexed(unique=true)
	private String objectName;
	private String description;
	private String location;
	private String customerName;
	private List<Attachment> attachments;
	
	public InspectionObject() {
		super();
	}
	
	public InspectionObject(String objectName, String description, String location, String customerName) {
		this.objectName = objectName;
		this.description = description;
		this.location = location;
		this.customerName = customerName;
	}
	
	public InspectionObject(String objectName, String description, String location, String customerName, List<Attachment> attachments) {
		this.objectName = objectName;
		this.description = description;
		this.location = location;
		this.customerName = customerName;
		this.attachments = attachments;
	}
	
	@Override
	public String toString(){
		return "InspectionObject [id=" + id + ", objectName=" + objectName + ", description="
				+ description + ", location=" + location+ ", customerName=" + customerName 
				+ " attachments=" + attachments + "]";
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

}
