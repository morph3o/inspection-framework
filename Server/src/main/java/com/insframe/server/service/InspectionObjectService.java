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
	
	public List<InspectionObject> findAll() {
		return inspectionObjectRepository.findAll();
	}
	
	public List<InspectionObject> findByCustomerName(String customerName) {
		return inspectionObjectRepository.findByCustomerName(customerName);
	}
	
	public InspectionObject findByObjectName(String objectName) {
		return inspectionObjectRepository.findByObjectName(objectName);
	}
	
    public void deleteAll(){
    	inspectionObjectRepository.deleteAll();
    }
    
    public void deleteInspectionObjectByID(String id) throws InspectionObjectAccessException{
    	if(findById(id) != null) {;
    		inspectionObjectRepository.delete(id);
    	} else {
    		throw new InspectionObjectAccessException(InspectionObjectAccessException.OBJECT_ID_NOT_FOUND_TEXT_ID,new String[]{id});
    	}
    }
    
    public InspectionObject findById(String id){
    	return inspectionObjectRepository.findById(id);
    }
    
    public InspectionObject updateById(String id, InspectionObject updateInspectionObject) throws InspectionObjectAccessException {
    	InspectionObject oldInspectionObject = inspectionObjectRepository.findById(id);
    	if(oldInspectionObject != null) {
	    	if(!updateInspectionObject.getObjectName().equalsIgnoreCase(oldInspectionObject.getObjectName())){
	    		oldInspectionObject.setObjectName(updateInspectionObject.getObjectName());
			} 
			if(!updateInspectionObject.getCustomerName().equalsIgnoreCase(oldInspectionObject.getCustomerName())){
				oldInspectionObject.setCustomerName(updateInspectionObject.getCustomerName());
			}
			if(!updateInspectionObject.getDescription().equalsIgnoreCase(oldInspectionObject.getDescription())){
				oldInspectionObject.setDescription(updateInspectionObject.getDescription());
			}
			if(!updateInspectionObject.getLocation().equalsIgnoreCase(oldInspectionObject.getLocation())){
				oldInspectionObject.setLocation(updateInspectionObject.getLocation());
			}
    	} else {
    		throw new InspectionObjectAccessException(InspectionObjectAccessException.OBJECT_ID_NOT_FOUND_TEXT_ID,new String[]{id});
    	}
		
		inspectionObjectRepository.save(oldInspectionObject);
    	return updateInspectionObject;
    }
}
