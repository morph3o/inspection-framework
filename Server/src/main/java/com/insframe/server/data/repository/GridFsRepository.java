package com.insframe.server.data.repository;

import java.io.InputStream;
import java.util.List;

import com.insframe.server.model.FileMetaData;
import com.mongodb.gridfs.GridFSDBFile;

public interface GridFsRepository {
	public String store(InputStream inputStream, String fileName, String contentType, FileMetaData metaData);
	public GridFSDBFile findById(String id);
	public GridFSDBFile findByFilename(String filename);
	public List<GridFSDBFile> findAll();
}
