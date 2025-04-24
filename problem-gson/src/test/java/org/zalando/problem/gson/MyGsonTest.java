package org.zalando.problem.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.junit.jupiter.api.Test;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;
import org.zalando.problem.ThrowableProblem;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URI;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MyGsonTest {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(new ProblemAdapterFactory(Status.class).withStackTraces())
            .create();

    @Test
    void shouldReturnUnknownStatusCode(){
        UnknownStatus status = new UnknownStatus(499);
        assertThat(status.getStatusCode()).isEqualTo(499);
    }

    @Test
    void shouldReturnUnknownReasonPhrase() {
        UnknownStatus status = new UnknownStatus(499);
        assertThat(status.getReasonPhrase()).isEqualTo("Unknown");
    }

    @Test
    void shouldWriteStatusTypeAsInt() throws IOException {
        StatusTypeAdapter adapter = new StatusTypeAdapter(Collections.emptyMap());
        StringWriter out = new StringWriter();
        JsonWriter writer = new JsonWriter(out);
        adapter.write(writer, Status.NOT_FOUND);
        writer.flush();
        assertThat(out.toString()).contains("404");
    }
}
