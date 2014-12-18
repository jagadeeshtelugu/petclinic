<%@page import="java.io.File"%>
<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<html lang="en">

    <jsp:include page="../fragments/headTag.jsp"/>

<script type="text/javascript">
		$(document).ready(function() {
			
			$('#submit').click(function(event) {
	
				var multiSelect = $('select#specialities').val();
				$('select#specialities').find('option').removeAttr("selected");
				alert(multiSelect);
				$('select#specialities').append("<option selected='selected' class='hidden' value=" + multiSelect + ">added</option>");
			});
			
		});
    </script>

	<style>
		.hidden {
			visibility: hidden;
		}
		
	</style>


<body>
        <div class="container">
            <jsp:include page="../fragments/bodyHeader.jsp"/>
            <c:choose>
                <c:when test="${vet['new']}"><c:set var="method" value="post"/></c:when>
                <c:otherwise><c:set var="method" value="post"/></c:otherwise>
            </c:choose>

            <h2>
                <c:if test="${vet['new']}">New </c:if> Vet
                </h2>

            <form:form modelAttribute="vet" method="${method}" 
                       class="form-horizontal" id="add-vet-form"
                       enctype="multipart/form-data">

                <petclinic:inputField label="First Name" name="firstName"/>
                <petclinic:inputField label="Last Name" name="lastName"/>
                
                <div class="control-group ">
                	<label class="control-label">Speciality In :</label>
                <!--  	<form:select path="specialties" multiple="true" items="${specialities}" itemValue="id" itemLabel="name"/> -->
					
				<form:select path="specialties" multiple="true" id="specialities">
                    <form:options items="${specialities}" itemValue="id" itemLabel="name" />
                </form:select>
					
					<form:errors path="specialties" />
				</div>
				
                <div class="form-actions">
                    <c:choose>
                        <c:when test="${vet['new']}">
                            <button type="submit" id="submit">Add Vet</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" id="submit">Update Vet</button>
                        </c:otherwise>
                    </c:choose>
                </div>

            </form:form>
        </div>
        <jsp:include page="../fragments/footer.jsp"/>
    </body>

</html>
