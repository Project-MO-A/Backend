package com.moa.global.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.moa.global.filter.exception.BadValueAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.util.Map;

public class LoginProcessFilter extends AbstractAuthenticationProcessingFilter {

    private static final String METHOD_NAME = "POST";
    private static final String CONTENT_TYPE = "application/json";
    private static final String SPRING_SECURITY_FORM_EMAIL_KEY = "email";
    private static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    private static final String DEFAULT_FILTER_PROCESSES_URL = "/form-login";
    private final ObjectMapper objectMapper;

    public LoginProcessFilter(ObjectMapper objectMapper, AuthenticationManager authenticationManager) {
        super(DEFAULT_FILTER_PROCESSES_URL, authenticationManager);
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        if ((request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)) && !request.getMethod().equals(METHOD_NAME)) {
            throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
        }

        Map<String, String> parameter = objectMapper.readValue(request.getInputStream(), Map.class);
        String email = parameter.get(SPRING_SECURITY_FORM_EMAIL_KEY);
        String password = parameter.get(SPRING_SECURITY_FORM_PASSWORD_KEY);

        validateParameterValue(email, password);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private void validateParameterValue(String email, String password) throws AuthenticationException {
        if (email == null || password == null) {
            throw new BadValueAuthenticationException("email, password 값 둘 다 입력해 주세요");
        }
    }
}
