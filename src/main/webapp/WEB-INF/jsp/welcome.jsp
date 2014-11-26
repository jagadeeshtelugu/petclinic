<!DOCTYPE html> 

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html lang="en">

    <jsp:include page="fragments/headTag.jsp"/>

    <body>
        <div class="container" style="margin-left: 25%;">
            <jsp:include page="fragments/bodyHeader.jsp"/>
            <div style="float: left;" class="login-div">
                <form:form modelAttribute="login" method="put" id="loginForm" 
                           cssStyle="margin-left: 40px;margin-top: 20px;">
                    <div>
                        <table>
                            <th colspan="2"><spring:message code="user.login"/></th>
                            <tr>
                                <td class="lbl"><spring:message code="username"/></td>
                                <td>
                                    <form:input path="username" id="uname"/><br/>
                                    <form:errors path="username" cssClass="error" />
                                </td>
                            </tr>
                            <tr>
                                <td class="lbl"><spring:message code="password"/></td>
                                <td>
                                    <form:password path="password" id="pwd"/><br/>
                                    <form:errors path="password" cssClass="error" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input style="float: right; width: 220px;" 
                                           type="submit" value=<spring:message code="login"/>>
                                    <br/>
                                    <span class="error" style="float: right;">${invalidUser}</span>
                                </td>
                            </tr>
                        </table>
                    </div>
                </form:form>
            </div>
            <br/>
            

            <div style="margin-left: 60%;">
                <h2><fmt:message key="welcome"/></h2>
                <spring:url value="/resources/images/pets.png" htmlEscape="true" var="petsImage"/>
                <img src="${petsImage}"/>
            </div>
            
            <div style="margin-left: 130px;">
                <a href='<spring:url value="/users/new" htmlEscape="true"/>'>New User ? Register</a></div>

            <%--<jsp:include page="fragments/footer.jsp"/>--%>
            <a id="en_lang" href="?lang=en" ></a>
            <a id="de_lang" href="?lang=de"></a>
            <%--Current Locale : ${pageContext.response.locale}--%>

        </div>
    </body>

</html>
