<%-- 
    Document   : portal
    Created on : 06/11/2019, 23:28:09
    Author     : Victor
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="CSS/style.css" />
    
        <%-- Jquery --%>
        <script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
        
        <%-- Font Awesome Icons --%>
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
    
        <%-- Used for "Choose File" text change --%>
        <script src="https://cdn.jsdelivr.net/npm/bs-custom-file-input/dist/bs-custom-file-input.min.js"></script>
        <script>
            $(document).ready(function () {
                bsCustomFileInput.init();
            });
        </script>
        
        <%-- concurso_entrada_hidden.js --%>
        <script type="text/javascript" src="JS/concurso_entrada_hidden.js"></script>
        
    </head>
    <body>
        <%-- Redirect : Not logged in user attempting to access portal --%>
        <c:if test="${empty sessionScope.nome}">
           <jsp:forward page="login.jsp">
           <jsp:param name="erro" value="4" />
           </jsp:forward>
        </c:if>
        
        <%-- Navbar --%>
        <%@ include file="../JSP/navbar.jsp"%>   
        
        <%-- Messages --%>
        <c:choose>
            <c:when test="${param.msg == '1'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    O mínimo de campos não foi preenchido corretamente.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.msg == '2'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Somente arquivos com extensão PNG são aceitos.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
        </c:choose>
        
        <%-- Core --%>      
        <br>
        <br>
        <br>
        <div class="row">
            <div class="col"></div>
            <div class="col-8">
                <div class="shadow p-3 mb-5 bg-white">
                    <div align="center"><h2><c:out value = "${entrada.titulo}"/></h2></div>
                    <div align="center"><img src="ImageServlet?id=<c:out value = "${entrada.img.codImagem}"/>" alt="..." width="1280" height="790"></div>
                    <hr>
                    <div align="center"><p class="text-muted"><c:out value = "${entrada.descricao}"/></p></div>
                    <hr>
                    <div align="center">
                        <c:if test="${not inativo}">
                            <c:choose>
                                <c:when test="${likeStatus}">
                                    <a class="btn btn-secondary" href="ConcursoControladora?action=avaliarentrada&id=<c:out value = "${entrada.codEntrada}"/>&likeStatus=true" role="button"><i class="fa fa-thumbs-up" aria-hidden="true"></i> Remover Avaliação</a>
                                </c:when>
                                <c:otherwise>
                                    <a class="btn btn-success" href="ConcursoControladora?action=avaliarentrada&id=<c:out value = "${entrada.codEntrada}"/>&likeStatus=false" role="button"><i class="fa fa-thumbs-up" aria-hidden="true"></i> Avaliar Entrada</a>
                                </c:otherwise>
                            </c:choose>
                        </c:if>
                        <a class="btn btn-primary" href="ConcursoControladora?action=abrirconcurso&id=<c:out value = "${entrada.concurso.codConcurso}"/>" role="button">Retornar ao concurso '<c:out value = "${entrada.concurso.nome}"/>'</a>
                    </div>
                </div>
            </div>
            <div class="col"></div>   
        </div>
        
        <%-- Bootstrap --%>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    </body>
</html>
