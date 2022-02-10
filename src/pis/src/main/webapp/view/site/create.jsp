<%-- 
    Document   : create
    Created on : Feb 10, 2022, 12:46:51 AM
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
        <title>PIS - Cadastrar site</title>
    </head>
    
    <body>
       <div class="container">
            <h2 class="text-center">Cadastro de site</h2>

            <form
                class="form"
                action="${pageContext.servletContext.contextPath}/site/create"
                method="POST">

                <div class="form-group">
                    <label class="control-label" for="site-name">Nome</label>
                    <input id="site-name" class="form-control" type="text" name="name" required autofocus/>
                    <p class="help-block"></p>
                </div>

                <div class="form-group">
                    <label class="control-label" for="site-url">Nome</label>
                    <input id="site-url" class="form-control" type="text" name="url" required autofocus/>
                    <p class="help-block"></p>
                </div>

                <div class="form-group">
                    <label class="control-label" for="site-cnpj">CNPJ</label>
                    <input id="site-cnpj" class="form-control" type="number" name="cnpj" required autofocus/>
                    <p class="help-block"></p>
                </div>
                
                <div class="form-group">
                    <label class="control-label" for="site-phone">Telefone</label>
                    <input id="site-phone" class="form-control" type="number" name="phone" required autofocus/>
                    <p class="help-block"></p>
                </div>
                
                <div class="text-center">
                    <button class="btn btn-lg btn-primary" type="submit">Salvar</button>
                </div>
            </form>
        </div>

        <script src="${pageContext.servletContext.contextPath}/assets/js/user.js"></script>
    </body>
    <%@include file="/view/templates/footer.jsp"%>
</html>