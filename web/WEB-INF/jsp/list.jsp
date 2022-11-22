<%@ page import="com.basejava.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page import="com.basejava.model.ContactType" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 22.11.2022
  Time: 10:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; cherset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
    <section>
        <table border="1" cellpadding="8" cellspacing="0">
            <tr>
                <th>Фамилия Имя Отчество</th>
                <th>Email</th>
            </tr>
            <%
                for(Resume resume : (List<Resume>) request.getAttribute("resumes")) {
            %>>
            <tr>
                <td> <a href="resume?uuid=<%=resume.getUuid()%>"><%=resume.getFullName()%></a></td>
                <td><%=resume.getContact(ContactType.EMAIL)%></td>
            </tr>
            <%
                }
            %>
        </table>>
    </section>
</body>
</html>
