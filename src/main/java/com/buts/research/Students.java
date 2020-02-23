package com.buts.research;

public class Students {
	
	private int code;
	
    private String name;
    
    private int id;
    
    private String course;
 
    public Students() {
    }
 
    public Students(int code, String name, int id, String course) {
    	this.code = code;
    	this.name = name;
    	this.id = id;
    	this.course = course;
    }
    public int getCode() {
        return this.code;
    }
 
    public void setCode(int c) {
        this.code = c;
    }
    public String getName() {
        return this.name;
    }
 
    public void setName(String n) {
        this.name = n;
    }
    public int getId() {
        return this.id;
    }
 
    public void setId(int i) {
        this.id = i;
    }
    public String getCourse() {
        return this.course;
    }
 
    public void setCourse(String c) {
        this.course = c;
    }
 
    // getters and setters
}