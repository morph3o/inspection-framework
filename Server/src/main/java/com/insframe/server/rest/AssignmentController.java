package com.insframe.server.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.insframe.server.error.AssignmentAccessException;
import com.insframe.server.error.AssignmentStorageException;
import com.insframe.server.error.FileUploadException;
import com.insframe.server.error.UserAccessException;
import com.insframe.server.model.Assignment;
import com.insframe.server.model.FileMetaData;
import com.insframe.server.model.Task;
import com.insframe.server.service.AssignmentService;
import com.insframe.server.service.GridFsService;
import com.insframe.server.service.MailService;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {
	
	@Autowired
	private AssignmentService assignmentService;
	@Autowired
	private GridFsService gridFsService;
	@Autowired
	private MailService mailService;

	@RequestMapping(method=RequestMethod.GET)
    public List<Assignment> getAssignments(@RequestParam(value = "user_id", required=false) String userId,
    										@RequestParam(required=false, value="addAttachmentDetails", defaultValue="false") Boolean addAttachmentDetails) throws AssignmentAccessException {
		if(userId != null) {
			return assignmentService.findByUserId(userId, addAttachmentDetails);
		} else {
			return assignmentService.findAll(addAttachmentDetails);
		}
    }
	
	@RequestMapping(value="/{assignmentId}", method=RequestMethod.GET)
	public Assignment getAssignmentById(@PathVariable("assignmentId") String assignmentId,
										@RequestParam(required=false, value="addAttachmentDetails", defaultValue="false") Boolean addAttachmentDetails) throws AssignmentAccessException {
		return assignmentService.findById(assignmentId, addAttachmentDetails);
	}
	
	@RequestMapping(value="/{assignmentId}/task", method=RequestMethod.GET)
	public List<Task> getAssignmentTasks(@PathVariable("assignmentId") String assignmentId,
										@RequestParam(required=false, value="addAttachmentDetails", defaultValue="false") Boolean addAttachmentDetails) throws AssignmentAccessException {
		return assignmentService.findById(assignmentId, addAttachmentDetails).getTasks();
	}
	
	@RequestMapping(value="/{assignmentId}/task/{taskId}", method=RequestMethod.GET)
	public Task getAssignmentTask(@PathVariable("assignmentId") String assignmentId,
							  	@PathVariable("taskId") String taskId,
							  	@RequestParam(required=false, value="addAttachmentDetails", defaultValue="false") Boolean addAttachmentDetails) throws AssignmentAccessException, AssignmentStorageException, UserAccessException {
		return assignmentService.findTaskById(assignmentId, taskId, addAttachmentDetails);
	}

	@RequestMapping(method=RequestMethod.POST)
	public Assignment createAssignment(@RequestBody Assignment assignment) throws AssignmentStorageException, AssignmentAccessException, UserAccessException {
		return assignmentService.createAssignment(assignment);
	}
	
	@RequestMapping(value="/{assignmentId}", method=RequestMethod.DELETE)
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void deleteAssignmentById(@PathVariable("assignmentId") String assignmentId) throws AssignmentAccessException, AssignmentStorageException {
		assignmentService.deleteAssignmentById(assignmentId);
	}
	
	@RequestMapping(value="/{assignmentId}", method=RequestMethod.PUT)
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void updateAssignmentByID(@PathVariable("assignmentId") String assignmentId,
									 @RequestBody Assignment assignment,
									 @RequestParam(value = "overwrite", required = false) boolean overwrite) throws AssignmentAccessException, AssignmentStorageException, UserAccessException {
		assignmentService.updateAllAttributesById(assignmentId, assignment, overwrite);
	}
	
	@RequestMapping(value="/{assignmentId}/task/{taskId}", method=RequestMethod.PUT)
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void updateTaskByID(@PathVariable("assignmentId") String assignmentId,
								@PathVariable("taskId") String taskId,
								@RequestBody Task task,
								@RequestParam(value = "overwrite", required = false) boolean overwrite) throws AssignmentAccessException, AssignmentStorageException, UserAccessException {
		assignmentService.updateAssignmentTaskById(assignmentId, taskId, task, overwrite);
	}
	
	
	@RequestMapping(value="/{assignmentId}/attachment", method=RequestMethod.POST)
	public void addAttachmentToAssignment(@PathVariable("assignmentId") String assignmentId,
									@RequestParam("fileUpload") List<MultipartFile> fileList,
									@RequestParam(value = "fileDescription", required = false) List<String> descriptionList)
								 throws AssignmentAccessException, IOException, FileUploadException, AssignmentStorageException, UserAccessException{
		if(descriptionList == null){
			descriptionList = new ArrayList<String>();
		}
		for (int i = 0; i < fileList.size(); i++) {
			MultipartFile multipartFile = fileList.get(i);
			if (!multipartFile.isEmpty()) {
	        	// TODO: Up to now it is possible to upload any type of file (even corrupted or malicious files!)
	        	// as this is a student project, it is not the primary concern
	        	// https://wiki.mozilla.org/WebAppSec/Secure_Coding_Guidelines#Uploads
	        	// http://stackoverflow.com/questions/9354300/securing-file-upload
				if(descriptionList.size() > i)
					assignmentService.addAttachmentToAssignment(assignmentId, multipartFile.getInputStream(), multipartFile.getOriginalFilename(), multipartFile.getContentType(), new FileMetaData(descriptionList.get(i)));
				else {
					assignmentService.addAttachmentToAssignment(assignmentId, multipartFile.getInputStream(), multipartFile.getOriginalFilename(), multipartFile.getContentType(), new FileMetaData(""));
				}
	        } else {
	        	throw new FileUploadException(FileUploadException.EMPTY_FILE_UPLOAD_TEXT_ID,new String[]{});
	        }
		}
	}
	
	@RequestMapping(value="/{assignmentId}/task/{taskId}/attachment", method=RequestMethod.POST)
	public void addAttachmentToTask(@PathVariable("assignmentId") String assignmentId,
									@PathVariable("taskId") String taskId,
									@RequestParam("fileUpload") List<MultipartFile> fileList,
									@RequestParam(value = "fileDescription", required = false) List<String> descriptionList)
								 throws AssignmentAccessException, IOException, FileUploadException, AssignmentStorageException, UserAccessException{
		if(descriptionList == null){
			descriptionList = new ArrayList<String>();
		}
		for (int i = 0; i < fileList.size(); i++) {
			MultipartFile multipartFile = fileList.get(i);
			if (!multipartFile.isEmpty()) {
	        	// TODO: Up to now it is possible to upload any type of file (even corrupted or malicious files!)
	        	// as this is a student project, it is not the primary concern
	        	// https://wiki.mozilla.org/WebAppSec/Secure_Coding_Guidelines#Uploads
	        	// http://stackoverflow.com/questions/9354300/securing-file-upload
				if(descriptionList.size() > i)
					assignmentService.addAttachmentToTask(assignmentId, taskId, multipartFile.getInputStream(), multipartFile.getOriginalFilename(), multipartFile.getContentType(), new FileMetaData(descriptionList.get(i)));
				else {
					assignmentService.addAttachmentToTask(assignmentId, taskId, multipartFile.getInputStream(), multipartFile.getOriginalFilename(), multipartFile.getContentType(), new FileMetaData(""));
				}
	        } else {
	        	throw new FileUploadException(FileUploadException.EMPTY_FILE_UPLOAD_TEXT_ID,new String[]{});
	        }
		}
	}

	@RequestMapping(value="/{assignmentId}/attachment/{attachmentId}", method=RequestMethod.DELETE)
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void deleteAssignmentAttachment(@PathVariable("assignmentId") String assignmentId,
									@PathVariable("attachmentId") String attachmentId) throws AssignmentAccessException, AssignmentStorageException, UserAccessException {
		assignmentService.deleteAttachment(assignmentId, attachmentId);
	}
	
	@RequestMapping(value="/{assignmentId}/task/{taskId}/attachment/{attachmentId}", method=RequestMethod.DELETE)
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void deleteTaskAttachment(@PathVariable("assignmentId") String assignmentId,
									@PathVariable("taskId") String taskId,
									@PathVariable("attachmentId") String attachmentId) throws AssignmentAccessException, AssignmentStorageException, UserAccessException {
		assignmentService.deleteAttachment(assignmentId, taskId, attachmentId);
	}
	
	@RequestMapping(value="/{assignmentId}/attachment", method=RequestMethod.DELETE)
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void deleteAllAttachments(@PathVariable("assignmentId") String assignmentId) throws AssignmentAccessException, AssignmentStorageException, UserAccessException{
		assignmentService.deleteAllAttachments(assignmentId);
	}
}