package org.communityfarmer.seedbanks.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.communityfarmer.seedbanks.domain.Harvest;
import org.communityfarmer.seedbanks.domain.Variety;
import org.communityfarmer.seedbanks.repository.HarvestRepository;
import org.communityfarmer.seedbanks.repository.UserRepository;
import org.communityfarmer.seedbanks.repository.VarietyRepository;
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
 * REST controller for managing Variety.
 */
@RestController
@RequestMapping("/api")
public class VarietyResource {

    private final Logger log = LoggerFactory.getLogger(VarietyResource.class);

    @Inject
    private VarietyRepository varietyRepository;

    @Inject
    private HarvestRepository harvestRepository;

    /**
     * POST  /varietys -> Create a new variety.
     */
    @RequestMapping(value = "/varietys",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Variety variety) {
        log.debug("REST request to save Variety : {}", variety);
        varietyRepository.save(variety);
    }

    /**
     * GET  /varietys -> get all the varietys.
     */
    @RequestMapping(value = "/varietys",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Variety> getAll() {
        log.debug("REST request to get all Varietys");
        return varietyRepository.findAll();
    }

    /**
     * GET  /varietys/:id -> get the "id" variety.
     */
    @RequestMapping(value = "/varietys/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Variety> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Variety : {}", id);
        Variety variety = varietyRepository.findOne(id);
        if (variety == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(variety, HttpStatus.OK);
    }


    /**
     * GET  /varietys/:id -> get the "id" variety.
     */
    @RequestMapping(value = "/varietys/{id}/harvests",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Harvest> getHarvestsByVariety(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Harvests shared by Variety");
        return harvestRepository.findAllByVarietyIdAndSharedIsTrue(id);
    }

    /**
     * DELETE  /varietys/:id -> delete the "id" variety.
     */
    @RequestMapping(value = "/varietys/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Variety : {}", id);
        varietyRepository.delete(id);
    }
}
