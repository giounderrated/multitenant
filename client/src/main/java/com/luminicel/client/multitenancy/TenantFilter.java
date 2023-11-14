package com.luminicel.client.multitenancy;

import com.luminicel.client.rest.JSend;
import com.luminicel.client.rest.Success;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TenantFilter extends OncePerRequestFilter {

    private final RestTemplate restTemplate;
    private static final String CHECK_ENDPOINT = "http://localhost:8080/api/v1/tenants/check/";

    public TenantFilter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String domain = getTenantDomain(request);
//        if(domain==null){
//            throw new IllegalArgumentException("Tenant is not defined");
//        }
//
//        if(!checkIfDomainExists(domain)){
//            throw new IllegalArgumentException(String.format("Tenant with domain %s is not defined",domain));
//        }

        TenantContext.setTenantId(domain);
        filterChain.doFilter(request, response);
        TenantContext.clear();
    }

    private boolean checkIfDomainExists(final String domain){
        final HttpHeaders headers = new HttpHeaders();
        final String endpoint = CHECK_ENDPOINT + domain;
        final ParameterizedTypeReference<Boolean> responseType = new ParameterizedTypeReference<>() {};

        ResponseEntity<Boolean> responseEntity =
                restTemplate.exchange(endpoint, HttpMethod.GET, new HttpEntity<>(headers), responseType);

        return responseEntity.getBody();

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
