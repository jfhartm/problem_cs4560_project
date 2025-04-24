package org.zalando.problem.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.zalando.problem.StatusType;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

final class StatusTypeAdapter extends TypeAdapter<StatusType> {

    private final Map<Integer, StatusType> index;

    StatusTypeAdapter(final Map<Integer, StatusType> index) {
        this.index = index;
    }

    @Override
    public void write(
            final JsonWriter out,
            @Nullable final StatusType status) throws IOException {

        if (Objects.isNull(status)) {
            out.nullValue();
            return;
        }

        out.value(status.getStatusCode());
    }

    @Override
    public StatusType read(final JsonReader in) throws IOException {
        final JsonToken peek = in.peek();

        if (peek == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        final int statusCode = in.nextInt();
        @Nullable
        final StatusType status = index.get(statusCode);
        return status == null ? new UnknownStatus(statusCode) : status;
    }

}
