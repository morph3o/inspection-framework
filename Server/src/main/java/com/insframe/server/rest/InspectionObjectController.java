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

import com.insframe.server.error.FileUploadException;
import com.insframe.server.error.InspectionObjectAccessException;
import com.insframe.server.error.InspectionObjectStorageException;
import com.insframe.server.error.UserAccessException;
import com.insframe.server.model.FileMetaData;
import com.insframe.server.model.InspectionObject;
import com.insframe.server.service.InspectionObjectService;

@RestController
@RequestMapping("/inspectionobject")
public class InspectionObjectController {
	@Autowired
	private InspectionObjectService inspectionObjectService;

	@RequestMapping(method=RequestMethod.GET)
    public List<InspectionObject> getInspectionObjectsByCustomerName(@RequestParam(required=false, value="customerName") String customerName,
    																@RequestParam(required=false, value="addAttachmentDetails", defaultValue="false") Boolean addAttachmentDetails) throws InspectionObjectAccessException {
		if(customerName == null) {
			return inspectionObjectService.findAll(addAttachmentDetails);
		} else {
			return inspectionObjectService.findByCustomerName(customerName, addAttachmentDetails);
		}
    }
	
	@RequestMapping(method=RequestMethod.POST)
	public InspectionObject createInspectionObject(@RequestBody InspectionObject inspectionObject) throws InspectionObjectStorageException {
		InspectionObject savedInspectionObject = inspectionObjectService.save(inspectionObject);
		return savedInspectionObject;
	}
	
	@RequestMapping(value="/{inspectionObjectId}", method=RequestMethod.DELETE)
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void deleteInspectionObjectByID(@PathVariable("inspectionObjectId") String inspectionObjectId) throws InspectionObjectAccessException {
		inspectionObjectService.deleteInspectionObjectByID(inspectionObjectId);
	}
	
	@RequestMapping(value="/{inspectionObjectId}", method=RequestMethod.GET)
	public InspectionObject getInspectionObjectByID(@PathVariable("inspectionObjectId") String inspectionObjectId) throws InspectionObjectAccessException {
		return inspectionObjectService.findById(inspectionObjectId);
	}
	
	@RequestMapping(value="/{inspectionObjectId}", method=RequestMethod.PUT)
	@ResponseStatus(value=HttpStatus.NO_CONTENT)
	public void updateInspectionObjectByID(@PathVariable("inspectionObjectId") String inspectionObjectId,
										 	@RequestBody InspectionObject inspectionObject) throws InspectionObjectAccessException, InspectionObjectStorageException {
		inspectionObjectService.updateById(inspectionObjectId, inspectionObject);
	}
	
	@RequestMapping(value="/{inspectionObjectId}/attachment", method=RequestMethod.POST)
	public void addAttachmentToAssignment(@PathVariable("inspectionObjectId") String inspectionObjectId,
									@RequestParam("fileUpload") List<MultipartFile> fileList,
									@RequestParam(value = "fileDescription", required = false) List<String> descriptionList)
								 throws InspectionObjectAccessException, IOException, FileUploadException, InspectionObjectStorageException {
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
					inspectionObjectService.addAttachmentToInspectionObject(inspectionObjectId, multipartFile.getInputStream(), multipartFile.getOriginalFilename(), multipartFile.getContentType(), new FileMetaData(descriptionList.get(i)));
				else {
					inspectionObjectService.addAttachmentToInspectionObject(inspectionObjectId, multipartFile.getInputStream(), multipartFile.getOriginalFilename(), multipartFile.getContentType(), new FileMetaData(""));
				}
	        } else {
	        	throw new FileUploadException(FileUploadException.EMPTY_FILE_UPLOAD_TEXT_ID,new String[]{});
	        }
		}
	}
	
	@RequestMapping(value="/{inspectionObjectId}/attachment/{attachmentId}", method=RequestMethod.DELETE)
	public void deleteInspectionObjectAttachment(@PathVariable("inspectionObjectId") String inspectionObjectId,
											@PathVariable("attachmentId") String attachmentId) throws InspectionObjectAccessException, UserAccessException, InspectionObjectStorageException {
		inspectionObjectService.deleteAttachment(inspectionObjectId, attachmentId);
	}
}
