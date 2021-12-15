package ru.vlsu.psytest.service;

import ru.vlsu.psytest.domain.ResultTest;
import ru.vlsu.psytest.repository.ResultTestRepository;
import ru.vlsu.psytest.service.dto.ResultTestDTO;
import ru.vlsu.psytest.service.mapper.ResultTestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ResultTest}.
 */
@Service
@Transactional
public class ResultTestService {

    private final Logger log = LoggerFactory.getLogger(ResultTestService.class);

    private final ResultTestRepository resultTestRepository;

    private final ResultTestMapper resultTestMapper;

    public ResultTestService(ResultTestRepository resultTestRepository, ResultTestMapper resultTestMapper) {
        this.resultTestRepository = resultTestRepository;
        this.resultTestMapper = resultTestMapper;
    }

    /**
     * Save a resultTest.
     *
     * @param resultTestDTO the entity to save.
     * @return the persisted entity.
     */
    public ResultTestDTO save(ResultTestDTO resultTestDTO) {
        log.debug("Request to save ResultTest : {}", resultTestDTO);
        ResultTest resultTest = resultTestMapper.toEntity(resultTestDTO);
        resultTest = resultTestRepository.save(resultTest);
        return resultTestMapper.toDto(resultTest);
    }

    /**
     * Get all the resultTests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ResultTestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ResultTests");
        return resultTestRepository.findAll(pageable)
            .map(resultTestMapper::toDto);
    }


    /**
     * Get one resultTest by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ResultTestDTO> findOne(Long id) {
        log.debug("Request to get ResultTest : {}", id);
        return resultTestRepository.findById(id)
            .map(resultTestMapper::toDto);
    }

    /**
     * Delete the resultTest by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ResultTest : {}", id);
        resultTestRepository.deleteById(id);
    }
}
