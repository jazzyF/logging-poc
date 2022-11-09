package com.example;

import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Objects;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LoggingRequestFilter extends OncePerRequestFilter {

    public static final String SYSTEM_USER = "system";
    public static final String LOGGED_IN_USER = "loggedInUser";
    public static final String CORRELATION_ID = "correlation-id";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        UUID uuid = UUID.randomUUID();
        request.setAttribute("filterUUID", uuid);
        MDC.put(CORRELATION_ID, uuid.toString());

        Principal userPrincipal = request.getUserPrincipal();
        if (Objects.nonNull(userPrincipal)) {
            String name = userPrincipal.getName();
            System.out.println("userPrincipal.getName(): " + name);
            request.setAttribute(LOGGED_IN_USER, name);
            MDC.put(LOGGED_IN_USER, name);
        } else {
            System.out.println("User principal is null");
            request.setAttribute(LOGGED_IN_USER, SYSTEM_USER);
            MDC.put(LOGGED_IN_USER, "anon");
        }
        filterChain.doFilter(request, response);
    }
}