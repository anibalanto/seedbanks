package org.communityfarmer.seedbanks.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.communityfarmer.seedbanks.domain.Interchange;
import org.communityfarmer.seedbanks.repository.InterchangeRepository;
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
 * REST controller for managing Interchange.
 */
@RestController
@RequestMapping("/api")
public class InterchangeResource {

    private final Logger log = LoggerFactory.getLogger(InterchangeResource.class);

    @Inject
    private InterchangeRepository interchangeRepository;

    /**
     * POST  /interchanges -> Create a new interchange.
     */
    @RequestMapping(value = "/interchanges",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void create(@RequestBody Interchange interchange) {
        log.debug("REST request to save Interchange : {}", interchange);
        interchangeRepository.save(interchange);
    }

    /**
     * GET  /interchanges -> get all the interchanges.
     */
    @RequestMapping(value = "/interchanges",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Interchange> getAll() {
        log.debug("REST request to get all Interchanges");
        return interchangeRepository.findAll();
    }

    /**
     * GET  /interchanges/:id -> get the "id" interchange.
     */
    @RequestMapping(value = "/interchanges/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Interchange> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Interchange : {}", id);
        Interchange interchange = interchangeRepository.findOne(id);
        if (interchange == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(interchange, HttpStatus.OK);
    }

    /**
     * DELETE  /interchanges/:id -> delete the "id" interchange.
     */
    @RequestMapping(value = "/interchanges/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Interchange : {}", id);
        interchangeRepository.delete(id);
    }
}
