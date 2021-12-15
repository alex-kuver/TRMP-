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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link ru.vlsu.psytest.domain.ResultTest} entity. This class is used
 * in {@link ru.vlsu.psytest.web.rest.ResultTestResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /result-tests?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ResultTestCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter finishedAt;

    private IntegerFilter demonstrativeType;

    private IntegerFilter stuckType;

    private IntegerFilter pedanticType;

    private IntegerFilter excitableType;

    private IntegerFilter hyperthymicType;

    private IntegerFilter dysthymicType;

    private IntegerFilter anxiouslyFearfulType;

    private IntegerFilter emotionallyExaltedType;

    private IntegerFilter emotiveType;

    private IntegerFilter cyclothymicType;

    private LongFilter userId;

    public ResultTestCriteria() {
    }

    public ResultTestCriteria(ResultTestCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.finishedAt = other.finishedAt == null ? null : other.finishedAt.copy();
        this.demonstrativeType = other.demonstrativeType == null ? null : other.demonstrativeType.copy();
        this.stuckType = other.stuckType == null ? null : other.stuckType.copy();
        this.pedanticType = other.pedanticType == null ? null : other.pedanticType.copy();
        this.excitableType = other.excitableType == null ? null : other.excitableType.copy();
        this.hyperthymicType = other.hyperthymicType == null ? null : other.hyperthymicType.copy();
        this.dysthymicType = other.dysthymicType == null ? null : other.dysthymicType.copy();
        this.anxiouslyFearfulType = other.anxiouslyFearfulType == null ? null : other.anxiouslyFearfulType.copy();
        this.emotionallyExaltedType = other.emotionallyExaltedType == null ? null : other.emotionallyExaltedType.copy();
        this.emotiveType = other.emotiveType == null ? null : other.emotiveType.copy();
        this.cyclothymicType = other.cyclothymicType == null ? null : other.cyclothymicType.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public ResultTestCriteria copy() {
        return new ResultTestCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(InstantFilter finishedAt) {
        this.finishedAt = finishedAt;
    }

    public IntegerFilter getDemonstrativeType() {
        return demonstrativeType;
    }

    public void setDemonstrativeType(IntegerFilter demonstrativeType) {
        this.demonstrativeType = demonstrativeType;
    }

    public IntegerFilter getStuckType() {
        return stuckType;
    }

    public void setStuckType(IntegerFilter stuckType) {
        this.stuckType = stuckType;
    }

    public IntegerFilter getPedanticType() {
        return pedanticType;
    }

    public void setPedanticType(IntegerFilter pedanticType) {
        this.pedanticType = pedanticType;
    }

    public IntegerFilter getExcitableType() {
        return excitableType;
    }

    public void setExcitableType(IntegerFilter excitableType) {
        this.excitableType = excitableType;
    }

    public IntegerFilter getHyperthymicType() {
        return hyperthymicType;
    }

    public void setHyperthymicType(IntegerFilter hyperthymicType) {
        this.hyperthymicType = hyperthymicType;
    }

    public IntegerFilter getDysthymicType() {
        return dysthymicType;
    }

    public void setDysthymicType(IntegerFilter dysthymicType) {
        this.dysthymicType = dysthymicType;
    }

    public IntegerFilter getAnxiouslyFearfulType() {
        return anxiouslyFearfulType;
    }

    public void setAnxiouslyFearfulType(IntegerFilter anxiouslyFearfulType) {
        this.anxiouslyFearfulType = anxiouslyFearfulType;
    }

    public IntegerFilter getEmotionallyExaltedType() {
        return emotionallyExaltedType;
    }

    public void setEmotionallyExaltedType(IntegerFilter emotionallyExaltedType) {
        this.emotionallyExaltedType = emotionallyExaltedType;
    }

    public IntegerFilter getEmotiveType() {
        return emotiveType;
    }

    public void setEmotiveType(IntegerFilter emotiveType) {
        this.emotiveType = emotiveType;
    }

    public IntegerFilter getCyclothymicType() {
        return cyclothymicType;
    }

    public void setCyclothymicType(IntegerFilter cyclothymicType) {
        this.cyclothymicType = cyclothymicType;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ResultTestCriteria that = (ResultTestCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(finishedAt, that.finishedAt) &&
            Objects.equals(demonstrativeType, that.demonstrativeType) &&
            Objects.equals(stuckType, that.stuckType) &&
            Objects.equals(pedanticType, that.pedanticType) &&
            Objects.equals(excitableType, that.excitableType) &&
            Objects.equals(hyperthymicType, that.hyperthymicType) &&
            Objects.equals(dysthymicType, that.dysthymicType) &&
            Objects.equals(anxiouslyFearfulType, that.anxiouslyFearfulType) &&
            Objects.equals(emotionallyExaltedType, that.emotionallyExaltedType) &&
            Objects.equals(emotiveType, that.emotiveType) &&
            Objects.equals(cyclothymicType, that.cyclothymicType) &&
            Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        finishedAt,
        demonstrativeType,
        stuckType,
        pedanticType,
        excitableType,
        hyperthymicType,
        dysthymicType,
        anxiouslyFearfulType,
        emotionallyExaltedType,
        emotiveType,
        cyclothymicType,
        userId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResultTestCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (finishedAt != null ? "finishedAt=" + finishedAt + ", " : "") +
                (demonstrativeType != null ? "demonstrativeType=" + demonstrativeType + ", " : "") +
                (stuckType != null ? "stuckType=" + stuckType + ", " : "") +
                (pedanticType != null ? "pedanticType=" + pedanticType + ", " : "") +
                (excitableType != null ? "excitableType=" + excitableType + ", " : "") +
                (hyperthymicType != null ? "hyperthymicType=" + hyperthymicType + ", " : "") +
                (dysthymicType != null ? "dysthymicType=" + dysthymicType + ", " : "") +
                (anxiouslyFearfulType != null ? "anxiouslyFearfulType=" + anxiouslyFearfulType + ", " : "") +
                (emotionallyExaltedType != null ? "emotionallyExaltedType=" + emotionallyExaltedType + ", " : "") +
                (emotiveType != null ? "emotiveType=" + emotiveType + ", " : "") +
                (cyclothymicType != null ? "cyclothymicType=" + cyclothymicType + ", " : "") +
                (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }

}
