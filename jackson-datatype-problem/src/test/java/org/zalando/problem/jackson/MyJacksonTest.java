package org.zalando.problem.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MyJacksonTest {
    @Test
    void shouldSerializeProblemWithModule() throws Exception {
        ObjectMapper mapper = new ObjectMapper().registerModule(new ProblemModule());
        Problem problem = Problem.valueOf(Status.NOT_FOUND);
        String json = mapper.writeValueAsString(problem);
        assertThat(json).contains("\"status\":404");
    }


}

