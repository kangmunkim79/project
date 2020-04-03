package com.shch.demo.security;

import org.springframework.security.core.userdetails.User;

import com.shch.demo.userinfo.dto.UserInfo;

public class SecurityUser extends User {

	private static final long serialVersionUID = 1L;
    
    private String ip;
    
    public SecurityUser(UserInfo user) {
        super(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
 
    public String getIp() {
        return ip;
    }
 
    public void setIp(String ip) {
        this.ip = ip;
    }

}