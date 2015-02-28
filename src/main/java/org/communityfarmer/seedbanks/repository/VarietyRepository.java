package org.communityfarmer.seedbanks.repository;

import org.communityfarmer.seedbanks.domain.Variety;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Variety entity.
 */
public interface VarietyRepository extends JpaRepository<Variety,Long>{

}
