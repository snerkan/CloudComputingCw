<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<jsp:include page="Header.jsp"></jsp:include>


<div class="container">
	<div class="row">
		<div class="col">1 of 3</div>
		<div class="col-6">2 of 3 (wider)</div>
		<div class="col">3 of 3</div>
	</div>
</div>


<div class="container">
	<table class="table">
		<thead>
			<tr>
				<th scope="col">Name</th>
				<th scope="col">Creation Date</th>
				<th scope="col">Size</th>
				<th scope="col"></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<th scope="row">-</th>
				<td>-</td>
				<td>-</td>
				<td>
					<div class="row">
						<div class="col-3">
							<button type="button" class="btn btn-light">Download</button>
						</div>
						<div class="col-3">
							<button type="button" class="btn btn-light">Share</button>
						</div>
						<div class="col-3">
							<button type="button" class="btn btn-light">Delete</button>
						</div>
						<div class="col-3">
							<div class="btn-group">
								<button type="button" class="btn btn-info dropdown-toggle"
									data-toggle="dropdown" aria-haspopup="true"
									aria-expanded="false">Actions</button>
								<div class="dropdown-menu">
									<a class="dropdown-item" href="#">Action</a> <a
										class="dropdown-item" href="#">Another action</a> <a
										class="dropdown-item" href="#">Something else here</a>
									<div class="dropdown-divider"></div>
									<a class="dropdown-item" href="#">Separated link</a>
								</div>
							</div>
						</div>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<script type="text/javascript">
	$('.dropdown-toggle').dropdown();
	$('#myDropdown').on('show.bs.dropdown', function() {
		// do something…
	});
</script>
</html>
