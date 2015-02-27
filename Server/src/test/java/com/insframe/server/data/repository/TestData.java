package com.insframe.server.data.repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.insframe.server.config.WebAppConfigurationAware;
import com.insframe.server.error.AssignmentStorageException;
import com.insframe.server.error.UserAccessException;
import com.insframe.server.error.UserStorageException;
import com.insframe.server.model.Assignment;
import com.insframe.server.model.FileMetaData;
import com.insframe.server.model.InspectionObject;
import com.insframe.server.model.Task;
import com.insframe.server.model.User;
import com.insframe.server.service.AssignmentService;
import com.insframe.server.service.GridFsService;
import com.insframe.server.service.InspectionObjectService;
import com.insframe.server.service.UserService;

public class TestData extends WebAppConfigurationAware{
	
	@Autowired
	private AssignmentService assignmentService;
	@Autowired
	private UserService userService;
	@Autowired
	private InspectionObjectService inspectionObjectService;
	@Autowired
	private GridFsService gridFsService;
	
	@Before
	public void init() throws UserStorageException, FileNotFoundException, AssignmentStorageException, UserAccessException{
		
		/**
		 * Clean all the documents of the database
		 * */
		userService.deleteAll();
		inspectionObjectService.deleteAll();
		assignmentService.deleteAll();
		
		/**
		 * Creation of User tests. The parameters are the following in order
		 * 
		 * - Username
		 * - Email
		 * - Password
		 * - Last name
		 * - First name
		 * - Phone number
		 * - Mobile number
		 * 
		 * */
		User user1 = new User("fpetru", 
							  "fpetru@gmail.com", 
							  "pass1", 
							  "ADMIN", 
							  "Petrus", 
							  "Frank", 
							  "+49162123456", 
							  "+49162123456");
		
		User user2 = new User("hcarns", 
				 		      "hcarns@gmail.com", 
				 		      "pass2", 
				 		      "INSPECTOR", 
				 		      "Carns", 
				 		      "Hertha", 
				 		      "+49162123456", 
				 		      "+49162123456");
		
		User user3 = new User("ppilgrim", 
							  "ppilgrim@gmail.com", 
							  "pass3", 
							  "INSPECTOR", 
							  "Pilgrim", 
							  "Parthenia", 
							  "+49162123456", 
							  "+49162123456");
		/**
		 * Save the User tests in the database
		 * */
		userService.save(user1);
		userService.save(user2);
		userService.save(user3);
		
		/**
		 * Creation of Inspection Object tests. The parameters are the following in order:
		 * 
		 * - Object name
		 * - Description
		 * - Location
		 * - Customer name
		 * 
		 * */
		InspectionObject iO1 = new InspectionObject("University of Mannheim", 
													"The University of Mannheim", 
													"Mannheim", 
													"Studentenwerk Mannheim");
		InspectionObject iO2 = new InspectionObject("Montpellier bridge", 
													"Montpellier bridge in Heidelberg", 
													"Heidelberg", 
													"City of Heidelberg");		
	    InspectionObject iO3 = new InspectionObject("Mannheim main station", 
	    											"Mannheim main station", 
	    											"Mannheim", 
	    											"Deutsche Bahn");
	    InspectionObject iO4 = new InspectionObject("Tegel Airport", 
	    											"Berlin Airport Tegel", 
	    											"Berlin", 
	    											"Berlin Airport GmbH");
		/**
		 * Save the Inspection object test in the database
		 * */
		inspectionObjectService.save(iO1);
		inspectionObjectService.save(iO2);
		inspectionObjectService.save(iO3);
		inspectionObjectService.save(iO4);
		
		/**
		 * Creation of Assignment tests. The parameters are the following in order:
		 * 
		 * - Assignment name
		 * - Description
		 * - isTemplate (true|false)
		 * - tasks (List)
		 * - Start date 
		 * - End date
		 * - User
		 * - Inspection object
		 * - Attachments (List)  
		 * */
		Assignment as1 = new Assignment("Check computers of the Studentenwerk office", 
										"It is necessary to do the routine check of the computers in the Studentenwerk.", 
										false, 
										null, 
										new Date(), 
										new Date(), 
										user2, 
										iO1, 
										null);
		/**
		 * Set of the tasks
		 * */
		Task task1 = new Task("Check computer of the reception", "Check computer of the reception", Task.STATE_INITIAL);
		Task task2 = new Task("Check computer of the secretary", "Check computer of the secretary", Task.STATE_INITIAL);
		Task task3 = new Task("Check computer of the other secretary", "Check computer of the other secretary, this computer use Linux", Task.STATE_INITIAL);
		
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(task1);
		tasks.add(task2);
		tasks.add(task3);
		
		/**
		 * Set of the attachment
		 * */
		InputStream studentenPicture = new FileInputStream("logo-studentenwerk.png");
		
		as1.setTasks(tasks);
		as1.addAttachment(gridFsService.store(studentenPicture, "logo-studentenwerk.png", "image/png", new FileMetaData("Studentenwerk logo")));
		
		/**
		 * Creation of Assignment template. The parameters are the following, in order:
		 * 
		 * - Assignment name
		 * - Description
		 * - isTemplate (true|false)
		 * */
		Assignment as2 = new Assignment("Check University of Mannheim", 
										"Go to the university of Mannheim and perform the needed checks.", 
										true);
		
		assignmentService.save(as1);
		assignmentService.save(as2);
	}
	
	@Test
	public void testStoreOfData(){
		
	}

}
