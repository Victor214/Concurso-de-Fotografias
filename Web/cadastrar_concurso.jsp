<%-- 
    Document   : cadastrar_concurso.jsp
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
        
        <%-- Jquery UI --%>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.css" integrity="sha256-p6xU9YulB7E2Ic62/PX+h59ayb3PBJ0WFTEQxq0EjHw=" crossorigin="anonymous" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.js" integrity="sha256-T0Vest3yCU7pafRw9r+settMBX6JkKN06dqBnpQ8d30=" crossorigin="anonymous"></script>
        
        <%-- Moment.js and pt-br locale --%>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/locale/pt-br.js"></script>
        
        <%-- Font Awesome Icons --%>
        <link href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
        
        <%-- Tempus Dominus Datepicker --%>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.1.2/js/tempusdominus-bootstrap-4.min.js"></script>
        <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.1.2/css/tempusdominus-bootstrap-4.min.css" />
        
        <%-- Used for "Choose File" text change --%>
        <script src="https://cdn.jsdelivr.net/npm/bs-custom-file-input/dist/bs-custom-file-input.min.js"></script>
        <script>
            $(document).ready(function () {
                bsCustomFileInput.init();
            });
        </script>
        
        
        <%-- cadastrar_concurso.js --%>
        <script type="text/javascript" src="JS/cadastrar_concurso.js"></script>
    </head>
    <body>
        <%-- Redirect : Not logged in user attempting to access portal --%>
        
        <c:if test="${empty sessionScope.nome}">
           <jsp:forward page="login.jsp">
           <jsp:param name="erro" value="4" />
           </jsp:forward>
        </c:if>
        
        <%-- Redirect : Not an administrator --%>
        <c:if test="${sessionScope.tipo != 2}">
           <jsp:forward page="index.jsp">
           <jsp:param name="msg" value="2" />
           </jsp:forward>
        </c:if>
        
        <%-- Navbar --%>
        <%@ include file="JSP/navbar.jsp"%>   
        
        <%-- Messages --%>
        <c:choose>
            <c:when test="${param.msg == '1'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Um dos campos não foi preenchido.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.msg == '2'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Apenas arquivos do formato PNG são aceitos.
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
            </c:when>
            <c:when test="${param.msg == '3'}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Nome do arquivo muito longo.
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
            <div class="col-4">
                <div class="shadow p-3 mb-5 bg-white rounded">
                    <h2>Cadastrar Concurso</h2>
                    <br>
                    <form action="ConcursoControladora?action=cadastrarconcurso" method="POST" id="formCadastrarConcurso" class="upload-form" enctype="multipart/form-data">
                        <%-- Nome --%>
                        <div><label class="font-weight-bold" for="nome">Nome</label></div>
                        <div><input class="form-control" type="text" name="nome" id="nome" size="13" maxlength="30"></div>
                        <small id="nomeHelp" class="form-text text-muted">Escolha um nome para o concurso a ser realizado.</small>
                        <br>
                        
                        <%-- Descrição --%>
                        <div><label class="font-weight-bold" for="desc">Descrição</label></div>
                        <div><textarea class="form-control" name="desc" id="desc" maxlength="150" rows="3" ></textarea></div>
                        <small id="descHelp" class="form-text text-muted">Digite uma descrição breve a ser utilizada.</small>
                        <br>
                        
                        <%-- Date picker --%>
                        <div><label class="font-weight-bold" for="datepicker-start">Inicio do Concurso</label></div>
                        <div class="form-group">
                           <div class="input-group date" id="datepicker-start" data-target-input="nearest">
                                <input type="text" name="datepicker-start" class="form-control datetimepicker-input" data-target="#datepicker-start"/>
                                <div class="input-group-append" data-target="#datepicker-start" data-toggle="datetimepicker">
                                    <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                                </div>
                            </div>
                            <small id="dpStartHelp" class="form-text text-muted">Escolha uma data para o início do concurso (incluindo datas passadas).</small>
                        </div>
                        
                        <div><label class="font-weight-bold" for="datepicker-end">Fim do Concurso</label></div>
                        <div class="form-group">
                           <div class="input-group date" id="datepicker-end" data-target-input="nearest">
                                <input type="text" name="datepicker-end" class="form-control datetimepicker-input" data-target="#datepicker-end"/>
                                <div class="input-group-append" data-target="#datepicker-end" data-toggle="datetimepicker">
                                    <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                                </div>
                            </div>
                            <small id="dpEndHelp" class="form-text text-muted">Agora escolha uma data para o fim do concurso.</small>
                        </div>
                        
                        <%-- Min Max Photos --%>
                        <div><label class="font-weight-bold" for="qtdFotos">Quantidade de Fotos</label></div> 
                        <input type="text" id="qtdFotos" readonly style="border:0; font-weight:bold;">
                        <div id="slider-range"></div>
                        <small id="nomeHelp" class="form-text text-muted">Escolha a quantidade minima e máxima de fotos que podem ser colocadas no concurso.</small>
                        
                        <input type="text" id="minFotos" class="invisivel" name="minFotos" readonly>
                        <input type="text" id="maxFotos" class="invisivel" name="maxFotos" readonly>
                        <br>
                        
                        <%-- Logo Upload --%>
                        <div><label class="font-weight-bold" for="logoInput">Imagem do concurso</label></div>
                        <div class="custom-file">
                            <input type="file" name="logoInput" class="custom-file-input upload-file" data-max-size="5000000" id="logoInput" />
                            <label class="custom-file-label" for="logoInput">Escolha um arquivo</label>
                        </div>
                        <small id="imgHelp" class="form-text text-muted">Um tamanho proximo de 320x200 é recomendado para a imagem.</small>
                        
                        

                    </form>
                    <br> 
                    <button type="submit" form="formCadastrarConcurso" class="btn btn-primary">Cadastrar</button>
                    <a class="btn btn-secondary float-right" href="portal.jsp" role="button">Retornar aos concursos</a>
                </div>
            </div>
            <div class="col"></div>   
        </div>          
        
        <%-- Bootstrap --%>
        <%--<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>--%>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js" integrity="sha384-ChfqqxuZUCnJSK3+MXmPNIyE6ZbWh2IMqE241rYiqJxyMiZ6OW/JmZQ5stwEULTy" crossorigin="anonymous"></script>
    </body>
</html>
