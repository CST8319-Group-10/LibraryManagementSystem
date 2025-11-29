package com.ac.cst8319.lms.dao.impl;

import com.ac.cst8319.lms.dao.AuditLogDAO;
import com.ac.cst8319.lms.model.AuditLog;
import com.ac.cst8319.lms.model.AuditLogEntry;
import com.ac.cst8319.lms.util.DatabaseConnection;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of AuditLogDAO.
 */
public class AuditLogDAOImpl implements AuditLogDAO {

    @Override
    public long insert(AuditLog auditLog) {
        String sql = "INSERT INTO AuditLog (UserID, ActionID, Details, IpAddress, CreatedAt) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setLong(1, auditLog.getUserId());
            stmt.setInt(2, auditLog.getActionId());
            stmt.setString(3, auditLog.getDetails());
            stmt.setString(4, auditLog.getIpAddress());
            stmt.setObject(5, auditLog.getCreatedAt() != null ? auditLog.getCreatedAt() : Instant.now());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Inserting audit log failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Inserting audit log failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting audit log", e);
        }
    }

    @Override
    public List<AuditLog> findRecent(int limit) {
        String sql = "SELECT * FROM AuditLog ORDER BY CreatedAt DESC LIMIT ?";
        List<AuditLog> logs = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapResultSetToAuditLog(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding recent audit logs", e);
        }
        return logs;
    }

    @Override
    public List<AuditLog> findAll(int limit, int offset) {
        String sql = "SELECT * FROM AuditLog ORDER BY CreatedAt DESC LIMIT ? OFFSET ?";
        List<AuditLog> logs = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapResultSetToAuditLog(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding all audit logs", e);
        }
        return logs;
    }

    @Override
    public List<AuditLog> findByUserId(long userId, int limit, int offset) {
        String sql = "SELECT * FROM AuditLog WHERE UserID = ? ORDER BY CreatedAt DESC LIMIT ? OFFSET ?";
        List<AuditLog> logs = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            stmt.setInt(2, limit);
            stmt.setInt(3, offset);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapResultSetToAuditLog(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding audit logs by user", e);
        }
        return logs;
    }

    @Override
    public List<AuditLog> findByActionId(int actionId, int limit, int offset) {
        String sql = "SELECT * FROM AuditLog WHERE ActionID = ? ORDER BY CreatedAt DESC LIMIT ? OFFSET ?";
        List<AuditLog> logs = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, actionId);
            stmt.setInt(2, limit);
            stmt.setInt(3, offset);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapResultSetToAuditLog(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding audit logs by action", e);
        }
        return logs;
    }

    @Override
    public List<AuditLog> findByDateRange(Instant startDate, Instant endDate, int limit, int offset) {
        String sql = "SELECT * FROM AuditLog WHERE CreatedAt BETWEEN ? AND ? " +
                     "ORDER BY CreatedAt DESC LIMIT ? OFFSET ?";
        List<AuditLog> logs = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, startDate);
            stmt.setObject(2, endDate);
            stmt.setInt(3, limit);
            stmt.setInt(4, offset);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    logs.add(mapResultSetToAuditLog(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding audit logs by date range", e);
        }
        return logs;
    }

    @Override
    public long count() {
        String sql = "SELECT COUNT(*) FROM AuditLog";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting audit logs", e);
        }
        return 0;
    }

    @Override
    public long countByUserId(long userId) {
        String sql = "SELECT COUNT(*) FROM AuditLog WHERE UserID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting audit logs by user", e);
        }
        return 0;
    }

    @Override
    public List<AuditLogEntry> findWithFilters(Long userId, Integer roleId, Integer actionId,
                                                Instant startDate, Instant endDate, int limit, int offset) {
        StringBuilder sql = new StringBuilder(
            "SELECT a.AuditEntryID, a.UserID, a.ActionID, a.Details, a.IpAddress, a.CreatedAt, " +
            "u.Email, u.FirstName, u.LastName, u.RoleID, " +
            "r.Name as RoleName, " +
            "act.Action, act.Description " +
            "FROM AuditLog a " +
            "LEFT JOIN UserAccount u ON a.UserID = u.UserID " +
            "LEFT JOIN Role r ON u.RoleID = r.RoleID " +
            "LEFT JOIN AuditLogAction act ON a.ActionID = act.AuditActionID " +
            "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (userId != null) {
            sql.append("AND a.UserID = ? ");
            params.add(userId);
        }

        if (roleId != null) {
            sql.append("AND u.RoleID = ? ");
            params.add(roleId);
        }

        if (actionId != null) {
            sql.append("AND a.ActionID = ? ");
            params.add(actionId);
        }

        if (startDate != null) {
            sql.append("AND a.CreatedAt >= ? ");
            params.add(startDate);
        }

        if (endDate != null) {
            sql.append("AND a.CreatedAt <= ? ");
            params.add(endDate);
        }

        sql.append("ORDER BY a.CreatedAt DESC LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        List<AuditLogEntry> entries = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    entries.add(mapResultSetToAuditLogEntry(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding audit logs with filters", e);
        }
        return entries;
    }

    @Override
    public long countWithFilters(Long userId, Integer roleId, Integer actionId,
                                  Instant startDate, Instant endDate) {
        StringBuilder sql = new StringBuilder(
            "SELECT COUNT(*) FROM AuditLog a " +
            "LEFT JOIN UserAccount u ON a.UserID = u.UserID " +
            "WHERE 1=1 "
        );

        List<Object> params = new ArrayList<>();

        if (userId != null) {
            sql.append("AND a.UserID = ? ");
            params.add(userId);
        }

        if (roleId != null) {
            sql.append("AND u.RoleID = ? ");
            params.add(roleId);
        }

        if (actionId != null) {
            sql.append("AND a.ActionID = ? ");
            params.add(actionId);
        }

        if (startDate != null) {
            sql.append("AND a.CreatedAt >= ? ");
            params.add(startDate);
        }

        if (endDate != null) {
            sql.append("AND a.CreatedAt <= ? ");
            params.add(endDate);
        }

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                stmt.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting audit logs with filters", e);
        }
        return 0;
    }

    /**
     * Helper method to map ResultSet to AuditLog object.
     */
    private AuditLog mapResultSetToAuditLog(ResultSet rs) throws SQLException {
        AuditLog log = new AuditLog();
        log.setAuditEntryId(rs.getLong("AuditEntryID"));
        log.setUserId(rs.getLong("UserID"));
        log.setActionId(rs.getInt("ActionID"));
        log.setDetails(rs.getString("Details"));
        log.setIpAddress(rs.getString("IpAddress"));

        Timestamp createdAt = rs.getTimestamp("CreatedAt");
        if (createdAt != null) {
            log.setCreatedAt(createdAt.toInstant());
        }

        return log;
    }

    /**
     * Helper method to map ResultSet to AuditLogEntry object with JOINed data.
     */
    private AuditLogEntry mapResultSetToAuditLogEntry(ResultSet rs) throws SQLException {
        AuditLogEntry entry = new AuditLogEntry();

        // AuditLog fields
        entry.setAuditEntryId(rs.getLong("AuditEntryID"));
        entry.setUserId(rs.getLong("UserID"));
        entry.setActionId(rs.getInt("ActionID"));
        entry.setDetails(rs.getString("Details"));
        entry.setIpAddress(rs.getString("IpAddress"));

        Timestamp createdAt = rs.getTimestamp("CreatedAt");
        if (createdAt != null) {
            entry.setCreatedAt(createdAt.toInstant());
        }

        // UserAccount fields
        entry.setUserEmail(rs.getString("Email"));
        entry.setUserFirstName(rs.getString("FirstName"));
        entry.setUserLastName(rs.getString("LastName"));
        entry.setRoleId(rs.getInt("RoleID"));

        // Role field
        entry.setRoleName(rs.getString("RoleName"));

        // AuditLogAction fields
        entry.setActionName(rs.getString("Action"));
        entry.setActionDescription(rs.getString("Description"));

        return entry;
    }
}
