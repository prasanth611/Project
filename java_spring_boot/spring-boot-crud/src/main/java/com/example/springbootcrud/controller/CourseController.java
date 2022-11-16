package com.example.springbootcrud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbootcrud.model.Course;

import com.example.springbootcrud.service.CourseService;

@RestController
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	@GetMapping("/courses")
	public List<Object> index() {
		return courseService.getAllCourses();
	}
	
	@PostMapping("/course")
	public Course add(@RequestBody Course newCourse) {
		
		return courseService.add(newCourse);
	}
	
	@DeleteMapping("/course/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        return courseService.delete(id);
    }
	
	@PutMapping("/course")
	public Course update(@RequestBody Course course) {
		return courseService.update(course);
	}
}

