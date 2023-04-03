package com.deepak.mybooks.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
public class Expert {
    
	public Integer getBandWidth() {
		return bandWidth;
	}

	public void setBandWidth(Integer bandWidth) {
		this.bandWidth = bandWidth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
    
	@Id
    @Column(nullable = false,unique=true)
    private String name;
  
    
    @OneToMany(mappedBy = "expert", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;
    
    @Column(nullable=false)
    private Integer bandWidth=8;
    
    // constructors, getters, setters, etc.
    
}