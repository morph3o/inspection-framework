package com.insframe.server.data.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.insframe.server.model.Assignment;

public interface AssignmentRepository extends MongoRepository<Assignment, String>{
	// Index and ID access of single objects
	public Assignment findByAssignmentName(String assignmentName);
	public Assignment findById(String id);

	// access to multiple objects
	@Query(value = "{ 'user.$id' : ?0 }")
	public List<Assignment> findByUserId(ObjectId userId);
}
