package com.ac.cst8319.lms.service;

import com.ac.cst8319.lms.dao.UserAccountDAO;
import com.ac.cst8319.lms.model.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserManagementService.
 * Tests user management operations including creating, updating, and retrieving users.
 */
@ExtendWith(MockitoExtension.class)
public class UserManagementServiceTest {

    @Mock
    private UserAccountDAO mockUserDAO;

    private UserManagementService userManagementService;

    @BeforeEach
    void setUp() throws Exception {
        userManagementService = new UserManagementService();

        // Use reflection to inject the mock DAO
        Field userDAOField = UserManagementService.class.getDeclaredField("userDAO");
        userDAOField.setAccessible(true);
        userDAOField.set(userManagementService, mockUserDAO);
    }

    // ==================== Test getAllUsers ====================

    @Test
    void testGetAllUsers_ReturnsListOfUsers() {
        // Arrange
        List<UserAccount> expectedUsers = createTestUsers();
        when(mockUserDAO.findAll()).thenReturn(expectedUsers);

        // Act
        List<UserAccount> actualUsers = userManagementService.getAllUsers();

        // Assert
        assertNotNull(actualUsers);
        assertEquals(3, actualUsers.size());
        verify(mockUserDAO, times(1)).findAll();
    }

    @Test
    void testGetAllUsers_ReturnsEmptyList_WhenNoUsers() {
        // Arrange
        when(mockUserDAO.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<UserAccount> actualUsers = userManagementService.getAllUsers();

        // Assert
        assertNotNull(actualUsers);
        assertTrue(actualUsers.isEmpty());
        verify(mockUserDAO, times(1)).findAll();
    }

    // ==================== Test getUserById ====================

    @Test
    void testGetUserById_ReturnsUser_WhenUserExists() {
        // Arrange
        UserAccount expectedUser = createTestUser(1L, "user@test.com", "John", "Doe");
        when(mockUserDAO.findById(1L)).thenReturn(Optional.of(expectedUser));

        // Act
        Optional<UserAccount> actualUser = userManagementService.getUserById(1L);

        // Assert
        assertTrue(actualUser.isPresent());
        assertEquals("user@test.com", actualUser.get().getEmail());
        verify(mockUserDAO, times(1)).findById(1L);
    }

    @Test
    void testGetUserById_ReturnsEmpty_WhenUserDoesNotExist() {
        // Arrange
        when(mockUserDAO.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<UserAccount> actualUser = userManagementService.getUserById(999L);

        // Assert
        assertFalse(actualUser.isPresent());
        verify(mockUserDAO, times(1)).findById(999L);
    }

    // ==================== Test getUsersByRole ====================

    @Test
    void testGetUsersByRole_ReturnsUsersWithRole() {
        // Arrange
        List<UserAccount> admins = List.of(
            createTestUser(1L, "admin1@test.com", "Admin", "One"),
            createTestUser(2L, "admin2@test.com", "Admin", "Two")
        );
        when(mockUserDAO.findByRole(4)).thenReturn(admins);

        // Act
        List<UserAccount> actualUsers = userManagementService.getUsersByRole(4);

        // Assert
        assertNotNull(actualUsers);
        assertEquals(2, actualUsers.size());
        verify(mockUserDAO, times(1)).findByRole(4);
    }

    // ==================== Test getUsersByStanding ====================

    @Test
    void testGetUsersByStanding_ReturnsUsersWithStanding() {
        // Arrange
        List<UserAccount> suspendedUsers = List.of(
            createTestUser(1L, "suspended1@test.com", "User", "One")
        );
        when(mockUserDAO.findByStanding(2)).thenReturn(suspendedUsers);

        // Act
        List<UserAccount> actualUsers = userManagementService.getUsersByStanding(2);

        // Assert
        assertNotNull(actualUsers);
        assertEquals(1, actualUsers.size());
        verify(mockUserDAO, times(1)).findByStanding(2);
    }

    // ==================== Test createUser ====================

    @Test
    void testCreateUser_Success() {
        // Arrange
        UserAccount newUser = createTestUser(0L, "newuser@test.com", "New", "User");
        when(mockUserDAO.findByEmail("newuser@test.com")).thenReturn(Optional.empty());
        when(mockUserDAO.insert(any(UserAccount.class))).thenReturn(100L);

        // Act
        UserAccount createdUser = userManagementService.createUser(newUser, "ValidPass123!", 1L, "127.0.0.1");

        // Assert
        assertNotNull(createdUser);
        assertEquals(100L, createdUser.getUserId());
        assertNotNull(createdUser.getPasswordHash());
        verify(mockUserDAO, times(1)).findByEmail("newuser@test.com");
        verify(mockUserDAO, times(1)).insert(any(UserAccount.class));
    }

    @Test
    void testCreateUser_ThrowsException_WhenEmailAlreadyExists() {
        // Arrange
        UserAccount existingUser = createTestUser(1L, "existing@test.com", "Existing", "User");
        UserAccount newUser = createTestUser(0L, "existing@test.com", "New", "User");
        when(mockUserDAO.findByEmail("existing@test.com")).thenReturn(Optional.of(existingUser));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userManagementService.createUser(newUser, "ValidPass123!", 1L, "127.0.0.1");
        });
        verify(mockUserDAO, times(1)).findByEmail("existing@test.com");
        verify(mockUserDAO, never()).insert(any(UserAccount.class));
    }

