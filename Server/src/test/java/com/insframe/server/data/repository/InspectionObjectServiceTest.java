package com.insframe.server.data.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.insframe.server.config.WebAppConfigurationAware;
import com.insframe.server.error.InspectionObjectAccessException;
import com.insframe.server.model.InspectionObject;
import com.insframe.server.service.InspectionObjectService;

public class InspectionObjectServiceTest extends WebAppConfigurationAware {
	
	@Autowired
	private InspectionObjectService inspectionObjectService;
	
	@Before
	public void init(){
		inspectionObjectService.deleteAll();
		inspectionObjectService.save(new InspectionObject("University of Mannheim", "The university of Mannheim", "Mannheim", "Studentenwerk Mannheim"));
		inspectionObjectService.save(new InspectionObject("Montpellier bridge", "Montpellier bridge in Heidelberg", "Heidelberg", "City of Heidelberg"));
		inspectionObjectService.save(new InspectionObject("Mannheim main station", "Mannheim main station", "Mannheim", "Deutsche Bahn"));
		inspectionObjectService.save(new InspectionObject("Tegel Airport", "Berlin Airport Tegel", "Berlin", "Berlin Airport GmbH"));
	}
	
	@Test
	public void executePrintAllInspectionObjects(){
		System.out.println("****** Should Show all the inspectionObjects in the database ******");
		System.out.println("Show the 4 inspectionObjects and their data");
		List<InspectionObject> inspectionObjectList = inspectionObjectService.findAll();
		for(InspectionObject inspectionObject : inspectionObjectList){
			System.out.println(inspectionObject);
		}
		Assert.assertTrue(inspectionObjectList.size() == 4);
		System.out.println("****** End of Should Show All the inspectionObjects in the database ******");
	}
	
	@Test
	public void executeInspectionObjectByIdDeletion() throws InspectionObjectAccessException {
		System.out.println("****** Should Delete InspectionObject by ID ******");
		System.out.println("Now the first returned inspection object will be deleted.");
		List<InspectionObject> inspectionObjectList = inspectionObjectService.findAll();
		Assert.assertNotNull(inspectionObjectService.findById(inspectionObjectList.get(0).getId()));
		inspectionObjectService.deleteInspectionObjectByID(inspectionObjectList.get(0).getId());
		Assert.assertNull(inspectionObjectService.findById(inspectionObjectList.get(0).getId()));
		inspectionObjectList = inspectionObjectService.findAll();
		Assert.assertTrue(inspectionObjectList.size() == 3);
		System.out.println("First inspection object was deleted.");
		System.out.println("****** End of deletion of InspectionObject by ID ******");
	}
	
	@Test
	public void executeShowInspectionObjectByObjectName(){
		System.out.println("****** Should show InspectionObject found by ObjectName ******");
		InspectionObject inspectionObject = (InspectionObject) inspectionObjectService.findByObjectName("University of Mannheim");
		
		Assert.assertNotNull(inspectionObject);
		Assert.assertEquals("University of Mannheim", inspectionObject.getObjectName());
		Assert.assertEquals("Mannheim", inspectionObject.getLocation());
		Assert.assertEquals("The university of Mannheim", inspectionObject.getDescription());
		Assert.assertEquals("Studentenwerk Mannheim", inspectionObject.getCustomerName());
		
		System.out.println(inspectionObject);
		System.out.println("****** End of should show InspectionObject found by ObjectName ******");
	}
	
	@Test
	public void executeUpdateInspectionObject() throws InspectionObjectAccessException{
		System.out.println("****** execute update InspectionObject ******");
		InspectionObject inspectionObject = inspectionObjectService.findByObjectName("University of Mannheim");
		System.out.println(inspectionObject);
		inspectionObject.setDescription("Mannheim University");
		
		inspectionObjectService.updateById(inspectionObject.getId(), inspectionObject);
		
		InspectionObject newInspectionObject = inspectionObjectService.findByObjectName("University of Mannheim");
		System.out.println(newInspectionObject);
		Assert.assertEquals("Mannheim University", newInspectionObject.getDescription());
		System.out.println("****** End of execute update InspectionObject ******");
	}

}
