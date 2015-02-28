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
import java.math.BigDecimal;
import java.util.List;

import org.communityfarmer.seedbanks.Application;
import org.communityfarmer.seedbanks.domain.User;
import org.communityfarmer.seedbanks.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserResource REST controller.
 *
 * @see UserResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class UserResourceTest {


    private static final BigDecimal DEFAULT_RELIABILITY = BigDecimal.ZERO;
    private static final BigDecimal UPDATED_RELIABILITY = BigDecimal.ONE;

    @Inject
    private UserRepository userRepository;

    private MockMvc restUserMockMvc;

    private User user;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserResource userResource = new UserResource();
        ReflectionTestUtils.setField(userResource, "userRepository", userRepository);
        this.restUserMockMvc = MockMvcBuilders.standaloneSetup(userResource).build();
    }

    @Before
    public void initTest() {
        user = new User();
        user.setReliability(DEFAULT_RELIABILITY);
    }

    @Test
    @Transactional
    public void createUser() throws Exception {
        // Validate the database is empty
        assertThat(userRepository.findAll()).hasSize(0);

        // Create the User
        restUserMockMvc.perform(post("/api/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user)))
                .andExpect(status().isOk());

        // Validate the User in the database
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(1);
        User testUser = users.iterator().next();
        assertThat(testUser.getReliability()).isEqualTo(DEFAULT_RELIABILITY);
    }

    @Test
    @Transactional
    public void getAllUsers() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        // Get all the users
        restUserMockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[0].id").value(user.getId().intValue()))
                .andExpect(jsonPath("$.[0].reliability").value(DEFAULT_RELIABILITY.intValue()));
    }

    @Test
    @Transactional
    public void getUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        // Get the user
        restUserMockMvc.perform(get("/api/users/{id}", user.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(user.getId().intValue()))
            .andExpect(jsonPath("$.reliability").value(DEFAULT_RELIABILITY.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUser() throws Exception {
        // Get the user
        restUserMockMvc.perform(get("/api/users/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        // Update the user
        user.setReliability(UPDATED_RELIABILITY);
        restUserMockMvc.perform(post("/api/users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(user)))
                .andExpect(status().isOk());

        // Validate the User in the database
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(1);
        User testUser = users.iterator().next();
        assertThat(testUser.getReliability()).isEqualTo(UPDATED_RELIABILITY);
    }

    @Test
    @Transactional
    public void deleteUser() throws Exception {
        // Initialize the database
        userRepository.saveAndFlush(user);

        // Get the user
        restUserMockMvc.perform(delete("/api/users/{id}", user.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(0);
    }
}
