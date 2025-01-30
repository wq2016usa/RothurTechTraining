package com.fullstack.demo.service;

import java.util.List;

import com.fullstack.demo.entity.EUser;
import org.springframework.stereotype.Service;

@Service
public interface EUserService {
	
	public EUser create(EUser euser);
	
	public List<EUser> getAllUsers();
	
	public EUser getUserById(Integer id);
	
	public EUser getUserByUsername(String username);

}
