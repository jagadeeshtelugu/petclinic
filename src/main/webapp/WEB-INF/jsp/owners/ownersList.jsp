<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="datatables"
	uri="http://github.com/dandelion/datatables"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en">

<jsp:include page="../fragments/headTag.jsp" />

<head>

<script type="text/javascript">

        $(document).ready(function(){
        	$('.img_upload').click(function(event) {
        		if($("#form_" + event.target.id).is(":visible") ){
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
					<form:form action="owners/processUploadImage" method="post" modelAttribute="uploadImage"  enctype="multipart/form-data">
						<input name="file" type="file" />
						<input name="ownerID" type="hidden" value="${owner.id}"/>
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
