package ru.vlsu.psytest.web.rest;

import ru.vlsu.psytest.PsychologicallyTestBackendApp;
import ru.vlsu.psytest.domain.ResultTest;
import ru.vlsu.psytest.domain.User;
import ru.vlsu.psytest.repository.ResultTestRepository;
import ru.vlsu.psytest.service.ResultTestService;
import ru.vlsu.psytest.service.dto.ResultTestDTO;
import ru.vlsu.psytest.service.mapper.ResultTestMapper;
import ru.vlsu.psytest.service.dto.ResultTestCriteria;
import ru.vlsu.psytest.service.ResultTestQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ResultTestResource} REST controller.
 */
@SpringBootTest(classes = PsychologicallyTestBackendApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ResultTestResourceIT {

    private static final Instant DEFAULT_FINISHED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FINISHED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_DEMONSTRATIVE_TYPE = 1;
    private static final Integer UPDATED_DEMONSTRATIVE_TYPE = 2;
    private static final Integer SMALLER_DEMONSTRATIVE_TYPE = 1 - 1;

    private static final Integer DEFAULT_STUCK_TYPE = 1;
    private static final Integer UPDATED_STUCK_TYPE = 2;
    private static final Integer SMALLER_STUCK_TYPE = 1 - 1;

    private static final Integer DEFAULT_PEDANTIC_TYPE = 1;
    private static final Integer UPDATED_PEDANTIC_TYPE = 2;
    private static final Integer SMALLER_PEDANTIC_TYPE = 1 - 1;

    private static final Integer DEFAULT_EXCITABLE_TYPE = 1;
    private static final Integer UPDATED_EXCITABLE_TYPE = 2;
    private static final Integer SMALLER_EXCITABLE_TYPE = 1 - 1;

    private static final Integer DEFAULT_HYPERTHYMIC_TYPE = 1;
    private static final Integer UPDATED_HYPERTHYMIC_TYPE = 2;
    private static final Integer SMALLER_HYPERTHYMIC_TYPE = 1 - 1;

    private static final Integer DEFAULT_DYSTHYMIC_TYPE = 1;
    private static final Integer UPDATED_DYSTHYMIC_TYPE = 2;
    private static final Integer SMALLER_DYSTHYMIC_TYPE = 1 - 1;

    private static final Integer DEFAULT_ANXIOUSLY_FEARFUL_TYPE = 1;
    private static final Integer UPDATED_ANXIOUSLY_FEARFUL_TYPE = 2;
    private static final Integer SMALLER_ANXIOUSLY_FEARFUL_TYPE = 1 - 1;

    private static final Integer DEFAULT_EMOTIONALLY_EXALTED_TYPE = 1;
    private static final Integer UPDATED_EMOTIONALLY_EXALTED_TYPE = 2;
    private static final Integer SMALLER_EMOTIONALLY_EXALTED_TYPE = 1 - 1;

    private static final Integer DEFAULT_EMOTIVE_TYPE = 1;
    private static final Integer UPDATED_EMOTIVE_TYPE = 2;
    private static final Integer SMALLER_EMOTIVE_TYPE = 1 - 1;

    private static final Integer DEFAULT_CYCLOTHYMIC_TYPE = 1;
    private static final Integer UPDATED_CYCLOTHYMIC_TYPE = 2;
    private static final Integer SMALLER_CYCLOTHYMIC_TYPE = 1 - 1;

    @Autowired
    private ResultTestRepository resultTestRepository;

    @Autowired
    private ResultTestMapper resultTestMapper;

    @Autowired
    private ResultTestService resultTestService;

    @Autowired
    private ResultTestQueryService resultTestQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restResultTestMockMvc;

    private ResultTest resultTest;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultTest createEntity(EntityManager em) {
        ResultTest resultTest = new ResultTest()
            .finishedAt(DEFAULT_FINISHED_AT)
            .demonstrativeType(DEFAULT_DEMONSTRATIVE_TYPE)
            .stuckType(DEFAULT_STUCK_TYPE)
            .pedanticType(DEFAULT_PEDANTIC_TYPE)
            .excitableType(DEFAULT_EXCITABLE_TYPE)
            .hyperthymicType(DEFAULT_HYPERTHYMIC_TYPE)
            .dysthymicType(DEFAULT_DYSTHYMIC_TYPE)
            .anxiouslyFearfulType(DEFAULT_ANXIOUSLY_FEARFUL_TYPE)
            .emotionallyExaltedType(DEFAULT_EMOTIONALLY_EXALTED_TYPE)
            .emotiveType(DEFAULT_EMOTIVE_TYPE)
            .cyclothymicType(DEFAULT_CYCLOTHYMIC_TYPE);
        return resultTest;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ResultTest createUpdatedEntity(EntityManager em) {
        ResultTest resultTest = new ResultTest()
            .finishedAt(UPDATED_FINISHED_AT)
            .demonstrativeType(UPDATED_DEMONSTRATIVE_TYPE)
            .stuckType(UPDATED_STUCK_TYPE)
            .pedanticType(UPDATED_PEDANTIC_TYPE)
            .excitableType(UPDATED_EXCITABLE_TYPE)
            .hyperthymicType(UPDATED_HYPERTHYMIC_TYPE)
            .dysthymicType(UPDATED_DYSTHYMIC_TYPE)
            .anxiouslyFearfulType(UPDATED_ANXIOUSLY_FEARFUL_TYPE)
            .emotionallyExaltedType(UPDATED_EMOTIONALLY_EXALTED_TYPE)
            .emotiveType(UPDATED_EMOTIVE_TYPE)
            .cyclothymicType(UPDATED_CYCLOTHYMIC_TYPE);
        return resultTest;
    }

    @BeforeEach
    public void initTest() {
        resultTest = createEntity(em);
    }

    @Test
    @Transactional
    public void createResultTest() throws Exception {
        int databaseSizeBeforeCreate = resultTestRepository.findAll().size();
        // Create the ResultTest
        ResultTestDTO resultTestDTO = resultTestMapper.toDto(resultTest);
        restResultTestMockMvc.perform(post("/api/result-tests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultTestDTO)))
            .andExpect(status().isCreated());

        // Validate the ResultTest in the database
        List<ResultTest> resultTestList = resultTestRepository.findAll();
        assertThat(resultTestList).hasSize(databaseSizeBeforeCreate + 1);
        ResultTest testResultTest = resultTestList.get(resultTestList.size() - 1);
        assertThat(testResultTest.getFinishedAt()).isEqualTo(DEFAULT_FINISHED_AT);
        assertThat(testResultTest.getDemonstrativeType()).isEqualTo(DEFAULT_DEMONSTRATIVE_TYPE);
        assertThat(testResultTest.getStuckType()).isEqualTo(DEFAULT_STUCK_TYPE);
        assertThat(testResultTest.getPedanticType()).isEqualTo(DEFAULT_PEDANTIC_TYPE);
        assertThat(testResultTest.getExcitableType()).isEqualTo(DEFAULT_EXCITABLE_TYPE);
        assertThat(testResultTest.getHyperthymicType()).isEqualTo(DEFAULT_HYPERTHYMIC_TYPE);
        assertThat(testResultTest.getDysthymicType()).isEqualTo(DEFAULT_DYSTHYMIC_TYPE);
        assertThat(testResultTest.getAnxiouslyFearfulType()).isEqualTo(DEFAULT_ANXIOUSLY_FEARFUL_TYPE);
        assertThat(testResultTest.getEmotionallyExaltedType()).isEqualTo(DEFAULT_EMOTIONALLY_EXALTED_TYPE);
        assertThat(testResultTest.getEmotiveType()).isEqualTo(DEFAULT_EMOTIVE_TYPE);
        assertThat(testResultTest.getCyclothymicType()).isEqualTo(DEFAULT_CYCLOTHYMIC_TYPE);
    }

    @Test
    @Transactional
    public void createResultTestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resultTestRepository.findAll().size();

        // Create the ResultTest with an existing ID
        resultTest.setId(1L);
        ResultTestDTO resultTestDTO = resultTestMapper.toDto(resultTest);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResultTestMockMvc.perform(post("/api/result-tests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultTestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResultTest in the database
        List<ResultTest> resultTestList = resultTestRepository.findAll();
        assertThat(resultTestList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFinishedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultTestRepository.findAll().size();
        // set the field null
        resultTest.setFinishedAt(null);

        // Create the ResultTest, which fails.
        ResultTestDTO resultTestDTO = resultTestMapper.toDto(resultTest);


        restResultTestMockMvc.perform(post("/api/result-tests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultTestDTO)))
            .andExpect(status().isBadRequest());

        List<ResultTest> resultTestList = resultTestRepository.findAll();
        assertThat(resultTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDemonstrativeTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultTestRepository.findAll().size();
        // set the field null
        resultTest.setDemonstrativeType(null);

        // Create the ResultTest, which fails.
        ResultTestDTO resultTestDTO = resultTestMapper.toDto(resultTest);


        restResultTestMockMvc.perform(post("/api/result-tests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultTestDTO)))
            .andExpect(status().isBadRequest());

        List<ResultTest> resultTestList = resultTestRepository.findAll();
        assertThat(resultTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStuckTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultTestRepository.findAll().size();
        // set the field null
        resultTest.setStuckType(null);

        // Create the ResultTest, which fails.
        ResultTestDTO resultTestDTO = resultTestMapper.toDto(resultTest);


        restResultTestMockMvc.perform(post("/api/result-tests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultTestDTO)))
            .andExpect(status().isBadRequest());

        List<ResultTest> resultTestList = resultTestRepository.findAll();
        assertThat(resultTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPedanticTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultTestRepository.findAll().size();
        // set the field null
        resultTest.setPedanticType(null);

        // Create the ResultTest, which fails.
        ResultTestDTO resultTestDTO = resultTestMapper.toDto(resultTest);


        restResultTestMockMvc.perform(post("/api/result-tests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultTestDTO)))
            .andExpect(status().isBadRequest());

        List<ResultTest> resultTestList = resultTestRepository.findAll();
        assertThat(resultTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExcitableTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultTestRepository.findAll().size();
        // set the field null
        resultTest.setExcitableType(null);

        // Create the ResultTest, which fails.
        ResultTestDTO resultTestDTO = resultTestMapper.toDto(resultTest);


        restResultTestMockMvc.perform(post("/api/result-tests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultTestDTO)))
            .andExpect(status().isBadRequest());

        List<ResultTest> resultTestList = resultTestRepository.findAll();
        assertThat(resultTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHyperthymicTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultTestRepository.findAll().size();
        // set the field null
        resultTest.setHyperthymicType(null);

        // Create the ResultTest, which fails.
        ResultTestDTO resultTestDTO = resultTestMapper.toDto(resultTest);


        restResultTestMockMvc.perform(post("/api/result-tests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultTestDTO)))
            .andExpect(status().isBadRequest());

        List<ResultTest> resultTestList = resultTestRepository.findAll();
        assertThat(resultTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDysthymicTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultTestRepository.findAll().size();
        // set the field null
        resultTest.setDysthymicType(null);

        // Create the ResultTest, which fails.
        ResultTestDTO resultTestDTO = resultTestMapper.toDto(resultTest);


        restResultTestMockMvc.perform(post("/api/result-tests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultTestDTO)))
            .andExpect(status().isBadRequest());

        List<ResultTest> resultTestList = resultTestRepository.findAll();
        assertThat(resultTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnxiouslyFearfulTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultTestRepository.findAll().size();
        // set the field null
        resultTest.setAnxiouslyFearfulType(null);

        // Create the ResultTest, which fails.
        ResultTestDTO resultTestDTO = resultTestMapper.toDto(resultTest);


        restResultTestMockMvc.perform(post("/api/result-tests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultTestDTO)))
            .andExpect(status().isBadRequest());

        List<ResultTest> resultTestList = resultTestRepository.findAll();
        assertThat(resultTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmotionallyExaltedTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultTestRepository.findAll().size();
        // set the field null
        resultTest.setEmotionallyExaltedType(null);

        // Create the ResultTest, which fails.
        ResultTestDTO resultTestDTO = resultTestMapper.toDto(resultTest);


        restResultTestMockMvc.perform(post("/api/result-tests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultTestDTO)))
            .andExpect(status().isBadRequest());

        List<ResultTest> resultTestList = resultTestRepository.findAll();
        assertThat(resultTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmotiveTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resultTestRepository.findAll().size();
        // set the field null
        resultTest.setEmotiveType(null);

        // Create the ResultTest, which fails.
        ResultTestDTO resultTestDTO = resultTestMapper.toDto(resultTest);


        restResultTestMockMvc.perform(post("/api/result-tests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultTestDTO)))
            .andExpect(status().isBadRequest());

        List<ResultTest> resultTestList = resultTestRepository.findAll();
        assertThat(resultTestList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResultTests() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList
        restResultTestMockMvc.perform(get("/api/result-tests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultTest.getId().intValue())))
            .andExpect(jsonPath("$.[*].finishedAt").value(hasItem(DEFAULT_FINISHED_AT.toString())))
            .andExpect(jsonPath("$.[*].demonstrativeType").value(hasItem(DEFAULT_DEMONSTRATIVE_TYPE)))
            .andExpect(jsonPath("$.[*].stuckType").value(hasItem(DEFAULT_STUCK_TYPE)))
            .andExpect(jsonPath("$.[*].pedanticType").value(hasItem(DEFAULT_PEDANTIC_TYPE)))
            .andExpect(jsonPath("$.[*].excitableType").value(hasItem(DEFAULT_EXCITABLE_TYPE)))
            .andExpect(jsonPath("$.[*].hyperthymicType").value(hasItem(DEFAULT_HYPERTHYMIC_TYPE)))
            .andExpect(jsonPath("$.[*].dysthymicType").value(hasItem(DEFAULT_DYSTHYMIC_TYPE)))
            .andExpect(jsonPath("$.[*].anxiouslyFearfulType").value(hasItem(DEFAULT_ANXIOUSLY_FEARFUL_TYPE)))
            .andExpect(jsonPath("$.[*].emotionallyExaltedType").value(hasItem(DEFAULT_EMOTIONALLY_EXALTED_TYPE)))
            .andExpect(jsonPath("$.[*].emotiveType").value(hasItem(DEFAULT_EMOTIVE_TYPE)))
            .andExpect(jsonPath("$.[*].cyclothymicType").value(hasItem(DEFAULT_CYCLOTHYMIC_TYPE)));
    }
    
    @Test
    @Transactional
    public void getResultTest() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get the resultTest
        restResultTestMockMvc.perform(get("/api/result-tests/{id}", resultTest.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(resultTest.getId().intValue()))
            .andExpect(jsonPath("$.finishedAt").value(DEFAULT_FINISHED_AT.toString()))
            .andExpect(jsonPath("$.demonstrativeType").value(DEFAULT_DEMONSTRATIVE_TYPE))
            .andExpect(jsonPath("$.stuckType").value(DEFAULT_STUCK_TYPE))
            .andExpect(jsonPath("$.pedanticType").value(DEFAULT_PEDANTIC_TYPE))
            .andExpect(jsonPath("$.excitableType").value(DEFAULT_EXCITABLE_TYPE))
            .andExpect(jsonPath("$.hyperthymicType").value(DEFAULT_HYPERTHYMIC_TYPE))
            .andExpect(jsonPath("$.dysthymicType").value(DEFAULT_DYSTHYMIC_TYPE))
            .andExpect(jsonPath("$.anxiouslyFearfulType").value(DEFAULT_ANXIOUSLY_FEARFUL_TYPE))
            .andExpect(jsonPath("$.emotionallyExaltedType").value(DEFAULT_EMOTIONALLY_EXALTED_TYPE))
            .andExpect(jsonPath("$.emotiveType").value(DEFAULT_EMOTIVE_TYPE))
            .andExpect(jsonPath("$.cyclothymicType").value(DEFAULT_CYCLOTHYMIC_TYPE));
    }


    @Test
    @Transactional
    public void getResultTestsByIdFiltering() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        Long id = resultTest.getId();

        defaultResultTestShouldBeFound("id.equals=" + id);
        defaultResultTestShouldNotBeFound("id.notEquals=" + id);

        defaultResultTestShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultResultTestShouldNotBeFound("id.greaterThan=" + id);

        defaultResultTestShouldBeFound("id.lessThanOrEqual=" + id);
        defaultResultTestShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllResultTestsByFinishedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where finishedAt equals to DEFAULT_FINISHED_AT
        defaultResultTestShouldBeFound("finishedAt.equals=" + DEFAULT_FINISHED_AT);

        // Get all the resultTestList where finishedAt equals to UPDATED_FINISHED_AT
        defaultResultTestShouldNotBeFound("finishedAt.equals=" + UPDATED_FINISHED_AT);
    }

    @Test
    @Transactional
    public void getAllResultTestsByFinishedAtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where finishedAt not equals to DEFAULT_FINISHED_AT
        defaultResultTestShouldNotBeFound("finishedAt.notEquals=" + DEFAULT_FINISHED_AT);

        // Get all the resultTestList where finishedAt not equals to UPDATED_FINISHED_AT
        defaultResultTestShouldBeFound("finishedAt.notEquals=" + UPDATED_FINISHED_AT);
    }

    @Test
    @Transactional
    public void getAllResultTestsByFinishedAtIsInShouldWork() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where finishedAt in DEFAULT_FINISHED_AT or UPDATED_FINISHED_AT
        defaultResultTestShouldBeFound("finishedAt.in=" + DEFAULT_FINISHED_AT + "," + UPDATED_FINISHED_AT);

        // Get all the resultTestList where finishedAt equals to UPDATED_FINISHED_AT
        defaultResultTestShouldNotBeFound("finishedAt.in=" + UPDATED_FINISHED_AT);
    }

    @Test
    @Transactional
    public void getAllResultTestsByFinishedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where finishedAt is not null
        defaultResultTestShouldBeFound("finishedAt.specified=true");

        // Get all the resultTestList where finishedAt is null
        defaultResultTestShouldNotBeFound("finishedAt.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultTestsByDemonstrativeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where demonstrativeType equals to DEFAULT_DEMONSTRATIVE_TYPE
        defaultResultTestShouldBeFound("demonstrativeType.equals=" + DEFAULT_DEMONSTRATIVE_TYPE);

        // Get all the resultTestList where demonstrativeType equals to UPDATED_DEMONSTRATIVE_TYPE
        defaultResultTestShouldNotBeFound("demonstrativeType.equals=" + UPDATED_DEMONSTRATIVE_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByDemonstrativeTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where demonstrativeType not equals to DEFAULT_DEMONSTRATIVE_TYPE
        defaultResultTestShouldNotBeFound("demonstrativeType.notEquals=" + DEFAULT_DEMONSTRATIVE_TYPE);

        // Get all the resultTestList where demonstrativeType not equals to UPDATED_DEMONSTRATIVE_TYPE
        defaultResultTestShouldBeFound("demonstrativeType.notEquals=" + UPDATED_DEMONSTRATIVE_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByDemonstrativeTypeIsInShouldWork() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where demonstrativeType in DEFAULT_DEMONSTRATIVE_TYPE or UPDATED_DEMONSTRATIVE_TYPE
        defaultResultTestShouldBeFound("demonstrativeType.in=" + DEFAULT_DEMONSTRATIVE_TYPE + "," + UPDATED_DEMONSTRATIVE_TYPE);

        // Get all the resultTestList where demonstrativeType equals to UPDATED_DEMONSTRATIVE_TYPE
        defaultResultTestShouldNotBeFound("demonstrativeType.in=" + UPDATED_DEMONSTRATIVE_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByDemonstrativeTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where demonstrativeType is not null
        defaultResultTestShouldBeFound("demonstrativeType.specified=true");

        // Get all the resultTestList where demonstrativeType is null
        defaultResultTestShouldNotBeFound("demonstrativeType.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultTestsByDemonstrativeTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where demonstrativeType is greater than or equal to DEFAULT_DEMONSTRATIVE_TYPE
        defaultResultTestShouldBeFound("demonstrativeType.greaterThanOrEqual=" + DEFAULT_DEMONSTRATIVE_TYPE);

        // Get all the resultTestList where demonstrativeType is greater than or equal to UPDATED_DEMONSTRATIVE_TYPE
        defaultResultTestShouldNotBeFound("demonstrativeType.greaterThanOrEqual=" + UPDATED_DEMONSTRATIVE_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByDemonstrativeTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where demonstrativeType is less than or equal to DEFAULT_DEMONSTRATIVE_TYPE
        defaultResultTestShouldBeFound("demonstrativeType.lessThanOrEqual=" + DEFAULT_DEMONSTRATIVE_TYPE);

        // Get all the resultTestList where demonstrativeType is less than or equal to SMALLER_DEMONSTRATIVE_TYPE
        defaultResultTestShouldNotBeFound("demonstrativeType.lessThanOrEqual=" + SMALLER_DEMONSTRATIVE_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByDemonstrativeTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where demonstrativeType is less than DEFAULT_DEMONSTRATIVE_TYPE
        defaultResultTestShouldNotBeFound("demonstrativeType.lessThan=" + DEFAULT_DEMONSTRATIVE_TYPE);

        // Get all the resultTestList where demonstrativeType is less than UPDATED_DEMONSTRATIVE_TYPE
        defaultResultTestShouldBeFound("demonstrativeType.lessThan=" + UPDATED_DEMONSTRATIVE_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByDemonstrativeTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where demonstrativeType is greater than DEFAULT_DEMONSTRATIVE_TYPE
        defaultResultTestShouldNotBeFound("demonstrativeType.greaterThan=" + DEFAULT_DEMONSTRATIVE_TYPE);

        // Get all the resultTestList where demonstrativeType is greater than SMALLER_DEMONSTRATIVE_TYPE
        defaultResultTestShouldBeFound("demonstrativeType.greaterThan=" + SMALLER_DEMONSTRATIVE_TYPE);
    }


    @Test
    @Transactional
    public void getAllResultTestsByStuckTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where stuckType equals to DEFAULT_STUCK_TYPE
        defaultResultTestShouldBeFound("stuckType.equals=" + DEFAULT_STUCK_TYPE);

        // Get all the resultTestList where stuckType equals to UPDATED_STUCK_TYPE
        defaultResultTestShouldNotBeFound("stuckType.equals=" + UPDATED_STUCK_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByStuckTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where stuckType not equals to DEFAULT_STUCK_TYPE
        defaultResultTestShouldNotBeFound("stuckType.notEquals=" + DEFAULT_STUCK_TYPE);

        // Get all the resultTestList where stuckType not equals to UPDATED_STUCK_TYPE
        defaultResultTestShouldBeFound("stuckType.notEquals=" + UPDATED_STUCK_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByStuckTypeIsInShouldWork() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where stuckType in DEFAULT_STUCK_TYPE or UPDATED_STUCK_TYPE
        defaultResultTestShouldBeFound("stuckType.in=" + DEFAULT_STUCK_TYPE + "," + UPDATED_STUCK_TYPE);

        // Get all the resultTestList where stuckType equals to UPDATED_STUCK_TYPE
        defaultResultTestShouldNotBeFound("stuckType.in=" + UPDATED_STUCK_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByStuckTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where stuckType is not null
        defaultResultTestShouldBeFound("stuckType.specified=true");

        // Get all the resultTestList where stuckType is null
        defaultResultTestShouldNotBeFound("stuckType.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultTestsByStuckTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where stuckType is greater than or equal to DEFAULT_STUCK_TYPE
        defaultResultTestShouldBeFound("stuckType.greaterThanOrEqual=" + DEFAULT_STUCK_TYPE);

        // Get all the resultTestList where stuckType is greater than or equal to UPDATED_STUCK_TYPE
        defaultResultTestShouldNotBeFound("stuckType.greaterThanOrEqual=" + UPDATED_STUCK_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByStuckTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where stuckType is less than or equal to DEFAULT_STUCK_TYPE
        defaultResultTestShouldBeFound("stuckType.lessThanOrEqual=" + DEFAULT_STUCK_TYPE);

        // Get all the resultTestList where stuckType is less than or equal to SMALLER_STUCK_TYPE
        defaultResultTestShouldNotBeFound("stuckType.lessThanOrEqual=" + SMALLER_STUCK_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByStuckTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where stuckType is less than DEFAULT_STUCK_TYPE
        defaultResultTestShouldNotBeFound("stuckType.lessThan=" + DEFAULT_STUCK_TYPE);

        // Get all the resultTestList where stuckType is less than UPDATED_STUCK_TYPE
        defaultResultTestShouldBeFound("stuckType.lessThan=" + UPDATED_STUCK_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByStuckTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where stuckType is greater than DEFAULT_STUCK_TYPE
        defaultResultTestShouldNotBeFound("stuckType.greaterThan=" + DEFAULT_STUCK_TYPE);

        // Get all the resultTestList where stuckType is greater than SMALLER_STUCK_TYPE
        defaultResultTestShouldBeFound("stuckType.greaterThan=" + SMALLER_STUCK_TYPE);
    }


    @Test
    @Transactional
    public void getAllResultTestsByPedanticTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where pedanticType equals to DEFAULT_PEDANTIC_TYPE
        defaultResultTestShouldBeFound("pedanticType.equals=" + DEFAULT_PEDANTIC_TYPE);

        // Get all the resultTestList where pedanticType equals to UPDATED_PEDANTIC_TYPE
        defaultResultTestShouldNotBeFound("pedanticType.equals=" + UPDATED_PEDANTIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByPedanticTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where pedanticType not equals to DEFAULT_PEDANTIC_TYPE
        defaultResultTestShouldNotBeFound("pedanticType.notEquals=" + DEFAULT_PEDANTIC_TYPE);

        // Get all the resultTestList where pedanticType not equals to UPDATED_PEDANTIC_TYPE
        defaultResultTestShouldBeFound("pedanticType.notEquals=" + UPDATED_PEDANTIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByPedanticTypeIsInShouldWork() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where pedanticType in DEFAULT_PEDANTIC_TYPE or UPDATED_PEDANTIC_TYPE
        defaultResultTestShouldBeFound("pedanticType.in=" + DEFAULT_PEDANTIC_TYPE + "," + UPDATED_PEDANTIC_TYPE);

        // Get all the resultTestList where pedanticType equals to UPDATED_PEDANTIC_TYPE
        defaultResultTestShouldNotBeFound("pedanticType.in=" + UPDATED_PEDANTIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByPedanticTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where pedanticType is not null
        defaultResultTestShouldBeFound("pedanticType.specified=true");

        // Get all the resultTestList where pedanticType is null
        defaultResultTestShouldNotBeFound("pedanticType.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultTestsByPedanticTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where pedanticType is greater than or equal to DEFAULT_PEDANTIC_TYPE
        defaultResultTestShouldBeFound("pedanticType.greaterThanOrEqual=" + DEFAULT_PEDANTIC_TYPE);

        // Get all the resultTestList where pedanticType is greater than or equal to UPDATED_PEDANTIC_TYPE
        defaultResultTestShouldNotBeFound("pedanticType.greaterThanOrEqual=" + UPDATED_PEDANTIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByPedanticTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where pedanticType is less than or equal to DEFAULT_PEDANTIC_TYPE
        defaultResultTestShouldBeFound("pedanticType.lessThanOrEqual=" + DEFAULT_PEDANTIC_TYPE);

        // Get all the resultTestList where pedanticType is less than or equal to SMALLER_PEDANTIC_TYPE
        defaultResultTestShouldNotBeFound("pedanticType.lessThanOrEqual=" + SMALLER_PEDANTIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByPedanticTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where pedanticType is less than DEFAULT_PEDANTIC_TYPE
        defaultResultTestShouldNotBeFound("pedanticType.lessThan=" + DEFAULT_PEDANTIC_TYPE);

        // Get all the resultTestList where pedanticType is less than UPDATED_PEDANTIC_TYPE
        defaultResultTestShouldBeFound("pedanticType.lessThan=" + UPDATED_PEDANTIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByPedanticTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where pedanticType is greater than DEFAULT_PEDANTIC_TYPE
        defaultResultTestShouldNotBeFound("pedanticType.greaterThan=" + DEFAULT_PEDANTIC_TYPE);

        // Get all the resultTestList where pedanticType is greater than SMALLER_PEDANTIC_TYPE
        defaultResultTestShouldBeFound("pedanticType.greaterThan=" + SMALLER_PEDANTIC_TYPE);
    }


    @Test
    @Transactional
    public void getAllResultTestsByExcitableTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where excitableType equals to DEFAULT_EXCITABLE_TYPE
        defaultResultTestShouldBeFound("excitableType.equals=" + DEFAULT_EXCITABLE_TYPE);

        // Get all the resultTestList where excitableType equals to UPDATED_EXCITABLE_TYPE
        defaultResultTestShouldNotBeFound("excitableType.equals=" + UPDATED_EXCITABLE_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByExcitableTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where excitableType not equals to DEFAULT_EXCITABLE_TYPE
        defaultResultTestShouldNotBeFound("excitableType.notEquals=" + DEFAULT_EXCITABLE_TYPE);

        // Get all the resultTestList where excitableType not equals to UPDATED_EXCITABLE_TYPE
        defaultResultTestShouldBeFound("excitableType.notEquals=" + UPDATED_EXCITABLE_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByExcitableTypeIsInShouldWork() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where excitableType in DEFAULT_EXCITABLE_TYPE or UPDATED_EXCITABLE_TYPE
        defaultResultTestShouldBeFound("excitableType.in=" + DEFAULT_EXCITABLE_TYPE + "," + UPDATED_EXCITABLE_TYPE);

        // Get all the resultTestList where excitableType equals to UPDATED_EXCITABLE_TYPE
        defaultResultTestShouldNotBeFound("excitableType.in=" + UPDATED_EXCITABLE_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByExcitableTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where excitableType is not null
        defaultResultTestShouldBeFound("excitableType.specified=true");

        // Get all the resultTestList where excitableType is null
        defaultResultTestShouldNotBeFound("excitableType.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultTestsByExcitableTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where excitableType is greater than or equal to DEFAULT_EXCITABLE_TYPE
        defaultResultTestShouldBeFound("excitableType.greaterThanOrEqual=" + DEFAULT_EXCITABLE_TYPE);

        // Get all the resultTestList where excitableType is greater than or equal to UPDATED_EXCITABLE_TYPE
        defaultResultTestShouldNotBeFound("excitableType.greaterThanOrEqual=" + UPDATED_EXCITABLE_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByExcitableTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where excitableType is less than or equal to DEFAULT_EXCITABLE_TYPE
        defaultResultTestShouldBeFound("excitableType.lessThanOrEqual=" + DEFAULT_EXCITABLE_TYPE);

        // Get all the resultTestList where excitableType is less than or equal to SMALLER_EXCITABLE_TYPE
        defaultResultTestShouldNotBeFound("excitableType.lessThanOrEqual=" + SMALLER_EXCITABLE_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByExcitableTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where excitableType is less than DEFAULT_EXCITABLE_TYPE
        defaultResultTestShouldNotBeFound("excitableType.lessThan=" + DEFAULT_EXCITABLE_TYPE);

        // Get all the resultTestList where excitableType is less than UPDATED_EXCITABLE_TYPE
        defaultResultTestShouldBeFound("excitableType.lessThan=" + UPDATED_EXCITABLE_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByExcitableTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where excitableType is greater than DEFAULT_EXCITABLE_TYPE
        defaultResultTestShouldNotBeFound("excitableType.greaterThan=" + DEFAULT_EXCITABLE_TYPE);

        // Get all the resultTestList where excitableType is greater than SMALLER_EXCITABLE_TYPE
        defaultResultTestShouldBeFound("excitableType.greaterThan=" + SMALLER_EXCITABLE_TYPE);
    }


    @Test
    @Transactional
    public void getAllResultTestsByHyperthymicTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where hyperthymicType equals to DEFAULT_HYPERTHYMIC_TYPE
        defaultResultTestShouldBeFound("hyperthymicType.equals=" + DEFAULT_HYPERTHYMIC_TYPE);

        // Get all the resultTestList where hyperthymicType equals to UPDATED_HYPERTHYMIC_TYPE
        defaultResultTestShouldNotBeFound("hyperthymicType.equals=" + UPDATED_HYPERTHYMIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByHyperthymicTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where hyperthymicType not equals to DEFAULT_HYPERTHYMIC_TYPE
        defaultResultTestShouldNotBeFound("hyperthymicType.notEquals=" + DEFAULT_HYPERTHYMIC_TYPE);

        // Get all the resultTestList where hyperthymicType not equals to UPDATED_HYPERTHYMIC_TYPE
        defaultResultTestShouldBeFound("hyperthymicType.notEquals=" + UPDATED_HYPERTHYMIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByHyperthymicTypeIsInShouldWork() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where hyperthymicType in DEFAULT_HYPERTHYMIC_TYPE or UPDATED_HYPERTHYMIC_TYPE
        defaultResultTestShouldBeFound("hyperthymicType.in=" + DEFAULT_HYPERTHYMIC_TYPE + "," + UPDATED_HYPERTHYMIC_TYPE);

        // Get all the resultTestList where hyperthymicType equals to UPDATED_HYPERTHYMIC_TYPE
        defaultResultTestShouldNotBeFound("hyperthymicType.in=" + UPDATED_HYPERTHYMIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByHyperthymicTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where hyperthymicType is not null
        defaultResultTestShouldBeFound("hyperthymicType.specified=true");

        // Get all the resultTestList where hyperthymicType is null
        defaultResultTestShouldNotBeFound("hyperthymicType.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultTestsByHyperthymicTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where hyperthymicType is greater than or equal to DEFAULT_HYPERTHYMIC_TYPE
        defaultResultTestShouldBeFound("hyperthymicType.greaterThanOrEqual=" + DEFAULT_HYPERTHYMIC_TYPE);

        // Get all the resultTestList where hyperthymicType is greater than or equal to UPDATED_HYPERTHYMIC_TYPE
        defaultResultTestShouldNotBeFound("hyperthymicType.greaterThanOrEqual=" + UPDATED_HYPERTHYMIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByHyperthymicTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where hyperthymicType is less than or equal to DEFAULT_HYPERTHYMIC_TYPE
        defaultResultTestShouldBeFound("hyperthymicType.lessThanOrEqual=" + DEFAULT_HYPERTHYMIC_TYPE);

        // Get all the resultTestList where hyperthymicType is less than or equal to SMALLER_HYPERTHYMIC_TYPE
        defaultResultTestShouldNotBeFound("hyperthymicType.lessThanOrEqual=" + SMALLER_HYPERTHYMIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByHyperthymicTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where hyperthymicType is less than DEFAULT_HYPERTHYMIC_TYPE
        defaultResultTestShouldNotBeFound("hyperthymicType.lessThan=" + DEFAULT_HYPERTHYMIC_TYPE);

        // Get all the resultTestList where hyperthymicType is less than UPDATED_HYPERTHYMIC_TYPE
        defaultResultTestShouldBeFound("hyperthymicType.lessThan=" + UPDATED_HYPERTHYMIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByHyperthymicTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where hyperthymicType is greater than DEFAULT_HYPERTHYMIC_TYPE
        defaultResultTestShouldNotBeFound("hyperthymicType.greaterThan=" + DEFAULT_HYPERTHYMIC_TYPE);

        // Get all the resultTestList where hyperthymicType is greater than SMALLER_HYPERTHYMIC_TYPE
        defaultResultTestShouldBeFound("hyperthymicType.greaterThan=" + SMALLER_HYPERTHYMIC_TYPE);
    }


    @Test
    @Transactional
    public void getAllResultTestsByDysthymicTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where dysthymicType equals to DEFAULT_DYSTHYMIC_TYPE
        defaultResultTestShouldBeFound("dysthymicType.equals=" + DEFAULT_DYSTHYMIC_TYPE);

        // Get all the resultTestList where dysthymicType equals to UPDATED_DYSTHYMIC_TYPE
        defaultResultTestShouldNotBeFound("dysthymicType.equals=" + UPDATED_DYSTHYMIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByDysthymicTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where dysthymicType not equals to DEFAULT_DYSTHYMIC_TYPE
        defaultResultTestShouldNotBeFound("dysthymicType.notEquals=" + DEFAULT_DYSTHYMIC_TYPE);

        // Get all the resultTestList where dysthymicType not equals to UPDATED_DYSTHYMIC_TYPE
        defaultResultTestShouldBeFound("dysthymicType.notEquals=" + UPDATED_DYSTHYMIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByDysthymicTypeIsInShouldWork() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where dysthymicType in DEFAULT_DYSTHYMIC_TYPE or UPDATED_DYSTHYMIC_TYPE
        defaultResultTestShouldBeFound("dysthymicType.in=" + DEFAULT_DYSTHYMIC_TYPE + "," + UPDATED_DYSTHYMIC_TYPE);

        // Get all the resultTestList where dysthymicType equals to UPDATED_DYSTHYMIC_TYPE
        defaultResultTestShouldNotBeFound("dysthymicType.in=" + UPDATED_DYSTHYMIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByDysthymicTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where dysthymicType is not null
        defaultResultTestShouldBeFound("dysthymicType.specified=true");

        // Get all the resultTestList where dysthymicType is null
        defaultResultTestShouldNotBeFound("dysthymicType.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultTestsByDysthymicTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where dysthymicType is greater than or equal to DEFAULT_DYSTHYMIC_TYPE
        defaultResultTestShouldBeFound("dysthymicType.greaterThanOrEqual=" + DEFAULT_DYSTHYMIC_TYPE);

        // Get all the resultTestList where dysthymicType is greater than or equal to UPDATED_DYSTHYMIC_TYPE
        defaultResultTestShouldNotBeFound("dysthymicType.greaterThanOrEqual=" + UPDATED_DYSTHYMIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByDysthymicTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where dysthymicType is less than or equal to DEFAULT_DYSTHYMIC_TYPE
        defaultResultTestShouldBeFound("dysthymicType.lessThanOrEqual=" + DEFAULT_DYSTHYMIC_TYPE);

        // Get all the resultTestList where dysthymicType is less than or equal to SMALLER_DYSTHYMIC_TYPE
        defaultResultTestShouldNotBeFound("dysthymicType.lessThanOrEqual=" + SMALLER_DYSTHYMIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByDysthymicTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where dysthymicType is less than DEFAULT_DYSTHYMIC_TYPE
        defaultResultTestShouldNotBeFound("dysthymicType.lessThan=" + DEFAULT_DYSTHYMIC_TYPE);

        // Get all the resultTestList where dysthymicType is less than UPDATED_DYSTHYMIC_TYPE
        defaultResultTestShouldBeFound("dysthymicType.lessThan=" + UPDATED_DYSTHYMIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByDysthymicTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where dysthymicType is greater than DEFAULT_DYSTHYMIC_TYPE
        defaultResultTestShouldNotBeFound("dysthymicType.greaterThan=" + DEFAULT_DYSTHYMIC_TYPE);

        // Get all the resultTestList where dysthymicType is greater than SMALLER_DYSTHYMIC_TYPE
        defaultResultTestShouldBeFound("dysthymicType.greaterThan=" + SMALLER_DYSTHYMIC_TYPE);
    }


    @Test
    @Transactional
    public void getAllResultTestsByAnxiouslyFearfulTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where anxiouslyFearfulType equals to DEFAULT_ANXIOUSLY_FEARFUL_TYPE
        defaultResultTestShouldBeFound("anxiouslyFearfulType.equals=" + DEFAULT_ANXIOUSLY_FEARFUL_TYPE);

        // Get all the resultTestList where anxiouslyFearfulType equals to UPDATED_ANXIOUSLY_FEARFUL_TYPE
        defaultResultTestShouldNotBeFound("anxiouslyFearfulType.equals=" + UPDATED_ANXIOUSLY_FEARFUL_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByAnxiouslyFearfulTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where anxiouslyFearfulType not equals to DEFAULT_ANXIOUSLY_FEARFUL_TYPE
        defaultResultTestShouldNotBeFound("anxiouslyFearfulType.notEquals=" + DEFAULT_ANXIOUSLY_FEARFUL_TYPE);

        // Get all the resultTestList where anxiouslyFearfulType not equals to UPDATED_ANXIOUSLY_FEARFUL_TYPE
        defaultResultTestShouldBeFound("anxiouslyFearfulType.notEquals=" + UPDATED_ANXIOUSLY_FEARFUL_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByAnxiouslyFearfulTypeIsInShouldWork() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where anxiouslyFearfulType in DEFAULT_ANXIOUSLY_FEARFUL_TYPE or UPDATED_ANXIOUSLY_FEARFUL_TYPE
        defaultResultTestShouldBeFound("anxiouslyFearfulType.in=" + DEFAULT_ANXIOUSLY_FEARFUL_TYPE + "," + UPDATED_ANXIOUSLY_FEARFUL_TYPE);

        // Get all the resultTestList where anxiouslyFearfulType equals to UPDATED_ANXIOUSLY_FEARFUL_TYPE
        defaultResultTestShouldNotBeFound("anxiouslyFearfulType.in=" + UPDATED_ANXIOUSLY_FEARFUL_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByAnxiouslyFearfulTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where anxiouslyFearfulType is not null
        defaultResultTestShouldBeFound("anxiouslyFearfulType.specified=true");

        // Get all the resultTestList where anxiouslyFearfulType is null
        defaultResultTestShouldNotBeFound("anxiouslyFearfulType.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultTestsByAnxiouslyFearfulTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where anxiouslyFearfulType is greater than or equal to DEFAULT_ANXIOUSLY_FEARFUL_TYPE
        defaultResultTestShouldBeFound("anxiouslyFearfulType.greaterThanOrEqual=" + DEFAULT_ANXIOUSLY_FEARFUL_TYPE);

        // Get all the resultTestList where anxiouslyFearfulType is greater than or equal to UPDATED_ANXIOUSLY_FEARFUL_TYPE
        defaultResultTestShouldNotBeFound("anxiouslyFearfulType.greaterThanOrEqual=" + UPDATED_ANXIOUSLY_FEARFUL_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByAnxiouslyFearfulTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where anxiouslyFearfulType is less than or equal to DEFAULT_ANXIOUSLY_FEARFUL_TYPE
        defaultResultTestShouldBeFound("anxiouslyFearfulType.lessThanOrEqual=" + DEFAULT_ANXIOUSLY_FEARFUL_TYPE);

        // Get all the resultTestList where anxiouslyFearfulType is less than or equal to SMALLER_ANXIOUSLY_FEARFUL_TYPE
        defaultResultTestShouldNotBeFound("anxiouslyFearfulType.lessThanOrEqual=" + SMALLER_ANXIOUSLY_FEARFUL_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByAnxiouslyFearfulTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where anxiouslyFearfulType is less than DEFAULT_ANXIOUSLY_FEARFUL_TYPE
        defaultResultTestShouldNotBeFound("anxiouslyFearfulType.lessThan=" + DEFAULT_ANXIOUSLY_FEARFUL_TYPE);

        // Get all the resultTestList where anxiouslyFearfulType is less than UPDATED_ANXIOUSLY_FEARFUL_TYPE
        defaultResultTestShouldBeFound("anxiouslyFearfulType.lessThan=" + UPDATED_ANXIOUSLY_FEARFUL_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByAnxiouslyFearfulTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where anxiouslyFearfulType is greater than DEFAULT_ANXIOUSLY_FEARFUL_TYPE
        defaultResultTestShouldNotBeFound("anxiouslyFearfulType.greaterThan=" + DEFAULT_ANXIOUSLY_FEARFUL_TYPE);

        // Get all the resultTestList where anxiouslyFearfulType is greater than SMALLER_ANXIOUSLY_FEARFUL_TYPE
        defaultResultTestShouldBeFound("anxiouslyFearfulType.greaterThan=" + SMALLER_ANXIOUSLY_FEARFUL_TYPE);
    }


    @Test
    @Transactional
    public void getAllResultTestsByEmotionallyExaltedTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where emotionallyExaltedType equals to DEFAULT_EMOTIONALLY_EXALTED_TYPE
        defaultResultTestShouldBeFound("emotionallyExaltedType.equals=" + DEFAULT_EMOTIONALLY_EXALTED_TYPE);

        // Get all the resultTestList where emotionallyExaltedType equals to UPDATED_EMOTIONALLY_EXALTED_TYPE
        defaultResultTestShouldNotBeFound("emotionallyExaltedType.equals=" + UPDATED_EMOTIONALLY_EXALTED_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByEmotionallyExaltedTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where emotionallyExaltedType not equals to DEFAULT_EMOTIONALLY_EXALTED_TYPE
        defaultResultTestShouldNotBeFound("emotionallyExaltedType.notEquals=" + DEFAULT_EMOTIONALLY_EXALTED_TYPE);

        // Get all the resultTestList where emotionallyExaltedType not equals to UPDATED_EMOTIONALLY_EXALTED_TYPE
        defaultResultTestShouldBeFound("emotionallyExaltedType.notEquals=" + UPDATED_EMOTIONALLY_EXALTED_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByEmotionallyExaltedTypeIsInShouldWork() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where emotionallyExaltedType in DEFAULT_EMOTIONALLY_EXALTED_TYPE or UPDATED_EMOTIONALLY_EXALTED_TYPE
        defaultResultTestShouldBeFound("emotionallyExaltedType.in=" + DEFAULT_EMOTIONALLY_EXALTED_TYPE + "," + UPDATED_EMOTIONALLY_EXALTED_TYPE);

        // Get all the resultTestList where emotionallyExaltedType equals to UPDATED_EMOTIONALLY_EXALTED_TYPE
        defaultResultTestShouldNotBeFound("emotionallyExaltedType.in=" + UPDATED_EMOTIONALLY_EXALTED_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByEmotionallyExaltedTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where emotionallyExaltedType is not null
        defaultResultTestShouldBeFound("emotionallyExaltedType.specified=true");

        // Get all the resultTestList where emotionallyExaltedType is null
        defaultResultTestShouldNotBeFound("emotionallyExaltedType.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultTestsByEmotionallyExaltedTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where emotionallyExaltedType is greater than or equal to DEFAULT_EMOTIONALLY_EXALTED_TYPE
        defaultResultTestShouldBeFound("emotionallyExaltedType.greaterThanOrEqual=" + DEFAULT_EMOTIONALLY_EXALTED_TYPE);

        // Get all the resultTestList where emotionallyExaltedType is greater than or equal to UPDATED_EMOTIONALLY_EXALTED_TYPE
        defaultResultTestShouldNotBeFound("emotionallyExaltedType.greaterThanOrEqual=" + UPDATED_EMOTIONALLY_EXALTED_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByEmotionallyExaltedTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where emotionallyExaltedType is less than or equal to DEFAULT_EMOTIONALLY_EXALTED_TYPE
        defaultResultTestShouldBeFound("emotionallyExaltedType.lessThanOrEqual=" + DEFAULT_EMOTIONALLY_EXALTED_TYPE);

        // Get all the resultTestList where emotionallyExaltedType is less than or equal to SMALLER_EMOTIONALLY_EXALTED_TYPE
        defaultResultTestShouldNotBeFound("emotionallyExaltedType.lessThanOrEqual=" + SMALLER_EMOTIONALLY_EXALTED_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByEmotionallyExaltedTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where emotionallyExaltedType is less than DEFAULT_EMOTIONALLY_EXALTED_TYPE
        defaultResultTestShouldNotBeFound("emotionallyExaltedType.lessThan=" + DEFAULT_EMOTIONALLY_EXALTED_TYPE);

        // Get all the resultTestList where emotionallyExaltedType is less than UPDATED_EMOTIONALLY_EXALTED_TYPE
        defaultResultTestShouldBeFound("emotionallyExaltedType.lessThan=" + UPDATED_EMOTIONALLY_EXALTED_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByEmotionallyExaltedTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where emotionallyExaltedType is greater than DEFAULT_EMOTIONALLY_EXALTED_TYPE
        defaultResultTestShouldNotBeFound("emotionallyExaltedType.greaterThan=" + DEFAULT_EMOTIONALLY_EXALTED_TYPE);

        // Get all the resultTestList where emotionallyExaltedType is greater than SMALLER_EMOTIONALLY_EXALTED_TYPE
        defaultResultTestShouldBeFound("emotionallyExaltedType.greaterThan=" + SMALLER_EMOTIONALLY_EXALTED_TYPE);
    }


    @Test
    @Transactional
    public void getAllResultTestsByEmotiveTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where emotiveType equals to DEFAULT_EMOTIVE_TYPE
        defaultResultTestShouldBeFound("emotiveType.equals=" + DEFAULT_EMOTIVE_TYPE);

        // Get all the resultTestList where emotiveType equals to UPDATED_EMOTIVE_TYPE
        defaultResultTestShouldNotBeFound("emotiveType.equals=" + UPDATED_EMOTIVE_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByEmotiveTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where emotiveType not equals to DEFAULT_EMOTIVE_TYPE
        defaultResultTestShouldNotBeFound("emotiveType.notEquals=" + DEFAULT_EMOTIVE_TYPE);

        // Get all the resultTestList where emotiveType not equals to UPDATED_EMOTIVE_TYPE
        defaultResultTestShouldBeFound("emotiveType.notEquals=" + UPDATED_EMOTIVE_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByEmotiveTypeIsInShouldWork() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where emotiveType in DEFAULT_EMOTIVE_TYPE or UPDATED_EMOTIVE_TYPE
        defaultResultTestShouldBeFound("emotiveType.in=" + DEFAULT_EMOTIVE_TYPE + "," + UPDATED_EMOTIVE_TYPE);

        // Get all the resultTestList where emotiveType equals to UPDATED_EMOTIVE_TYPE
        defaultResultTestShouldNotBeFound("emotiveType.in=" + UPDATED_EMOTIVE_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByEmotiveTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where emotiveType is not null
        defaultResultTestShouldBeFound("emotiveType.specified=true");

        // Get all the resultTestList where emotiveType is null
        defaultResultTestShouldNotBeFound("emotiveType.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultTestsByEmotiveTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where emotiveType is greater than or equal to DEFAULT_EMOTIVE_TYPE
        defaultResultTestShouldBeFound("emotiveType.greaterThanOrEqual=" + DEFAULT_EMOTIVE_TYPE);

        // Get all the resultTestList where emotiveType is greater than or equal to UPDATED_EMOTIVE_TYPE
        defaultResultTestShouldNotBeFound("emotiveType.greaterThanOrEqual=" + UPDATED_EMOTIVE_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByEmotiveTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where emotiveType is less than or equal to DEFAULT_EMOTIVE_TYPE
        defaultResultTestShouldBeFound("emotiveType.lessThanOrEqual=" + DEFAULT_EMOTIVE_TYPE);

        // Get all the resultTestList where emotiveType is less than or equal to SMALLER_EMOTIVE_TYPE
        defaultResultTestShouldNotBeFound("emotiveType.lessThanOrEqual=" + SMALLER_EMOTIVE_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByEmotiveTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where emotiveType is less than DEFAULT_EMOTIVE_TYPE
        defaultResultTestShouldNotBeFound("emotiveType.lessThan=" + DEFAULT_EMOTIVE_TYPE);

        // Get all the resultTestList where emotiveType is less than UPDATED_EMOTIVE_TYPE
        defaultResultTestShouldBeFound("emotiveType.lessThan=" + UPDATED_EMOTIVE_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByEmotiveTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where emotiveType is greater than DEFAULT_EMOTIVE_TYPE
        defaultResultTestShouldNotBeFound("emotiveType.greaterThan=" + DEFAULT_EMOTIVE_TYPE);

        // Get all the resultTestList where emotiveType is greater than SMALLER_EMOTIVE_TYPE
        defaultResultTestShouldBeFound("emotiveType.greaterThan=" + SMALLER_EMOTIVE_TYPE);
    }


    @Test
    @Transactional
    public void getAllResultTestsByCyclothymicTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where cyclothymicType equals to DEFAULT_CYCLOTHYMIC_TYPE
        defaultResultTestShouldBeFound("cyclothymicType.equals=" + DEFAULT_CYCLOTHYMIC_TYPE);

        // Get all the resultTestList where cyclothymicType equals to UPDATED_CYCLOTHYMIC_TYPE
        defaultResultTestShouldNotBeFound("cyclothymicType.equals=" + UPDATED_CYCLOTHYMIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByCyclothymicTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where cyclothymicType not equals to DEFAULT_CYCLOTHYMIC_TYPE
        defaultResultTestShouldNotBeFound("cyclothymicType.notEquals=" + DEFAULT_CYCLOTHYMIC_TYPE);

        // Get all the resultTestList where cyclothymicType not equals to UPDATED_CYCLOTHYMIC_TYPE
        defaultResultTestShouldBeFound("cyclothymicType.notEquals=" + UPDATED_CYCLOTHYMIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByCyclothymicTypeIsInShouldWork() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where cyclothymicType in DEFAULT_CYCLOTHYMIC_TYPE or UPDATED_CYCLOTHYMIC_TYPE
        defaultResultTestShouldBeFound("cyclothymicType.in=" + DEFAULT_CYCLOTHYMIC_TYPE + "," + UPDATED_CYCLOTHYMIC_TYPE);

        // Get all the resultTestList where cyclothymicType equals to UPDATED_CYCLOTHYMIC_TYPE
        defaultResultTestShouldNotBeFound("cyclothymicType.in=" + UPDATED_CYCLOTHYMIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByCyclothymicTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where cyclothymicType is not null
        defaultResultTestShouldBeFound("cyclothymicType.specified=true");

        // Get all the resultTestList where cyclothymicType is null
        defaultResultTestShouldNotBeFound("cyclothymicType.specified=false");
    }

    @Test
    @Transactional
    public void getAllResultTestsByCyclothymicTypeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where cyclothymicType is greater than or equal to DEFAULT_CYCLOTHYMIC_TYPE
        defaultResultTestShouldBeFound("cyclothymicType.greaterThanOrEqual=" + DEFAULT_CYCLOTHYMIC_TYPE);

        // Get all the resultTestList where cyclothymicType is greater than or equal to UPDATED_CYCLOTHYMIC_TYPE
        defaultResultTestShouldNotBeFound("cyclothymicType.greaterThanOrEqual=" + UPDATED_CYCLOTHYMIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByCyclothymicTypeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where cyclothymicType is less than or equal to DEFAULT_CYCLOTHYMIC_TYPE
        defaultResultTestShouldBeFound("cyclothymicType.lessThanOrEqual=" + DEFAULT_CYCLOTHYMIC_TYPE);

        // Get all the resultTestList where cyclothymicType is less than or equal to SMALLER_CYCLOTHYMIC_TYPE
        defaultResultTestShouldNotBeFound("cyclothymicType.lessThanOrEqual=" + SMALLER_CYCLOTHYMIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByCyclothymicTypeIsLessThanSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where cyclothymicType is less than DEFAULT_CYCLOTHYMIC_TYPE
        defaultResultTestShouldNotBeFound("cyclothymicType.lessThan=" + DEFAULT_CYCLOTHYMIC_TYPE);

        // Get all the resultTestList where cyclothymicType is less than UPDATED_CYCLOTHYMIC_TYPE
        defaultResultTestShouldBeFound("cyclothymicType.lessThan=" + UPDATED_CYCLOTHYMIC_TYPE);
    }

    @Test
    @Transactional
    public void getAllResultTestsByCyclothymicTypeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        // Get all the resultTestList where cyclothymicType is greater than DEFAULT_CYCLOTHYMIC_TYPE
        defaultResultTestShouldNotBeFound("cyclothymicType.greaterThan=" + DEFAULT_CYCLOTHYMIC_TYPE);

        // Get all the resultTestList where cyclothymicType is greater than SMALLER_CYCLOTHYMIC_TYPE
        defaultResultTestShouldBeFound("cyclothymicType.greaterThan=" + SMALLER_CYCLOTHYMIC_TYPE);
    }


    @Test
    @Transactional
    public void getAllResultTestsByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        resultTest.setUser(user);
        resultTestRepository.saveAndFlush(resultTest);
        Long userId = user.getId();

        // Get all the resultTestList where user equals to userId
        defaultResultTestShouldBeFound("userId.equals=" + userId);

        // Get all the resultTestList where user equals to userId + 1
        defaultResultTestShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResultTestShouldBeFound(String filter) throws Exception {
        restResultTestMockMvc.perform(get("/api/result-tests?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resultTest.getId().intValue())))
            .andExpect(jsonPath("$.[*].finishedAt").value(hasItem(DEFAULT_FINISHED_AT.toString())))
            .andExpect(jsonPath("$.[*].demonstrativeType").value(hasItem(DEFAULT_DEMONSTRATIVE_TYPE)))
            .andExpect(jsonPath("$.[*].stuckType").value(hasItem(DEFAULT_STUCK_TYPE)))
            .andExpect(jsonPath("$.[*].pedanticType").value(hasItem(DEFAULT_PEDANTIC_TYPE)))
            .andExpect(jsonPath("$.[*].excitableType").value(hasItem(DEFAULT_EXCITABLE_TYPE)))
            .andExpect(jsonPath("$.[*].hyperthymicType").value(hasItem(DEFAULT_HYPERTHYMIC_TYPE)))
            .andExpect(jsonPath("$.[*].dysthymicType").value(hasItem(DEFAULT_DYSTHYMIC_TYPE)))
            .andExpect(jsonPath("$.[*].anxiouslyFearfulType").value(hasItem(DEFAULT_ANXIOUSLY_FEARFUL_TYPE)))
            .andExpect(jsonPath("$.[*].emotionallyExaltedType").value(hasItem(DEFAULT_EMOTIONALLY_EXALTED_TYPE)))
            .andExpect(jsonPath("$.[*].emotiveType").value(hasItem(DEFAULT_EMOTIVE_TYPE)))
            .andExpect(jsonPath("$.[*].cyclothymicType").value(hasItem(DEFAULT_CYCLOTHYMIC_TYPE)));

        // Check, that the count call also returns 1
        restResultTestMockMvc.perform(get("/api/result-tests/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResultTestShouldNotBeFound(String filter) throws Exception {
        restResultTestMockMvc.perform(get("/api/result-tests?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResultTestMockMvc.perform(get("/api/result-tests/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingResultTest() throws Exception {
        // Get the resultTest
        restResultTestMockMvc.perform(get("/api/result-tests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResultTest() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        int databaseSizeBeforeUpdate = resultTestRepository.findAll().size();

        // Update the resultTest
        ResultTest updatedResultTest = resultTestRepository.findById(resultTest.getId()).get();
        // Disconnect from session so that the updates on updatedResultTest are not directly saved in db
        em.detach(updatedResultTest);
        updatedResultTest
            .finishedAt(UPDATED_FINISHED_AT)
            .demonstrativeType(UPDATED_DEMONSTRATIVE_TYPE)
            .stuckType(UPDATED_STUCK_TYPE)
            .pedanticType(UPDATED_PEDANTIC_TYPE)
            .excitableType(UPDATED_EXCITABLE_TYPE)
            .hyperthymicType(UPDATED_HYPERTHYMIC_TYPE)
            .dysthymicType(UPDATED_DYSTHYMIC_TYPE)
            .anxiouslyFearfulType(UPDATED_ANXIOUSLY_FEARFUL_TYPE)
            .emotionallyExaltedType(UPDATED_EMOTIONALLY_EXALTED_TYPE)
            .emotiveType(UPDATED_EMOTIVE_TYPE)
            .cyclothymicType(UPDATED_CYCLOTHYMIC_TYPE);
        ResultTestDTO resultTestDTO = resultTestMapper.toDto(updatedResultTest);

        restResultTestMockMvc.perform(put("/api/result-tests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultTestDTO)))
            .andExpect(status().isOk());

        // Validate the ResultTest in the database
        List<ResultTest> resultTestList = resultTestRepository.findAll();
        assertThat(resultTestList).hasSize(databaseSizeBeforeUpdate);
        ResultTest testResultTest = resultTestList.get(resultTestList.size() - 1);
        assertThat(testResultTest.getFinishedAt()).isEqualTo(UPDATED_FINISHED_AT);
        assertThat(testResultTest.getDemonstrativeType()).isEqualTo(UPDATED_DEMONSTRATIVE_TYPE);
        assertThat(testResultTest.getStuckType()).isEqualTo(UPDATED_STUCK_TYPE);
        assertThat(testResultTest.getPedanticType()).isEqualTo(UPDATED_PEDANTIC_TYPE);
        assertThat(testResultTest.getExcitableType()).isEqualTo(UPDATED_EXCITABLE_TYPE);
        assertThat(testResultTest.getHyperthymicType()).isEqualTo(UPDATED_HYPERTHYMIC_TYPE);
        assertThat(testResultTest.getDysthymicType()).isEqualTo(UPDATED_DYSTHYMIC_TYPE);
        assertThat(testResultTest.getAnxiouslyFearfulType()).isEqualTo(UPDATED_ANXIOUSLY_FEARFUL_TYPE);
        assertThat(testResultTest.getEmotionallyExaltedType()).isEqualTo(UPDATED_EMOTIONALLY_EXALTED_TYPE);
        assertThat(testResultTest.getEmotiveType()).isEqualTo(UPDATED_EMOTIVE_TYPE);
        assertThat(testResultTest.getCyclothymicType()).isEqualTo(UPDATED_CYCLOTHYMIC_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingResultTest() throws Exception {
        int databaseSizeBeforeUpdate = resultTestRepository.findAll().size();

        // Create the ResultTest
        ResultTestDTO resultTestDTO = resultTestMapper.toDto(resultTest);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResultTestMockMvc.perform(put("/api/result-tests")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(resultTestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ResultTest in the database
        List<ResultTest> resultTestList = resultTestRepository.findAll();
        assertThat(resultTestList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResultTest() throws Exception {
        // Initialize the database
        resultTestRepository.saveAndFlush(resultTest);

        int databaseSizeBeforeDelete = resultTestRepository.findAll().size();

        // Delete the resultTest
        restResultTestMockMvc.perform(delete("/api/result-tests/{id}", resultTest.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ResultTest> resultTestList = resultTestRepository.findAll();
        assertThat(resultTestList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
