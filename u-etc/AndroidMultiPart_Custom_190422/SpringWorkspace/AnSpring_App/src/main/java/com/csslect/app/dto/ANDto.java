package com.csslect.app.dto;

import java.sql.Date;

public class ANDto {
	
	int id;
	String name;	
	Date hire_date;
	String image1;
	String uploadtype;
	String videoImagePath;
		
	public ANDto() {
		
	}

	public ANDto(int id, String name, Date hire_date, String image1, String uploadtype, String videoImagePath) {		
		this.id = id;
		this.name = name;
		this.hire_date = hire_date;
		this.image1 = image1;
		this.uploadtype = uploadtype;
		this.videoImagePath = videoImagePath;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHire_date(Date hire_date) {
		this.hire_date = hire_date;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getHire_date() {
		return hire_date;
	}

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getUploadtype() {
		return uploadtype;
	}

	public void setUploadtype(String uploadtype) {
		this.uploadtype = uploadtype;
	}

	public String getVideoImagePath() {
		return videoImagePath;
	}

	public void setVideoImagePath(String videoImagePath) {
		this.videoImagePath = videoImagePath;
	}	
	
	
}
