package com.insframe.server.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.insframe.server.error.InspectionObjectAccessException;
import com.insframe.server.model.Assignment;
import com.insframe.server.model.FileMetaData;
import com.insframe.server.service.AssignmentService;
import com.insframe.server.service.GridFsService;

@RestController
@RequestMapping("/assignment")
public class AssignmentController {
	@Autowired
	private AssignmentService assignmentService;
	
	@Autowired
	private GridFsService gridFsService;

	@RequestMapping(method=RequestMethod.GET)
    public List<Assignment> getAssignments() throws InspectionObjectAccessException {		
		List<Assignment> assignments = assignmentService.findAll();
		
		if(assignments.size() > 0){
			return assignments;
		} else {
			// TODO: create new exception and raise with different text ID
			throw new InspectionObjectAccessException(InspectionObjectAccessException.NO_OBJECTS_FOUND_TEXT_ID,new String[]{});
		}
    }
	
	@RequestMapping(method=RequestMethod.POST)
	public Assignment createAssignment(@RequestBody Assignment assignment) {
		Assignment savedAssignment = assignmentService.save(assignment);
		return savedAssignment;
	}
	
	@RequestMapping(value="/{assignmentId}", method=RequestMethod.DELETE)
	public void deleteAssignmentById(@PathVariable("assignmentId") String assignmentId) throws InspectionObjectAccessException {
		assignmentService.deleteAssignmentById(assignmentId);
	}
	
	@RequestMapping(value="/{assignmentId}", method=RequestMethod.GET)
	public Assignment getAssignmentById(@PathVariable("assignmentId") String assignmentId) throws InspectionObjectAccessException {
		return assignmentService.findById(assignmentId);
	}
	
	@RequestMapping(value="/{assignmentId}", method=RequestMethod.PUT)
	public void updateAssignmentByID(@PathVariable("assignmentId") String assignmentId,
										@RequestBody Assignment assignment) throws InspectionObjectAccessException {
		assignmentService.updateById(assignmentId, assignment);
	}
	
	
	@RequestMapping(value="/{assignmentId}/attachment", method=RequestMethod.POST)
	public @ResponseBody String addAttachment(@PathVariable("assignmentId") String assignmentId,
												@RequestParam("fileUpload") MultipartFile file){
		String name = file.getOriginalFilename();
        if (!file.isEmpty()) {
            try {
            	assignmentService.addAttachment(assignmentId, file.getInputStream(), file.getOriginalFilename(), file.getContentType(), new FileMetaData("Test"));
                return "You successfully uploaded " + name + " for assignment " + assignmentId + "!";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
	}
	
//	@RequestMapping(value="/{assignmentId}/attachment/", method=RequestMethod.POST)
//	public @ResponseBody String addAttachment(@PathVariable("assignmentId") String assignmentId,
//												@RequestParam("file") MultipartFile file){
//		String name = file.getOriginalFilename();
//        if (!file.isEmpty()) {
//            try {
//            	assignmentService.addAttachment(assignmentId, file.getInputStream(), file.getOriginalFilename(), file.getContentType(), new FileMetaData("Test"));
//                return "You successfully uploaded " + name + " for assignment " + assignmentId + "!";
//            } catch (Exception e) {
//                return "You failed to upload " + name + " => " + e.getMessage();
//            }
//        } else {
//            return "You failed to upload " + name + " because the file was empty.";
//        }
//	}
}
