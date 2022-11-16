package com.example.springbootcrud.service;

import com.example.springbootcrud.model.Course;
import com.example.springbootcrud.model.Student;

import java.util.List;

public interface StudentService {
	List<Object> getAllStudents();
	
	Student add(Student student);
	
	Student update(Student student);
	
	String delete(Long id);
	
	List<Course> getAllCourses();
}
