package com.insframe.server.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import com.insframe.server.data.repository.AssignmentRepository;
import com.insframe.server.model.Assignment;
import com.insframe.server.model.FileMetaData;

@Repository
public class AssignmentService {

	@Autowired
	private AssignmentRepository assignmentRepository;
	@Autowired	
	private MongoOperations mongoOpts;
	@Autowired
	GridFsService gridFsService;
	
    public Assignment findById(String id){
    	return assignmentRepository.findById(id);
    }

	public List<Assignment> findAll() {
		return assignmentRepository.findAll();
	}

	public Assignment findByAssignmentName(String assignmentName) {
		return assignmentRepository.findByAssignmentName(assignmentName);
	}
	
	public void addAttachment(String assignmentId, InputStream inputStream, String fileName, String contentType, FileMetaData metaData) {
		findById(assignmentId).addAttachment(gridFsService.store(inputStream, fileName, contentType, metaData));
	}
	
	public Assignment save(Assignment assignment) {
		return assignmentRepository.save(assignment);
	}
	
    public void deleteAll(){
    	assignmentRepository.deleteAll();
    }
    
//    public void deleteInspectionObjectByID(String id) throws InspectionObjectAccessException{
//    	if(findById(id) != null) {;
//    	assignmentRepository.delete(id);
//    	} else {
//    		throw new InspectionObjectAccessException(InspectionObjectAccessException.OBJECT_ID_NOT_FOUND_TEXT_ID,new String[]{id});
//    	}
//    }
    

//    public Assignment updateById(String id, InspectionObject updateInspectionObject) throws InspectionObjectAccessException {
//    	Assignment oldInspectionObject = assignmentRepository.findById(id);
//    	if(oldInspectionObject != null) {
//	    	if(!updateInspectionObject.getObjectName().equalsIgnoreCase(oldInspectionObject.getObjectName())){
//	    		oldInspectionObject.setObjectName(updateInspectionObject.getObjectName());
//			} 
//			if(!updateInspectionObject.getCustomerName().equalsIgnoreCase(oldInspectionObject.getCustomerName())){
//				oldInspectionObject.setCustomerName(updateInspectionObject.getCustomerName());
//			}
//			if(!updateInspectionObject.getDescription().equalsIgnoreCase(oldInspectionObject.getDescription())){
//				oldInspectionObject.setDescription(updateInspectionObject.getDescription());
//			}
//			if(!updateInspectionObject.getLocation().equalsIgnoreCase(oldInspectionObject.getLocation())){
//				oldInspectionObject.setLocation(updateInspectionObject.getLocation());
//			}
//    	} else {
//    		throw new InspectionObjectAccessException(InspectionObjectAccessException.OBJECT_ID_NOT_FOUND_TEXT_ID,new String[]{id});
//    	}
//		
//		inspectionObjectRepository.save(oldInspectionObject);
//    	return updateInspectionObject;
//    }
}
