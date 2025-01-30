package com.manju.department_service.controller;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manju.department_service.client.EmployeeClient;
import com.manju.department_service.model.Department;
import com.manju.department_service.repository.DepartmentRepository;

@RestController
@RequestMapping("/department")
public class DepartmentController {

	private static final Logger  LOGGER= LoggerFactory.getLogger(DepartmentController.class);
	
	@Autowired
	private DepartmentRepository repository;
	
	@Autowired
	private EmployeeClient employeeClient;
	
	@PostMapping
	public Department add(@RequestBody Department department) {
		LOGGER.info("Department add: {}", department);
		return repository.addDepartment(department);
	}
	@GetMapping
	public List<Department> findAll() {
		LOGGER.info("Department find");
		return repository.findAll();
	}
	@GetMapping("/{id}")
	public Department findById(@PathVariable Long id) {
		LOGGER.info("Department find: id={}",id);
		return repository.findById(id);
	}
	@GetMapping("/with-employees")
	public List<Department> findAllWithEmployees() {
		LOGGER.info("Department find");
		List<Department> departments = repository.findAll();
		departments.forEach(department->
		department.setEmployees(employeeClient.findByDepartment(department.getId())));
		return departments;
	}
}
