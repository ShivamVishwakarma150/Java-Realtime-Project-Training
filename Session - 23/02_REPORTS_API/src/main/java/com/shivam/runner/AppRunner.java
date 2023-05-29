package com.shivam.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.shivam.EligibilityDetailsRepo;
import com.shivam.entity.EligibilityDetails;

@Component
public class AppRunner implements ApplicationRunner {
	
	@Autowired
	private EligibilityDetailsRepo repo;
	
	public void run(ApplicationArguments args) {
		EligibilityDetails entity1 = new EligibilityDetails();
		
		entity1.setEligid(1);
		entity1.setName("John");
		entity1.setMobile(324324324l);
		entity1.setGender('M');
		entity1.setSsn(343543445342l);
		entity1.setPlanName("SNAP");
		entity1.setPlanStatus("Approved");
		repo.save(entity1);
		
		EligibilityDetails entity2 = new EligibilityDetails();
		
		entity2.setEligid(2);
		entity2.setName("Smith");
		entity2.setMobile(343432423l);
		entity2.setGender('M');
		entity2.setSsn(3445433445342l);
		entity2.setPlanName("CAP");
		entity2.setPlanStatus("Denied");
		repo.save(entity2);
		
		EligibilityDetails entity3 = new EligibilityDetails();
		
		entity3.setEligid(3);
		entity3.setName("Wikk");
		entity3.setMobile(33344324324l);
		entity3.setGender('F');
		entity3.setSsn(35454445342l);
		entity3.setPlanName("SNAP");
		entity3.setPlanStatus("Approved");
		repo.save(entity3);
		
	}
}
