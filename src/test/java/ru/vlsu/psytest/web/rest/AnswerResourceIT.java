package ru.vlsu.psytest.web.rest;

import ru.vlsu.psytest.PsychologicallyTestBackendApp;
import ru.vlsu.psytest.domain.Answer;
import ru.vlsu.psytest.domain.Question;
import ru.vlsu.psytest.domain.ResultTest;
import ru.vlsu.psytest.repository.AnswerRepository;
import ru.vlsu.psytest.service.AnswerService;
import ru.vlsu.psytest.service.dto.AnswerDTO;
import ru.vlsu.psytest.service.mapper.AnswerMapper;
import ru.vlsu.psytest.service.dto.AnswerCriteria;
import ru.vlsu.psytest.service.AnswerQueryService;

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

/**
 * Integration tests for the {@link AnswerResource} REST controller.
 */
@SpringBootTest(classes = PsychologicallyTestBackendApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AnswerResourceIT {

    private static final Boolean DEFAULT_ANSWER = false;
    private static final Boolean UPDATED_ANSWER = true;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private AnswerQueryService answerQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnswerMockMvc;

    private Answer answer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Answer createEntity(EntityManager em) {
        Answer answer = new Answer()
            .answer(DEFAULT_ANSWER);
        return answer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Answer createUpdatedEntity(EntityManager em) {
        Answer answer = new Answer()
            .answer(UPDATED_ANSWER);
        return answer;
    }

    @BeforeEach
    public void initTest() {
        answer = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnswer() throws Exception {
        int databaseSizeBeforeCreate = answerRepository.findAll().size();
        // Create the Answer
        AnswerDTO answerDTO = answerMapper.toDto(answer);
        restAnswerMockMvc.perform(post("/api/answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isCreated());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeCreate + 1);
        Answer testAnswer = answerList.get(answerList.size() - 1);
        assertThat(testAnswer.isAnswer()).isEqualTo(DEFAULT_ANSWER);
    }

    @Test
    @Transactional
    public void createAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = answerRepository.findAll().size();

        // Create the Answer with an existing ID
        answer.setId(1L);
        AnswerDTO answerDTO = answerMapper.toDto(answer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnswerMockMvc.perform(post("/api/answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAnswerIsRequired() throws Exception {
        int databaseSizeBeforeTest = answerRepository.findAll().size();
        // set the field null
        answer.setAnswer(null);

        // Create the Answer, which fails.
        AnswerDTO answerDTO = answerMapper.toDto(answer);


        restAnswerMockMvc.perform(post("/api/answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isBadRequest());

        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnswers() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList
        restAnswerMockMvc.perform(get("/api/answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(answer.getId().intValue())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAnswer() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get the answer
        restAnswerMockMvc.perform(get("/api/answers/{id}", answer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(answer.getId().intValue()))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER.booleanValue()));
    }


    @Test
    @Transactional
    public void getAnswersByIdFiltering() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        Long id = answer.getId();

        defaultAnswerShouldBeFound("id.equals=" + id);
        defaultAnswerShouldNotBeFound("id.notEquals=" + id);

        defaultAnswerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAnswerShouldNotBeFound("id.greaterThan=" + id);

        defaultAnswerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAnswerShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAnswersByAnswerIsEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where answer equals to DEFAULT_ANSWER
        defaultAnswerShouldBeFound("answer.equals=" + DEFAULT_ANSWER);

        // Get all the answerList where answer equals to UPDATED_ANSWER
        defaultAnswerShouldNotBeFound("answer.equals=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllAnswersByAnswerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where answer not equals to DEFAULT_ANSWER
        defaultAnswerShouldNotBeFound("answer.notEquals=" + DEFAULT_ANSWER);

        // Get all the answerList where answer not equals to UPDATED_ANSWER
        defaultAnswerShouldBeFound("answer.notEquals=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllAnswersByAnswerIsInShouldWork() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where answer in DEFAULT_ANSWER or UPDATED_ANSWER
        defaultAnswerShouldBeFound("answer.in=" + DEFAULT_ANSWER + "," + UPDATED_ANSWER);

        // Get all the answerList where answer equals to UPDATED_ANSWER
        defaultAnswerShouldNotBeFound("answer.in=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllAnswersByAnswerIsNullOrNotNull() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        // Get all the answerList where answer is not null
        defaultAnswerShouldBeFound("answer.specified=true");

        // Get all the answerList where answer is null
        defaultAnswerShouldNotBeFound("answer.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnswersByQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);
        Question question = QuestionResourceIT.createEntity(em);
        em.persist(question);
        em.flush();
        answer.setQuestion(question);
        answerRepository.saveAndFlush(answer);
        Long questionId = question.getId();

        // Get all the answerList where question equals to questionId
        defaultAnswerShouldBeFound("questionId.equals=" + questionId);

        // Get all the answerList where question equals to questionId + 1
        defaultAnswerShouldNotBeFound("questionId.equals=" + (questionId + 1));
    }


    @Test
    @Transactional
    public void getAllAnswersByResultTestIsEqualToSomething() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);
        ResultTest resultTest = ResultTestResourceIT.createEntity(em);
        em.persist(resultTest);
        em.flush();
        answer.setResultTest(resultTest);
        answerRepository.saveAndFlush(answer);
        Long resultTestId = resultTest.getId();

        // Get all the answerList where resultTest equals to resultTestId
        defaultAnswerShouldBeFound("resultTestId.equals=" + resultTestId);

        // Get all the answerList where resultTest equals to resultTestId + 1
        defaultAnswerShouldNotBeFound("resultTestId.equals=" + (resultTestId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAnswerShouldBeFound(String filter) throws Exception {
        restAnswerMockMvc.perform(get("/api/answers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(answer.getId().intValue())))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER.booleanValue())));

        // Check, that the count call also returns 1
        restAnswerMockMvc.perform(get("/api/answers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAnswerShouldNotBeFound(String filter) throws Exception {
        restAnswerMockMvc.perform(get("/api/answers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAnswerMockMvc.perform(get("/api/answers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingAnswer() throws Exception {
        // Get the answer
        restAnswerMockMvc.perform(get("/api/answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnswer() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        int databaseSizeBeforeUpdate = answerRepository.findAll().size();

        // Update the answer
        Answer updatedAnswer = answerRepository.findById(answer.getId()).get();
        // Disconnect from session so that the updates on updatedAnswer are not directly saved in db
        em.detach(updatedAnswer);
        updatedAnswer
            .answer(UPDATED_ANSWER);
        AnswerDTO answerDTO = answerMapper.toDto(updatedAnswer);

        restAnswerMockMvc.perform(put("/api/answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isOk());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeUpdate);
        Answer testAnswer = answerList.get(answerList.size() - 1);
        assertThat(testAnswer.isAnswer()).isEqualTo(UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void updateNonExistingAnswer() throws Exception {
        int databaseSizeBeforeUpdate = answerRepository.findAll().size();

        // Create the Answer
        AnswerDTO answerDTO = answerMapper.toDto(answer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnswerMockMvc.perform(put("/api/answers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(answerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Answer in the database
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnswer() throws Exception {
        // Initialize the database
        answerRepository.saveAndFlush(answer);

        int databaseSizeBeforeDelete = answerRepository.findAll().size();

        // Delete the answer
        restAnswerMockMvc.perform(delete("/api/answers/{id}", answer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Answer> answerList = answerRepository.findAll();
        assertThat(answerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
