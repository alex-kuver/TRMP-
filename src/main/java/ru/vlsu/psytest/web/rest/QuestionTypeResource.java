package ru.vlsu.psytest.web.rest;

import ru.vlsu.psytest.service.QuestionTypeService;
import ru.vlsu.psytest.web.rest.errors.BadRequestAlertException;
import ru.vlsu.psytest.service.dto.QuestionTypeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ru.vlsu.psytest.domain.QuestionType}.
 */
@RestController
@RequestMapping("/api")
public class QuestionTypeResource {

    private final Logger log = LoggerFactory.getLogger(QuestionTypeResource.class);

    private static final String ENTITY_NAME = "questionType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionTypeService questionTypeService;

    public QuestionTypeResource(QuestionTypeService questionTypeService) {
        this.questionTypeService = questionTypeService;
    }

    /**
     * {@code POST  /question-types} : Create a new questionType.
     *
     * @param questionTypeDTO the questionTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new questionTypeDTO, or with status {@code 400 (Bad Request)} if the questionType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/question-types")
    public ResponseEntity<QuestionTypeDTO> createQuestionType(@Valid @RequestBody QuestionTypeDTO questionTypeDTO) throws URISyntaxException {
        log.debug("REST request to save QuestionType : {}", questionTypeDTO);
        if (questionTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new questionType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        QuestionTypeDTO result = questionTypeService.save(questionTypeDTO);
        return ResponseEntity.created(new URI("/api/question-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /question-types} : Updates an existing questionType.
     *
     * @param questionTypeDTO the questionTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated questionTypeDTO,
     * or with status {@code 400 (Bad Request)} if the questionTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the questionTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/question-types")
    public ResponseEntity<QuestionTypeDTO> updateQuestionType(@Valid @RequestBody QuestionTypeDTO questionTypeDTO) throws URISyntaxException {
        log.debug("REST request to update QuestionType : {}", questionTypeDTO);
        if (questionTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        QuestionTypeDTO result = questionTypeService.save(questionTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, questionTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /question-types} : get all the questionTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questionTypes in body.
     */
    @GetMapping("/question-types")
    public List<QuestionTypeDTO> getAllQuestionTypes() {
        log.debug("REST request to get all QuestionTypes");
        return questionTypeService.findAll();
    }

    /**
     * {@code GET  /question-types/:id} : get the "id" questionType.
     *
     * @param id the id of the questionTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the questionTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/question-types/{id}")
    public ResponseEntity<QuestionTypeDTO> getQuestionType(@PathVariable Long id) {
        log.debug("REST request to get QuestionType : {}", id);
        Optional<QuestionTypeDTO> questionTypeDTO = questionTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(questionTypeDTO);
    }

    /**
     * {@code DELETE  /question-types/:id} : delete the "id" questionType.
     *
     * @param id the id of the questionTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/question-types/{id}")
    public ResponseEntity<Void> deleteQuestionType(@PathVariable Long id) {
        log.debug("REST request to delete QuestionType : {}", id);
        questionTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
