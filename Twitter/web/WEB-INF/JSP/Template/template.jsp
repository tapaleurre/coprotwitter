<%--
  Created by IntelliJ IDEA.
  User: zussym
  Date: 04/06/18
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="content" class="java.lang.String" scope="request"/>
<html>
<head>
    <title>Twitter | Cancer.org</title>
</head>
<body>
<jsp:include page="<%= application.getInitParameter(\"entetedepage\")%>"/>

<jsp:include page="<%=content%>" />

<jsp:include page="<%= application.getInitParameter(\"pieddepage\")%>"/>
</body>
</html>
