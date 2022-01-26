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
                    <div align="center"><h2><c:out value = "${concurso.nome}"/></h2></div>
                    <div align="center"><p><c:out value = "${concurso.descricao}"/></p></div>
                    <div align="center"><img src="ImageServlet?id=<c:out value = "${concurso.codImagem}"/>" alt="..." width="320" height="200"></div>
                    <br>
                    <div align="center"><a class="btn btn-primary" href="portal.jsp" role="button">Retornar aos concursos</a></div>
                </div>
                
                <c:if test="${not empty vencedor}">
                    <div class="shadow p-3 mb-5 bg-white rounded">
                        <h3>Vencedor do Concurso</h3>
                        <div class="row">
                            <div class="col" align="center">
                                <div class="card mt-2" style="width: 18rem;">
                                    <img src="ImageServlet?id=<c:out value = "${vencedor.img.codImagem}"/>" class="card-img-top" alt="..." width="356" height="200">
                                    <div class="card-body text-center">
                                        <h5 class="card-title"><c:out value = "${vencedor.titulo}"/></h5>
                                        <p class="card-text"><c:out value = "${vencedor.descricao}"/></p>
                                        <p class="card-text text-muted">Autor : <c:out value = "${vencedor.usuario.nome}"/></p>
                                        <a href="ConcursoControladora?action=abrirentrada&id=<c:out value = "${vencedor.codEntrada}"/>" class="btn btn-primary"><i class="fa fa-camera" aria-hidden="true"></i></a>
                                    </div>
                                </div>
                            </div>
                       </div>
                    </div>   
                </c:if>
                    
                <div class="shadow p-3 mb-5 bg-white rounded">
                    <h3>Exibição de Fotografias</h3>
                    <div class="row">
                        <c:forEach items="${listaEntrada}" var="ent">
                            <div class="col">
                                <div class="card mt-2" style="width: 18rem;">
                                    <img src="ImageServlet?id=<c:out value = "${ent.img.codImagem}"/>" class="card-img-top" alt="..." width="356" height="200">
                                    <div class="card-body text-center">
                                        <h5 class="card-title"><c:out value = "${ent.titulo}"/></h5>
                                        <p class="card-text"><c:out value = "${ent.descricao}"/></p>
                                        <a href="ConcursoControladora?action=abrirentrada&id=<c:out value = "${ent.codEntrada}"/>" class="btn btn-primary"><i class="fa fa-camera" aria-hidden="true"></i></a>
                                    </div>
                                </div>
                            </div>
                       </c:forEach>
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
