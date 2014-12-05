<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<jsp:include page="../fragments/headTag.jsp" />

<head>
<script type="text/javascript">
	$(document).ready(function() {
		$('#changeSearch').click(function(event) {
			var text = $("#changeSearch").text();

			if (text == 'Adv Search') {
				$("#changeSearch").text('Simple Search');
				$('#simpleSearchForm').hide();
				$('#advSearchForm').show();
			} else {

				$("#changeSearch").text('Adv Search');
				$('#simpleSearchForm').show();
				$('#advSearchForm').hide();
			}

		});

		$(function() {
			$("#fromBirthDate").datepicker({
				dateFormat : 'yy/mm/dd'
			});
			$("#toBirthDate").datepicker({
				dateFormat : 'yy/mm/dd'
			});
		});

	});
</script>
</head>

<body>
	<div class="container">
		<jsp:include page="../fragments/bodyHeader.jsp" />

		<h2>Find Owners</h2>
		<a id="changeSearch" href="#">Adv Search</a>

		<spring:url value="/owners.html" var="formUrl" />

		<div id="simpleSearchForm">
			<form:form modelAttribute="owner" action="${fn:escapeXml(formUrl)}"
				method="get" class="form-horizontal" id="search-owner-form">
				<fieldset>
					<div class="control-group" id="lastName">
						<label class="control-label">Last name </label>
						<form:input path="lastName" size="30" maxlength="80" />
						<span class="help-inline"><form:errors path="*" /></span>
					</div>
					<div class="form-actions">
						<button type="submit">Find Owner</button>
					</div>
				</fieldset>
			</form:form>
		</div>

		<div id="advSearchForm" hidden="true">
			<spring:url value="/owners/ownersByCriteria.html" var="advFormUrl" />

			<form:form modelAttribute="owner"
				action="${fn:escapeXml(advFormUrl)}" method="get"
				class="form-horizontal" id="adv-search-owner-form">

				<fieldset>

					<div class="control-group" id="firstName">
						<label class="control-label">First name </label>
						<form:input path="firstName" size="30" maxlength="80" />
						<span class="help-inline"><form:errors path="*" /></span>
					</div>

					<div class="control-group" id="lastName">
						<label class="control-label">Last name </label>
						<form:input path="lastName" size="30" maxlength="80" />
						<span class="help-inline"><form:errors path="*" /></span>
					</div>

					<div class="control-group" id="petName">
						<label class="control-label">Pet name </label>


						<form:input path="pets[0].name" size="30" maxlength="80" />


					</div>

					<!--  Pet DOB from-to -->
					<div class="control-group" id="petDobFrom">
						<label class="control-label">From Birth Date(Pet) </label>
						<form:input path="pets[0].fromBirthDate" id="fromBirthDate"
							size="30" maxlength="80" />
					</div>
					<div class="control-group" id="petDobTo">
						<label class="control-label">To Birth Date(Pet) </label>
						<form:input path="pets[0].toBirthDate" id="toBirthDate" size="30"
							maxlength="80" />
					</div>
					<!--  Pet DOB from-to -->

					<div class="form-actions">
						<button type="submit">Find Owner</button>
					</div>
				</fieldset>
			</form:form>

		</div>

		<br /> <a href='<spring:url value="/owners/new" htmlEscape="true"/>'>Add
			Owner</a>

		<jsp:include page="../fragments/footer.jsp" />

	</div>
</body>

</html>
