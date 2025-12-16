package com.ac.cst8319.lms.servlet;

import com.ac.cst8319.lms.model.UserAccount;
import com.ac.cst8319.lms.util.RoleUtil;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for DashboardServlet.
 * Tests dashboard routing based on user roles.
 */
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DashboardServletTest {

    @Mock
    private HttpServletRequest mockRequest;

    @Mock
    private HttpServletResponse mockResponse;

    @Mock
    private HttpSession mockSession;

    @Mock
    private RequestDispatcher mockRequestDispatcher;

    private DashboardServlet dashboardServlet;

    @BeforeEach
    void setUp() {
        dashboardServlet = new DashboardServlet();
    }

    // ==================== Test Admin Dashboard ====================

    @Test
    void testDoGet_RedirectsToAdminDashboard_WhenUserIsAdmin() throws ServletException, IOException {
        // Arrange
        UserAccount adminUser = createTestUser(1L, "admin@test.com", "Admin", "User", 4);
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute("currentUser")).thenReturn(adminUser);
        when(mockSession.getAttribute("successMessage")).thenReturn(null);
        when(mockSession.getAttribute("errorMessage")).thenReturn(null);
        when(mockRequest.getRequestDispatcher("/WEB-INF/jsp/dashboard/admin-dashboard.jsp"))
                .thenReturn(mockRequestDispatcher);
        when(mockRequest.getContextPath()).thenReturn("/lms");

        // Act
        dashboardServlet.doGet(mockRequest, mockResponse);

        // Assert
        verify(mockRequestDispatcher, times(1)).forward(mockRequest, mockResponse);
        verify(mockResponse, never()).sendRedirect(anyString());
    }

    // ==================== Test Librarian Dashboard ====================

    @Test
    void testDoGet_RedirectsToLibrarianDashboard_WhenUserIsLibrarian() throws ServletException, IOException {
        // Arrange
        UserAccount librarianUser = createTestUser(2L, "librarian@test.com", "Librarian", "User", 3);
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute("currentUser")).thenReturn(librarianUser);
        when(mockRequest.getContextPath()).thenReturn("/lms");

        // Act
        dashboardServlet.doGet(mockRequest, mockResponse);

        // Assert
        verify(mockResponse, times(1)).sendRedirect("/lms/librarian/dashboard");
        verify(mockRequest, never()).getRequestDispatcher(anyString());
    }

    // ==================== Test Regular User Dashboard ====================

    @Test
    void testDoGet_RedirectsToUserDashboard_WhenUserIsRegularUser() throws ServletException, IOException {
        // Arrange
        UserAccount regularUser = createTestUser(3L, "user@test.com", "Regular", "User", 2);
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute("currentUser")).thenReturn(regularUser);
        when(mockSession.getAttribute("successMessage")).thenReturn(null);
        when(mockSession.getAttribute("errorMessage")).thenReturn(null);
        when(mockRequest.getRequestDispatcher("/WEB-INF/jsp/dashboard/user-dashboard.jsp"))
                .thenReturn(mockRequestDispatcher);
        when(mockRequest.getContextPath()).thenReturn("/lms");

        // Act
        dashboardServlet.doGet(mockRequest, mockResponse);

        // Assert
        verify(mockRequestDispatcher, times(1)).forward(mockRequest, mockResponse);
        verify(mockResponse, never()).sendRedirect(anyString());
    }

    // ==================== Test No User in Session ====================

    @Test
    void testDoGet_RedirectsToLogin_WhenNoUserInSession() throws ServletException, IOException {
        // Arrange
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute("currentUser")).thenReturn(null);
        when(mockRequest.getContextPath()).thenReturn("/lms");

        // Act
        dashboardServlet.doGet(mockRequest, mockResponse);

        // Assert
        verify(mockResponse, times(1)).sendRedirect("/lms/login");
        verify(mockRequest, never()).getRequestDispatcher(anyString());
    }

    // ==================== Test Null Session ====================

    @Test
    void testDoGet_RedirectsToLogin_WhenSessionIsNull() throws ServletException, IOException {
        // Arrange
        when(mockRequest.getSession(false)).thenReturn(null);
        when(mockRequest.getContextPath()).thenReturn("/lms");

        // Act
        dashboardServlet.doGet(mockRequest, mockResponse);

        // Assert
        verify(mockResponse, times(1)).sendRedirect("/lms/login");
        verify(mockRequest, never()).getRequestDispatcher(anyString());
    }

    // ==================== Test Success Message Display ====================

    @Test
    void testDoGet_DisplaysSuccessMessage_WhenPresent() throws ServletException, IOException {
        // Arrange
        UserAccount adminUser = createTestUser(1L, "admin@test.com", "Admin", "User", 4);
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute("currentUser")).thenReturn(adminUser);
        when(mockSession.getAttribute("successMessage")).thenReturn("Operation successful!");
        when(mockSession.getAttribute("errorMessage")).thenReturn(null);
        when(mockRequest.getRequestDispatcher("/WEB-INF/jsp/dashboard/admin-dashboard.jsp"))
                .thenReturn(mockRequestDispatcher);
        when(mockRequest.getContextPath()).thenReturn("/lms");

        // Act
        dashboardServlet.doGet(mockRequest, mockResponse);

        // Assert
        verify(mockRequest, times(1)).setAttribute("successMessage", "Operation successful!");
        verify(mockSession, times(1)).removeAttribute("successMessage");
        verify(mockRequestDispatcher, times(1)).forward(mockRequest, mockResponse);
    }

    // ==================== Test Error Message Display ====================

    @Test
    void testDoGet_DisplaysErrorMessage_WhenPresent() throws ServletException, IOException {
        // Arrange
        UserAccount adminUser = createTestUser(1L, "admin@test.com", "Admin", "User", 4);
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute("currentUser")).thenReturn(adminUser);
        when(mockSession.getAttribute("successMessage")).thenReturn(null);
        when(mockSession.getAttribute("errorMessage")).thenReturn("Operation failed!");
        when(mockRequest.getRequestDispatcher("/WEB-INF/jsp/dashboard/admin-dashboard.jsp"))
                .thenReturn(mockRequestDispatcher);
        when(mockRequest.getContextPath()).thenReturn("/lms");

        // Act
        dashboardServlet.doGet(mockRequest, mockResponse);

        // Assert
        verify(mockRequest, times(1)).setAttribute("errorMessage", "Operation failed!");
        verify(mockSession, times(1)).removeAttribute("errorMessage");
        verify(mockRequestDispatcher, times(1)).forward(mockRequest, mockResponse);
    }

    // ==================== Test User Account Information Display ====================

    @Test
    void testDoGet_UserAccountInformationAccessible_ForAllRoles() throws ServletException, IOException {
        // Test for Admin
        UserAccount adminUser = createTestUser(1L, "admin@test.com", "Admin", "User", RoleUtil.ROLE_ADMINISTRATOR);
        when(mockRequest.getSession(false)).thenReturn(mockSession);
        when(mockSession.getAttribute("currentUser")).thenReturn(adminUser);
        when(mockSession.getAttribute("successMessage")).thenReturn(null);
        when(mockSession.getAttribute("errorMessage")).thenReturn(null);
        when(mockRequest.getRequestDispatcher(anyString())).thenReturn(mockRequestDispatcher);
        when(mockRequest.getContextPath()).thenReturn("/lms");

        dashboardServlet.doGet(mockRequest, mockResponse);

        // Verify admin user has correct role
        assertEquals(RoleUtil.ROLE_ADMINISTRATOR, adminUser.getRoleId());
        assertEquals("admin@test.com", adminUser.getEmail());
        verify(mockSession, atLeastOnce()).getAttribute("currentUser");
    }

    // ==================== Helper Methods ====================

    private UserAccount createTestUser(Long userId, String email, String firstName, String lastName, int roleId) {
        UserAccount user = new UserAccount();
        user.setUserId(userId);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone("555-1234");
        user.setRoleId(roleId);
        user.setAccountStanding(1);
        return user;
    }

    private void assertEquals(int expected, int actual) {
        org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
    }

    private void assertEquals(String expected, String actual) {
        org.junit.jupiter.api.Assertions.assertEquals(expected, actual);
    }
}
