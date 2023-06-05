package com.shivam.service;

import java.util.List;

import com.shivam.bindings.ActivateAccount;
import com.shivam.bindings.Login;
import com.shivam.bindings.User;

public interface UserMgmtService {

	public boolean saveUser(User user);
	
	public boolean activateUserAcc(ActivateAccount activateAcc);
	
	public List<User> getAllUsers();
	
	public User getUserById(Integer userId);
	
	public boolean deleteUserById(Integer userId);
	
	public boolean changeAccountStatus(Integer userId,String accStatus);
	
	public String login(Login login);
	
	public String forgotPwd(String email);
	
}
