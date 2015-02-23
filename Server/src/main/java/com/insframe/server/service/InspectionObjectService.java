package com.insframe.server.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import com.insframe.server.data.repository.InspectionObjectRepository;
import com.insframe.server.error.InspectionObjectAccessException;
import com.insframe.server.error.InspectionObjectStorageException;
import com.insframe.server.model.Attachment;
import com.insframe.server.model.FileMetaData;
import com.insframe.server.model.InspectionObject;

@Repository
public class InspectionObjectService {

	@Autowired
	private InspectionObjectRepository inspectionObjectRepository;
	@Autowired
	GridFsService gridFsService;
	@Autowired	
	private MongoOperations mongoOpts;
	
	public InspectionObject save(InspectionObject inspectionObject) throws InspectionObjectStorageException {
		if(inspectionObject.getObjectName() == null
				|| inspectionObject.getObjectName() == "") {
				throw new InspectionObjectStorageException(InspectionObjectStorageException.MISSING_MANDATORY_PARAMETER_TEXT_ID,new String[]{"objectName"});
		}
		
		if(inspectionObject.getAttachments() == null) {
			inspectionObject.setAttachments(new ArrayList<Attachment>());
		}
		if(gridFsService.checkAttachmentsExist(inspectionObject.listAttachmentIds()) == false) {
			throw new InspectionObjectStorageException(InspectionObjectStorageException.INVALID_ATTACHMENT_REF_TEXT_ID,new String[]{});
		}
		
		try {
			return inspectionObjectRepository.save(inspectionObject);
		} catch (Exception e) {
			// TODO: should be more detailed here! Only catch Duplicate Key Exception, but what is right exception name to catch?
			if(inspectionObject.getId() == null) {
				throw new InspectionObjectStorageException(InspectionObjectStorageException.DUPLICATE_KEY_NAME,new String[]{inspectionObject.getObjectName()});
			} else {
				throw new InspectionObjectStorageException(InspectionObjectStorageException.DUPLICATE_KEY_NAME_ID,new String[]{inspectionObject.getId(), inspectionObject.getObjectName()});
			}
		}
	}
	
	@SuppressWarnings("null")
	public List<InspectionObject> findAll() throws InspectionObjectAccessException {
    	List<InspectionObject> queriedInspectionObjectList = inspectionObjectRepository.findAll();
    	if(queriedInspectionObjectList != null || queriedInspectionObjectList.size() > 0) {
    		return queriedInspectionObjectList;
    	} else{
    		throw new InspectionObjectAccessException(InspectionObjectAccessException.NO_OBJECTS_FOUND_TEXT_ID,new String[]{});
    	}
	}
	
	@SuppressWarnings("null")
	public List<InspectionObject> findByCustomerName(String customerName) throws InspectionObjectAccessException {
    	List<InspectionObject> queriedInspectionObjectList = inspectionObjectRepository.findByCustomerName(customerName);
    	if(queriedInspectionObjectList != null || queriedInspectionObjectList.size() > 0) {
    		return queriedInspectionObjectList;
    	} else{
    		throw new InspectionObjectAccessException(InspectionObjectAccessException.NO_OBJECTS_FOUND_BY_CUSTOMERNAME_TEXT_ID,new String[]{customerName});
    	}
	}
	
	public InspectionObject findByObjectName(String objectName) throws InspectionObjectAccessException {
    	InspectionObject queriedInspectionObject = inspectionObjectRepository.findByObjectName(objectName);
    	if(queriedInspectionObject != null) {
    		return queriedInspectionObject;
    	} else{
    		throw new InspectionObjectAccessException(InspectionObjectAccessException.NO_OBJECTS_FOUND_BY_OBJECTNAME_TEXT_ID,new String[]{objectName});
    	}
	}
	
    public void deleteAll(){
    	// TODO: make sure that also the attachments are deleted!
    	inspectionObjectRepository.deleteAll();
    }
    
    public void deleteInspectionObjectByID(String id) throws InspectionObjectAccessException{
    	InspectionObject inspectionObject = this.findById(id);
    	gridFsService.deleteFileList(inspectionObject.listAttachmentIds());
    	inspectionObjectRepository.delete(id);
    }
    
    public InspectionObject findById(String id) throws InspectionObjectAccessException{
    	InspectionObject queriedInspectionObject = inspectionObjectRepository.findById(id);
    	if(queriedInspectionObject != null) {
    		return queriedInspectionObject;
    	} else{
    		throw new InspectionObjectAccessException(InspectionObjectAccessException.OBJECT_ID_NOT_FOUND_TEXT_ID,new String[]{id});
    	}
    }
    
    public InspectionObject updateById(String id, InspectionObject updateInspectionObject) throws InspectionObjectAccessException, InspectionObjectStorageException {
    	InspectionObject oldInspectionObject = this.findById(id);
    	
		oldInspectionObject.setObjectName(updateInspectionObject.getObjectName());
		oldInspectionObject.setCustomerName(updateInspectionObject.getCustomerName());
		oldInspectionObject.setDescription(updateInspectionObject.getDescription());
		oldInspectionObject.setLocation(updateInspectionObject.getLocation());
		
		this.save(oldInspectionObject);
    	return oldInspectionObject;
    }
    
    public void addAttachmentToInspectionObject(String inspectionObjectId, InputStream inputStream, String fileName, String contentType, FileMetaData metaData) throws InspectionObjectAccessException, InspectionObjectStorageException {
		InspectionObject inspectionObject = findById(inspectionObjectId);
		inspectionObject.addAttachment(gridFsService.store(inputStream, fileName, contentType, metaData));
		this.save(inspectionObject);
	}
    
	public void deleteAttachment(String inspectionObjectId, String attachmentId) throws InspectionObjectAccessException, InspectionObjectStorageException {
		InspectionObject inspectionObject = findById(inspectionObjectId);
		List<Attachment> attachments = inspectionObject.getAttachments();
		for (int i = 0; i < attachments.size(); i++) {
			Attachment assignedAttachment = attachments.get(i);
			if(assignedAttachment.getGridFsId().equalsIgnoreCase(attachmentId)){
				gridFsService.deleteById(assignedAttachment.getGridFsId());
				attachments.remove(assignedAttachment);
			}
		}
		save(inspectionObject);
	}
}
