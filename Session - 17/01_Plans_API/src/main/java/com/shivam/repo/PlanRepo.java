package com.shivam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shivam.entity.Plan;

public interface PlanRepo extends JpaRepository<Plan, Integer> {

}
