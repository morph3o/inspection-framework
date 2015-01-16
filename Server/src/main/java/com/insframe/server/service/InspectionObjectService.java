package com.insframe.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import com.insframe.server.data.repository.InspectionObjectRepository;
import com.insframe.server.error.InspectionObjectAccessException;
import com.insframe.server.model.InspectionObject;

@Repository
public class InspectionObjectService {

	@Autowired
	private InspectionObjectRepository inspectionObjectRepository;
	@Autowired	
	private MongoOperations mongoOpts;
	
	public InspectionObject save(InspectionObject inspectionObject) {
		return inspectionObjectRepository.save(inspectionObject);
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
    	inspectionObjectRepository.deleteAll();
    }
    
    public void deleteInspectionObjectByID(String id) throws InspectionObjectAccessException{
    	this.findById(id);
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
    
    public InspectionObject updateById(String id, InspectionObject updateInspectionObject) throws InspectionObjectAccessException {
    	InspectionObject oldInspectionObject = this.findById(id);
    	
		oldInspectionObject.setObjectName(updateInspectionObject.getObjectName());
		oldInspectionObject.setCustomerName(updateInspectionObject.getCustomerName());
		oldInspectionObject.setDescription(updateInspectionObject.getDescription());
		oldInspectionObject.setLocation(updateInspectionObject.getLocation());
		
		inspectionObjectRepository.save(oldInspectionObject);
    	return oldInspectionObject;
    }
}
