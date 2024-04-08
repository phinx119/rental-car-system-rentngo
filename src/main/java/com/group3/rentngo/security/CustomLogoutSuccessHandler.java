package com.group3.rentngo.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                org.springframework.security.core.Authentication authentication)
            throws IOException, ServletException {
        // Invalidate session on logout
        request.getSession().invalidate();
        response.sendRedirect("/login?logout");
    }
}
