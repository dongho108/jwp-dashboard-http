package nextstep.jwp.application;

import static org.apache.coyote.header.HttpHeaderType.COOKIE;

import java.util.Optional;
import org.apache.coyote.header.HttpHeader;
import org.apache.coyote.header.HttpHeaders;
import org.apache.coyote.request.HttpRequest;

public class SessionAuthorizeService {

    private static final String JSESSIONID = "JSESSIONID";

    private SessionAuthorizeService() {
    }

    private static class AuthorizeServiceGenerator{
        private static final SessionAuthorizeService INSTANCE = new SessionAuthorizeService();
    }

    public static SessionAuthorizeService getInstance() {
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
