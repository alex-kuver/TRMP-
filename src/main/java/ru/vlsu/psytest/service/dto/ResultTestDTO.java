package ru.vlsu.psytest.service.dto;

import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link ru.vlsu.psytest.domain.ResultTest} entity.
 */
public class ResultTestDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant finishedAt;

    @NotNull
    private Integer demonstrativeType;

    @NotNull
    private Integer stuckType;

    @NotNull
    private Integer pedanticType;

    @NotNull
    private Integer excitableType;

    @NotNull
    private Integer hyperthymicType;

    @NotNull
    private Integer dysthymicType;

    @NotNull
    private Integer anxiouslyFearfulType;

    @NotNull
    private Integer emotionallyExaltedType;

    @NotNull
    private Integer emotiveType;

    private Integer cyclothymicType;


    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Integer getDemonstrativeType() {
        return demonstrativeType;
    }

    public void setDemonstrativeType(Integer demonstrativeType) {
        this.demonstrativeType = demonstrativeType;
    }

    public Integer getStuckType() {
        return stuckType;
    }

    public void setStuckType(Integer stuckType) {
        this.stuckType = stuckType;
    }

    public Integer getPedanticType() {
        return pedanticType;
    }

    public void setPedanticType(Integer pedanticType) {
        this.pedanticType = pedanticType;
    }

    public Integer getExcitableType() {
        return excitableType;
    }

    public void setExcitableType(Integer excitableType) {
        this.excitableType = excitableType;
    }

    public Integer getHyperthymicType() {
        return hyperthymicType;
    }

    public void setHyperthymicType(Integer hyperthymicType) {
        this.hyperthymicType = hyperthymicType;
    }

    public Integer getDysthymicType() {
        return dysthymicType;
    }

    public void setDysthymicType(Integer dysthymicType) {
        this.dysthymicType = dysthymicType;
    }

    public Integer getAnxiouslyFearfulType() {
        return anxiouslyFearfulType;
    }

    public void setAnxiouslyFearfulType(Integer anxiouslyFearfulType) {
        this.anxiouslyFearfulType = anxiouslyFearfulType;
    }

    public Integer getEmotionallyExaltedType() {
        return emotionallyExaltedType;
    }

    public void setEmotionallyExaltedType(Integer emotionallyExaltedType) {
        this.emotionallyExaltedType = emotionallyExaltedType;
    }

    public Integer getEmotiveType() {
        return emotiveType;
    }

    public void setEmotiveType(Integer emotiveType) {
        this.emotiveType = emotiveType;
    }

    public Integer getCyclothymicType() {
        return cyclothymicType;
    }

    public void setCyclothymicType(Integer cyclothymicType) {
        this.cyclothymicType = cyclothymicType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ResultTestDTO() {
    }

    public ResultTestDTO(
        @NotNull Integer demonstrativeType,
        @NotNull Integer stuckType,
        @NotNull Integer pedanticType,
        @NotNull Integer excitableType,
        @NotNull Integer hyperthymicType,
        @NotNull Integer dysthymicType,
        @NotNull Integer anxiouslyFearfulType,
        @NotNull Integer emotionallyExaltedType,
        @NotNull Integer emotiveType,
        @NotNull Integer cyclothymicType,
        Long userId) {
        this.demonstrativeType = demonstrativeType;
        this.stuckType = stuckType;
        this.pedanticType = pedanticType;
        this.excitableType = excitableType;
        this.hyperthymicType = hyperthymicType;
        this.dysthymicType = dysthymicType;
        this.anxiouslyFearfulType = anxiouslyFearfulType;
        this.emotionallyExaltedType = emotionallyExaltedType;
        this.emotiveType = emotiveType;
        this.cyclothymicType = cyclothymicType;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResultTestDTO)) {
            return false;
        }

        return id != null && id.equals(((ResultTestDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResultTestDTO{" +
            "id=" + getId() +
            ", finishedAt='" + getFinishedAt() + "'" +
            ", demonstrativeType=" + getDemonstrativeType() +
            ", stuckType=" + getStuckType() +
            ", pedanticType=" + getPedanticType() +
            ", excitableType=" + getExcitableType() +
            ", hyperthymicType=" + getHyperthymicType() +
            ", dysthymicType=" + getDysthymicType() +
            ", anxiouslyFearfulType=" + getAnxiouslyFearfulType() +
            ", emotionallyExaltedType=" + getEmotionallyExaltedType() +
            ", emotiveType=" + getEmotiveType() +
            ", cyclothymicType=" + getCyclothymicType() +
            ", userId=" + getUserId() +
            "}";
    }
}
