package com.insframe.server.rest;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.insframe.server.error.InspectionObjectAccessException;
import com.insframe.server.model.FileMetaData;
import com.insframe.server.service.AssignmentService;
import com.insframe.server.service.GridFsService;
import com.mongodb.gridfs.GridFSDBFile;

@RestController
@RequestMapping("/attachment")
public class AttachmentController {
	@Autowired
	private AssignmentService assignmentService;
	
	@Autowired
	private GridFsService gridFsService;

	@RequestMapping(value="/{imageId}", method=RequestMethod.GET)
	public void getItemImage(@PathVariable("imageId") String imageId, HttpServletResponse response) throws IOException {
	   GridFSDBFile gridFsImage = gridFsService.findById(imageId);
	   response.setContentType(gridFsImage.getContentType());
	   IOUtils.copy(gridFsImage.getInputStream(), response.getOutputStream());
	   response.getOutputStream().flush();
	} 
	
	/*
	 method only used for development purposes
	*/
//	@RequestMapping(method=RequestMethod.GET)
//    public void getAttachmentList(HttpServletResponse response) throws InspectionObjectAccessException, IOException {		
//		List<GridFSDBFile> attachmentList= gridFsService.findAll();
//		if(attachmentList.size() > 0) {
//			response.setContentType("application/json");
//			response.getWriter().print(attachmentList.toString());
//			response.flushBuffer();
//		} else {
//			// TODO: create new exception and raise with different text ID
//			throw new InspectionObjectAccessException(InspectionObjectAccessException.NO_OBJECTS_FOUND_TEXT_ID,new String[]{});
//		}
//    }
	
	/*
	 method only used for development purposes
	*/
//	@RequestMapping(method=RequestMethod.POST)
//	public @ResponseBody String handleFileUpload(@RequestParam("fileUpload") MultipartFile file){
//		String name = file.getOriginalFilename();
//        if (!file.isEmpty()) {
//            try {
//                gridFsService.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), new FileMetaData("Test"));
//                return "You successfully uploaded " + name + " into " + name + "-uploaded !";
//            } catch (Exception e) {
//                return "You failed to upload " + name + " => " + e.getMessage();
//            }
//        } else {
//            return "You failed to upload " + name + " because the file was empty.";
//        }
//	}
	

}
