<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.basejava.model.ListSection" %>
<%@ page import="com.basejava.model.OrganizationSection" %>
<%@ page import="com.basejava.model.TextSection" %>
<%@ page import="com.basejava.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.basejava.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.basejava.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <hr>
    <table cellpadding="2">
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.basejava.model.SectionType, java.lang.String>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.basejava.model.Section"/>
            <tr>
                <td>
                    <h3>
                        <a name="type.name">${type.title}</a>
                    </h3>
                </td>
                <c:if test="${type=='OBJECTIVE'}">
                    <td>
                        <h3>
                            <%=((TextSection) section).getContent()%>
                        </h3>
                    </td>
                </c:if>
            </tr>
            <c:if test="${type!='OBJECTIVE'}">
                <c:choose>
                    <c:when test="${type=='PERSONAL'}">
                        <td>
                            <h3>
                                <%=((TextSection) section).getContent()%>
                            </h3>
                        </td>
                    </c:when>
                    <c:when test="${type=='QUALIFICATION' || type=='ACHIEVEMENT'}">
                        <tr>
                            <td>
                                <ul>
                                    <c:forEach var="item" items="<%=((ListSection) section).getItems()%>">
                                        <li>${item}</li>
                                    </c:forEach>
                                </ul>
                            </td>
                        </tr>
                    </c:when>
                    <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                        <c:forEach var="org" items="<%=((OrganizationSection) section).getOrganizations()%>">
                            <tr>
                                <td>
                                    <c:choose>
                                        <c:when test="${empty org.homePage.url}">
                                            <h3>${org.homePage.name}</h3>
                                        </c:when>
                                        <c:otherwise>
                                            <h3><a href="${org.homePage.url}">${org.homePage.name}</a></h3>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <c:forEach var="position" items="${org.properties}">
                                <jsp:useBean id="position" type="com.basejava.model.Organization.Property"/>
                                <tr>
                                    <td>
                                        <%=HtmlUtil.formatDates(position)%>
                                    </td>
                                    <td>
                                        <b>${position.title}</b><br>${position.description}
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:if>
        </c:forEach>
    </table>
    <button onclick="window.history.back()">OK</button>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
