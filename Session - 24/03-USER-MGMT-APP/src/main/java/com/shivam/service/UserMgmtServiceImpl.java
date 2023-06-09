package com.shivam.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.shivam.bindings.ActivateAccount;
import com.shivam.bindings.Login;
import com.shivam.bindings.User;
import com.shivam.entity.UserMaster;
import com.shivam.repo.UserMasterRepo;
import com.shivam.utils.EmailUtils;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {

	@Autowired
	private UserMasterRepo userMasterRepo;
	
	@Autowired 
	private EmailUtils emailUtils;
	
	@Override
	public boolean saveUser(User user) {
		
		UserMaster entity=new UserMaster();
		BeanUtils.copyProperties(user, entity);
		
		
		entity.setPassword(generateRandomPassword());
		entity.setAccStatus("In-Active");;
		UserMaster save = userMasterRepo.save(entity);
		
		// TODO: Send Registration Email
		
		String subject = "Your Registration Successfull";
		String filename = "REG-MAIL-BODY.txt";
		String body=readEmailBody(entity.getFullname(),entity.getPassword(),filename);
		
		emailUtils.sendEmail(user.getEmail(), subject, body);
		
		return save.getUserId()!=null;
	}

	@Override
	public boolean activateUserAcc(ActivateAccount activateAcc) {
		UserMaster entity=new UserMaster();
		entity.setEmail(activateAcc.getEmail());
		entity.setPassword(activateAcc.getTempPwd());
		
		//QBE
		Example<UserMaster> of = Example.of(entity);
		
		List<UserMaster> findAll= userMasterRepo.findAll(of);
		
		if(findAll.isEmpty()) {
			return false;
		}else {
			UserMaster userMaster = findAll.get(0);
			userMaster.setPassword(activateAcc.getNewPwd());
			userMaster.setAccStatus("Active");
			userMasterRepo.save(userMaster);
			return true;
			
		}
		
	}

	@Override
	public List<User> getAllUsers() {
		List<UserMaster> findAll = userMasterRepo.findAll();
		
		List<User> users=new ArrayList<>();
		
		for(UserMaster entity:findAll) {
			User user= new User();
			BeanUtils.copyProperties(entity, user);
			users.add(user);
		}
		
		return users;
	}

	@Override
	public User getUserById(Integer userId) {
		Optional<UserMaster> findById = userMasterRepo.findById(userId);
		
		if(findById.isPresent()) {
			User user=new User();
			UserMaster userMaster=findById.get();
			BeanUtils.copyProperties(userMaster,user);
			return user; 
		}
		return null;
	}

	@Override
	public boolean deleteUserById(Integer userId) {
		try {
			userMasterRepo.deleteById(userId);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean changeAccountStatus(Integer userId, String accStatus) {
		Optional<UserMaster> findById = userMasterRepo.findById(userId);
		if(findById.isPresent()) {
			UserMaster userMaster=findById.get();
			userMaster.setAccStatus(accStatus);
		return true;
		}
		return false;
	}

	@Override
	public String login(Login login) {
		
		UserMaster entity=new UserMaster();
		
		entity.setEmail(login.getEmail());
		entity.setPassword(login.getPassword());
		
		Example<UserMaster> of=Example.of(entity);
		List<UserMaster> findAll = userMasterRepo.findAll(of);
		
		if(findAll.isEmpty()) {
			return "Invalid Credentials";
			
		}else {
			UserMaster userMaster=findAll.get(0);
			if(userMaster.getAccStatus().equals("Active")) {
				return "SUCCESS";
			}else {
				return "Account not activated";
			}
		}
	}

	@Override
	public String forgotPwd(String email) {
		UserMaster entity=userMasterRepo.findByEmail(email);
		
		if(entity==null) {
			return "Invalid Email";
			
		}
		
		// TODO : Send pwd to user in Email
		String subject="Forgot Password";
		String fileName = "RECOVER-PWD-BODY.txt";
		String body=readEmailBody(entity.getFullname(),entity.getPassword(),fileName);
		
		boolean sendEmail = emailUtils.sendEmail(fileName, subject, body);
		
		if(sendEmail) {
			return "Password sent to your Registered email";
		}
		
		return null;
	}
	
	private String generateRandomPassword() {
		    String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		    String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
		    String numbers = "0123456789";
		    
		    String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;
		    StringBuilder sb = new StringBuilder();
		    Random random = new Random();
		    int length = 6;

		    for(int i = 0; i < length; i++) {
		      int index = random.nextInt(alphaNumeric.length());
		      char randomChar = alphaNumeric.charAt(index);
		      sb.append(randomChar);
		    }

		    String randomString = sb.toString();
		    return randomString;

	}
	
	private String readEmailBody(String fullName,String pwd,String fileName)
	{
		
		String mailBody=null;
		String url="";
		try {
			
			FileReader fr=new FileReader(fileName);
			BufferedReader br=new BufferedReader(fr);
			StringBuffer buffer = new StringBuffer();
			String line=br.readLine();
			while(line!=null) {
				//process the data
				buffer.append(line);
				line = br.readLine();
			}
			br.close();
			
			
			mailBody = buffer.toString();
			mailBody = mailBody.replace("{FULLNAME}", fullName);
			mailBody = mailBody.replace("{TEMP-PWD}", pwd);
			mailBody = mailBody.replace("{URL}", url);
			mailBody = mailBody.replace("{PWD}", pwd);
			
			
			
		}catch(Exception e) {
			e.printStackTrace();		
		}
		
		return mailBody;
	}
}
