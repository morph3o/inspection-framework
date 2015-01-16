package com.insframe.server.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsCriteria;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

import com.insframe.server.data.repository.GridFsRepository;
import com.insframe.server.model.FileMetaData;
import com.mongodb.gridfs.GridFSDBFile;

@Repository
public class GridFsService implements GridFsRepository {
	
	@Autowired
	GridFsOperations operations; 
	
	@Autowired
	GridFsTemplate gridFsTemplate;
	
	public String store(InputStream inputStream, String fileName, String contentType, FileMetaData metaData) {
		return gridFsTemplate.store(inputStream, fileName, contentType, metaData).getId().toString();
	}
	
	public void deleteById(String id) {
		gridFsTemplate.delete(new Query(GridFsCriteria.where("_id").is(id)));
	}

	public GridFSDBFile findById(String id) {
		return gridFsTemplate.findOne(new Query(GridFsCriteria.where("_id").is(id)));
	}

	public GridFSDBFile findByFilename(String fileName) {
		return gridFsTemplate.findOne(new Query(GridFsCriteria.whereFilename().is(fileName)));
	}

	public List<GridFSDBFile> findAll() {
		return gridFsTemplate.find(null);
	}
}
