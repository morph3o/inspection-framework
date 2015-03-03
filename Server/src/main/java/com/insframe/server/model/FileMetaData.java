package com.insframe.server.model;


import com.mongodb.BasicDBObject;

@SuppressWarnings("serial")
public class FileMetaData extends BasicDBObject {
	public FileMetaData() {
		super();
	}
	
	public FileMetaData(String fileDescription) {
		super();
		this.setFileDescription(fileDescription); 
	}

	public String getFileDescription() {
		return (String) this.get("fileDescription");
	}

	public void setFileDescription(String fileDescription) {
		this.append("fileDescription", fileDescription);
	}
}
