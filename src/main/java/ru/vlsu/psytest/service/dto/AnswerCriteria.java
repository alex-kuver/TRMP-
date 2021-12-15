package ru.vlsu.psytest.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ru.vlsu.psytest.domain.Answer} entity. This class is used
 * in {@link ru.vlsu.psytest.web.rest.AnswerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /answers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AnswerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter answer;

    private LongFilter questionId;

    private LongFilter resultTestId;

    public AnswerCriteria() {
    }

    public AnswerCriteria(AnswerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.answer = other.answer == null ? null : other.answer.copy();
        this.questionId = other.questionId == null ? null : other.questionId.copy();
        this.resultTestId = other.resultTestId == null ? null : other.resultTestId.copy();
    }

    @Override
    public AnswerCriteria copy() {
        return new AnswerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getAnswer() {
        return answer;
    }

    public void setAnswer(BooleanFilter answer) {
        this.answer = answer;
    }

    public LongFilter getQuestionId() {
        return questionId;
    }

    public void setQuestionId(LongFilter questionId) {
        this.questionId = questionId;
    }

    public LongFilter getResultTestId() {
        return resultTestId;
    }

    public void setResultTestId(LongFilter resultTestId) {
        this.resultTestId = resultTestId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AnswerCriteria that = (AnswerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(answer, that.answer) &&
            Objects.equals(questionId, that.questionId) &&
            Objects.equals(resultTestId, that.resultTestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        answer,
        questionId,
        resultTestId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnswerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (answer != null ? "answer=" + answer + ", " : "") +
                (questionId != null ? "questionId=" + questionId + ", " : "") +
                (resultTestId != null ? "resultTestId=" + resultTestId + ", " : "") +
            "}";
    }

}
