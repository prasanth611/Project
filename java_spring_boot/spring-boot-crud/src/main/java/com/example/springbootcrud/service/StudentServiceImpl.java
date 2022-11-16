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
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private CourseRepository courseRepository;

	@Override
	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}
	
	@Override
	public List<Object> getAllStudents() {

		JedisPool jedis = Redis.getPoolInstance();
		Set<String> keys = jedis.getResource().keys("student-*");

		List<String> values = Collections.emptyList();
		if( !keys.isEmpty() ) {
			values = jedis.getResource().mget(keys.toArray(new String[keys.size()]));
		}

		if( CollectionUtils.isEmpty(values) ) {
			//retrieve from database, add into cache and then return the value
			List<Object> students = (List) studentRepository.findAll();

			//edge case when there are no values in cache but exists in db

			return students;
		}

		Gson gson = new Gson();
		return  values.stream()
				.map(student -> gson.fromJson(student.toString(), (Type) Student.class)
				).collect(Collectors.toList());
	}
	
	@Override
	public Student add(Student newStudent) {

		Student dbResponse = studentRepository.save(newStudent);

		String studentkey = "student-" + dbResponse.getId();
		this.updateStudentFromRedis(studentkey, newStudent);

		//fetch all courses and add them in cache

		return dbResponse;
	}
	
	@Override
	public String delete(Long id) {
		try {
			studentRepository.deleteById(id);

			String studentkey = "student-" + id;
			this.deleteStudentFromRedis(studentkey);

		}catch(Exception err) {
			return "Failed to delete Student with id" + id;
		}
		return "Deleted Student with id " + id;
	}
	
	@Override
	public Student update(Student student) {

		if(!studentRepository.existsById(student.getId())) {
			return null;
		}
		
		Student dbResponse = studentRepository.save(student);

		String studentkey = "student-" + dbResponse.getId();
		this.updateStudentFromRedis(studentkey, student);

		return dbResponse;
	}

	private void updateStudentFromRedis(String key, Student obj) {

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
