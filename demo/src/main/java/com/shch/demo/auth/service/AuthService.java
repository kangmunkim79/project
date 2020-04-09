package com.shch.demo.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shch.demo.auth.mapper.AuthMapper;

@Service
public class AuthService {

	@Autowired
	AuthMapper authMapper;
}
