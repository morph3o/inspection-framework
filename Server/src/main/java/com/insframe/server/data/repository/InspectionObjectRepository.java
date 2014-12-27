package com.insframe.server.data.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.insframe.server.model.InspectionObject;

public interface InspectionObjectRepository extends MongoRepository<InspectionObject, String>{
	// Index and ID access of single objects
	public InspectionObject findByObjectName(String objectName);
	public InspectionObject findById(String id);
	
	// access to multiple objects
	public List<InspectionObject> findByCustomerName(String customerName);
}
