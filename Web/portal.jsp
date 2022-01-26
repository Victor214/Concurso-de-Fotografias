<%-- 
    Document   : portal
    Created on : 07/11/2019, 23:32:36
    Author     : Victor
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%-- Redirect : Not logged in user attempting to access portal --%>
        <c:if test="${empty sessionScope.nome}">
           <jsp:forward page="login.jsp">
           <jsp:param name="erro" value="4" />
           </jsp:forward>
        </c:if>
        
        <jsp:include page="ConcursoControladoraPortal"/>
    </body>
</html>
