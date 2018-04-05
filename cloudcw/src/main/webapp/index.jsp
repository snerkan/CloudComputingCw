<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<jsp:include page="Header.jsp"></jsp:include>
<head>
<!-- Google Sign-In for Websites  -->
<meta name="google-signin-scope" content="profile email">
<meta name="google-signin-client_id"
	content="629296123157-k3ronshj46oauuvlss5hd1agig4vilrq.apps.googleusercontent.com">
<!--  gapi.auth2.init() -->
</head>
<body>

	<div class="g-signin2" data-onsuccess="onSignIn" data-theme="dark"></div>
	<script>
		function onSignIn(googleUser) {
			// Useful data for your client-side scripts:
			var profile = googleUser.getBasicProfile();
			console.log("ID: " + profile.getId()); // Don't send this directly to your server!
			console.log("Email: " + profile.getEmail());

			// The ID token you need to pass to your backend:
			var id_token = googleUser.getAuthResponse().id_token;	
			console.log("IDToken: " + id_token);

			var xhr = new XMLHttpRequest();
			xhr.open('POST', '/index/usersignin');
			xhr.setRequestHeader('Content-Type',
					'application/x-www-form-urlencoded');
			xhr.onload = function() {
				console.log('Signed in as: ' + xhr.responseText);
			};
			xhr.send('idtoken=' + id_token);
		};
	</script>
</body>



</html>
