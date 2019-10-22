<%@ page import="java.util.Enumeration" %><%--
  Created by IntelliJ IDEA.
  User: Hyun Woo Son
  Date: 7/27/18
  Time: 12:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<body>


<h1>HTTP Request Headers Received</h1>
<table border="1" cellpadding="4" cellspacing="0">
    <%
        Enumeration eNames = request.getHeaderNames();
        while (eNames.hasMoreElements()) {
            String name = (String) eNames.nextElement();
            String value = normalize(request.getHeader(name));
    %>
    <tr><td><%= name %></td><td><%= value %></td></tr>
    <%
        }
    %>
</table>
</body>
</html>


<%!
    private String normalize(String value)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            sb.append(c);
            if (c == ';')
                sb.append("<br>");
        }
        return sb.toString();
    }
%>