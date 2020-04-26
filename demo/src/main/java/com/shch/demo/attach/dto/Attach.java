package com.shch.demo.attach.dto;

import com.shch.demo.security.Session;

public class Attach extends Session{
	
	private int rnum;
	private String filecd;
	private String filegrpcd;
	private String uploadfilename;
	private String orgfilename;
	private long filesize;
	private String fileurl;
	private String ext;
	private String filename;
	
	public String getFilecd() {
		return filecd;
	}
	public void setFilecd(String filecd) {
		this.filecd = filecd;
	}
	public String getFilegrpcd() {
		return filegrpcd;
	}
	public void setFilegrpcd(String filegrpcd) {
		this.filegrpcd = filegrpcd;
	}
	public String getUploadfilename() {
		return uploadfilename;
	}
	public void setUploadfilename(String uploadfilename) {
		this.uploadfilename = uploadfilename;
	}
	public String getOrgfilename() {
		return orgfilename;
	}
	public void setOrgfilename(String orgfilename) {
		this.orgfilename = orgfilename;
	}
	public long getFilesize() {
		return filesize;
	}
	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
	public String getFileurl() {
		return fileurl;
	}
	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getRnum() {
		return rnum;
	}
	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	
}
