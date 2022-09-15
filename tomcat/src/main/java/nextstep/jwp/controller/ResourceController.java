package nextstep.jwp.controller;

import static org.apache.coyote.header.HttpHeaderType.CONTENT_TYPE;
import static org.apache.coyote.header.HttpHeaderType.LOCATION;
import static org.apache.coyote.response.HttpStatus.OK;
import static org.apache.coyote.response.HttpStatus.REDIRECT;

import java.io.IOException;
import java.net.URISyntaxException;
import nextstep.jwp.exception.InternalException;
import org.apache.catalina.webutils.IOUtils;
import org.apache.catalina.webutils.Parser;
import org.apache.coyote.header.ContentType;
import org.apache.coyote.http11.controller.AbstractController;
import org.apache.coyote.request.HttpRequest;
import org.apache.coyote.response.HttpResponse;

public class ResourceController extends AbstractController {

    @Override
    public void doGet(final HttpRequest httpRequest,
                      final HttpResponse httpResponse) {
        setResource(httpRequest, httpResponse);
    }

    private void setResource(final HttpRequest httpRequest,
                             final HttpResponse httpResponse) {
        final String path = httpRequest.getStartLine().getPath();
        final String fileName = Parser.convertResourceFileName(path);
        setResourceByFileName(fileName, httpResponse);
    }

    protected void setResource(final String fileName,
                               final HttpResponse httpResponse) {
        setResourceByFileName(fileName, httpResponse);
    }

    private void setResourceByFileName(final String fileName,
                                       final HttpResponse httpResponse) {
        final String fileType = Parser.parseFileType(fileName);
        try {
            final String body = IOUtils.readResourceFile(fileName);
            httpResponse.setHttpStatus(OK);
            httpResponse.addHeader(CONTENT_TYPE.getValue(), ContentType.of(fileType) + ";charset=utf-8");
//            httpResponse.setCharacterEncoding("utf-8");
            httpResponse.setBody(body);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new InternalException("서버 에러가 발생했습니다.");
        }
    }

    protected void setRedirectHeader(final HttpResponse httpResponse, final ResourceUrls resourceUrls) {
        httpResponse.setHttpStatus(REDIRECT);
        httpResponse.addHeader(LOCATION.getValue(), resourceUrls.getValue());
    }
}
