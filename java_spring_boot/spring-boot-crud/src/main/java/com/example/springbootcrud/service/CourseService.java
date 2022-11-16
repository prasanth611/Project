package com.example.springbootcrud.service;

import com.example.springbootcrud.model.Course;
import com.example.springbootcrud.model.Student;

import java.util.List;

public interface CourseService {
	List<Object> getAllCourses();
	
	Course add(Course course);
	
	String delete(Long id);
	
	Course update(Course course);

	List<Student> getAllStudents();

}
