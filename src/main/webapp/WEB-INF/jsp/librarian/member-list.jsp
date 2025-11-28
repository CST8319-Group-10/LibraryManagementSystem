<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:include page="../common/header.jsp">
    <jsp:param name="title" value="Member List - Library Management System"/>
</jsp:include>

<h1>Member List</h1>

<jsp:include page="../common/messages.jsp"/>

<c:choose>
    <c:when test="${not empty members}">
        <table class="data-table">
            <thead>
                <tr>
                    <th>User ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Account Standing</th>
                    <th>Overdue Count</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${members}" var="member">
                    <tr>
                        <td>${member.userId}</td>
                        <td>${member.firstName} ${member.lastName}</td>
                        <td>${member.email}</td>
                        <td>${member.phone}</td>
                        <td>
                            <c:choose>
                                <c:when test="${member.accountStanding == 1}">
                                    <span class="badge badge-success">Good Standing</span>
                                </c:when>
                                <c:when test="${member.accountStanding == 2}">
                                    <span class="badge badge-danger">Suspended</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-warning">Unknown</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:set var="overdueCount" value="${overdueCountMap[member.userId]}"/>
                            <c:choose>
                                <c:when test="${overdueCount > 0}">
                                    <span style="color: #e74c3c; font-weight: bold;">${overdueCount}</span>
                                </c:when>
                                <c:otherwise>
                                    ${overdueCount}
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="actions">
                            <c:choose>
                                <c:when test="${member.accountStanding == 1}">
                                    <form method="post" action="${pageContext.request.contextPath}/librarian/members/deactivate"
                                          style="display: inline;"
                                          onsubmit="return confirm('Are you sure you want to suspend this member?');">
                                        <input type="hidden" name="id" value="${member.userId}">
                                        <button type="submit" class="btn btn-small btn-danger">Suspend</button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <form method="post" action="${pageContext.request.contextPath}/librarian/members/activate"
                                          style="display: inline;"
                                          onsubmit="return confirm('Are you sure you want to activate this member?');">
                                        <input type="hidden" name="id" value="${member.userId}">
                                        <button type="submit" class="btn btn-small btn-primary">Activate</button>
                                    </form>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <div class="no-data">
            <p>No members found.</p>
        </div>
    </c:otherwise>
</c:choose>

<div style="margin-top: 1.5rem;">
    <a href="${pageContext.request.contextPath}/librarian/dashboard" class="btn btn-secondary">Back to Dashboard</a>
</div>

<jsp:include page="../common/footer.jsp"/>
