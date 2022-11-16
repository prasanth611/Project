package com.example.springbootcrud.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

/**
 * Tag domain model
 * @author javaguides.net
 *
 */
@Entity
@Table( name = "courses")
public class Course {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
		
	@ManyToMany(mappedBy = "courses")
	@JsonIgnoreProperties("courses")
	private List<Student> students;
	
	public Course() {
		
	}
	
	public Course(String name) {
		super();
		this.name = name;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Student> getStudents() {
	   return students;
	}
	  
	public void setStudents(List<Student> students) {
	   this.students = students;
	}

	@Override
	public String toString() {
		return "Course{" +
				"id=" + id +
				", name='" + name + '\'' +
				", students=" + students +
				'}';
	}

	public String toJsonString() {
		return "{" +
				"id=" + id +
				", name='" + name + '\'' +
				", students=" + students +
				'}';
	}
}
