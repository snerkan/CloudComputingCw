
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
				<th>Type</th>
				<th>Name</th>
				<th>Creation Date</th>
				<th>Flag</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="folder" items="${folders}">
				<tr>
					<td>Folder</td>
					<td><a href="/items/folder/${folder.id}" id="folderButton">${folder.name}</a></td>
					<td>${folder.creationDate}</td>
					<td><div class="btn-group">
							<button type="button" class="btn  btn-outline-info dropdown-toggle"
								data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false">${folder.flag}</button>
							<div class="dropdown-menu" id="flagDropdownButton">
								<c:forEach var="flag" items="${flags}">
									<a class="dropdown-item" href="/items/changeFolderFlag/${folder.id}/${flag}">${flag} </a>
								</c:forEach>
							</div>
						</div></td>
					<td>
						<div class="btn-group">
							<button type="button" class="btn btn-info dropdown-toggle"
								data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false">Actions</button>
							<div class="dropdown-menu" id="deleteDropdownButton"
								data-fileid="${folder.id}">
								<!--  	<a class="dropdown-item" href="#">Download</a> <a
									class="dropdown-item" href="#">Share</a>
									-->
								<a class="dropdown-item" data-toggle="modal"
									data-target="#deleteModal"
									data-fileid="/items/deleteFolder/${folder.id}">Delete</a> <a
									class="dropdown-item" data-toggle="modal"
									data-target="#renameModal"
									data-fileid="/items/renameFolder/${folder.id}"
									data-oldName="${folder.name}">Rename</a>
								<!-- 
									<div class="dropdown-divider">Move</div>
								<a class="dropdown-item" href="#">Flag</a> -->
							</div>
						</div>
					</td>
				</tr>
			</c:forEach>

			<c:forEach var="file" items="${files}">
				<tr>
					<td>File</td>
					<td>${file.name}</td>
					<td>${file.creationDate}</td>
					<td><div class="btn-group">
							<button type="button" class="btn  btn-outline-info dropdown-toggle"
								data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false">${file.flag}</button>
							<div class="dropdown-menu" id="flagDropdownButton">
								<c:forEach var="flag" items="${flags}">
									<a class="dropdown-item" href="/items/changeFileFlag/${file.id}/${flag}">${flag}</a>
								</c:forEach>
							</div>
						</div>
					</td>
					<td>
						<div class="btn-group">
							<button type="button" class="btn btn-info dropdown-toggle"
								data-toggle="dropdown" aria-haspopup="true"
								aria-expanded="false">Actions</button>
							<div class="dropdown-menu" id="deleteDropdownButton"
								data-fileid="${file.id}">
								<a class="dropdown-item" href="${file.storageAddress}">Download</a>
								<a class="dropdown-item" data-toggle="modal"
									data-target="#shareModal" data-fileid="${file.storageAddress}">Share</a>
								<a class="dropdown-item" data-toggle="modal"
									data-target="#deleteModal"
									data-fileid="/items/deleteFile/${file.id}">Delete</a> <a
									class="dropdown-item" data-toggle="modal"
									data-target="#renameModal"
									data-fileid="/items/renameFile/${file.id}"
									data-oldName="${file.name}">Rename</a>
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




<!-- Rename Modal -->
<div class="modal fade" id="renameModal" tabindex="-1" role="dialog">
	<div class="modal-dialog modal-dialog-centered" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="renameModalTitle">Rename Folder</h5>
				<button type="Reset" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<form action="" method="post" id="renameForm">
					<div class="form-group">
						<label class="col-form-label">Please, enter a new name:</label> <input
							type="text" class="form-control" id="newName" name="newName">
						<div class="modal-footer">
							<button type="Reset" class="btn btn-secondary"
								data-dismiss="modal">Close</button>
							<button type="submit" class="btn btn-primary" value="Submit">Rename</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>




<!-- Share Modal -->
<div class="modal fade" id="shareModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="shareModalLabel">Share the Link!</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" id="shareModelBody"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
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

				<form action="/items/createFile/${folderId}" method="post"
					enctype="multipart/form-data">
					<div class="form-group">
						<label class="col-form-label">Please, choose your file:</label> <input
							type="file" class="form-control-file" name="file" id="file" />
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
							name="parentId" value='${folderId}'> <input type="text"
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

	$(document).ready(function() {
		$('input:file').change(function() {
			if ($(this).val()) {
				$('#fileSubmit').attr('disabled', false);
				// $('input:submit').removeAttr('disabled'); 
			}
		});
	});

	$(document).ready(function() {
		$("#navbarDropdownNew").removeClass('invisible')

	});

	$('#deleteModal').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget)
		$(this).find('#deleteButton').attr('href', button.data('fileid'))
	});

	$('#renameModal').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget)
		$(this).find('#renameForm').attr('action', button.data('fileid'))
		var oldName = button.data('oldname').split(".")
		$(this).find('#newName').val(oldName[0])
	});

	$('#shareModal').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget)
		var link = " " + button.data('fileid')
		$(this).find('#shareModelBody').val(link)
		//$(this).find('#shareModelBody').append(link)
	});

	$('.modal').on('hidden.bs.modal', function() {
		$(this).find('form').trigger('reset');
		$(this).find('.modal-body').trigger('reset');
	})
</script>
</html>
