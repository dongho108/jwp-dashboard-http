package nextstep.jwp.application;

import static org.apache.coyote.http11.header.HttpHeaderType.COOKIE;

import java.util.Optional;
import org.apache.coyote.http11.header.HttpHeader;
import org.apache.coyote.http11.http.HttpHeaders;
import org.apache.coyote.http11.http.request.HttpRequest;

public class AuthorizeService {

    private static final String JSESSIONID = "JSESSIONID";

    private AuthorizeService() {
    }

    private static class AuthorizeServiceGenerator{
        private static final AuthorizeService INSTANCE = new AuthorizeService();
    }

    public static AuthorizeService getInstance() {
        return AuthorizeServiceGenerator.INSTANCE;
    }

    public boolean isAuthorized(final HttpRequest httpRequest) {
        final HttpHeaders headers = httpRequest.getHeaders();
        if (!headers.contains(COOKIE.getValue())) {
            return false;
        }

        final HttpHeader httpHeader = headers.get(COOKIE.getValue());
        final Optional<String> jsessionid = httpHeader.getValues().stream()
                .filter(it -> it.contains(JSESSIONID))
                .findFirst();

        return jsessionid.isPresent();
    }
}
