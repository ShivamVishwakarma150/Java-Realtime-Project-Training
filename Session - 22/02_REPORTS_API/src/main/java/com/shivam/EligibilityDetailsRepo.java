package com.shivam;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shivam.entity.EligibilityDetails;

public interface EligibilityDetailsRepo extends JpaRepository<EligibilityDetails, Integer> {
    // You can add custom query methods or use the default methods provided by CrudRepository
	
	@Query("select distinct(planName) from EligibilityDetails")
	public List<String> findPlanNames();
	
	@Query("select distinct(planStatus) from EligibilityDetals")
	public List<String> findPlanStatuses();
	
	
}
