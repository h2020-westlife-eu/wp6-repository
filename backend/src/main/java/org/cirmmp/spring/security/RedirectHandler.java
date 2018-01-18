package org.cirmmp.spring.security;

import org.cirmmp.spring.controller.AppController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//TODO check if it is needed - from logs it seems that it's not called.

/* redirects to url set in url_prior_login attribute */
public class RedirectHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory
            .getLogger(RedirectHandler.class);
    public RedirectHandler(String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
        logger.info("defaultTargetUrl"+defaultTargetUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String redirectUrl = (String) session.getAttribute("url_prior_login");
        logger.info("url_prior_login:"+redirectUrl);
        if (redirectUrl != null) {
                // clean this attribute from session
                session.removeAttribute("url_prior_login");
                //fix ../staff/index.html is redirected to /admin../staff/index.html
                //bug redirects to 127.0.0.1
                if (redirectUrl.startsWith("..")) redirectUrl=request.getHeader("referer")+redirectUrl.substring(3);
                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
        } else {
             super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