    @Test
    void testCreateUser_ThrowsException_WhenInvalidEmail() {
        // Arrange
        UserAccount newUser = createTestUser(0L, "invalid-email", "New", "User");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userManagementService.createUser(newUser, "ValidPass123!", 1L, "127.0.0.1");
        });
        verify(mockUserDAO, never()).insert(any(UserAccount.class));
    }

    @Test
    void testCreateUser_ThrowsException_WhenInvalidPassword() {
        // Arrange
        UserAccount newUser = createTestUser(0L, "user@test.com", "New", "User");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userManagementService.createUser(newUser, "weak", 1L, "127.0.0.1");
        });
        verify(mockUserDAO, never()).insert(any(UserAccount.class));
    }

    // ==================== Test updateUserRole ====================

    @Test
    void testUpdateUserRole_Success() {
        // Arrange
        when(mockUserDAO.updateRole(1L, 3)).thenReturn(true);

        // Act
        boolean result = userManagementService.updateUserRole(1L, 3, 1L, "127.0.0.1");

        // Assert
        assertTrue(result);
        verify(mockUserDAO, times(1)).updateRole(1L, 3);
    }

    @Test
    void testUpdateUserRole_ThrowsException_WhenInvalidRoleId() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userManagementService.updateUserRole(1L, 99, 1L, "127.0.0.1");
        });
        verify(mockUserDAO, never()).updateRole(anyLong(), anyInt());
    }

    // ==================== Test deleteUser ====================

    @Test
    void testDeleteUser_Success() {
        // Arrange
        when(mockUserDAO.delete(2L)).thenReturn(true);

        // Act
        boolean result = userManagementService.deleteUser(2L, 1L, "127.0.0.1");

        // Assert
        assertTrue(result);
        verify(mockUserDAO, times(1)).delete(2L);
    }

    @Test
    void testDeleteUser_ThrowsException_WhenDeletingSelf() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            userManagementService.deleteUser(1L, 1L, "127.0.0.1");
        });
        verify(mockUserDAO, never()).delete(anyLong());
    }

    // ==================== Test getTotalUserCount ====================

    @Test
    void testGetTotalUserCount_ReturnsCorrectCount() {
        // Arrange
        when(mockUserDAO.count()).thenReturn(150L);

        // Act
        long count = userManagementService.getTotalUserCount();

        // Assert
        assertEquals(150L, count);
        verify(mockUserDAO, times(1)).count();
    }

    // ==================== Test getUserCountByRole ====================

    @Test
    void testGetUserCountByRole_ReturnsCorrectCount() {
        // Arrange
        when(mockUserDAO.countByRole(4)).thenReturn(5L);

        // Act
        long count = userManagementService.getUserCountByRole(4);

        // Assert
        assertEquals(5L, count);
        verify(mockUserDAO, times(1)).countByRole(4);
    }

    // ==================== Helper Methods ====================

    private UserAccount createTestUser(Long userId, String email, String firstName, String lastName) {
        UserAccount user = new UserAccount();
        user.setUserId(userId);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone("555-123-4567"); // Valid phone format
        user.setRoleId(2); // Registered user
        user.setAccountStanding(1); // Good standing
        return user;
    }

    private List<UserAccount> createTestUsers() {
        List<UserAccount> users = new ArrayList<>();
        users.add(createTestUser(1L, "user1@test.com", "User", "One"));
        users.add(createTestUser(2L, "user2@test.com", "User", "Two"));
        users.add(createTestUser(3L, "user3@test.com", "User", "Three"));
        return users;
    }
}
