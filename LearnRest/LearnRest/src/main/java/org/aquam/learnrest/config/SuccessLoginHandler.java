package org.aquam.learnrest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

// based on the role
@Configuration
public class SuccessLoginHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetURL = determineTargetURL(authentication);
        if (response.isCommitted())
            return;
        redirectStrategy.sendRedirect(request, response, targetURL);
    }

    protected String determineTargetURL(final Authentication authentication) {

        Map<String, String> roleTargetURL = new HashMap<>();
        roleTargetURL.put("TEACHER", "/learn/home");    // main/home.html
        roleTargetURL.put("STUDENT", "/learn/home");
        roleTargetURL.put("ADMIN", "/learn/admin");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        //List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            String authorityName = authority.getAuthority();
            if (roleTargetURL.containsKey(authorityName))
                return roleTargetURL.get(authorityName);
        }

        throw new IllegalStateException();
        // return "/learn/login";
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return;
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
