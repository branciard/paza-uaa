package com.branciard.paza.pazauaa.web.rest;

import com.branciard.paza.pazauaa.PazauaaApp;

import com.branciard.paza.pazauaa.config.SecurityBeanOverrideConfiguration;

import com.branciard.paza.pazauaa.domain.ChainUser;
import com.branciard.paza.pazauaa.repository.ChainUserRepository;
import com.branciard.paza.pazauaa.service.ChainUserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.branciard.paza.pazauaa.domain.enumeration.ChainUserType;
/**
 * Test class for the ChainUserResource REST controller.
 *
 * @see ChainUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PazauaaApp.class)
public class ChainUserResourceIntTest {

    private static final String DEFAULT_ENROLLMENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_ENROLLMENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ENROLLMENT_SECRET = "AAAAAAAAAA";
    private static final String UPDATED_ENROLLMENT_SECRET = "BBBBBBBBBB";

    private static final ChainUserType DEFAULT_TYPE = ChainUserType.CLIENT;
    private static final ChainUserType UPDATED_TYPE = ChainUserType.PEER;

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final String DEFAULT_E_CERT = "AAAAAAAAAA";
    private static final String UPDATED_E_CERT = "BBBBBBBBBB";

    @Inject
    private ChainUserRepository chainUserRepository;

    @Inject
    private ChainUserService chainUserService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restChainUserMockMvc;

    private ChainUser chainUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ChainUserResource chainUserResource = new ChainUserResource();
        ReflectionTestUtils.setField(chainUserResource, "chainUserService", chainUserService);
        this.restChainUserMockMvc = MockMvcBuilders.standaloneSetup(chainUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ChainUser createEntity(EntityManager em) {
        ChainUser chainUser = new ChainUser()
                .enrollmentId(DEFAULT_ENROLLMENT_ID)
                .enrollmentSecret(DEFAULT_ENROLLMENT_SECRET)
                .type(DEFAULT_TYPE)
                .activated(DEFAULT_ACTIVATED)
                .eCert(DEFAULT_E_CERT);
        return chainUser;
    }

    @Before
    public void initTest() {
        chainUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createChainUser() throws Exception {
        int databaseSizeBeforeCreate = chainUserRepository.findAll().size();

        // Create the ChainUser

        restChainUserMockMvc.perform(post("/api/chain-users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(chainUser)))
                .andExpect(status().isCreated());

        // Validate the ChainUser in the database
        List<ChainUser> chainUsers = chainUserRepository.findAll();
        assertThat(chainUsers).hasSize(databaseSizeBeforeCreate + 1);
        ChainUser testChainUser = chainUsers.get(chainUsers.size() - 1);
        assertThat(testChainUser.getEnrollmentId()).isEqualTo(DEFAULT_ENROLLMENT_ID);
        assertThat(testChainUser.getEnrollmentSecret()).isEqualTo(DEFAULT_ENROLLMENT_SECRET);
        assertThat(testChainUser.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testChainUser.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testChainUser.geteCert()).isEqualTo(DEFAULT_E_CERT);
    }

    @Test
    @Transactional
    public void checkEnrollmentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = chainUserRepository.findAll().size();
        // set the field null
        chainUser.setEnrollmentId(null);

        // Create the ChainUser, which fails.

        restChainUserMockMvc.perform(post("/api/chain-users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(chainUser)))
                .andExpect(status().isBadRequest());

        List<ChainUser> chainUsers = chainUserRepository.findAll();
        assertThat(chainUsers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = chainUserRepository.findAll().size();
        // set the field null
        chainUser.setType(null);

        // Create the ChainUser, which fails.

        restChainUserMockMvc.perform(post("/api/chain-users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(chainUser)))
                .andExpect(status().isBadRequest());

        List<ChainUser> chainUsers = chainUserRepository.findAll();
        assertThat(chainUsers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkActivatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = chainUserRepository.findAll().size();
        // set the field null
        chainUser.setActivated(null);

        // Create the ChainUser, which fails.

        restChainUserMockMvc.perform(post("/api/chain-users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(chainUser)))
                .andExpect(status().isBadRequest());

        List<ChainUser> chainUsers = chainUserRepository.findAll();
        assertThat(chainUsers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllChainUsers() throws Exception {
        // Initialize the database
        chainUserRepository.saveAndFlush(chainUser);

        // Get all the chainUsers
        restChainUserMockMvc.perform(get("/api/chain-users?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(chainUser.getId().intValue())))
                .andExpect(jsonPath("$.[*].enrollmentId").value(hasItem(DEFAULT_ENROLLMENT_ID.toString())))
                .andExpect(jsonPath("$.[*].enrollmentSecret").value(hasItem(DEFAULT_ENROLLMENT_SECRET.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
                .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
                .andExpect(jsonPath("$.[*].eCert").value(hasItem(DEFAULT_E_CERT.toString())));
    }

    @Test
    @Transactional
    public void getChainUser() throws Exception {
        // Initialize the database
        chainUserRepository.saveAndFlush(chainUser);

        // Get the chainUser
        restChainUserMockMvc.perform(get("/api/chain-users/{id}", chainUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chainUser.getId().intValue()))
            .andExpect(jsonPath("$.enrollmentId").value(DEFAULT_ENROLLMENT_ID.toString()))
            .andExpect(jsonPath("$.enrollmentSecret").value(DEFAULT_ENROLLMENT_SECRET.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.eCert").value(DEFAULT_E_CERT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChainUser() throws Exception {
        // Get the chainUser
        restChainUserMockMvc.perform(get("/api/chain-users/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChainUser() throws Exception {
        // Initialize the database
        chainUserService.save(chainUser);

        int databaseSizeBeforeUpdate = chainUserRepository.findAll().size();

        // Update the chainUser
        ChainUser updatedChainUser = chainUserRepository.findOne(chainUser.getId());
        updatedChainUser
                .enrollmentId(UPDATED_ENROLLMENT_ID)
                .enrollmentSecret(UPDATED_ENROLLMENT_SECRET)
                .type(UPDATED_TYPE)
                .activated(UPDATED_ACTIVATED)
                .eCert(UPDATED_E_CERT);

        restChainUserMockMvc.perform(put("/api/chain-users")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedChainUser)))
                .andExpect(status().isOk());

        // Validate the ChainUser in the database
        List<ChainUser> chainUsers = chainUserRepository.findAll();
        assertThat(chainUsers).hasSize(databaseSizeBeforeUpdate);
        ChainUser testChainUser = chainUsers.get(chainUsers.size() - 1);
        assertThat(testChainUser.getEnrollmentId()).isEqualTo(UPDATED_ENROLLMENT_ID);
        assertThat(testChainUser.getEnrollmentSecret()).isEqualTo(UPDATED_ENROLLMENT_SECRET);
        assertThat(testChainUser.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testChainUser.isActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testChainUser.geteCert()).isEqualTo(UPDATED_E_CERT);
    }

    @Test
    @Transactional
    public void deleteChainUser() throws Exception {
        // Initialize the database
        chainUserService.save(chainUser);

        int databaseSizeBeforeDelete = chainUserRepository.findAll().size();

        // Get the chainUser
        restChainUserMockMvc.perform(delete("/api/chain-users/{id}", chainUser.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ChainUser> chainUsers = chainUserRepository.findAll();
        assertThat(chainUsers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
