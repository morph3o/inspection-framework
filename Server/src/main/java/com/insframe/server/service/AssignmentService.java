package com.insframe.server.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import com.insframe.server.data.repository.AssignmentRepository;
import com.insframe.server.error.AssignmentAccessException;
import com.insframe.server.error.AssignmentCreationException;
import com.insframe.server.error.InspectionObjectAccessException;
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
	@Autowired
	InspectionObjectService inspectionObjectService;
	@Autowired
	UserService userService;
	
    public void addAttachmentToAssignment(String assignmentId, InputStream inputStream, String fileName, String contentType, FileMetaData metaData) throws AssignmentAccessException {
		Assignment assignment = findById(assignmentId);
		assignment.addAttachment(gridFsService.store(inputStream, fileName, contentType, metaData));
		assignmentRepository.save(assignment);
	}

	public Assignment findById(String id) throws AssignmentAccessException{
    	Assignment queriedAssignment = assignmentRepository.findById(id);
    	if(queriedAssignment == null) {
    		throw new AssignmentAccessException(AssignmentAccessException.OBJECT_ID_NOT_FOUND_TEXT_ID,new String[]{id});
    	}
    	return queriedAssignment;
    }

	public List<Assignment> findAll() throws AssignmentAccessException {
		List<Assignment> assignments = assignmentRepository.findAll();
		if(assignments == null) {
			throw new AssignmentAccessException(AssignmentAccessException.NO_OBJECTS_FOUND_TEXT_ID,new String[]{});
		}
		return assignments;
	}

	public Assignment findByAssignmentName(String assignmentName) throws AssignmentAccessException {
		Assignment queriedAssignment = assignmentRepository.findByAssignmentName(assignmentName);
		if(queriedAssignment == null) {
			throw new AssignmentAccessException(AssignmentAccessException.NO_OBJECTS_FOUND_BY_NAME_TEXT_ID,new String[]{});
		}
		return queriedAssignment;
	}
	
	public void deleteAssignmentById(String id) throws AssignmentAccessException{
		findById(id);
		assignmentRepository.delete(id);
	}

	public void deleteAll(){
		assignmentRepository.deleteAll();
	}

	public void deleteAttachment(String assignmentId, String attachmentId) throws AssignmentAccessException {
		Assignment assignment = findById(assignmentId);
		List<String> attachmentIds = assignment.getAttachmentIds();
		for (String assignedAttachmentId : attachmentIds) {
			if(assignedAttachmentId.equalsIgnoreCase(attachmentId)){
				attachmentIds.remove(assignedAttachmentId);
			}
		}
	}
	
	public void deleteAllAttachments(String assignmentId) throws AssignmentAccessException {
		Assignment assignment = findById(assignmentId);
		List<String> attachmentIds = assignment.getAttachmentIds();
		for (String attachmentId : attachmentIds) {
			gridFsService.deleteById(attachmentId);
		}
		attachmentIds.clear();
		assignmentRepository.save(assignment);
	}
	
	public Assignment save(Assignment assignment) throws AssignmentCreationException {
		if(assignment.getAssignmentName() == null
			|| assignment.getIsTemplate() == null) {
			throw new AssignmentCreationException(AssignmentCreationException.MISSING_MANDATORY_PARAMETER_TEXT_ID,new String[]{});
		}
		
		if(assignment.getIsTemplate() == false) {
			if(checkInspectionObjectExists(assignment) == false) {
				throw new AssignmentCreationException(AssignmentCreationException.INVALID_USER_REF_TEXT_ID,new String[]{});
			}
			if(checkUserExists(assignment) == false) {
				throw new AssignmentCreationException(AssignmentCreationException.INVALID_INSP_OBJECT_REF_TEXT_ID,new String[]{});
			}
			if(assignment.getAttachmentIds() == null) {
				assignment.setAttachmentIds(new ArrayList<String>());
			}
			if(checkAttachmentsExist(assignment) == false) {
				throw new AssignmentCreationException(AssignmentCreationException.INVALID_ATTACHMENT_REF_TEXT_ID,new String[]{});
			}
		} else {
			if (assignment.getInspectionObject() != null) {
				throw new AssignmentCreationException(AssignmentCreationException.INVALID_TEMPLATE_ATTR_TEXT_ID,new String[]{"inspectionObject"});
			}
			if (assignment.getUser() != null) {
				throw new AssignmentCreationException(AssignmentCreationException.INVALID_TEMPLATE_ATTR_TEXT_ID,new String[]{"user"});
			}
			if (assignment.getStartDate() != null) {
				throw new AssignmentCreationException(AssignmentCreationException.INVALID_TEMPLATE_ATTR_TEXT_ID,new String[]{"startDate"});
			}
			if (assignment.getEndDate() != null) {
				throw new AssignmentCreationException(AssignmentCreationException.INVALID_TEMPLATE_ATTR_TEXT_ID,new String[]{"endDate"});
			}
			if (assignment.getAttachmentIds() != null) {
				throw new AssignmentCreationException(AssignmentCreationException.INVALID_TEMPLATE_ATTR_TEXT_ID,new String[]{"attachments"});
			}
		}	
		try {
			return assignmentRepository.save(assignment);	
		} catch (Exception e) {
			// TODO: should be more detailed here! Only catch Duplicate Key Exception, but what is right exception name to catch?
			if(assignment.getId() == null) {
				throw new AssignmentCreationException(AssignmentCreationException.DUPLICATE_KEY_NAME,new String[]{assignment.getAssignmentName()});
			} else {
				throw new AssignmentCreationException(AssignmentCreationException.DUPLICATE_KEY_NAME_ID,new String[]{assignment.getId(), assignment.getAssignmentName()});
			}
			
		}
		
	}
	
	public Assignment createAssignment(Assignment assignment) throws AssignmentCreationException, AssignmentAccessException {
		if(assignment.getId() == null) {
			return save(assignment);
		} else {
			return updateAllAttributesById(assignment.getId(), assignment);
		}
	}
	
    public Assignment updateAllAttributesById(String id, Assignment updateAssignment) throws AssignmentAccessException {
    	Assignment oldAssignment = findById(id);
    	
		oldAssignment.setAssignmentName(updateAssignment.getAssignmentName());
		oldAssignment.setDescription(updateAssignment.getDescription());
		oldAssignment.setStartDate(updateAssignment.getStartDate());
		oldAssignment.setEndDate(updateAssignment.getEndDate());
		oldAssignment.setAttachmentIds(updateAssignment.getAttachmentIds());
		oldAssignment.setInspectionObject(updateAssignment.getInspectionObject());
		oldAssignment.setUser(updateAssignment.getUser());
		oldAssignment.setTasks(updateAssignment.getTasks());
    		
		assignmentRepository.save(oldAssignment);
    	return oldAssignment;
    }
    
    private boolean checkInspectionObjectExists(Assignment assignment) {
    	if(assignment.getInspectionObject() != null) {
			try {
				inspectionObjectService.findById(assignment.getInspectionObject().getId());
				return true;
			} catch (InspectionObjectAccessException e) {
				return false;
			}
    	} else {
    		return false;
    	}
    }
    
    private boolean checkUserExists(Assignment assignment) {
    	if(assignment.getUser() != null) {
			if(userService.findById(assignment.getUser().getId()) == null) {
				return false;
			}
			return true;
    	} else {
    		return false;
    	}
    }
    
    private boolean checkAttachmentsExist(Assignment assignment) {
    	if(assignment.getAttachmentIds() != null) {
    		for(String attachmentId : assignment.getAttachmentIds()) {
    			if(gridFsService.findById(attachmentId) == null) {
    				return false;
    			}
    		}
    	}
    	return true;
    }
}
