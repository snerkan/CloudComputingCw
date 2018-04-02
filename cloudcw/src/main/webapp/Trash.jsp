
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
				<th>Size</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="file" items="${files}">
				<tr>
					<td>${file.name}</td>
					<td>${file.creationDate}</td>
					<td>-</td>
					<td>
						<div class="btn-group">
							<button type="button" class="btn btn-info dropdown-toggle"
								data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false">Actions</button>
							<div class="dropdown-menu" id="deleteDropdownButton"
								data-fileid="${file.id}">
								<a class="dropdown-item" data-toggle="modal"
									data-target="#deleteModal">Put Back</a> <a class="dropdown-item"
									data-toggle="modal" data-target="#deleteModal">Delete Permanently </a>
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
				<h5 class="modal-title" id="deleteModalTitle">Delete Permanently</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">Are you sure? You can't undo this action</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				<a href="" class="btn btn-primary" id="deleteButton">Delete</a>
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

	$('#deleteDropdownButton').click(
			function() {
				var fileId = $(this).data('fileid');
				$('#deleteModal').find('#deleteButton').attr('href',
						("/items/trash/deleteFile/" + fileId))
			});

	$('#MyModal').on('hidden.bs.modal', function() {
		$(this).find('form').trigger('reset');
	})
</script>
</html>
