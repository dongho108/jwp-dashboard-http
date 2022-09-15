package org.apache.coyote.response;

import static org.apache.coyote.header.HttpHeaderType.CONTENT_LENGTH;
import static org.apache.coyote.response.HttpStatus.OK;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.coyote.HttpVersion;
import org.apache.coyote.header.HttpHeader;
import org.apache.coyote.header.HttpHeaders;

public class HttpResponse {

    private static final String NEW_LINE_LETTER = "\r\n";
    private static final String EMPTY_LETTER = "";
    private static final String BLANK_LETTER = " ";
    private static final String COLON_LETTER = ":";
    private static final String SEMI_COLON_LETTER = ";";
    private static final HttpVersion DEFAULT_HTTP_VERSION = HttpVersion.HTTP11;

    private HttpVersion httpVersion = DEFAULT_HTTP_VERSION;
    private HttpStatus httpStatus = OK;
    private HttpHeaders headers = new HttpHeaders();
    private String body = "";

    public String getBody() {
        return body;
    }

    public void setHttpStatus(final HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void addHeader(final String name, final String value) {
        headers.add(HttpHeader.of(name, value));
    }

    public void setBody(final String body) {
        final int length = body.getBytes(StandardCharsets.UTF_8).length;
        addHeader(CONTENT_LENGTH.getValue(), String.valueOf(length));
        this.body = body;
    }

    public String generateResponse() {
        final String statusLine = generateStatusLine();
        final String headerLine = generateHeaderLine();
        return String.join(NEW_LINE_LETTER, statusLine, headerLine, EMPTY_LETTER, body);
    }

    private String generateStatusLine() {
        return String.join(BLANK_LETTER,
                httpVersion.getVersion(),
                String.valueOf(httpStatus.getCode()),
                httpStatus.getMessage(),
                EMPTY_LETTER);
    }

    private String generateHeaderLine() {
        final List<String> headers = new ArrayList<>();
        for (String httpHeaderType : this.headers.keySet()) {
            final String header = String.join(COLON_LETTER + BLANK_LETTER, httpHeaderType,
                    String.join(SEMI_COLON_LETTER, this.headers.get(httpHeaderType).getValues()));
            headers.add(header + BLANK_LETTER);
        }
        return String.join(NEW_LINE_LETTER, headers);
    }
}
