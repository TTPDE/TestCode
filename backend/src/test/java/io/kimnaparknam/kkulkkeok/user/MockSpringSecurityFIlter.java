package io.kimnaparknam.kkulkkeok.user;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

public class MockSpringSecurityFIlter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication((Authentication) ((HttpServletRequest) servletRequest).getUserPrincipal());
        filterChain.doFilter(servletRequest, servletResponse);
    }
    @Override
    public void destroy() {
        SecurityContextHolder.clearContext();
    }
}
