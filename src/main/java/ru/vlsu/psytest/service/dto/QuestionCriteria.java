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
 * Criteria class for the {@link ru.vlsu.psytest.domain.Question} entity. This class is used
 * in {@link ru.vlsu.psytest.web.rest.QuestionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /questions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class QuestionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter question;

    private BooleanFilter isAdd;

    private LongFilter questionTypeId;

    public QuestionCriteria() {
    }

    public QuestionCriteria(QuestionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.question = other.question == null ? null : other.question.copy();
        this.isAdd = other.isAdd == null ? null : other.isAdd.copy();
        this.questionTypeId = other.questionTypeId == null ? null : other.questionTypeId.copy();
    }

    @Override
    public QuestionCriteria copy() {
        return new QuestionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getQuestion() {
        return question;
    }

    public void setQuestion(StringFilter question) {
        this.question = question;
    }

    public BooleanFilter getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(BooleanFilter isAdd) {
        this.isAdd = isAdd;
    }

    public LongFilter getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(LongFilter questionTypeId) {
        this.questionTypeId = questionTypeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final QuestionCriteria that = (QuestionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(question, that.question) &&
            Objects.equals(isAdd, that.isAdd) &&
            Objects.equals(questionTypeId, that.questionTypeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        question,
        isAdd,
        questionTypeId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (question != null ? "question=" + question + ", " : "") +
                (isAdd != null ? "isAdd=" + isAdd + ", " : "") +
                (questionTypeId != null ? "questionTypeId=" + questionTypeId + ", " : "") +
            "}";
    }

}
