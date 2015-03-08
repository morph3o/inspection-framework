package com.insframe.server.model;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.beans.factory.annotation.Autowired;

import com.insframe.server.service.GridFsService;
import com.mongodb.gridfs.GridFSDBFile;

public class Attachment {

	private String gridFsId;
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	private String fileName;
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	private String url;
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	private String description;
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	private String filetype;
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	private long bytes;
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
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
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setAttachmentDetails(GridFSDBFile gridFsFileInformation,
			String protocol, String hostName, String serverContextPath, int port) {
		this.fileName = gridFsFileInformation.getFilename();
		this.uploadDate = gridFsFileInformation.getUploadDate();
		this.filetype = gridFsFileInformation.getContentType();
		this.bytes = gridFsFileInformation.getLength();
		if (gridFsFileInformation.getMetaData().get("fileDescription") != null) {
			this.description = gridFsFileInformation.getMetaData().get("fileDescription").toString();
		} else {
			this.description = "";
		}
		if (serverContextPath != null && serverContextPath != "") {
			this.url = protocol + "://" + hostName + ":" + port + "/"
					+ serverContextPath + "/attachment/" + this.gridFsId;
		} else {
			this.url = protocol + "://" + hostName + ":" + port + "/attachment/" + this.gridFsId;
		}
	}
	

}
