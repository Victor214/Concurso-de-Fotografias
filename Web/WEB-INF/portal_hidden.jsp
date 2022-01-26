<%-- 
    Document   : portal
    Created on : 06/11/2019, 23:28:09
    Author     : Victor
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="CSS/style.css" />
    
        <%-- Font Awesome Icons --%>
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
    </head>
    <body>        
        <%-- Navbar --%>
        <%@ include file="../JSP/navbar.jsp"%>   
        
        <%-- Messages --%>
        <c:choose>
            <c:when test="${param.msg == '1'}">
                <div class="alert alert-success alert-dismissible my-0 fade show" role="alert">
                    Login efetuado com sucesso.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.msg == '2'}">
                <div class="alert alert-success alert-dismissible my-0 fade show" role="alert">
                    Concurso criado com sucesso.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.msg == '3'}">
                <div class="alert alert-success alert-dismissible my-0 fade show" role="alert">
                    Entradas no concurso cadastradas.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
        </c:choose>
                
        <c:choose>
            <c:when test="${param.erro == '1'}">
                <div class="alert alert-danger alert-dismissible my-0 fade show" role="alert">
                    Você já se encontra logado no sistema.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.erro == '2'}">
                <div class="alert alert-danger alert-dismissible my-0 fade show" role="alert">
                    Concurso não encontrado.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.erro == '3'}">
                <div class="alert alert-danger alert-dismissible my-0 fade show" role="alert">
                    Erro desconhecido.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.erro == '4'}">
                <div class="alert alert-danger alert-dismissible my-0 fade show" role="alert">
                    Esta entrada não está mais disponível para votação.
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
                <h2>Concursos Disponiveis</h2>
                <div class="shadow p-3 mb-5 bg-white rounded d-flex justify-content-center">
                    <c:if test="${fn:length(concursos) == 0}">
                        <br>
                        <p>Parece que não há nada aqui.</p>
                    </c:if>
                    <div class="row">
                        <c:forEach items="${concursos}" var="conc">
                            <div class="col">
                                <div class="card" style="width: 18rem;">
                                    <img src="ImageServlet?id=<c:out value = "${conc.codImagem}"/>" class="card-img-top" alt="..." width="320" height="200">
                                    <div class="card-body text-center">
                                        <h5 class="card-title"><c:out value = "${conc.nome}"/></h5>
                                        <p class="card-text"><c:out value = "${conc.descricao}"/></p>
                                        <a href="ConcursoControladora?action=abrirconcurso&id=<c:out value = "${conc.codConcurso}"/>" class="btn btn-primary"><i class="fa fa-camera" aria-hidden="true"></i></a>
                                    </div>
                                </div>
                            </div>
                       </c:forEach>
                    </div>
                </div>
                
                <h2>Concursos Passados</h2>
                <div class="shadow p-3 mb-5 bg-white rounded d-flex justify-content-center">
                    <c:if test="${fn:length(concursosExp) == 0}">
                        <br>
                        <p>Parece que não há nada aqui.</p>
                    </c:if>
                    <div class="row">
                        <c:forEach items="${concursosExp}" var="conc">
                            <div class="col">
                                <div class="card" style="width: 18rem;">
                                    <img src="ImageServlet?id=<c:out value = "${conc.codImagem}"/>" class="card-img-top pretoebranco" alt="..." width="320" height="200">
                                    <div class="card-body text-center">
                                        <h5 class="card-title"><c:out value = "${conc.nome}"/></h5>
                                        <p class="card-text"><c:out value = "${conc.descricao}"/></p>
                                        <a href="ConcursoControladora?action=abrirconcurso&id=<c:out value = "${conc.codConcurso}"/>" class="btn btn-primary"><i class="fa fa-camera" aria-hidden="true"></i></a>
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
