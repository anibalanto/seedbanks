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
import org.communityfarmer.seedbanks.domain.Variety;
import org.communityfarmer.seedbanks.repository.VarietyRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VarietyResource REST controller.
 *
 * @see VarietyResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class VarietyResourceTest {

    private static final String DEFAULT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_NAME = "UPDATED_TEXT";

    @Inject
    private VarietyRepository varietyRepository;

    private MockMvc restVarietyMockMvc;

    private Variety variety;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VarietyResource varietyResource = new VarietyResource();
        ReflectionTestUtils.setField(varietyResource, "varietyRepository", varietyRepository);
        this.restVarietyMockMvc = MockMvcBuilders.standaloneSetup(varietyResource).build();
    }

    @Before
    public void initTest() {
        variety = new Variety();
        variety.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createVariety() throws Exception {
        // Validate the database is empty
        assertThat(varietyRepository.findAll()).hasSize(0);

        // Create the Variety
        restVarietyMockMvc.perform(post("/api/varietys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(variety)))
                .andExpect(status().isOk());

        // Validate the Variety in the database
        List<Variety> varietys = varietyRepository.findAll();
        assertThat(varietys).hasSize(1);
        Variety testVariety = varietys.iterator().next();
        assertThat(testVariety.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void getAllVarietys() throws Exception {
        // Initialize the database
        varietyRepository.saveAndFlush(variety);

        // Get all the varietys
        restVarietyMockMvc.perform(get("/api/varietys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(variety.getId().intValue()))
                .andExpect(jsonPath("$.[0].name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getVariety() throws Exception {
        // Initialize the database
        varietyRepository.saveAndFlush(variety);

        // Get the variety
        restVarietyMockMvc.perform(get("/api/varietys/{id}", variety.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(variety.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVariety() throws Exception {
        // Get the variety
        restVarietyMockMvc.perform(get("/api/varietys/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVariety() throws Exception {
        // Initialize the database
        varietyRepository.saveAndFlush(variety);

        // Update the variety
        variety.setName(UPDATED_NAME);
        restVarietyMockMvc.perform(post("/api/varietys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(variety)))
                .andExpect(status().isOk());

        // Validate the Variety in the database
        List<Variety> varietys = varietyRepository.findAll();
        assertThat(varietys).hasSize(1);
        Variety testVariety = varietys.iterator().next();
        assertThat(testVariety.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteVariety() throws Exception {
        // Initialize the database
        varietyRepository.saveAndFlush(variety);

        // Get the variety
        restVarietyMockMvc.perform(delete("/api/varietys/{id}", variety.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Variety> varietys = varietyRepository.findAll();
        assertThat(varietys).hasSize(0);
    }
}
