<%-- 
    Document   : login
    Created on : 06/11/2019, 19:35:21
    Author     : Victor
--%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PLACEHOLDER Concursos de Fotografia</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="CSS/style.css" />
    </head>
    <body>
        <%-- Redirect : Logged in user attempting to login --%>
        <c:if test="${not empty sessionScope.nome}">
           <jsp:forward page="portal.jsp">
           <jsp:param name="erro" value="1" />
           </jsp:forward>
       </c:if>
        
        <%-- Navbar --%>
        <%@ include file="JSP/navbar.jsp"%>
        
        <%-- Messages --%>
        <c:if test="${param.sucesso == 'true'}">
            <div class="alert alert-success" role="alert">
                Conta criada com sucesso. Utilize os campos abaixo para realizar o login.
            </div>
        </c:if>
        
        <c:choose>
            <c:when test="${param.erro == '1'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Um dos campos não foi preenchido corretamente.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.erro == '2'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Email não encontrado.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.erro == '3'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Senha incorreta.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.erro == '4'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Efetue login para acessar o sistema.
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
            <div class="col-3">
                <h3>Login</h3>
                <div class="shadow p-3 mb-5 bg-white rounded">
                    <form action="CadastroControladora?action=login" method="POST" id="formLogin">
                        <div><label for="email">Email</label></div>
                        <div><input class="form-control" type="email" name="email" id="email" size="13" maxlength="20"></div>
                        <div><label for="senha">Senha</label></div>
                        <div><input class="form-control" type="password" name="senha" id="senha" size="13" maxlength="12"></div>
                        <small id="senhaHelp" class="form-text text-muted">Sua senha é criptografada e não será compartilhada com ninguém.</small>
                    </form>
                    <br>
                    <button type="submit" form="formLogin" class="btn btn-primary">Login</button>
                    <a class="btn btn-primary float-right" href="cadastro.jsp" role="button">Quero me cadastrar</a>
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
