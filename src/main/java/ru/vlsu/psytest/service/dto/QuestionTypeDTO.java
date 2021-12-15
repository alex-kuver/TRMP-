package ru.vlsu.psytest.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import ru.vlsu.psytest.domain.enumeration.Psychotype;

/**
 * A DTO for the {@link ru.vlsu.psytest.domain.QuestionType} entity.
 */
public class QuestionTypeDTO implements Serializable {
    
    private Long id;

    @NotNull
    private Psychotype type;

    @NotNull
    private Integer coefficient;

    @NotNull
    private String description;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Psychotype getType() {
        return type;
    }

    public void setType(Psychotype type) {
        this.type = type;
    }

    public Integer getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(Integer coefficient) {
        this.coefficient = coefficient;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionTypeDTO)) {
            return false;
        }

        return id != null && id.equals(((QuestionTypeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionTypeDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", coefficient=" + getCoefficient() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
