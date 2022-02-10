<%-- 
    Document   : update
    Created on : Feb 10, 2022, 1:10:14 AM
    Author     : wellinton
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/view/templates/header.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/view/include/head.jsp"  %>
        <title>PIS - Editar cadastro site</title>
    </head>
    <body>
        <div class="container">
            <h2 class="text-center">Edição do site <c:out value="${user.nome}"/></h2>

            <form
                class="form"
                action="${pageContext.servletContext.contextPath}/site/update"
                method="POST">
                
                <input type="hidden" name="id" value="${user.id}">
                    
                <div class="form-group">
                    <label class="control-label" for="site-name">Nome</label>
                    <input id="site-name" class="form-control" type="text" name="name" value="${site.name}" data-value="${site.name}" required autofocus/>
                    <p class="help-block"></p>
                </div>

                <div class="form-group">
                    <label class="control-label" for="site-cnpj">CNPJ</label>
                    <input id="site-cnpj" class="form-control" type="number" name="cnpj" value="${site.cnpj}" data-value="${site.cnpj}" required autofocus/>
                    <p class="help-block"></p>
                </div>

                <div class="form-group">
                    <label class="control-label" for="site-phone">Telefone</label>
                    <input id="site-phone" class="form-control" type="number" name="phone" value="${site.phone}" data-value="${site.phone}" required autofocus/>
                    <p class="help-block"></p>
                </div>

                <div class="form-group">
                    <label for="usuario-nome" class="control-label">Nome</label>
                    <input id="usuario-nome" class="form-control" type="text" name="nome" value="${user.nome}" required/>
                </div>

                <div class="text-center">
                    <button class="btn btn-lg btn-primary" type="submit">Atualizar</button>
                </div>
            </form>
        </div>

        <script src="${pageContext.servletContext.contextPath}/assets/js/user.js"></script>
    </body>
    <%@include file="/view/templates/footer.jsp"%>
</html>