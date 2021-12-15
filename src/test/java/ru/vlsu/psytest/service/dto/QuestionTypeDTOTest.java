package ru.vlsu.psytest.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ru.vlsu.psytest.web.rest.TestUtil;

public class QuestionTypeDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(QuestionTypeDTO.class);
        QuestionTypeDTO questionTypeDTO1 = new QuestionTypeDTO();
        questionTypeDTO1.setId(1L);
        QuestionTypeDTO questionTypeDTO2 = new QuestionTypeDTO();
        assertThat(questionTypeDTO1).isNotEqualTo(questionTypeDTO2);
        questionTypeDTO2.setId(questionTypeDTO1.getId());
        assertThat(questionTypeDTO1).isEqualTo(questionTypeDTO2);
        questionTypeDTO2.setId(2L);
        assertThat(questionTypeDTO1).isNotEqualTo(questionTypeDTO2);
        questionTypeDTO1.setId(null);
        assertThat(questionTypeDTO1).isNotEqualTo(questionTypeDTO2);
    }
}
