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
        <title>PLACEHOLDER Fotografia</title>
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
                </div>
                
                <div class="shadow p-3 mb-5 bg-white rounded">
                    <h3>Inscrição no Concurso</h3>
                    <p class="form-text text-muted">Você deve enviar pelo menos <c:out value = "${concurso.minFotos}"/> fotografias.</p>
                    <form action="ConcursoControladora?action=cadastrarentrada" method="POST" id="formCadastrarEntrada" class="upload-form" enctype="multipart/form-data">
                        <input type="text" id="idConcurso" class="invisivel" name="idConcurso" value="<c:out value = "${concurso.codConcurso}"/>" readonly>
                        <c:forEach var="i" begin="1" end="${concurso.maxFotos}">
                            <%-- Nome --%>
                            <div><label class="font-weight-bold" for="titulo<c:out value = "${i}"/>">Titulo</label></div>
                            <div><input class="form-control" type="text" name="titulo<c:out value = "${i}"/>" id="titulo<c:out value = "${i}"/>" size="13" maxlength="30"></div>
                            <small id="titulo<c:out value = "${i}"/>Help" class="form-text text-muted">Digite o titulo desta entrada.</small>
                            <br>
                            
                            <%-- Descrição --%>
                            <div><label class="font-weight-bold" for="desc<c:out value = "${i}"/>">Descrição</label></div>
                            <div><input class="form-control" type="text" name="desc<c:out value = "${i}"/>" id="desc<c:out value = "${i}"/>" size="13" maxlength="30"></div>
                            <small id="desc<c:out value = "${i}"/>Help" class="form-text text-muted">Informe uma breve descrição para a foto.</small>
                            <br>
                            
                            <%-- Logo Upload --%>
                            <div><label class="font-weight-bold" for="logoInput<c:out value = "${i}"/>">Imagem do concurso</label></div>
                            <div class="custom-file">
                                <input type="file" name="logoInput<c:out value = "${i}"/>" class="custom-file-input upload-file" data-max-size="15000000" id="logoInput<c:out value = "${i}"/>" />
                                <label class="custom-file-label" for="logoInput<c:out value = "${i}"/>">Escolha um arquivo</label>
                            </div>
                            <small id="img<c:out value = "${i}"/>Help" class="form-text text-muted">Um tamanho proximo de 1920x1080, com limite de 15 megabytes, é recomendado para a imagem.</small>
                            
                            <hr>
                            <br>
                            <br>
                        </c:forEach>
                    </form>
                    <button type="submit" form="formCadastrarEntrada" class="btn btn-primary">Cadastrar Entradas no Concurso</button>
                    <a class="btn btn-secondary float-right" href="portal.jsp" role="button">Retornar aos concursos</a>
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
