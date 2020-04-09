package com.shch.demo.menu.dto;

import com.shch.demo.security.Session;

public class Menu extends Session{

	private String menucd;
	private String pmenucd;
	private String menunm;
	private String depth;
	private String urlpath;
	private String param;
	private String usrtype;
	private String sortseq;
	private String useflag;
	private String sts;
	private String regcd;   
	private String regdt;  
	private String modcd;  
	private String moddt;

	private int pid;
	private int id;
	
	private String submenuChk;
	
	public String getMenucd() {
		return menucd;
	}
	public void setMenucd(String menucd) {
		this.menucd = menucd;
	}
	public String getPmenucd() {
		return pmenucd;
	}
	public void setPmenucd(String pmenucd) {
		this.pmenucd = pmenucd;
	}
	public String getMenunm() {
		return menunm;
	}
	public void setMenunm(String menunm) {
		this.menunm = menunm;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	public String getUrlpath() {
		return urlpath;
	}
	public void setUrlpath(String urlpath) {
		this.urlpath = urlpath;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getUsrtype() {
		return usrtype;
	}
	public void setUsrtype(String usrtype) {
		this.usrtype = usrtype;
	}
	public String getSortseq() {
		return sortseq;
	}
	public void setSortseq(String sortseq) {
		this.sortseq = sortseq;
	}
	public String getUseflag() {
		return useflag;
	}
	public void setUseflag(String useflag) {
		this.useflag = useflag;
	}
	public String getSts() {
		return sts;
	}
	public void setSts(String sts) {
		this.sts = sts;
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
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSubmenuChk() {
		return submenuChk;
	}
	public void setSubmenuChk(String submenuChk) {
		this.submenuChk = submenuChk;
	}  
}
