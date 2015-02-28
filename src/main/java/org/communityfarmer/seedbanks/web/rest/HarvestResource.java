package org.communityfarmer.seedbanks.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.communityfarmer.seedbanks.domain.Harvest;
import org.communityfarmer.seedbanks.repository.HarvestRepository;
import org.communityfarmer.seedbanks.repository.UserRepository;
import org.communityfarmer.seedbanks.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Harvest.
 */
@RestController
@RequestMapping("/api")
public class HarvestResource {

    private final Logger log = LoggerFactory.getLogger(HarvestResource.class);

    @Inject
    private HarvestRepository harvestRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /harvests -> Create a new harvest.
     */
    @RequestMapping(value = "/harvests",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Harvest harvest) {
		harvest.setFarmer(userRepository.findOneByLogin(SecurityUtils.getCurrentLogin()));
        log.debug("REST request to save Harvest : {}", harvest);
        harvestRepository.save(harvest);
    }

    /**
     * GET  /harvests -> get all the harvests.
     */
    @RequestMapping(value = "/harvests",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Harvest> getAll() {
        log.debug("REST request to get all Harvests");
        //return harvestRepository.findAll();
        return harvestRepository.findAllByFarmerLogin(SecurityUtils.getCurrentLogin());
    }

    /**
     * GET  /harvests/:id -> get the "id" harvest.
     */
    @RequestMapping(value = "/harvests/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Harvest> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Harvest : {}", id);
        Harvest harvest = harvestRepository.findOne(id);
        if (harvest == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(harvest, HttpStatus.OK);
    }

    /**
     * DELETE  /harvests/:id -> delete the "id" harvest.
     */
    @RequestMapping(value = "/harvests/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Harvest : {}", id);
        harvestRepository.delete(id);
    }
}
