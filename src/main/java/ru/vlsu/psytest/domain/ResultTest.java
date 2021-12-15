package ru.vlsu.psytest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ResultTest.
 */
@Entity
@Table(name = "result_test")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResultTest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "finished_at", nullable = false)
    private Instant finishedAt;

    @NotNull
    @Column(name = "demonstrative_type", nullable = false)
    private Integer demonstrativeType;

    @NotNull
    @Column(name = "stuck_type", nullable = false)
    private Integer stuckType;

    @NotNull
    @Column(name = "pedantic_type", nullable = false)
    private Integer pedanticType;

    @NotNull
    @Column(name = "excitable_type", nullable = false)
    private Integer excitableType;

    @NotNull
    @Column(name = "hyperthymic_type", nullable = false)
    private Integer hyperthymicType;

    @NotNull
    @Column(name = "dysthymic_type", nullable = false)
    private Integer dysthymicType;

    @NotNull
    @Column(name = "anxiously_fearful_type", nullable = false)
    private Integer anxiouslyFearfulType;

    @NotNull
    @Column(name = "emotionally_exalted_type", nullable = false)
    private Integer emotionallyExaltedType;

    @NotNull
    @Column(name = "emotive_type", nullable = false)
    private Integer emotiveType;

    @Column(name = "cyclothymic_type")
    private Integer cyclothymicType;

    @ManyToOne
    @JsonIgnoreProperties(value = "resultTests", allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFinishedAt() {
        return finishedAt;
    }

    public ResultTest finishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
        return this;
    }

    public void setFinishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Integer getDemonstrativeType() {
        return demonstrativeType;
    }

    public ResultTest demonstrativeType(Integer demonstrativeType) {
        this.demonstrativeType = demonstrativeType;
        return this;
    }

    public void setDemonstrativeType(Integer demonstrativeType) {
        this.demonstrativeType = demonstrativeType;
    }

    public Integer getStuckType() {
        return stuckType;
    }

    public ResultTest stuckType(Integer stuckType) {
        this.stuckType = stuckType;
        return this;
    }

    public void setStuckType(Integer stuckType) {
        this.stuckType = stuckType;
    }

    public Integer getPedanticType() {
        return pedanticType;
    }

    public ResultTest pedanticType(Integer pedanticType) {
        this.pedanticType = pedanticType;
        return this;
    }

    public void setPedanticType(Integer pedanticType) {
        this.pedanticType = pedanticType;
    }

    public Integer getExcitableType() {
        return excitableType;
    }

    public ResultTest excitableType(Integer excitableType) {
        this.excitableType = excitableType;
        return this;
    }

    public void setExcitableType(Integer excitableType) {
        this.excitableType = excitableType;
    }

    public Integer getHyperthymicType() {
        return hyperthymicType;
    }

    public ResultTest hyperthymicType(Integer hyperthymicType) {
        this.hyperthymicType = hyperthymicType;
        return this;
    }

    public void setHyperthymicType(Integer hyperthymicType) {
        this.hyperthymicType = hyperthymicType;
    }

    public Integer getDysthymicType() {
        return dysthymicType;
    }

    public ResultTest dysthymicType(Integer dysthymicType) {
        this.dysthymicType = dysthymicType;
        return this;
    }

    public void setDysthymicType(Integer dysthymicType) {
        this.dysthymicType = dysthymicType;
    }

    public Integer getAnxiouslyFearfulType() {
        return anxiouslyFearfulType;
    }

    public ResultTest anxiouslyFearfulType(Integer anxiouslyFearfulType) {
        this.anxiouslyFearfulType = anxiouslyFearfulType;
        return this;
    }

    public void setAnxiouslyFearfulType(Integer anxiouslyFearfulType) {
        this.anxiouslyFearfulType = anxiouslyFearfulType;
    }

    public Integer getEmotionallyExaltedType() {
        return emotionallyExaltedType;
    }

    public ResultTest emotionallyExaltedType(Integer emotionallyExaltedType) {
        this.emotionallyExaltedType = emotionallyExaltedType;
        return this;
    }

    public void setEmotionallyExaltedType(Integer emotionallyExaltedType) {
        this.emotionallyExaltedType = emotionallyExaltedType;
    }

    public Integer getEmotiveType() {
        return emotiveType;
    }

    public ResultTest emotiveType(Integer emotiveType) {
        this.emotiveType = emotiveType;
        return this;
    }

    public void setEmotiveType(Integer emotiveType) {
        this.emotiveType = emotiveType;
    }

    public Integer getCyclothymicType() {
        return cyclothymicType;
    }

    public ResultTest cyclothymicType(Integer cyclothymicType) {
        this.cyclothymicType = cyclothymicType;
        return this;
    }

    public void setCyclothymicType(Integer cyclothymicType) {
        this.cyclothymicType = cyclothymicType;
    }

    public User getUser() {
        return user;
    }

    public ResultTest user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResultTest)) {
            return false;
        }
        return id != null && id.equals(((ResultTest) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResultTest{" +
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
            "}";
    }
}
