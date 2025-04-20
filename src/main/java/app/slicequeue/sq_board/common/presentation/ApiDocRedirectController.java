package app.slicequeue.sq_board.common.presentation;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class ApiDocRedirectController {

    @Value("${apidoc.url}")
    private String apidogUrl;

    @GetMapping("/api-docs")
    public void redirectToApidog(HttpServletResponse response) throws IOException {
        response.sendRedirect(apidogUrl);
    }
}
