package com.luminicel.client.multitenancy;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TenantFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String domain = getTenantDomain(request);
        TenantContext.setTenantId(domain);
        filterChain.doFilter(request, response);
        TenantContext.clear();
    }

    private String getTenantDomain(HttpServletRequest request) {
        String domain = request.getServerName();
        int dotIndex = domain.indexOf(".");
        if (dotIndex == -1) {
            return null;
        }
        return domain.substring(0, dotIndex);
    }
}
