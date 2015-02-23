package com.insframe.server.model;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.insframe.server.service.GridFsService;
import com.mongodb.gridfs.GridFSDBFile;

public class Attachment {

	private String gridFsId;
	private String url;
	private String description;
	private String filetype;
	private long bytes;
	private Date uploadDate;
	
	@Autowired
	GridFsService gridFsService;
	
	
	public Attachment() {
		super();
	}
	
	public Attachment(String gridFsId) {
		super();
		this.gridFsId = gridFsId;
	}


	public String getGridFsId() {
		return gridFsId;
	}


	public void setGridFsId(String gridFsId) {
		this.gridFsId = gridFsId;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public String getFiletype() {
		return filetype;
	}


	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	
	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	public long getBytes() {
		return bytes;
	}

	public void setBytes(long bytes) {
		this.bytes = bytes;
	}
	
	public void setAttachmentDetails(GridFSDBFile gridFsFileInformation, String hostName, String port) {
		this.uploadDate = gridFsFileInformation.getUploadDate();
		this.filetype = gridFsFileInformation.getContentType();
		this.bytes = gridFsFileInformation.getLength();
		this.description = gridFsFileInformation.getMetaData().get("description").toString();
		this.url = "http://localhost:8080";
	}
}
