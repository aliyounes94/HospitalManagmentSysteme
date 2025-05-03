package com.hms.model;

import java.util.ArrayList;
import java.util.List;

//Patient.java
public class Patient {
private String username;
private String name;
private String dob;
private String address;
private String phone;
private List<LabResult> labResults = new ArrayList<>();
// Constructors, getters, setters
public Patient() {
	super();}
public Patient(String username, String name, String dob, String address, String phone) {
	super();
	this.username = username;
	this.name = name;
	this.dob = dob;
	this.address = address;
	this.phone = phone;
	this.labResults =labResults;
}


public List<LabResult> getLabResults() {
    return labResults;
}

public void setLabResults(List<LabResult> labResults) {
    this.labResults = labResults;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDob() {
	return dob;
}
public void setDob(String dob) {
	this.dob = dob;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}



}