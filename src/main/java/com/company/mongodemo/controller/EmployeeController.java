package com.company.mongodemo.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.mongodemo.exception.ResourceNotFoundException;
import com.company.mongodemo.model.Employee;
import com.company.mongodemo.repository.EmployeeRepository;
import com.company.mongodemo.service.SequenceGeneratorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
	
	private final EmployeeRepository employeeRepository;
	private final SequenceGeneratorService sequenceGeneratorService;
	
	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployees(){
		return ResponseEntity.ok(employeeRepository.findAll());
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getAllEmployeeById(@PathVariable("id") Long id) throws ResourceNotFoundException{
		Employee employee=employeeRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Emp not found : "+id));
		return ResponseEntity.ok().body(employee);
	}
	
	@PostMapping("/employees")
	public Employee createEmployee(@Valid @RequestBody Employee employee) {
		employee.setId(sequenceGeneratorService.generateSequence(Employee.SEQUENCE_NAME));	
		return employeeRepository.save(employee);
	}
	
	@PutMapping("/employees/{id}")
	public Employee updateEmployee(@PathVariable(value = "id") Long employeeId, @Valid @RequestBody Employee employee) throws ResourceNotFoundException {
		Employee employeeExisting= employeeRepository.findById(employeeId).orElseThrow(()->new ResourceNotFoundException("Emp not found : "+employeeId));
		employeeExisting.setFirstName(employee.getFirstName());
		employeeExisting.setLastName(employee.getLastName());
		employeeExisting.setEmailId(employee.getEmailId());
		return employeeRepository.save(employeeExisting);
	}

	@DeleteMapping("/employees/{id}")
	public Map<String,Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId) throws ResourceNotFoundException{
		Employee employeeExisting= employeeRepository.findById(employeeId).orElseThrow(()->new ResourceNotFoundException("Emp not found : "+employeeId));
		employeeRepository.delete(employeeExisting);
		final Map<String, Boolean> map=new HashMap<>();
		map.put("Deleted", Boolean.TRUE);
		return map;
		
	}
	@GetMapping("/employees-page")
	public Map<String,Object> getEmployeesPage(@RequestParam(name="pageNo", defaultValue = "0") int pageNo,@RequestParam(name="pageSize", defaultValue = "5") int pageSize,
			@RequestParam(name="sortBy", defaultValue = "id") String sortBy){
		Map<String,Object> response=new HashMap<>();
		Sort sort=Sort.by(sortBy);
		Pageable pageable=PageRequest.of(pageNo, pageSize, sort);
		Page<Employee> employeePage=employeeRepository.findAll(pageable);
		response.put("data", employeePage.getContent());
		response.put("total_page", employeePage.getTotalPages());
		response.put("total_elements", employeePage.getTotalElements());
		response.put("current_page", employeePage.getNumber());
		return response;
		
	}
	
	@GetMapping("/employees-salary")
	public List<Employee> getEmployeesFilterSalary(@RequestParam(name="salary") float salary){
	
		return employeeRepository.findEmpSalary(salary);
	}
}
