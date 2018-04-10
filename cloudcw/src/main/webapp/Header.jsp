<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<html>
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>


<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
	integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
	integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
	crossorigin="anonymous"></script>

<!-- Datatables -->
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.16/css/dataTables.bootstrap4.min.css">
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/1.10.16/js/dataTables.bootstrap4.min.js"></script>

<!-- Google Sign-In for Websites  -->
<meta name="google-signin-scope" content="profile email">
<meta name="google-signin-client_id"
	content="629296123157-nt7agiuja56eqeqft89mmg154peog2ul.apps.googleusercontent.com">
<!--  gapi.auth2.init() -->

<script src="https://apis.google.com/js/platform.js" async defer></script>
</head>

<nav class="navbar navbar-expand-lg navbar-light bg-light"> <a
	class="navbar-brand" href="#">CloudCW</a>
<button class="navbar-toggler" type="button" data-toggle="collapse"
	data-target="#navbarSupportedContent"
	aria-controls="navbarSupportedContent" aria-expanded="false"
	aria-label="Toggle navigation">
	<span class="navbar-toggler-icon"></span>
</button>

<div class="collapse navbar-collapse" id="navbarSupportedContent">
	<ul class="navbar-nav mr-auto">
		<li class="nav-item active"><a class="nav-link" href="/items">Home
				<span class="sr-only">(current)</span>
		</a></li>
		<li class="nav-item active"><a class="nav-link"
			href="/trash">Trash</a></li>

		<li class="nav-item active dropdown"><a
			class="nav-link dropdown-toggle invisible" href="#"
			id="navbarDropdownNew" role="button" data-toggle="dropdown"
			aria-haspopup="true" aria-expanded="false"> New </a>
			<div class="dropdown-menu" aria-labelledby="navbarDropdown">
				<a class="dropdown-item" data-toggle="modal"
					data-target="#createFileModal">Upload File</a>
				<div class="dropdown-divider"></div>
				<a class="dropdown-item" data-toggle="modal"
					data-target="#createFolderModal">Create Folder</a>
			</div></li>
	</ul>
</div>
<!--  <form class="form-inline my-2 my-lg-0">
		<input class="form-control mr-sm-2" type="search" placeholder="Search"
			aria-label="Search">
		<button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
	</form>-->
<li class="nav-item"><a href="#" onclick="signOut();">Sign out</a></li>
</nav>
<br><br><br>

<script>
	function signOut() {
		var auth2 = gapi.auth2.getAuthInstance();
		auth2.signOut().then(function() {
			console.log('User signed out.');
		});
	}
</script>
</html>