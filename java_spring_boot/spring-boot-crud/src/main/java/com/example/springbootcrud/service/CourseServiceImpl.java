package com.example.springbootcrud.service;

import com.example.springbootcrud.clients.Redis;
import com.example.springbootcrud.model.Course;
import com.example.springbootcrud.model.Student;
import com.example.springbootcrud.repository.CourseRepository;
import com.example.springbootcrud.repository.StudentRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public List<Object> getAllCourses() {
		JedisPool jedis = Redis.getPoolInstance();
		Set<String> keys = jedis.getResource().keys("course-*");

		List<String> values = Collections.emptyList();
		if( !keys.isEmpty() ) {
			values = jedis.getResource().mget(keys.toArray(new String[keys.size()]));
		}

		if( CollectionUtils.isEmpty(values) ) {
			//retrieve from database, add into cache and then return the value
			List<Object> courses = (List) courseRepository.findAll();

			//edge case when there are no values in cache but exists in db

			return courses;
		}

		Gson gson = new Gson();
		return  values.stream()
				.map(course -> gson.fromJson(course.toString(), (Type) Course.class)
				).collect(Collectors.toList());
	}
	
	@Override
	public Course add(Course newCourse) {
		Course dbResponse = courseRepository.save(newCourse);

		String studentkey = "course-" + dbResponse.getId();
		this.updateStudentFromRedis(studentkey, newCourse);

		//fetch all students and add them in cache

		return dbResponse;
	}
	
	@Override
	public String delete(Long id) {
		try {
			courseRepository.deleteById(id);

			String courseKey = "course-" + id;
			this.deleteStudentFromRedis(courseKey);

		}catch(Exception err) {
			System.out.print(err.toString());
			return "Failed to delete Course with id " + id;
		}
	
		return "Deleted Course with id " + id;
	}
	
	@Override
	public Course update(Course course) {
		if(!courseRepository.existsById(course.getId())) {
			return null;
		}

		Course dbResponse = courseRepository.save(course);

		String courseKey = "course-" + dbResponse.getId();
		this.updateStudentFromRedis(courseKey, course);

		return course;
	}

	private void updateStudentFromRedis(String key, Course obj) {

		JedisPool jedis = Redis.getPoolInstance();
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();

		jedis.getResource().set(key, gson.toJson(obj));
	}

	private void deleteStudentFromRedis(String key) {

		JedisPool jedis = Redis.getPoolInstance();
		jedis.getResource().del(key);

	}
}
