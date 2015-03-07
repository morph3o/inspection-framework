package com.insframe.server.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import com.insframe.server.data.repository.AssignmentRepository;
import com.insframe.server.error.AssignmentAccessException;
import com.insframe.server.error.AssignmentStorageException;
import com.insframe.server.error.InspectionObjectAccessException;
import com.insframe.server.error.UserAccessException;
import com.insframe.server.model.Assignment;
import com.insframe.server.model.Attachment;
import com.insframe.server.model.FileMetaData;
import com.insframe.server.model.Task;
import com.insframe.server.model.User;
import com.insframe.server.security.CustomUserDetails;
import com.insframe.server.tools.SecurityTools;

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
	
    public void addAttachmentToAssignment(String assignmentId, InputStream inputStream, String fileName, String contentType, FileMetaData metaData) throws AssignmentAccessException, AssignmentStorageException, UserAccessException {
		Assignment assignment = findById(assignmentId);
		assignment.addAttachment(gridFsService.store(inputStream, fileName, contentType, metaData));
		save(assignment);
	}
    
    public void addAttachmentToTask(String assignmentId, String taskId, InputStream inputStream, String fileName, String contentType, FileMetaData metaData) throws AssignmentAccessException, AssignmentStorageException, UserAccessException {   	
    	Assignment assignment = findById(assignmentId);
    	Task task = assignment.getTask(taskId);
		if(task != null) {
			task.addAttachment(gridFsService.store(inputStream, fileName, contentType, metaData));
			save(assignment);
		} else {
			throw new AssignmentAccessException(AssignmentAccessException.TASK_ID_NOT_FOUND_TEXT_ID,new String[]{assignmentId, taskId});
		}
		
    }
    
    public void addTaskToAssignment(String assignmentId, Task task) throws AssignmentAccessException, AssignmentStorageException, UserAccessException {
    	Assignment assignment = findById(assignmentId);
    	assignment.getTasks().add(task);
    	save(assignment);
    }

	public Assignment findById(String id) throws AssignmentAccessException{
    	Assignment queriedAssignment = assignmentRepository.findById(id);
    	if(queriedAssignment == null || checkValidAssignmentOwner(queriedAssignment) == false) {
    		throw new AssignmentAccessException(AssignmentAccessException.OBJECT_ID_NOT_FOUND_TEXT_ID,new String[]{id});
    	}
    	return queriedAssignment;
    }

	public List<Assignment> findAll() throws AssignmentAccessException {
		List<Assignment> assignments = assignmentRepository.findAll();
		if(assignments == null) {
			throw new AssignmentAccessException(AssignmentAccessException.NO_OBJECTS_FOUND_TEXT_ID,new String[]{});
		}
		return filterByLoginUser(assignments);
	}

	public Assignment findByAssignmentName(String assignmentName) throws AssignmentAccessException {
		Assignment queriedAssignment = assignmentRepository.findByAssignmentName(assignmentName);
		if(queriedAssignment == null || checkValidAssignmentOwner(queriedAssignment) == false) {
			throw new AssignmentAccessException(AssignmentAccessException.NO_OBJECTS_FOUND_BY_NAME_TEXT_ID,new String[]{});
		}
		return queriedAssignment;
	}
	
	public List<Assignment> findByUserId(String userId) throws AssignmentAccessException {
		List<Assignment> assignments =  assignmentRepository.findByUserId(new ObjectId(userId));
		if(assignments == null) {
			throw new AssignmentAccessException(AssignmentAccessException.NO_OBJECTS_BY_USER_ID_FOUND_TEXT_ID,new String[]{userId});
		}
		return filterByLoginUser(assignments);
	}
	
	public Task findTaskById(String assignmentId, String taskId) throws AssignmentAccessException, AssignmentStorageException, UserAccessException {
		Task queriedTask = findById(assignmentId).getTask(taskId);
		if(queriedTask != null) {
			return queriedTask;
		} else {
			throw new AssignmentAccessException(AssignmentAccessException.TASK_ID_NOT_FOUND_TEXT_ID,new String[]{assignmentId, taskId});
		}
	}
	
	public void deleteTaskById(String assignmentId, String taskId) throws AssignmentAccessException, AssignmentStorageException, UserAccessException {
		Assignment queriedAssignment = findById(assignmentId);
		List<Task> tasks = queriedAssignment.getTasks();
		for (int i = 0; i < tasks.size(); i++) {
			if(tasks.get(i).getId().equalsIgnoreCase(taskId)) {
				gridFsService.deleteFileList(tasks.get(i).getAttachmentIds());
				tasks.remove(i);
			}
		}
		save(queriedAssignment);
	}
	
	public void deleteAssignmentById(String id) throws AssignmentAccessException{
		Assignment queriedAssignment = findById(id);
		gridFsService.deleteFileList(queriedAssignment.listAttachmentIds());
		assignmentRepository.delete(id);
	}

	public void deleteAll() throws AssignmentAccessException{
		List<Assignment> queriedAssignments = findAll();
		for (Assignment assignment : queriedAssignments) {
			gridFsService.deleteFileList(assignment.listAttachmentIds());
		}
		assignmentRepository.deleteAll();
	}

	public void deleteAttachment(String assignmentId, String attachmentId) throws AssignmentAccessException, AssignmentStorageException, UserAccessException {
		Assignment assignment = findById(assignmentId);
		List<String> attachmentIds = assignment.listAttachmentIds();
		for (int i = 0; i < attachmentIds.size(); i++) {
			String assignedAttachmentId = attachmentIds.get(i);
			if(assignedAttachmentId.equalsIgnoreCase(attachmentId)){
				gridFsService.deleteById(assignedAttachmentId);
				attachmentIds.remove(assignedAttachmentId);
			}
		}
		save(assignment);
	}
	
	public void deleteAttachment(String assignmentId, String taskId, String attachmentId) throws AssignmentAccessException, AssignmentStorageException, UserAccessException {
		Assignment assignment = findById(assignmentId);
		List<String> attachmentIds = assignment.getTask(taskId).getAttachmentIds();
		for (int i = 0; i < attachmentIds.size(); i++) {
			String assignedAttachmentId = attachmentIds.get(i);
			if(assignedAttachmentId.equalsIgnoreCase(attachmentId)){
				gridFsService.deleteById(assignedAttachmentId);
				attachmentIds.remove(assignedAttachmentId);
			}
		}
		save(assignment);
	}
	
	public void deleteAllAttachments(String assignmentId) throws AssignmentAccessException, AssignmentStorageException, UserAccessException {
		Assignment assignment = findById(assignmentId);
		List<String> attachmentIds = assignment.listAttachmentIds();
		gridFsService.deleteFileList(attachmentIds);
		attachmentIds.clear();
		save(assignment);
	}
	
	public void deleteAllAttachments(String assignmentId, String taskId) throws AssignmentAccessException, AssignmentStorageException, UserAccessException {
		Assignment queriedAssignment = findById(assignmentId);
		List<Task> tasks = queriedAssignment.getTasks();
		List<String> attachmentIds = null;
		
		for (int i = 0; i < tasks.size(); i++) {
			if(tasks.get(i).getId().equalsIgnoreCase(taskId)) {
				attachmentIds = tasks.get(i).getAttachmentIds();
			}
		}
		gridFsService.deleteFileList(attachmentIds);
		attachmentIds.clear();
		save(queriedAssignment);
	}
	
	public Assignment save(Assignment assignment) throws AssignmentStorageException, UserAccessException {
		if(assignment.getAssignmentName() == null
			|| assignment.getIsTemplate() == null) {
			throw new AssignmentStorageException(AssignmentStorageException.MISSING_MANDATORY_PARAMETER_TEXT_ID,new String[]{"assignmentName and isTemplate"});
		}
		
		if(assignment.getIsTemplate() == false) {
			if(checkInspectionObjectExists(assignment) == false) {
				throw new AssignmentStorageException(AssignmentStorageException.INVALID_USER_REF_TEXT_ID,new String[]{});
			}
			if(checkUserExists(assignment) == false) {
				throw new AssignmentStorageException(AssignmentStorageException.INVALID_INSP_OBJECT_REF_TEXT_ID,new String[]{});
			}
			if(assignment.listAttachmentIds() == null) {
				assignment.setAttachments(new ArrayList<Attachment>());
			}
			if(gridFsService.checkAttachmentsExist(assignment.listAttachmentIds()) == false) {
				throw new AssignmentStorageException(AssignmentStorageException.INVALID_ATTACHMENT_REF_TEXT_ID,new String[]{});
			}
		} else {
			if (assignment.getInspectionObject() != null) {
				throw new AssignmentStorageException(AssignmentStorageException.INVALID_TEMPLATE_ATTR_TEXT_ID,new String[]{"inspectionObject"});
			}
			if (assignment.getUser() != null) {
				throw new AssignmentStorageException(AssignmentStorageException.INVALID_TEMPLATE_ATTR_TEXT_ID,new String[]{"user"});
			}
			if (assignment.getStartDate() != null) {
				throw new AssignmentStorageException(AssignmentStorageException.INVALID_TEMPLATE_ATTR_TEXT_ID,new String[]{"startDate"});
			}
			if (assignment.getEndDate() != null) {
				throw new AssignmentStorageException(AssignmentStorageException.INVALID_TEMPLATE_ATTR_TEXT_ID,new String[]{"endDate"});
			}
			if (assignment.listAttachmentIds() != null) {
				throw new AssignmentStorageException(AssignmentStorageException.INVALID_TEMPLATE_ATTR_TEXT_ID,new String[]{"attachments"});
			}
		}
		
		if(assignment.getState() == null) {
			assignment.setState(Assignment.STATE_INITIAL);
		} else {
			if(assignment.getState() < 0 || assignment.getState() > 2) {
				throw new AssignmentStorageException(AssignmentStorageException.INVALID_STATE_TEXT_ID,new String[]{Integer.toString(assignment.getState())});
			}
		}
		
		if(assignment.getVersion() == null){
			assignment.setVersion(0);
		}

		if(assignment.getTasks() == null) {
			assignment.setTasks(new ArrayList<Task>());
		} else {
			for (Task task : assignment.getTasks()) {
				if(task.getId() == null) {
					task.setId(ObjectId.get().toString());
				}
				if(task.getState() == null) {
					task.setState(Task.STATE_OKAY);
				} else {
					if(task.getState() < Task.STATE_INITIAL || task.getState() > Task.STATE_ERROR) {
						throw new AssignmentStorageException(AssignmentStorageException.INVALID_STATE_TEXT_ID,new String[]{Integer.toString(task.getState()), task.getTaskName()});
					}
				}
			}
		}

		try {
			return assignmentRepository.save(assignment);	
		} catch (DuplicateKeyException e) {
			// TODO: should be more detailed here! Only catch Duplicate Key Exception, but what is right exception name to catch?
			if(assignment.getId() == null) {
				throw new AssignmentStorageException(AssignmentStorageException.DUPLICATE_KEY_NAME,new String[]{assignment.getAssignmentName()});
			} else {
				throw new AssignmentStorageException(AssignmentStorageException.DUPLICATE_KEY_NAME_ID,new String[]{assignment.getId(), assignment.getAssignmentName()});
			}
		}
	}
	
	public Assignment createAssignment(Assignment assignment) throws AssignmentStorageException, AssignmentAccessException, UserAccessException {
		if(assignment.getId() == null) {
			return save(assignment);
		} else {
			return updateAllAttributesById(assignment.getId(), assignment, false);
		}
	}
	
    public Assignment updateAllAttributesById(String id, Assignment updateAssignment, boolean overwrite) throws AssignmentAccessException, AssignmentStorageException, UserAccessException {
    	if(checkValidAssignmentOwner(updateAssignment)){
    		Assignment oldAssignment = findById(id);
    		if(oldAssignment.getState() != Assignment.STATE_FINISHED || SecurityTools.currentUserHasAuthority("ROLE_ADMIN")){
	    		oldAssignment.setAssignmentName(updateAssignment.getAssignmentName());
	    		oldAssignment.setDescription(updateAssignment.getDescription());
	    		oldAssignment.setState(updateAssignment.getState());
	    		oldAssignment.setStartDate(updateAssignment.getStartDate());
	    		oldAssignment.setEndDate(updateAssignment.getEndDate());
	    		oldAssignment.setInspectionObject(updateAssignment.getInspectionObject());
	    		oldAssignment.setUser(updateAssignment.getUser());
	    		oldAssignment.setTasks(updateAssignment.getTasks());
	    		if(checkAssignmentVersion(oldAssignment, updateAssignment) || overwrite){
	    			oldAssignment.setVersion(oldAssignment.getVersion()+1);
	    			this.save(oldAssignment);
	    		} else {
	    			throw new AssignmentStorageException(AssignmentStorageException.UPDATED_VERSION_AVAILABLE, new String[]{updateAssignment.getAssignmentName()});
	    		}
	    		return oldAssignment;
    		}
    		throw new AssignmentStorageException(AssignmentStorageException.ASSIGNMENT_FINISHED, new String[]{oldAssignment.getAssignmentName()});
    	} else {
    		throw new AssignmentStorageException(AssignmentStorageException.INVALID_USER_TO_MODIFY_ASSIGNMENT, new String[]{updateAssignment.getAssignmentName()});
    	}
    }
    
    private boolean checkValidAssignmentOwner(Assignment assignment){
    	final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if (authentication.getPrincipal() instanceof CustomUserDetails) {
			User u = ((CustomUserDetails) authentication.getPrincipal()).getUser();
			if(assignment.getUser().getUserName().equals(u.getUserName()) || SecurityTools.hasAuthority((CustomUserDetails) authentication.getPrincipal(), "ROLE_ADMIN")){
				return true;
			}
		}
    	return false;
    }
    
    private boolean checkAssignmentVersion(Assignment oldAssignment, Assignment updatedAssignment){
    	if(oldAssignment != null && updatedAssignment != null){
    		if(oldAssignment.getVersion() == updatedAssignment.getVersion()){
    			return true;
    		}
    		return false;
    	}
    	return false;
    }
    
    private boolean checkInspectionObjectExists(Assignment assignment) {
    	if(assignment.getInspectionObject() != null) {
			try {
				inspectionObjectService.findById(assignment.getInspectionObject().getId(), false);
				return true;
			} catch (InspectionObjectAccessException e) {
				return false;
			}
    	} else {
    		return false;
    	}
    }
    
    private boolean checkUserExists(Assignment assignment) throws UserAccessException {
    	if(assignment.getUser() != null) {
			if(userService.findById(assignment.getUser().getId()) == null) {
				return false;
			}
			return true;
    	} else {
    		return false;
    	}
    }
        
    private List<Assignment> filterByLoginUser(List<Assignment> assignments) {
    	//TODO: Remove that check for the current User. A user should always be logged in!
    	User currentUser = userService.getCurrentUser();
    	
    	if(currentUser != null) {
	    	for (int i = 0; i < assignments.size(); i++) {
	    		if(checkValidAssignmentOwner(assignments.get(i)) == false) {
	    			assignments.remove(i);
	    			i = i - 1;
	    		}
			}
    	}
    	return assignments;
    }
}
