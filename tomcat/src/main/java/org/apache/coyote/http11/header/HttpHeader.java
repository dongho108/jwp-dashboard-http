package org.apache.coyote.http11.header;

import java.util.List;

public class HttpHeader {

    private HttpHeaderType httpHeaderType;
    private List<String> values;

    public HttpHeader() {
    }

    private HttpHeader(final HttpHeaderType httpHeaderType,
                       final List<String> values) {
        this.httpHeaderType = httpHeaderType;
        this.values = values;
    }

    public static HttpHeader of(final HttpHeaderType httpHeaderType,
                                final String... values) {
        return new HttpHeader(httpHeaderType, List.of(values));
    }

    public HttpHeaderType getHttpHeaderType() {
        return httpHeaderType;
    }

    public List<String> getValues() {
        return values;
    }
}
