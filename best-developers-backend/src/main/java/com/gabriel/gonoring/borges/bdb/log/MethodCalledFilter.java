package com.gabriel.gonoring.borges.bdb.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class MethodCalledFilter extends HttpFilter {

    private Logger LOGGER = LoggerFactory.getLogger(MethodCalledFilter.class);

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        try {
            LOGGER.info("Method '{}' '{}' called", request.getMethod(), getMethodCalled(request));
        }catch (Exception e){
            LOGGER.warn("Could not register method called", e);
        }

        chain.doFilter(request, response);

    }

    private String getMethodCalled(HttpServletRequest request){
        return request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
    }
}
