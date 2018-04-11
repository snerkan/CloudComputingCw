<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security"
  uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<jsp:include page="Header.jsp"></jsp:include>

<body>

	<br>
	<br>
	<br>
	<div class="row">
		<div class="col-sm-3"></div>
		<div class="col-sm-6">
			<div class="card text-center border-info mb-3">
				<div class="card-header ">WELCOME!</div>
				<div class="card-body">
					<security:authorize access="isAuthenticated()">
						<p class="card-title">You have already signed up!</p>
						<a class="btn btn-outline-info" href="/items">Home Page</a>
					</security:authorize>
					<security:authorize access="isAnonymous()">
						<p class="card-title">You need to sign in</p>
						<c:forEach var="url" items="${urls}">
							<a class="btn btn-outline-info" href="${url.value}">Sign in
								with Google</a>
						</c:forEach>
					</security:authorize>
				</div>
				<div class="card-footer text-muted"></div>
			</div>
		</div>
		<div class="col-sm-3"></div>
	</div>
</body>

</html>
