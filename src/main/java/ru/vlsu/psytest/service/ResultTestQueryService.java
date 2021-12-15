package ru.vlsu.psytest.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import ru.vlsu.psytest.domain.ResultTest;
import ru.vlsu.psytest.domain.*; // for static metamodels
import ru.vlsu.psytest.repository.ResultTestRepository;
import ru.vlsu.psytest.service.dto.ResultTestCriteria;
import ru.vlsu.psytest.service.dto.ResultTestDTO;
import ru.vlsu.psytest.service.mapper.ResultTestMapper;

/**
 * Service for executing complex queries for {@link ResultTest} entities in the database.
 * The main input is a {@link ResultTestCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ResultTestDTO} or a {@link Page} of {@link ResultTestDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ResultTestQueryService extends QueryService<ResultTest> {

    private final Logger log = LoggerFactory.getLogger(ResultTestQueryService.class);

    private final ResultTestRepository resultTestRepository;

    private final ResultTestMapper resultTestMapper;

    public ResultTestQueryService(ResultTestRepository resultTestRepository, ResultTestMapper resultTestMapper) {
        this.resultTestRepository = resultTestRepository;
        this.resultTestMapper = resultTestMapper;
    }

    /**
     * Return a {@link List} of {@link ResultTestDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ResultTestDTO> findByCriteria(ResultTestCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ResultTest> specification = createSpecification(criteria);
        return resultTestMapper.toDto(resultTestRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ResultTestDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ResultTestDTO> findByCriteria(ResultTestCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ResultTest> specification = createSpecification(criteria);
        return resultTestRepository.findAll(specification, page)
            .map(resultTestMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ResultTestCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ResultTest> specification = createSpecification(criteria);
        return resultTestRepository.count(specification);
    }

    /**
     * Function to convert {@link ResultTestCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ResultTest> createSpecification(ResultTestCriteria criteria) {
        Specification<ResultTest> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ResultTest_.id));
            }
            if (criteria.getFinishedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFinishedAt(), ResultTest_.finishedAt));
            }
            if (criteria.getDemonstrativeType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDemonstrativeType(), ResultTest_.demonstrativeType));
            }
            if (criteria.getStuckType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStuckType(), ResultTest_.stuckType));
            }
            if (criteria.getPedanticType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPedanticType(), ResultTest_.pedanticType));
            }
            if (criteria.getExcitableType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExcitableType(), ResultTest_.excitableType));
            }
            if (criteria.getHyperthymicType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHyperthymicType(), ResultTest_.hyperthymicType));
            }
            if (criteria.getDysthymicType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDysthymicType(), ResultTest_.dysthymicType));
            }
            if (criteria.getAnxiouslyFearfulType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAnxiouslyFearfulType(), ResultTest_.anxiouslyFearfulType));
            }
            if (criteria.getEmotionallyExaltedType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmotionallyExaltedType(), ResultTest_.emotionallyExaltedType));
            }
            if (criteria.getEmotiveType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEmotiveType(), ResultTest_.emotiveType));
            }
            if (criteria.getCyclothymicType() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCyclothymicType(), ResultTest_.cyclothymicType));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                    root -> root.join(ResultTest_.user, JoinType.LEFT).get(User_.id)));
            }
        }
        return specification;
    }
}
