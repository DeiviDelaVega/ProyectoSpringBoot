package com.polo.webreservas.security;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.stereotype.Component;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;

@Component
public class CaptchaValidationFilter extends GenericFilter {

    private final AntPathRequestMatcher matcher = new AntPathRequestMatcher("/login", "POST");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        if (matcher.matches(req)) {
            String userInputCaptcha = req.getParameter("captcha");
            String sessionCaptcha = (String) req.getSession().getAttribute("captcha");

            if (sessionCaptcha == null || !sessionCaptcha.equalsIgnoreCase(userInputCaptcha)) {
                HttpServletResponse res = (HttpServletResponse) response;
                res.sendRedirect("/login?error=true");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
