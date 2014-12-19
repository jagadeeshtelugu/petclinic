<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>

<html lang="en">


<jsp:include page="../fragments/headTag.jsp"/>

<body>

<spring:url value="/resources/images/delete.png" var="delete" />
<spring:url value="/resources/images/delete-small.png" var="delete_small" />
<spring:url value="/resources/images/edit.png" var="edit" />

<div class="container">
    <jsp:include page="../fragments/bodyHeader.jsp"/>

    <h2>Veterinarians</h2>

    <datatables:table id="vets" data="${vets.vetList}" cdn="true" row="vet" theme="bootstrap2" cssClass="table table-striped" paginate="false" info="false">
        <datatables:column title="Name">
        
        	<spring:url value="/vet/${vet.id}/delete" var="deleteVet" />
        	<a href="${deleteVet}"><img alt="delete" src="${delete}" /></a>
        	
        	<spring:url value="/vet/${vet.id}/edit" var="editVet" />
        	<a href="${editVet}"><img alt="edit" src="${edit}" /></a>
        	&nbsp;&nbsp;
        	
            <c:out value="${vet.firstName} ${vet.lastName}"></c:out>
        </datatables:column>
        
        <datatables:column title="Specialties">
            <c:forEach var="specialty" items="${vet.specialties}">
            
            	<!-- Add delete functionality -->
            	<spring:url value="/vet/specialty/${vet.id}/${specialty.id}/delete" var="deleteSpecialty" />
            	<a href="${deleteSpecialty}"><img src="${delete_small}" alt="delete_specialty" /></a>
            
                <c:out value="${specialty.name}"/>
     
            </c:forEach>
            <c:if test="${vet.nrOfSpecialties == 0}">none</c:if>
        </datatables:column>
        
    </datatables:table>
    
    	<br /> 
	<a href='<spring:url value="/vets/new" htmlEscape="true"/>'>Add	Vet</a>
    
    <table class="table-buttons">
        <tr>
            <td>
                <a href="<spring:url value="/vets.xml" htmlEscape="true" />">View as XML</a>
            </td>
            <td>
                <a href="<spring:url value="/vets.atom" htmlEscape="true" />">Subscribe to Atom feed</a>
            </td>
        </tr>
    </table>

		<jsp:include page="../fragments/footer.jsp"/>
</div>
</body>

</html>
