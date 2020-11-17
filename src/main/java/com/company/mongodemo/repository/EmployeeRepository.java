package com.company.mongodemo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.company.mongodemo.model.Employee;
import com.fasterxml.jackson.annotation.JsonInclude;

public interface EmployeeRepository extends MongoRepository<Employee, Long>{

	@Query(value = "{'salary': {$gte:?0}}", fields = "{'firstName':1, 'emailId':1,'salary':1}")
	
	List<Employee> findEmpSalary(float salary);

}
