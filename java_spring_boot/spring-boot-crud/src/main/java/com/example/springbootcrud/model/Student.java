package com.example.springbootcrud.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
	
	@Column(name = "name")
    private String name;
	
	@Column(name = "created_at")
    private Date createdAt = new Date();
	
	@Column(name = "last_updated_at")
    private Date lastUpdatedAt = new Date();
	
	@ManyToMany(cascade = {CascadeType.REFRESH}, targetEntity = Course.class)
	@JoinTable(name = "student_courses", 
	joinColumns = { 
			@JoinColumn(name = "student_id")
			}, 
    inverseJoinColumns = { 
    		@JoinColumn(name = "course_id") 
    		})
	@JsonIgnoreProperties("students")
	private List<Course> courses;
    
    public Student() {
    	
    }
    
	public Student(String title) {
		super();
		this.name = title;
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
	
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getLastUpdatedAt() {
		return lastUpdatedAt;
	}
	public void setLastUpdatedAt(Date lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}
	public List<Course> getCourses() {
		return courses;
	}
	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "Student{" +
				"id=" + id +
				", name='" + name + '\'' +
				", createdAt=" + createdAt +
				", lastUpdatedAt=" + lastUpdatedAt +
				", courses=" + courses +
				'}';
	}

	public String toJsonString() {
		return "{" +
				"id=" + id +
				", name='" + name + '\'' +
				", createdAt=" + createdAt +
				", lastUpdatedAt=" + lastUpdatedAt +
				", courses=" + courses +
				'}';
	}
}