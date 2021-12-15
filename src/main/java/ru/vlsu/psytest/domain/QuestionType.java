package ru.vlsu.psytest.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import ru.vlsu.psytest.domain.enumeration.Psychotype;

/**
 * A QuestionType.
 */
@Entity
@Table(name = "question_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QuestionType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Psychotype type;

    @NotNull
    @Column(name = "coefficient", nullable = false)
    private Integer coefficient;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Psychotype getType() {
        return type;
    }

    public QuestionType type(Psychotype type) {
        this.type = type;
        return this;
    }

    public void setType(Psychotype type) {
        this.type = type;
    }

    public Integer getCoefficient() {
        return coefficient;
    }

    public QuestionType coefficient(Integer coefficient) {
        this.coefficient = coefficient;
        return this;
    }

    public void setCoefficient(Integer coefficient) {
        this.coefficient = coefficient;
    }

    public String getDescription() {
        return description;
    }

    public QuestionType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionType)) {
            return false;
        }
        return id != null && id.equals(((QuestionType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionType{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", coefficient=" + getCoefficient() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
