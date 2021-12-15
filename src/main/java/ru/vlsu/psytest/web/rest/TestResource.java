package ru.vlsu.psytest.web.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vlsu.psytest.service.TestService;
import ru.vlsu.psytest.service.dto.AnswerDTO;
import ru.vlsu.psytest.service.dto.ResultTestDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TestResource {
    private final Logger log = LoggerFactory.getLogger(AnswerResource.class);


    private final TestService testService;
    private final HttpServletRequest httpServletRequest;

    public TestResource(TestService testService, HttpServletRequest httpServletRequest) {
        this.testService = testService;
        this.httpServletRequest = httpServletRequest;
    }

    @PostMapping("/test/solve")
    public ResponseEntity SolveTest(@RequestBody ArrayList<AnswerDTO> answers) {
        log.debug("REST request to solve test");
        String userName = httpServletRequest.getRemoteUser();
        ResultTestDTO resultTestDTO = testService.solveTest(answers, userName != null ? userName : "ANONIM");
        if (resultTestDTO != null) {
            return ResponseEntity.ok(resultTestDTO);
        } else {
            return ResponseEntity.badRequest().body("Не на все вопросы есть ответы!");
        }

    }
}
