<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>

<html lang="en">


<jsp:include page="../fragments/headTag.jsp"/>

<body>
<div class="container">
    <jsp:include page="../fragments/bodyHeader.jsp"/>

    <h2>All Specialty</h2>

    <datatables:table id="specialty" data="${allSpecialty}" cdn="true" row="specialty" theme="bootstrap2" cssClass="table table-striped" paginate="false" info="false">
        <datatables:column title="Specialty Type">
            <c:out value="${specialty.name}"></c:out>
        </datatables:column>
        
        <datatables:column title="Vet">
            <c:forEach var="vet" items="${specialty.vet}">
            
                <c:out value="${vet.firstName} ${vet.lastName},"/>
     
            </c:forEach>
            <c:if test="${vet.nrOfSpecialties == 0}">none</c:if>
        </datatables:column>
        
    </datatables:table>

	<jsp:include page="../fragments/footer.jsp"/>
</div>
</body>

</html>
