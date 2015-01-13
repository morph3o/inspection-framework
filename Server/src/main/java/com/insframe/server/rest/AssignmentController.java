package com.insframe.server.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.insframe.server.error.InspectionObjectAccessException;
import com.insframe.server.model.Assignment;
import com.insframe.server.model.InspectionObject;
import com.insframe.server.service.AssignmentService;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {
	@Autowired
	private AssignmentService assignmentService;

	@RequestMapping(method=RequestMethod.GET)
    public Object getAssignments() throws InspectionObjectAccessException {		
		List<Assignment> assignments = assignmentService.findAll();
		
		if(assignments.size() > 0){
			return assignments;
		} else {
			// TODO: create new exception and raise with different text ID
			throw new InspectionObjectAccessException(InspectionObjectAccessException.NO_OBJECTS_FOUND_TEXT_ID,new String[]{});
		}
    }
	
	@RequestMapping(method=RequestMethod.POST)
	public Assignment createInspectionObject(@RequestBody Assignment assignment) {
		Assignment savedAssignment = assignmentService.save(assignment);
		return savedAssignment;
	}
	
//	@RequestMapping(value="/{id}/image", method=RequestMethod.GET)
//	public void getItemImage(@PathVariable Long id, HttpServletResponse response) throws IOException
//	{
//	   Item item = service.loadItem(id);
//	   response.setContentType(item.getImage().getContentType());
//	   response.getOutputStream().write(item.getImage().getBytes());
//
//	   // don't close the output stream from the response
//	} 
	
//	@RequestMapping(value="/{inspectionObjectId}", method=RequestMethod.DELETE)
//	public void deleteInspectionObjectByID(@PathVariable("inspectionObjectId") String inspectionObjectId) throws InspectionObjectAccessException {
//		inspectionObjectService.deleteInspectionObjectByID(inspectionObjectId);
//	}
//	
//	@RequestMapping(value="/{inspectionObjectId}", method=RequestMethod.GET)
//	public void getInspectionObjectByID(@PathVariable("inspectionObjectId") String inspectionObjectId) throws InspectionObjectAccessException {
//		inspectionObjectService.findById(inspectionObjectId);
//	}
//	
//	@RequestMapping(value="/{inspectionObjectId}", method=RequestMethod.PUT)
//	public void updateInspectionObjectByID(@PathVariable("inspectionObjectId") String inspectionObjectId,
//										 	@RequestBody InspectionObject inspectionObject) throws InspectionObjectAccessException {
//		inspectionObjectService.updateById(inspectionObjectId, inspectionObject);
//	}
}
