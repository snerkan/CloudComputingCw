
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
			<c:forEach var="folder" items="${folders}">
				<tr>
					<td>${folder.name}</td>
					<td>${folder.creationDate}</td>
					<td>-</td>
					<td>
						<div class="btn-group">
							<button type="button" class="btn btn-info dropdown-toggle"
								data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false">Actions</button>
							<div class="dropdown-menu" id="deleteDropdownButton"
								data-fileid="${folder.id}">
								<a class="dropdown-item" href="#">Download</a> <a
									class="dropdown-item" href="#">Share</a> <a
									class="dropdown-item" data-toggle="modal"
									data-target="#deleteModal"
									data-fileid="/items/deleteFolder/${folder.id}">Delete</a> <a
									class="dropdown-item" data-toggle="modal"
									data-target="#renameModal"
									data-fileid="/items/renameFolder/${folder.id}">Rename</a>
								<div class="dropdown-divider">Move</div>
								<a class="dropdown-item" href="#">Flag</a>
							</div>
						</div>
					</td>
				</tr>
			</c:forEach>

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
								<a class="dropdown-item" href="#">Download</a> <a
									class="dropdown-item" href="#">Share</a> <a
									class="dropdown-item" data-toggle="modal"
									data-target="#deleteModal"
									data-fileid="/items/deleteFile/${file.id}">Delete</a> <a
									class="dropdown-item" data-toggle="modal"
									data-target="#renameModal"
									data-fileid="/items/renameFile/${file.id}">Rename</a>
								<div class="dropdown-divider">Move</div>
								<a class="dropdown-item" href="#">Flag</a>
							</div>
						</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>



<!-- Rename Item -->
<div class="modal fade" id="renameModal" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="renameModalTitle">Rename</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form>
					<div class="form-group">
						<label class="col-form-label">Please, enter a new name:</label> <input
							type="hidden" class="form-control" id="newName" value=${file.id}>
						<input type="text" class="form-control" id="newName"
							value=${file.name}>
					</div>
				</form>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
				<button type="button" class="btn btn-primary">Save changes</button>
			</div>
		</div>
	</div>
</div>



<!-- Create File -->
<div class="modal fade" id="createFileModal" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="createFileModalTitle">Upload a File</h5>
				<button type="Reset" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form action="/items/createFile" method="post"
					enctype="multipart/form-data">
					<div class="form-group">
						<label class="col-form-label">Please, choose your file</label> <input
							type="file" class="form-control-file" name="file" id="file"
							class="form-control" /> <!--  <input type="hidden" name="parentId"
							value=0 />-->
						<div class="modal-footer">
							<button type="Reset" class="btn btn-secondary"
								data-dismiss="modal">Close</button>
							<button type="submit" id="fileSubmit" class="btn btn-primary"
								value="Submit" disabled>Upload</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>



<!-- Create Folder -->
<div class="modal fade" id="createFolderModal" tabindex="-1"
	role="dialog">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="createFolderModalTitle">Create a
					Folder</h5>
				<button type="Reset" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form action="/items/createFolder" method="post">
					<div class="form-group">
						<label class="col-form-label">Please, enter a folder name:</label>
						<input type="hidden" class="form-control" id="parentId"
							name="parentId" value="0"> <input type="text"
							class="form-control" id="name" name="name" value="New Folder">
						<div class="modal-footer">
							<button type="Reset" class="btn btn-secondary"
								data-dismiss="modal">Close</button>
							<button type="submit" class="btn btn-primary" value="Submit">Create</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>



<!-- Delete Item -->
<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="deleteModalTitle">Delete</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">Are you sure?</div>
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
	
	$(document).ready(function(){
		        $('input:file').change(
		            function(){
		                if ($(this).val()) {
		                    $('#fileSubmit').attr('disabled',false);   
		                    // $('input:submit').removeAttr('disabled'); 
		                }});    }); 


	$('#deleteModal').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget)
		$(this).find('#deleteButton').attr('href', button.data('fileid'))
	})

	$('.modal').on('hidden.bs.modal', function() {
		$(this).find('form').trigger('reset');
	})
</script>
</html>
