
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">

<jsp:include page="Header.jsp"></jsp:include>

<div class="container">
	<table id="contentTable" class="table" style="width: 100%">
		<thead>
			<tr>
				<th>Name</th>
				<th>Creation Date</th>
				<th><a href="/trash/deleteAllItems/" data-toggle="modal"
					data-target="#deleteAllModal" class="btn btn-light"
					id="deleteButton">Delete All</a></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="folder" items="${folders}">
				<tr>
					<td>${folder.name}</td>
					<td>${folder.creationDate}</td>
					<td>
						<div class="btn-group">
							<button type="button" class="btn btn-info dropdown-toggle"
								data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false">Actions</button>
							<div class="dropdown-menu">
								<a class="dropdown-item" data-toggle="modal"
									data-target="#deleteModal"
									data-fileid="/trash/deleteFolder/${folder.id}">Delete</a> <a
									href="/trash/putBackFolder/${folder.id}" class="dropdown-item"
									id="deleteButton">Put Back</a>
							</div>
						</div>
					</td>
				</tr>
			</c:forEach>

			<c:forEach var="file" items="${files}">
				<tr>
					<td>${file.name}</td>
					<td>${file.creationDate}</td>
					<td>
						<div class="btn-group">
							<button type="button" class="btn btn-info dropdown-toggle"
								data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false">Actions</button>
							<div class="dropdown-menu">
								<a class="dropdown-item" data-toggle="modal"
									data-target="#deleteModal"
									data-fileid="/trash/deleteFile/${file.id}">Delete</a> <a
									href="/trash/putBackFile/${file.id}" class="dropdown-item"
									id="deleteButton">Put Back</a>
							</div>
						</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>



<!-- Delete Item -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="deleteModalTitle">Delete
					Permanently</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">Are you sure? You can't undo this
				action</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				<a href="" class="btn btn-primary" id="deleteButton">Delete</a>
			</div>
		</div>
	</div>
</div>



<!-- Delete All Item -->
<div class="modal fade" id="deleteAllModal" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="deleteAllModalTitle">Delete All
					Permanently</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">Are you sure? You can't undo this
				action</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				<a href="/trash/deleteAllItems/" class="btn btn-primary"
					id="deleteButton">Delete All</a>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		$('#contentTable').DataTable({
			"sDom" : '<"bottom"flp> ',
			paging : false,
			scrollY : 400,
			ordering : true,
			colReorder : true
		});

	});

	$('#deleteModal').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget)
		$(this).find('#deleteButton').attr('href', button.data('fileid'))
	});
</script>
</html>
