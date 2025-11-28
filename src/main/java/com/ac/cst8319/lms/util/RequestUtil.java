package com.ac.cst8319.lms.util;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Utility class for HTTP request handling.
 */
public class RequestUtil {

    /**
     * Get client IP address from request.
     * Handles X-Forwarded-For header for proxied requests.
     *
     * @param request the HTTP servlet request
     * @return client IP address
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");

        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        // X-Forwarded-For may contain multiple IPs, take the first one
        if (ipAddress != null && ipAddress.contains(",")) {
            ipAddress = ipAddress.split(",")[0].trim();
        }

        return ipAddress;
    }

    /**
     * Check if the request is an AJAX request.
     * @param request the HTTP servlet request
     * @return true if AJAX request, false otherwise
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(header);
    }

    /**
     * Get parameter as long value.
     * @param request the HTTP servlet request
     * @param paramName parameter name
     * @param defaultValue default value if parameter is missing or invalid
     * @return parameter value as long, or default value
     */
    public static long getLongParameter(HttpServletRequest request, String paramName, long defaultValue) {
        String value = request.getParameter(paramName);
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Get parameter as int value.
     * @param request the HTTP servlet request
     * @param paramName parameter name
     * @param defaultValue default value if parameter is missing or invalid
     * @return parameter value as int, or default value
     */
    public static int getIntParameter(HttpServletRequest request, String paramName, int defaultValue) {
        String value = request.getParameter(paramName);
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Get parameter as String and trim whitespace.
     * @param request the HTTP servlet request
     * @param paramName parameter name
     * @return trimmed parameter value, or null if not present
     */
    public static String getStringParameter(HttpServletRequest request, String paramName) {
        String value = request.getParameter(paramName);
        return (value != null) ? value.trim() : null;
    }
}
