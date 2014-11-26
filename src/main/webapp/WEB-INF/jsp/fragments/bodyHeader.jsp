
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<spring:url value="/resources/images/banner-graphic.png" var="banner"/>
<img src="${banner}" style="width: 700px; height: 75px;"/>
<div class="navbar" style="width: 700px;">


    <div class="navbar-inner">

        <ul class="nav" style="float: inside;">

            <li style="width: 90px;">
                <a href="<spring:url value="/" htmlEscape="true" />"><i class="icon-home"></i>
                    <spring:message code="message.home" /></a>
            </li>

            <c:choose>
                <c:when test="${not empty sessionScope.users}" >
                    <c:set var="findOwner" value="/owners/find.html"/>
                </c:when>
                <c:otherwise><c:set var="findOwner" value="/"/></c:otherwise>
            </c:choose>

            <li style="width: 130px;">
                <a href="<spring:url value="${findOwner}" htmlEscape="true" />"><i
                        class="icon-search"></i> Find owners</a>
            </li>

            <c:choose>
                <c:when test="${not empty sessionScope.users}" >
                    <c:set var="veterinarians" value="/vets.html"/>
                </c:when>
                <c:otherwise><c:set var="findOwner" value="/"/></c:otherwise>
            </c:choose>

            <li style="width: 140px;">
                <a href="<spring:url value="${veterinarians}"  />"><i
                        class="icon-th-list"></i> Veterinarians</a>
            </li>

            <li style="width: 90px;">
                <a href="<spring:url value="/oups.html" htmlEscape="true" />"
                   title="trigger a RuntimeException to see how it is handled"><i
                        class="icon-warning-sign"></i> Error</a>
            </li>

            <li style="width: 80px;">
                <a href="#" title="not available yet. Work in progress!!"><i
                        class=" icon-question-sign"></i> Help</a>
            </li>

            <li>
            <c:set var="localeCode" value="${pageContext.response.locale}" />
                <select id="i18n" name="local" style="width: 53px; margin-top: 7px;">
                    <option value="en" <c:if test="${localeCode == 'en'}" >
                                <c:out value="selected"/>
                            </c:if> >en</option>
                    <option value="de" <c:if test="${localeCode == 'de'}" >
                                <c:out value="selected"/>
                            </c:if> >de</option>
                </select>
            </li>

        </ul>
    </div>


</div>

