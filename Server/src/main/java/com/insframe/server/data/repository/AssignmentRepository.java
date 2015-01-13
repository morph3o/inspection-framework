package com.insframe.server.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.insframe.server.model.Assignment;

public interface AssignmentRepository extends MongoRepository<Assignment, String>{
	// Index and ID access of single objects
	public Assignment findByAssignmentName(String assignmentName);
	public Assignment findById(String id);
	
	// access to multiple objects
}
