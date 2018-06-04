<%--
  Created by IntelliJ IDEA.
  User: simon
  Date: 23/05/18
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Welcome</title>
  </head>
  <body>
  <%
    response.sendRedirect(application.getContextPath()+"/do/home_page");
  %>
  </body>
</html>
