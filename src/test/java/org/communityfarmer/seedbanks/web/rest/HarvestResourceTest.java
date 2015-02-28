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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.util.List;

import org.communityfarmer.seedbanks.Application;
import org.communityfarmer.seedbanks.domain.Harvest;
import org.communityfarmer.seedbanks.repository.HarvestRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the HarvestResource REST controller.
 *
 * @see HarvestResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class HarvestResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    private static final String DEFAULT_CODE_VALIDATOR = "SAMPLE_TEXT";
    private static final String UPDATED_CODE_VALIDATOR = "UPDATED_TEXT";

    private static final DateTime DEFAULT_DATE = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_DATE = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_DATE_STR = dateTimeFormatter.print(DEFAULT_DATE);

    private static final Boolean DEFAULT_SHARED = false;
    private static final Boolean UPDATED_SHARED = true;

    @Inject
    private HarvestRepository harvestRepository;

    private MockMvc restHarvestMockMvc;

    private Harvest harvest;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        HarvestResource harvestResource = new HarvestResource();
        ReflectionTestUtils.setField(harvestResource, "harvestRepository", harvestRepository);
        this.restHarvestMockMvc = MockMvcBuilders.standaloneSetup(harvestResource).build();
    }

    @Before
    public void initTest() {
        harvest = new Harvest();
        harvest.setCodeValidator(DEFAULT_CODE_VALIDATOR);
        harvest.setDate(DEFAULT_DATE);
        harvest.setShared(DEFAULT_SHARED);
    }

    @Test
    @Transactional
    public void createHarvest() throws Exception {
        // Validate the database is empty
        assertThat(harvestRepository.findAll()).hasSize(0);

        // Create the Harvest
        restHarvestMockMvc.perform(post("/api/harvests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(harvest)))
                .andExpect(status().isOk());

        // Validate the Harvest in the database
        List<Harvest> harvests = harvestRepository.findAll();
        assertThat(harvests).hasSize(1);
        Harvest testHarvest = harvests.iterator().next();
        assertThat(testHarvest.getCodeValidator()).isEqualTo(DEFAULT_CODE_VALIDATOR);
        assertThat(testHarvest.getDate().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_DATE);
        assertThat(testHarvest.getShared()).isEqualTo(DEFAULT_SHARED);
    }

    @Test
    @Transactional
    public void getAllHarvests() throws Exception {
        // Initialize the database
        harvestRepository.saveAndFlush(harvest);

        // Get all the harvests
        restHarvestMockMvc.perform(get("/api/harvests"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(harvest.getId().intValue()))
                .andExpect(jsonPath("$.[0].codeValidator").value(DEFAULT_CODE_VALIDATOR.toString()))
                .andExpect(jsonPath("$.[0].date").value(DEFAULT_DATE_STR))
                .andExpect(jsonPath("$.[0].shared").value(DEFAULT_SHARED.booleanValue()));
    }

    @Test
    @Transactional
    public void getHarvest() throws Exception {
        // Initialize the database
        harvestRepository.saveAndFlush(harvest);

        // Get the harvest
        restHarvestMockMvc.perform(get("/api/harvests/{id}", harvest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(harvest.getId().intValue()))
            .andExpect(jsonPath("$.codeValidator").value(DEFAULT_CODE_VALIDATOR.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE_STR))
            .andExpect(jsonPath("$.shared").value(DEFAULT_SHARED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHarvest() throws Exception {
        // Get the harvest
        restHarvestMockMvc.perform(get("/api/harvests/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHarvest() throws Exception {
        // Initialize the database
        harvestRepository.saveAndFlush(harvest);

        // Update the harvest
        harvest.setCodeValidator(UPDATED_CODE_VALIDATOR);
        harvest.setDate(UPDATED_DATE);
        harvest.setShared(UPDATED_SHARED);
        restHarvestMockMvc.perform(post("/api/harvests")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(harvest)))
                .andExpect(status().isOk());

        // Validate the Harvest in the database
        List<Harvest> harvests = harvestRepository.findAll();
        assertThat(harvests).hasSize(1);
        Harvest testHarvest = harvests.iterator().next();
        assertThat(testHarvest.getCodeValidator()).isEqualTo(UPDATED_CODE_VALIDATOR);
        assertThat(testHarvest.getDate().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_DATE);
        assertThat(testHarvest.getShared()).isEqualTo(UPDATED_SHARED);
    }

    @Test
    @Transactional
    public void deleteHarvest() throws Exception {
        // Initialize the database
        harvestRepository.saveAndFlush(harvest);

        // Get the harvest
        restHarvestMockMvc.perform(delete("/api/harvests/{id}", harvest.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Harvest> harvests = harvestRepository.findAll();
        assertThat(harvests).hasSize(0);
    }
}
