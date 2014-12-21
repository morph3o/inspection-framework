package com.insframe.server.data.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.insframe.server.model.InspectionObject;

public interface InspectionObjectRepository extends MongoRepository<InspectionObject, String>{
	public InspectionObject findByObjectName(String objectName);
    public List<InspectionObject> findByCustomerName(String customerName);

}
