package com.shch.demo.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.shch.demo.GlobalPropertySource;
import com.shch.demo.loginhistory.mapper.LoginhistoryMapper;
import com.shch.demo.userinfo.dto.UserInfo;
import com.shch.demo.userinfo.mapper.UserInfoMapper;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    
    @Autowired
    GlobalPropertySource globalPropertySource;
    
	@Autowired
	LoginhistoryMapper loginhistoryMapper;
	
	@Autowired
	UserInfoMapper userInfoMapper;
	
	public CustomLoginSuccessHandler(String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, 
        Authentication authentication) throws ServletException, IOException {
        String ip = getClientIp(request);        
        ((SecurityUser)authentication.getPrincipal()).setIp(ip);                 
        HttpSession session = request.getSession();
        if (session != null) {
        	String username = ((SecurityUser)authentication.getPrincipal()).getUsername();
        	UserInfo user = userInfoMapper.readUser(username);
        	((SecurityUser)authentication.getPrincipal()).setName(user.getName());
            String redirectUrl = (String) session.getAttribute("prevPage");
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("username", username);
            param.put("ip", ip);            	
            loginhistoryMapper.insertLoginhistory(param);                
            if (redirectUrl != null) {
                session.removeAttribute("prevPage");
                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
            } else {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
	
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteHost();
        }
        return ip;
    }
    
}