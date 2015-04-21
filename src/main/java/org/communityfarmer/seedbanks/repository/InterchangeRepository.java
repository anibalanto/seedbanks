package org.communityfarmer.seedbanks.repository;

import org.communityfarmer.seedbanks.domain.Interchange;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Interchange entity.
 */
public interface InterchangeRepository extends JpaRepository<Interchange,Long>{

    List<Interchange> findAllByHarvestId(Long id);

}
