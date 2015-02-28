package org.communityfarmer.seedbanks.web.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import org.communityfarmer.seedbanks.Application;
import org.communityfarmer.seedbanks.domain.Interchange;
import org.communityfarmer.seedbanks.repository.InterchangeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InterchangeResource REST controller.
 *
 * @see InterchangeResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class InterchangeResourceTest {


    private static final Integer DEFAULT_SCORE = 0;
    private static final Integer UPDATED_SCORE = 1;
    private static final String DEFAULT_STATE = "SAMPLE_TEXT";
    private static final String UPDATED_STATE = "UPDATED_TEXT";

    @Inject
    private InterchangeRepository interchangeRepository;

    private MockMvc restInterchangeMockMvc;

    private Interchange interchange;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        InterchangeResource interchangeResource = new InterchangeResource();
        ReflectionTestUtils.setField(interchangeResource, "interchangeRepository", interchangeRepository);
        this.restInterchangeMockMvc = MockMvcBuilders.standaloneSetup(interchangeResource).build();
    }

    @Before
    public void initTest() {
        interchange = new Interchange();
        interchange.setScore(DEFAULT_SCORE);
        interchange.setState(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void createInterchange() throws Exception {
        // Validate the database is empty
        assertThat(interchangeRepository.findAll()).hasSize(0);

        // Create the Interchange
        restInterchangeMockMvc.perform(post("/api/interchanges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(interchange)))
                .andExpect(status().isOk());

        // Validate the Interchange in the database
        List<Interchange> interchanges = interchangeRepository.findAll();
        assertThat(interchanges).hasSize(1);
        Interchange testInterchange = interchanges.iterator().next();
        assertThat(testInterchange.getScore()).isEqualTo(DEFAULT_SCORE);
        assertThat(testInterchange.getState()).isEqualTo(DEFAULT_STATE);
    }

    @Test
    @Transactional
    public void getAllInterchanges() throws Exception {
        // Initialize the database
        interchangeRepository.saveAndFlush(interchange);

        // Get all the interchanges
        restInterchangeMockMvc.perform(get("/api/interchanges"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(interchange.getId().intValue()))
                .andExpect(jsonPath("$.[0].score").value(DEFAULT_SCORE))
                .andExpect(jsonPath("$.[0].state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    public void getInterchange() throws Exception {
        // Initialize the database
        interchangeRepository.saveAndFlush(interchange);

        // Get the interchange
        restInterchangeMockMvc.perform(get("/api/interchanges/{id}", interchange.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(interchange.getId().intValue()))
            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
            .andExpect(jsonPath("$.state").value(DEFAULT_STATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInterchange() throws Exception {
        // Get the interchange
        restInterchangeMockMvc.perform(get("/api/interchanges/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInterchange() throws Exception {
        // Initialize the database
        interchangeRepository.saveAndFlush(interchange);

        // Update the interchange
        interchange.setScore(UPDATED_SCORE);
        interchange.setState(UPDATED_STATE);
        restInterchangeMockMvc.perform(post("/api/interchanges")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(interchange)))
                .andExpect(status().isOk());

        // Validate the Interchange in the database
        List<Interchange> interchanges = interchangeRepository.findAll();
        assertThat(interchanges).hasSize(1);
        Interchange testInterchange = interchanges.iterator().next();
        assertThat(testInterchange.getScore()).isEqualTo(UPDATED_SCORE);
        assertThat(testInterchange.getState()).isEqualTo(UPDATED_STATE);
    }

    @Test
    @Transactional
    public void deleteInterchange() throws Exception {
        // Initialize the database
        interchangeRepository.saveAndFlush(interchange);

        // Get the interchange
        restInterchangeMockMvc.perform(delete("/api/interchanges/{id}", interchange.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Interchange> interchanges = interchangeRepository.findAll();
        assertThat(interchanges).hasSize(0);
    }
}
