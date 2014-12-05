<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables"
	uri="http://github.com/dandelion/datatables"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>

<html lang="en">

<jsp:include page="../fragments/headTag.jsp" />

<head>

<style>
#filter li {
	color: red;
	list-style-position: inside;
	border-right: 1px solid;
	border-right-color: black;
	background: #F8F8F8;
	width: 200px;
	height: 25px;
	padding-left: 3px;
}
</style>

<script src="http://malsup.github.com/jquery.form.js"></script>

<script type="text/javascript">
	$(document).ready(function() {

		//$("#ownerUpload").ajaxForm({
		//	success : function(html) {
		//		alert(html);
		//		$(".container").replaceWith(html);
		//	}
		//});

		$('.img_upload').click(function(event) {
			if ($("#form_" + event.target.id).is(":visible")) {
				$("#form_" + event.target.id).hide();
			} else {
				$("#form_" + event.target.id).show();
			}
		});

	});
</script>
</head>


<body>


	<div class="container">
		<jsp:include page="../fragments/bodyHeader.jsp" />
		<h2>Owners</h2>


		<div id="filter" class="navbar">

			<spring:url value="/resources/images/delete.png" var="delete" />

			<c:set var="owner" value="${sessionScope.owner}"></c:set>
			<c:set var="pet" value="${owner.pets[0]}"></c:set>

			<c:set var="firstName" value="${owner.firstName}"></c:set>
			<c:set var="lastName" value="${owner.lastName}"></c:set>
			<c:set var="petName" value="${pet.name}"></c:set>

			<spring:url value="/owners/removeFilter/{filter}.html"
				var="removeFirstName">
				<spring:param name="filter" value="firstName" />
			</spring:url>
			<spring:url value="/owners/removeFilter/{filter}.html"
				var="removeLastName">
				<spring:param name="filter" value="lastName" />
			</spring:url>
			<spring:url value="/owners/removeFilter/{filter}.html"
				var="removePetName">
				<spring:param name="filter" value="petName" />
			</spring:url>
			<spring:url value="/owners/removeFilter/{filter}.html"
				var="removeBirthDate">
				<spring:param name="filter" value="birthDate" />
			</spring:url>
			<spring:url value="/owners/removeFilter/{filter}.html"
				var="removeBirthDate">
				<spring:param name="filter" value="birthDate" />
			</spring:url>



			<ul class="nav" style="float: inside;">
				<li><b>Filters - </b></li>

				<c:if test="${not empty firstName}">
					<li>FirstName: <c:out value="${firstName}" /><i><a
							href="${removeFirstName}"> <img src="${delete}"
								id="dFirstName" alt="delete filter" /></a></i>
					</li>
				</c:if>

				<c:if test="${not empty lastName}">
					<li>LastName: <c:out value="${lastName}" /> <i><a
							href="${removeLastName}"><img src="${delete}" id="dLirstName"
								alt="delete filter" /></a></i>
					</li>
				</c:if>

				<c:if test="${not empty petName}">
					<li>PetName: <c:out value="${petName}" /><i><a
							href="${removePetName}"><img src="${delete}" id="dPetName"
								alt="delete filter" /></a></i>
					</li>
				</c:if>

				<c:if test="${not empty pet.fromBirthDate}">
					<li>FromBirthDate: <joda:format value="${pet.fromBirthDate}"
							pattern="yyyy-MM-dd" /><i><a href="${removeBirthDate}"><img
								src="${delete}" id="dfbd" alt="delete filter" /></a></i>
					</li>
				</c:if>

				<c:if test="${not empty pet.toBirthDate}">
					<li>ToBirthDate: <joda:format value="${pet.toBirthDate}"
							pattern="yyyy-MM-dd" /><i><a href="${removeBirthDate}"><img
								src="${delete}" id="dtbd" alt="delete filter" /></a></i>
					</li>
				</c:if>
			</ul>

		</div>


		<datatables:table id="owners" data="${selections}" cdn="true"
			row="owner" theme="bootstrap2" cssClass="table table-striped"
			paginate="false" info="false" export="pdf">


			<datatables:column title="" cssStyle="width: 70px;" display="html">
				<c:set var="ownerImagesList" value="${owner['ownerImage']}" />
				<spring:url
					value="/resources/ownerImages/${ownerImagesList[0].imageName}"
					var="ownerImageUrl" />
				<img src="${ownerImageUrl}" style="width: 60px; height: 60px;"
					class="img_upload" id="${owner.id}" />

				<div hidden="true" id="form_${owner.id}">
					<spring:url value="/processUploadImage.html" var="uploadUrl" />
					<form:form id="ownerUpload" action="${fn:escapeXml(uploadUrl)}"
						method="post" modelAttribute="uploadImage"
						enctype="multipart/form-data">
						<input name="file" type="file" />
						<input name="ownerID" type="hidden" value="${owner.id}" />
						<input type="submit" value="upload" />
					</form:form>
				</div>
			</datatables:column>

			<datatables:column title="Name" cssStyle="width: 150px;"
				display="html">
				<spring:url value="/owners/{ownerId}.html" var="ownerUrl">
					<spring:param name="ownerId" value="${owner.id}" />
				</spring:url>
				<a href="${fn:escapeXml(ownerUrl)}"><c:out
						value="${owner.firstName} ${owner.lastName}" /></a>
			</datatables:column>

			<datatables:column title="Name" display="pdf">
				<c:out value="${owner.firstName} ${owner.lastName}" />
			</datatables:column>
			<datatables:column title="Address" property="address"
				cssStyle="width: 200px;" />
			<datatables:column title="City" property="city" />
			<datatables:column title="Telephone" property="telephone" />
			<datatables:column title="Pets" cssStyle="width: 100px;">
				<c:forEach var="pet" items="${owner.pets}">
					<c:out value="${pet.name}" />
				</c:forEach>
			</datatables:column>
			<datatables:export type="pdf" cssClass="btn btn-small" />
		</datatables:table>

		<jsp:include page="../fragments/footer.jsp" />

	</div>
</body>



</html>
