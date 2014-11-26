<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<html lang="en">

    <jsp:include page="../fragments/headTag.jsp"/>

    <body>
        <div class="container">
            <jsp:include page="../fragments/bodyHeader.jsp"/>
            <c:choose>
                <c:when test="${user['new']}"><c:set var="method" value="post"/></c:when>
                <c:otherwise><c:set var="method" value="put"/></c:otherwise>
            </c:choose>

            <h2>
                <c:if test="${user['new']}">New </c:if> User
                </h2>
            <c:if test="${not empty userExit}">
                <div class="error"><h3><spring:message code="${userExit}" /></h3></div>
            </c:if>
            <form:form modelAttribute="user" method="${method}" class="form-horizontal">
                <petclinic:inputFieldCustom label="User Name" name="username"/>
                <br/>
                <petclinic:password label="Password" name="password"/>
                <br/>
                <petclinic:password label="Confirm Password" name="confirmPassword"/>
                <br/>
                <petclinic:inputFieldCustom label="First Name" name="firstName"/>
                <br/>
                <petclinic:inputFieldCustom label="Last Name" name="lastName"/>
                <br/>
                <petclinic:inputFieldCustom label="Birth Date" name="birthDate"/>
                <br/>
                <petclinic:inputFieldCustom label="Telephone" name="telephone"/>
                <br/>
                <petclinic:textArea label="Address" name="address"/>

                <div class="form-actions">
                    <c:choose>
                        <c:when test="${user['new']}">
                            <button type="submit">Add User</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit">Update User</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </form:form>
        </div>
        <jsp:include page="../fragments/footer.jsp"/>
    </body>

    <script>
        $(function() {
            $("#birthDate").datepicker({dateFormat: 'yy/mm/dd'});
        });
    </script>

</html>
