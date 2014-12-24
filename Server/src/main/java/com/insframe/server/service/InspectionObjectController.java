package com.insframe.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insframe.server.data.repository.InspectionObjectRepository;
import com.insframe.server.error.JSONErrorMessage;
import com.insframe.server.model.InspectionObject;

@RestController
public class InspectionObjectController {
	
	@Autowired
	private InspectionObjectRepository inspectionObjectRepository; 
	
	@RequestMapping("/inspectionobject")
    public Object getFirstInspectionObjectFoundByCustomerName(@RequestParam(value="customerName") String customerName) {
		if(inspectionObjectRepository.findByCustomerName(customerName).size() > 0){
			return (InspectionObject) inspectionObjectRepository.findByCustomerName(customerName).get(0);
		} else {
			return new JSONErrorMessage("There is no inspection object with customer name: "+customerName);
		}
    }

}
