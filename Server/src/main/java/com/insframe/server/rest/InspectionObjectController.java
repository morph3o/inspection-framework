package com.insframe.server.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insframe.server.error.InspectionObjectAccessException;
import com.insframe.server.model.InspectionObject;
import com.insframe.server.service.InspectionObjectService;

@RestController
@RequestMapping("/inspectionobject")
public class InspectionObjectController {
	@Autowired
	private InspectionObjectService inspectionObjectService;

	@RequestMapping(method=RequestMethod.GET)
    public Object getInspectionObjectsByCustomerName(@RequestParam(required=false, value="customerName") String customerName) throws InspectionObjectAccessException {
		List<InspectionObject> inspectionObjectList;
		
		if(customerName == null) {
			inspectionObjectList = inspectionObjectService.findAll();
		} else {
			inspectionObjectList = inspectionObjectService.findByCustomerName(customerName);
		}
		
		if(inspectionObjectList.size() > 0){
			return inspectionObjectList;
		} else {
			if(customerName == null){
				throw new InspectionObjectAccessException(InspectionObjectAccessException.NO_OBJECTS_FOUND_TEXT_ID,new String[]{});
			} else {
				throw new InspectionObjectAccessException(InspectionObjectAccessException.NO_OBJECTS_FOUND_BY_CUSTOMERNAME_TEXT_ID,new String[]{customerName});
			}
		}
    }
	
	@RequestMapping(method=RequestMethod.POST)
	public InspectionObject createInspectionObject(@RequestBody InspectionObject inspectionObject) {
		InspectionObject savedInspectionObject = inspectionObjectService.save(inspectionObject);
		return savedInspectionObject;
	}
	

	
	@RequestMapping(value="/{inspectionObjectId}", method=RequestMethod.DELETE)
	public void deleteInspectionObjectByID(@PathVariable("inspectionObjectId") String inspectionObjectId) throws InspectionObjectAccessException {
		inspectionObjectService.deleteInspectionObjectByID(inspectionObjectId);
	}
	
	@RequestMapping(value="/{inspectionObjectId}", method=RequestMethod.GET)
	public void getInspectionObjectByID(@PathVariable("inspectionObjectId") String inspectionObjectId) throws InspectionObjectAccessException {
		inspectionObjectService.findById(inspectionObjectId);
	}
	
	@RequestMapping(value="/{inspectionObjectId}", method=RequestMethod.PUT)
	public void updateInspectionObjectByID(@PathVariable("inspectionObjectId") String inspectionObjectId,
										 	@RequestBody InspectionObject inspectionObject) throws InspectionObjectAccessException {
		inspectionObjectService.updateById(inspectionObjectId, inspectionObject);
	}
}
