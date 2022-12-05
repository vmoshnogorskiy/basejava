<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.basejava.model.ContactType" %>
<%@ page import="com.basejava.model.SectionType" %>
<%@ page import="com.basejava.model.Section" %>
<%@ page import="com.basejava.model.ListSection" %>
<%@ page import="com.basejava.model.OrganizationSection" %>
<%@ page import="com.basejava.util.DateUtil" %>

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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">

        <h1>Имя:</h1>
        <dl>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h2>Контакты:</h2>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>
        <hr>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(sectionType)}"/>
            <jsp:useBean id="section" type="com.basejava.model.Section"/>
                <h2><a>${sectionType.title}</a></h2>
                <c:choose>
                    <c:when test="${sectionType=='OBJECTIVE'}">
                        <input type="text" name="${sectionType}" size=75 value=<%=section%>>
                    </c:when>
                    <c:when test="${sectionType=='PERSONAL'}">
                            <textarea name="${sectionType}" cols=75 rows=5>
                                <%=section%>
                            </textarea>
                    </c:when>
                    <c:when test="${sectionType=='ACHIEVEMENT' || sectionType=='QUALIFICATION' }">
                            <textarea name="${sectionType}" cols=75 rows=5>
                                <%=String.join("\n", ((ListSection) section).getItems())%>
                            </textarea>
                    </c:when>
                    <c:when test="${sectionType=='EXPERIENCE' || sectionType=='EDUCATION' }">
                        <c:forEach var="org" items="<%=((OrganizationSection) section).getOrganizations()%>"
                                   varStatus="counter">
                            <dl>
                                <dt>Название учреждения:</dt>
                                <dd><input type="text" name="${sectionType}" size=100 value="${org.homePage.name}">
                                </dd>
                            </dl>
                            <dl>
                                <dt>Сайт учреждения:</dt>
                                <dd>
                                    <input type="text" name="${sectionType}_url" size=100 value="${org.homePage.url}">
                                </dd>
                            </dl>
                            <br>
                            <div style="margin-left: 30px">
                                <c:forEach var="pos" items="${org.properties}">
                                    <jsp:useBean id="pos" type="com.basejava.model.Organization.Property"/>
                                    <dl>
                                        <dt>Начальная дата:</dt>
                                        <dd>
                                            <input type="text" name="${sectionType}${counter.index}startDate"
                                                   size=10
                                                   value="<%=DateUtil.format(pos.getStartDate())%>"
                                                   placeholder="MM/yyyy">
                                        </dd>
                                    </dl>
                                    <dl>
                                        <dt>Конечная дата:</dt>
                                        <dd>
                                            <input type="text" name="${sectionType}${counter.index}endDate"
                                                   size=10
                                                   value="<%=DateUtil.format(pos.getEndDate())%>"
                                                   placeholder="MM/yyyy">
                                        </dd>
                                    </dl>
                                    <dl>
                                        <dt>Должность:</dt>
                                        <dd>
                                            <input type="text" name="${sectionType}${counter.index}title"
                                                   size=75 value="${pos.title}">
                                        </dd>
                                    </dl>
                                    <dl>
                                        <dt>Описание:</dt>
                                        <dd>
                                                <textarea type="text" name="${sectionType}${counter.index}description"
                                                          rows=2
                                                          cols=75>${pos.description}
                                                </textarea>
                                        </dd>
                                    </dl>
                                </c:forEach>
                            </div>
                        </c:forEach>
                    </c:when>
                </c:choose>
        </c:forEach>

        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>

