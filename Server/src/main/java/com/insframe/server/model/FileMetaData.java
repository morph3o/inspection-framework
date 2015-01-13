package com.insframe.server.model;


import com.mongodb.BasicDBObject;

@SuppressWarnings("serial")
public class FileMetaData extends BasicDBObject {
	private String fileDescription;
	
	public FileMetaData(String fileDescription) {
		this.setFileDescription(fileDescription); 
	}

	public String getFileDescription() {
		return fileDescription;
	}

	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}
}
