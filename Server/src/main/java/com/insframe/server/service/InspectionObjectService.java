package com.insframe.server.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
	
	@SuppressWarnings("null")
	public List<InspectionObject> findAll(Boolean addAttachmentDetails) throws InspectionObjectAccessException {
    	List<InspectionObject> queriedInspectionObjectList = inspectionObjectRepository.findAll();
    	if(queriedInspectionObjectList != null || queriedInspectionObjectList.size() > 0) {
    		if(addAttachmentDetails) {
    			for (InspectionObject inspectionObject : queriedInspectionObjectList) {
    				gridFsService.addAttachmentDetails(inspectionObject.getAttachments());
				}
    		}
    		return queriedInspectionObjectList;
    	} else{
    		throw new InspectionObjectAccessException(InspectionObjectAccessException.NO_OBJECTS_FOUND_TEXT_ID, InspectionObjectAccessException.NO_OBJECTS_FOUND_ERRORCODE, new String[]{});
    	}
	}
	
	public InspectionObject findById(String id, Boolean addAttachmentDetails) throws InspectionObjectAccessException{
		InspectionObject queriedInspectionObject = inspectionObjectRepository.findById(id);
		if(queriedInspectionObject != null) {
			if(addAttachmentDetails) gridFsService.addAttachmentDetails(queriedInspectionObject.getAttachments());
			return queriedInspectionObject;
		} else{
			throw new InspectionObjectAccessException(InspectionObjectAccessException.OBJECT_ID_NOT_FOUND_TEXT_ID, InspectionObjectAccessException.OBJECT_ID_NOT_FOUND_ERRORCODE, new String[]{id});
		}
	}

	@SuppressWarnings("null")
	public List<InspectionObject> findByCustomerName(String customerName, Boolean addAttachmentDetails) throws InspectionObjectAccessException {
    	List<InspectionObject> queriedInspectionObjectList = inspectionObjectRepository.findByCustomerName(customerName);
    	if(queriedInspectionObjectList != null || queriedInspectionObjectList.size() > 0) {
    		if(addAttachmentDetails) {
    			for (InspectionObject inspectionObject : queriedInspectionObjectList) {
    				gridFsService.addAttachmentDetails(inspectionObject.getAttachments());
				}
    		}
    		return queriedInspectionObjectList;
    	} else{
    		throw new InspectionObjectAccessException(InspectionObjectAccessException.NO_OBJECTS_FOUND_BY_CUSTOMERNAME_TEXT_ID, InspectionObjectAccessException.NO_OBJECTS_FOUND_BY_CUSTOMERNAME_ERRORCODE, new String[]{customerName});
    	}
	}
	
	public InspectionObject findByObjectName(String objectName, Boolean addAttachmentDetails) throws InspectionObjectAccessException {
    	InspectionObject queriedInspectionObject = inspectionObjectRepository.findByObjectName(objectName);
    	if(queriedInspectionObject != null) {
    		if(addAttachmentDetails) gridFsService.addAttachmentDetails(queriedInspectionObject.getAttachments());;
    		return queriedInspectionObject;
    	} else{
    		throw new InspectionObjectAccessException(InspectionObjectAccessException.NO_OBJECTS_FOUND_BY_OBJECTNAME_TEXT_ID, InspectionObjectAccessException.NO_OBJECTS_FOUND_BY_OBJECTNAME_ERRORCODE, new String[]{objectName});
    	}
	}
	
    public InspectionObject save(InspectionObject inspectionObject) throws InspectionObjectStorageException {
		if(inspectionObject.getObjectName() == null || inspectionObject.getObjectName() == "") {
				throw new InspectionObjectStorageException(InspectionObjectStorageException.MISSING_MANDATORY_PARAMETER_TEXT_ID, InspectionObjectStorageException.MISSING_MANDATORY_PARAMETER_ERRORCODE, new String[]{"objectName"});
		}
		
		if(inspectionObject.getAttachments() == null) {
			inspectionObject.setAttachments(new ArrayList<Attachment>());
		}
		if(gridFsService.checkAttachmentsExist(inspectionObject.listAttachmentIds()) == false) {
			throw new InspectionObjectStorageException(InspectionObjectStorageException.INVALID_ATTACHMENT_REF_TEXT_ID, InspectionObjectStorageException.INVALID_ATTACHMENT_REF, new String[]{});
		}
		
		try {
			return inspectionObjectRepository.save(inspectionObject);
		} catch (DuplicateKeyException e) {
			if(inspectionObject.getId() == null) {
				throw new InspectionObjectStorageException(InspectionObjectStorageException.DUPLICATE_KEY_NAME_TEXT_ID, InspectionObjectStorageException.DUPLICATE_KEY_NAME_ERRORCODE, new String[]{inspectionObject.getObjectName()});
			} else {
				throw new InspectionObjectStorageException(InspectionObjectStorageException.DUPLICATE_KEY_NAME_ID_TEXT_ID,InspectionObjectStorageException.DUPLICATE_KEY_NAME_ID_ERRORCODE, new String[]{inspectionObject.getId(), inspectionObject.getObjectName()});
			}
		}
	}

	public void deleteAll() throws InspectionObjectAccessException{
    	// TODO: make sure that also the attachments are deleted!
		List<InspectionObject> queriedInspectionObjects = findAll(false);
		if(queriedInspectionObjects != null) {
			for (InspectionObject inspectionObject : queriedInspectionObjects) {
				gridFsService.deleteFileList(inspectionObject.listAttachmentIds());
			}
		}
    	inspectionObjectRepository.deleteAll();
    }
    
    public void deleteInspectionObjectByID(String id) throws InspectionObjectAccessException{
    	InspectionObject inspectionObject = this.findById(id, false);
    	gridFsService.deleteFileList(inspectionObject.listAttachmentIds());
    	inspectionObjectRepository.delete(id);
    }
    
    public InspectionObject updateById(String id, InspectionObject updateInspectionObject) throws InspectionObjectAccessException, InspectionObjectStorageException {
    	InspectionObject oldInspectionObject = this.findById(id, false);
    	
		oldInspectionObject.setObjectName(updateInspectionObject.getObjectName());
		oldInspectionObject.setCustomerName(updateInspectionObject.getCustomerName());
		oldInspectionObject.setDescription(updateInspectionObject.getDescription());
		oldInspectionObject.setLocation(updateInspectionObject.getLocation());
		
		save(oldInspectionObject);
    	return oldInspectionObject;
    }
    
    public void addAttachmentToInspectionObject(String inspectionObjectId, InputStream inputStream, String fileName, String contentType, FileMetaData metaData) throws InspectionObjectAccessException, InspectionObjectStorageException {
		InspectionObject inspectionObject = findById(inspectionObjectId, false);
		inspectionObject.addAttachment(gridFsService.store(inputStream, fileName, contentType, metaData));
		save(inspectionObject);
	}
    
	public void deleteAttachment(String inspectionObjectId, String attachmentId) throws InspectionObjectAccessException, InspectionObjectStorageException {
		InspectionObject inspectionObject = findById(inspectionObjectId, false);
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
