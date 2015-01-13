package com.insframe.server.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import com.insframe.server.data.repository.AssignmentRepository;
import com.insframe.server.error.InspectionObjectAccessException;
import com.insframe.server.model.Assignment;
import com.insframe.server.model.FileMetaData;
import com.insframe.server.model.InspectionObject;

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
		Assignment assignment = findById(assignmentId);
		assignment.addAttachment(gridFsService.store(inputStream, fileName, contentType, metaData));
		assignmentRepository.save(assignment);
	}
	
	public Assignment save(Assignment assignment) {
		return assignmentRepository.save(assignment);
	}
	
    public void deleteAssignmentById(String id) throws InspectionObjectAccessException{
    	if(findById(id) != null) {;
    		assignmentRepository.delete(id);
    	} else {
    		//TODO: create new exception and raise with different text ID
    		throw new InspectionObjectAccessException(InspectionObjectAccessException.OBJECT_ID_NOT_FOUND_TEXT_ID,new String[]{id});
    	}
    }
	
    public void deleteAll(){
    	assignmentRepository.deleteAll();
    }   

    public Assignment updateById(String id, Assignment updateAssignment) throws InspectionObjectAccessException {
    	Assignment oldAssignment = assignmentRepository.findById(id);
    	if(oldAssignment != null) {
    		oldAssignment.setAssignmentName(updateAssignment.getAssignmentName());
    		oldAssignment.setDescription(updateAssignment.getDescription());

			oldAssignment.setStartDate(updateAssignment.getStartDate());
			oldAssignment.setEndDate(updateAssignment.getEndDate());

    		oldAssignment.setInspectionObject(updateAssignment.getInspectionObject());
    		oldAssignment.setUser(updateAssignment.getUser());
    		oldAssignment.setTasks(updateAssignment.getTasks());
    	} else {
    		//TODO: create new exception and raise with different text ID
    		throw new InspectionObjectAccessException(InspectionObjectAccessException.OBJECT_ID_NOT_FOUND_TEXT_ID,new String[]{id});
    	}
		
		assignmentRepository.save(oldAssignment);
    	return updateAssignment;
    }
}
