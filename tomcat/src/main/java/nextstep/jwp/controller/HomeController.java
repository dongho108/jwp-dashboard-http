package nextstep.jwp.controller;

import static org.apache.coyote.response.HttpStatus.OK;

import org.apache.coyote.http11.controller.AbstractController;
import org.apache.coyote.request.HttpRequest;
import org.apache.coyote.response.HttpResponse;

public class HomeController extends AbstractController {
    private static final String DEFAULT_MESSAGE = "Hello world!";

    @Override
    public void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) {
        httpResponse.setHttpStatus(OK);
        httpResponse.addHeader("Content-Type", "text/html;charset=utf-8");
        httpResponse.setBody(DEFAULT_MESSAGE);
    }
}
