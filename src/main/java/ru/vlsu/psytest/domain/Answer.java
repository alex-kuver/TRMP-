package ru.vlsu.psytest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Answer.
 */
@Entity
@Table(name = "answer")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "answer", nullable = false)
    private Boolean answer;

    @ManyToOne
    @JsonIgnoreProperties(value = "answers", allowSetters = true)
    private Question question;

    @ManyToOne
    @JsonIgnoreProperties(value = "answers", allowSetters = true)
    private ResultTest resultTest;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAnswer() {
        return answer;
    }

    public Answer answer(Boolean answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    public Question getQuestion() {
        return question;
    }

    public Answer question(Question question) {
        this.question = question;
        return this;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public ResultTest getResultTest() {
        return resultTest;
    }

    public Answer resultTest(ResultTest resultTest) {
        this.resultTest = resultTest;
        return this;
    }

    public void setResultTest(ResultTest resultTest) {
        this.resultTest = resultTest;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Answer)) {
            return false;
        }
        return id != null && id.equals(((Answer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Answer{" +
            "id=" + getId() +
            ", answer='" + isAnswer() + "'" +
            "}";
    }
}
