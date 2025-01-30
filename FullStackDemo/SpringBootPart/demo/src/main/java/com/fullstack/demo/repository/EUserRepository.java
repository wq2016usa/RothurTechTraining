package com.fullstack.demo.repository;

import com.fullstack.demo.entity.EUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EUserRepository extends JpaRepository<EUser, Integer>{
	EUser findByUsername(String username);	
}
