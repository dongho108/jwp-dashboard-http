package nextstep.jwp.controller;

import static nextstep.jwp.controller.ResourceUrls.INDEX_HTML;
import static nextstep.jwp.controller.ResourceUrls.LOGIN_HTML;

import nextstep.jwp.application.SessionAuthorizeService;
import org.apache.coyote.request.HttpRequest;
import org.apache.coyote.response.HttpResponse;

public class IndexController extends ResourceController {

    private final SessionAuthorizeService sessionAuthorizeService = SessionAuthorizeService.getInstance();

    @Override
    public void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        if (sessionAuthorizeService.isAuthorized(httpRequest)) {
            setResource(INDEX_HTML.getValue(),httpResponse);
            return;
        }
        setRedirectHeader(httpResponse, LOGIN_HTML);
    }
}
