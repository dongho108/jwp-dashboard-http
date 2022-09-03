package org.apache.coyote.http11.header;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum HttpHeaderType {
    CONTENT_TYPE("Content-Type"),
    CONTENT_LENGTH("Content-Length"),
    HOST("Host");

    private String value;

    HttpHeaderType(final String value) {
        this.value = value;
    }

    public static HttpHeaderType from(final String value) {
        return Arrays.stream(HttpHeaderType.values())
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("해당하는 HttpHeaderType이 없습니다. " + value));
    }

    public String getValue() {
        return value;
    }
}
