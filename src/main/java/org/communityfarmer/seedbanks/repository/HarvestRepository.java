package org.communityfarmer.seedbanks.repository;

import org.communityfarmer.seedbanks.domain.Harvest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Harvest entity.
 */
public interface HarvestRepository extends JpaRepository<Harvest,Long>{

	List<Harvest> findAllByFarmerLogin(String login);
}
