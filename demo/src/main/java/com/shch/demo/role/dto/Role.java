package com.shch.demo.role.dto;

import com.shch.demo.security.Session;

public class Role extends Session{
	
	private String rolecd;
	private String rolenm;
	private String useflag;
	private String regcd;
	private String regdt;
	private String modcd;
	private String moddt;
	
	public String getRolecd() {
		return rolecd;
	}
	public void setRolecd(String rolecd) {
		this.rolecd = rolecd;
	}
	public String getRolenm() {
		return rolenm;
	}
	public void setRolenm(String rolenm) {
		this.rolenm = rolenm;
	}
	public String getUseflag() {
		return useflag;
	}
	public void setUseflag(String useflag) {
		this.useflag = useflag;
	}
	public String getRegcd() {
		return regcd;
	}
	public void setRegcd(String regcd) {
		this.regcd = regcd;
	}
	public String getRegdt() {
		return regdt;
	}
	public void setRegdt(String regdt) {
		this.regdt = regdt;
	}
	public String getModcd() {
		return modcd;
	}
	public void setModcd(String modcd) {
		this.modcd = modcd;
	}
	public String getModdt() {
		return moddt;
	}
	public void setModdt(String moddt) {
		this.moddt = moddt;
	}
}
