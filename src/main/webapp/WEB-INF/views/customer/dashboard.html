<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container">
            <a class="navbar-brand" href="#">Cleaning Service</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="<c:url value='/customer/dashboard'/>">Dashboard</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/customer/services'/>">Book Service</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/customer/bookings'/>">My Bookings</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/customer/profile'/>">My Profile</a>
                    </li>
                </ul>
                <div class="d-flex">
                    <span class="navbar-text me-3">
                        ${user.email}
                    </span>
                    <form action="<c:url value='/auth/logout'/>" method="post">
                        <button class="btn btn-outline-light" type="submit">Logout</button>
                    </form>
                </div>
            </div>
        </div>
    </nav>

    <div class="container mt-4">
        <h2>Welcome, ${user.email}!</h2>
        
        <div class="row mt-4">
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Active Bookings</h5>
                        <p class="card-text">${activeBookingsCount}</p>
                        <a href="<c:url value='/customer/bookings'/>" class="btn btn-primary">View Bookings</a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Available Services</h5>
                        <p class="card-text">${availableServicesCount}</p>
                        <a href="<c:url value='/customer/services'/>" class="btn btn-primary">Book a Service</a>
                    </div>
                </div>
            </div>
            
            <div class="col-md-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">Completed Services</h5>
                        <p class="card-text">${completedBookingsCount}</p>
                        <a href="<c:url value='/customer/bookings?status=completed'/>" class="btn btn-primary">View History</a>
                    </div>
                </div>
            </div>
        </div>

        <div class="row mt-4">
            <div class="col-12">
                <h3>Recent Bookings</h3>
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Booking ID</th>
                                <th>Date</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${empty recentBookings}">
                                    <tr>
                                        <td colspan="4" class="text-center">No recent bookings</td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${recentBookings}" var="booking">
                                        <tr>
                                            <td>${booking.bookingId}</td>
                                            <td><fmt:formatDate value="${booking.bookingDate}" pattern="dd-MM-yyyy"/></td>
                                            <td>${booking.status.statusName}</td>
                                            <td>
                                                <a href="<c:url value='/customer/bookings/${booking.bookingId}'/>" 
                                                   class="btn btn-sm btn-info">View</a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>