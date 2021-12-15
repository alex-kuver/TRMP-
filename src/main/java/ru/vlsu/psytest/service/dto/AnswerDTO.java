package ru.vlsu.psytest.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link ru.vlsu.psytest.domain.Answer} entity.
 */
public class AnswerDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Boolean answer;


    private Long questionId;

    private Long resultTestId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isAnswer() {
        return answer;
    }

    public void setAnswer(Boolean answer) {
        this.answer = answer;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getResultTestId() {
        return resultTestId;
    }

    public void setResultTestId(Long resultTestId) {
        this.resultTestId = resultTestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnswerDTO)) {
            return false;
        }

        return id != null && id.equals(((AnswerDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnswerDTO{" +
            "id=" + getId() +
            ", answer='" + isAnswer() + "'" +
            ", questionId=" + getQuestionId() +
            ", resultTestId=" + getResultTestId() +
            "}";
    }
}
