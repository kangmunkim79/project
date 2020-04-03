package com.shch.demo.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.shch.demo.userinfo.dto.UserInfo;
import com.shch.demo.userinfo.mapper.UserInfoMapper;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
    private static final String ROLE_PREFIX = "ROLE_";
    
    @Autowired
    UserInfoMapper userInfoMapper;
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        UserInfo user = userInfoMapper.readUser(username);
        if(user != null) {
        	user.setAuthorities(makeGrantedAuthority(userInfoMapper.readAuthority(username)));
        }
        return new SecurityUser(user);
    }
    
    private static List<GrantedAuthority> makeGrantedAuthority(List<String> roles){
        List<GrantedAuthority> list = new ArrayList<>();
        roles.forEach(role -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role)));
        return list;
    }

}