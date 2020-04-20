package com.shch.demo.auth.dto;

import com.shch.demo.security.Session;

public class Auth extends Session{
	private String roleuser;
	private String rolecd;
	private String useflag;
	
	public String getRoleuser() {
		return roleuser;
	}
	public void setRoleuser(String roleuser) {
		this.roleuser = roleuser;
	}
	public String getRolecd() {
		return rolecd;
	}
	public void setRolecd(String rolecd) {
		this.rolecd = rolecd;
	}
	public String getUseflag() {
		return useflag;
	}
	public void setUseflag(String useflag) {
		this.useflag = useflag;
	}
	
}
