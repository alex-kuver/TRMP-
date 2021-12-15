package ru.vlsu.psytest.web.rest;

import ru.vlsu.psytest.PsychologicallyTestBackendApp;
import ru.vlsu.psytest.domain.QuestionType;
import ru.vlsu.psytest.repository.QuestionTypeRepository;
import ru.vlsu.psytest.service.QuestionTypeService;
import ru.vlsu.psytest.service.dto.QuestionTypeDTO;
import ru.vlsu.psytest.service.mapper.QuestionTypeMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ru.vlsu.psytest.domain.enumeration.Psychotype;
/**
 * Integration tests for the {@link QuestionTypeResource} REST controller.
 */
@SpringBootTest(classes = PsychologicallyTestBackendApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class QuestionTypeResourceIT {

    private static final Psychotype DEFAULT_TYPE = Psychotype.DEMONSTRATIVE_TYPE;
    private static final Psychotype UPDATED_TYPE = Psychotype.STUCK_TYPE;

    private static final Integer DEFAULT_COEFFICIENT = 1;
    private static final Integer UPDATED_COEFFICIENT = 2;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private QuestionTypeRepository questionTypeRepository;

    @Autowired
    private QuestionTypeMapper questionTypeMapper;

    @Autowired
    private QuestionTypeService questionTypeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQuestionTypeMockMvc;

    private QuestionType questionType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionType createEntity(EntityManager em) {
        QuestionType questionType = new QuestionType()
            .type(DEFAULT_TYPE)
            .coefficient(DEFAULT_COEFFICIENT)
            .description(DEFAULT_DESCRIPTION);
        return questionType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static QuestionType createUpdatedEntity(EntityManager em) {
        QuestionType questionType = new QuestionType()
            .type(UPDATED_TYPE)
            .coefficient(UPDATED_COEFFICIENT)
            .description(UPDATED_DESCRIPTION);
        return questionType;
    }

    @BeforeEach
    public void initTest() {
        questionType = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionType() throws Exception {
        int databaseSizeBeforeCreate = questionTypeRepository.findAll().size();
        // Create the QuestionType
        QuestionTypeDTO questionTypeDTO = questionTypeMapper.toDto(questionType);
        restQuestionTypeMockMvc.perform(post("/api/question-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the QuestionType in the database
        List<QuestionType> questionTypeList = questionTypeRepository.findAll();
        assertThat(questionTypeList).hasSize(databaseSizeBeforeCreate + 1);
        QuestionType testQuestionType = questionTypeList.get(questionTypeList.size() - 1);
        assertThat(testQuestionType.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testQuestionType.getCoefficient()).isEqualTo(DEFAULT_COEFFICIENT);
        assertThat(testQuestionType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createQuestionTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionTypeRepository.findAll().size();

        // Create the QuestionType with an existing ID
        questionType.setId(1L);
        QuestionTypeDTO questionTypeDTO = questionTypeMapper.toDto(questionType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionTypeMockMvc.perform(post("/api/question-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionType in the database
        List<QuestionType> questionTypeList = questionTypeRepository.findAll();
        assertThat(questionTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionTypeRepository.findAll().size();
        // set the field null
        questionType.setType(null);

        // Create the QuestionType, which fails.
        QuestionTypeDTO questionTypeDTO = questionTypeMapper.toDto(questionType);


        restQuestionTypeMockMvc.perform(post("/api/question-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionTypeDTO)))
            .andExpect(status().isBadRequest());

        List<QuestionType> questionTypeList = questionTypeRepository.findAll();
        assertThat(questionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCoefficientIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionTypeRepository.findAll().size();
        // set the field null
        questionType.setCoefficient(null);

        // Create the QuestionType, which fails.
        QuestionTypeDTO questionTypeDTO = questionTypeMapper.toDto(questionType);


        restQuestionTypeMockMvc.perform(post("/api/question-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionTypeDTO)))
            .andExpect(status().isBadRequest());

        List<QuestionType> questionTypeList = questionTypeRepository.findAll();
        assertThat(questionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = questionTypeRepository.findAll().size();
        // set the field null
        questionType.setDescription(null);

        // Create the QuestionType, which fails.
        QuestionTypeDTO questionTypeDTO = questionTypeMapper.toDto(questionType);


        restQuestionTypeMockMvc.perform(post("/api/question-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionTypeDTO)))
            .andExpect(status().isBadRequest());

        List<QuestionType> questionTypeList = questionTypeRepository.findAll();
        assertThat(questionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQuestionTypes() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        // Get all the questionTypeList
        restQuestionTypeMockMvc.perform(get("/api/question-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].coefficient").value(hasItem(DEFAULT_COEFFICIENT)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getQuestionType() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        // Get the questionType
        restQuestionTypeMockMvc.perform(get("/api/question-types/{id}", questionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(questionType.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.coefficient").value(DEFAULT_COEFFICIENT))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingQuestionType() throws Exception {
        // Get the questionType
        restQuestionTypeMockMvc.perform(get("/api/question-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionType() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        int databaseSizeBeforeUpdate = questionTypeRepository.findAll().size();

        // Update the questionType
        QuestionType updatedQuestionType = questionTypeRepository.findById(questionType.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionType are not directly saved in db
        em.detach(updatedQuestionType);
        updatedQuestionType
            .type(UPDATED_TYPE)
            .coefficient(UPDATED_COEFFICIENT)
            .description(UPDATED_DESCRIPTION);
        QuestionTypeDTO questionTypeDTO = questionTypeMapper.toDto(updatedQuestionType);

        restQuestionTypeMockMvc.perform(put("/api/question-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionTypeDTO)))
            .andExpect(status().isOk());

        // Validate the QuestionType in the database
        List<QuestionType> questionTypeList = questionTypeRepository.findAll();
        assertThat(questionTypeList).hasSize(databaseSizeBeforeUpdate);
        QuestionType testQuestionType = questionTypeList.get(questionTypeList.size() - 1);
        assertThat(testQuestionType.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testQuestionType.getCoefficient()).isEqualTo(UPDATED_COEFFICIENT);
        assertThat(testQuestionType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionType() throws Exception {
        int databaseSizeBeforeUpdate = questionTypeRepository.findAll().size();

        // Create the QuestionType
        QuestionTypeDTO questionTypeDTO = questionTypeMapper.toDto(questionType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionTypeMockMvc.perform(put("/api/question-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(questionTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the QuestionType in the database
        List<QuestionType> questionTypeList = questionTypeRepository.findAll();
        assertThat(questionTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestionType() throws Exception {
        // Initialize the database
        questionTypeRepository.saveAndFlush(questionType);

        int databaseSizeBeforeDelete = questionTypeRepository.findAll().size();

        // Delete the questionType
        restQuestionTypeMockMvc.perform(delete("/api/question-types/{id}", questionType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<QuestionType> questionTypeList = questionTypeRepository.findAll();
        assertThat(questionTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
