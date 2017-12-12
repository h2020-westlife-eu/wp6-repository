package org.cirmmp.spring.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/* redirects to url set in url_prior_login attribute */
public class RedirectHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    public RedirectHandler(String defaultTargetUrl) {
        setDefaultTargetUrl(defaultTargetUrl);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session != null) {
            String redirectUrl = (String) session.getAttribute("url_prior_login");
            //fix ../staff/index.html is redirected to /admin../staff/index.html
            if (redirectUrl != null) {
                // clean this attribute from session
                session.removeAttribute("url_prior_login");
                if (redirectUrl.startsWith("..")) redirectUrl="/"+redirectUrl;
                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
            } else {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
