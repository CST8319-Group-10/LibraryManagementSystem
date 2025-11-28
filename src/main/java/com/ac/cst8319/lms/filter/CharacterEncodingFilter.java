package com.ac.cst8319.lms.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Filter to set character encoding to UTF-8 for all requests and responses.
 */
@WebFilter(urlPatterns = {"/*"})
public class CharacterEncodingFilter implements Filter {

    private static final String ENCODING = "UTF-8";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Set request encoding
        request.setCharacterEncoding(ENCODING);

        // Set response encoding
        response.setCharacterEncoding(ENCODING);
        response.setContentType("text/html; charset=" + ENCODING);

        // Continue the filter chain
        chain.doFilter(request, response);
    }
}
