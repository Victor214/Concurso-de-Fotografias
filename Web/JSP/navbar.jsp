<%-- 
    Document   : navbar
    Created on : 07/11/2019, 00:14:09
    Author     : Victor
--%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="index.jsp">PLACEHOLDER Fotografia</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="index.jsp">Home<span class="sr-only">(current)</span></a>
            </li>
            
            <c:choose>
                <%-- Deslogado --%>
                <c:when test="${empty sessionScope.nome}">
                    <li class="nav-item">
                        <a class="nav-link" href="login.jsp">Login</a>
                    </li>
        </ul>
                </c:when>
                    
                <%-- Logado --%>    
                <c:otherwise>
                    <li class="nav-item">
                        <a class="nav-link" href="portal.jsp">Concursos</a>
                    </li>
                    
                    <%-- Caso o usuário seja um administrador do sistema --%> 
                    <c:if test="${sessionScope.tipo == 2}">
                        <li class="nav-item">
                            <a class="nav-link" href="cadastrar_concurso.jsp">Cadastrar novo concurso</a>
                        </li>
                    </c:if>
        </ul> 
                    <a class="nav-link disabled text-light" href="#">Bem vindo, <c:out value="${sessionScope.nome}"/>.</a>
                    <form action="CadastroControladora?action=deslogar" method="POST">
                        <button class="btn btn-outline-light my-2 my-sm-0" type="submit">Deslogar</button>
                    </form>
                </c:otherwise>
            </c:choose>
        
    </div>
</nav>
