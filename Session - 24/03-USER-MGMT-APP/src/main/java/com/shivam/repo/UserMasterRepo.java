package com.shivam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shivam.entity.UserMaster;

public interface UserMasterRepo extends JpaRepository<UserMaster, Integer>{
	
	public UserMaster findByEmail(String email);
}
