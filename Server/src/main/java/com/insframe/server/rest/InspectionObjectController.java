package com.insframe.server.rest;

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

import com.insframe.server.error.InspectionObjectAccessException;
import com.insframe.server.model.InspectionObject;
import com.insframe.server.service.InspectionObjectService;

@RestController
@RequestMapping("/inspectionobject")
public class InspectionObjectController {
	@Autowired
	private InspectionObjectService inspectionObjectService;

	@RequestMapping(method=RequestMethod.GET)
    public List<InspectionObject> getInspectionObjectsByCustomerName(@RequestParam(required=false, value="customerName") String customerName) throws InspectionObjectAccessException {
		if(customerName == null) {
			return inspectionObjectService.findAll();
		} else {
			return inspectionObjectService.findByCustomerName(customerName);
		}
    }
	
	@RequestMapping(method=RequestMethod.POST)
	public InspectionObject createInspectionObject(@RequestBody InspectionObject inspectionObject) {
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
										 	@RequestBody InspectionObject inspectionObject) throws InspectionObjectAccessException {
		inspectionObjectService.updateById(inspectionObjectId, inspectionObject);
	}
}
