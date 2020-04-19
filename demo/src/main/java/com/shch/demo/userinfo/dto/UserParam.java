package com.shch.demo.userinfo.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.shch.demo.security.Session;

public class UserParam extends Session{
	private String userno;
	private String usertype;
	private String uid;
	private String username;
	private String name;
	private String password;
	private String repassword;
	private String email;
	private String deptcd;
	private String poscd;
	private String tel;
	private String addr;
	private String addrdtl;
	private String useflag;
	private String langcd;
	private String lastlogindt;
	private String lastloginip;
	private String regcd;
	private String regdt;
	private String modcd;
	private String moddt;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public String getUsertype() {
		return usertype;
	}
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDeptcd() {
		return deptcd;
	}
	public void setDeptcd(String deptcd) {
		this.deptcd = deptcd;
	}
	public String getPoscd() {
		return poscd;
	}
	public void setPoscd(String poscd) {
		this.poscd = poscd;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getAddrdtl() {
		return addrdtl;
	}
	public void setAddrdtl(String addrdtl) {
		this.addrdtl = addrdtl;
	}
	public String getUseflag() {
		return useflag;
	}
	public void setUseflag(String useflag) {
		this.useflag = useflag;
	}
	public String getLangcd() {
		return langcd;
	}
	public void setLangcd(String langcd) {
		this.langcd = langcd;
	}
	public String getLastlogindt() {
		return lastlogindt;
	}
	public void setLastlogindt(String lastlogindt) {
		this.lastlogindt = lastlogindt;
	}
	public String getLastloginip() {
		return lastloginip;
	}
	public void setLastloginip(String lastloginip) {
		this.lastloginip = lastloginip;
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
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
}
