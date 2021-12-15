package ru.vlsu.psytest.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import ru.vlsu.psytest.web.rest.TestUtil;

public class ResultTestTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultTest.class);
        ResultTest resultTest1 = new ResultTest();
        resultTest1.setId(1L);
        ResultTest resultTest2 = new ResultTest();
        resultTest2.setId(resultTest1.getId());
        assertThat(resultTest1).isEqualTo(resultTest2);
        resultTest2.setId(2L);
        assertThat(resultTest1).isNotEqualTo(resultTest2);
        resultTest1.setId(null);
        assertThat(resultTest1).isNotEqualTo(resultTest2);
    }
}
