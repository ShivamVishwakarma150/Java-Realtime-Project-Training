package com.shivam;

import org.springframework.data.repository.CrudRepository;

public interface EligibilityDetailsRepo extends CrudRepository<EligibilityDetailsRepo, Integer> {
    // You can add custom query methods or use the default methods provided by CrudRepository
}
