<%-- 
    Document   : create
    Created on : Feb 10, 2022, 12:46:51 AM
    Author     : wellinton
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../templates/header.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../include/head.jsp"  %>
        <title>PIS - Cadastrar Site</title>
    </head>
    <body>
        <div class="container mt-3 p-0">
            <h2 class="text-center">Cadastro de site</h2>

            <form
                class="form"
                action="${pageContext.servletContext.contextPath}/store/create"
                method="POST">

                <div class="form-group">
                    <label class="control-label" for="site-name">Nome</label>
                    <input id="site-name" class="form-control" type="text" name="name" required autofocus/>
                    <p class="help-block"></p>
                </div>
                
                <!--
                <div class="form-group">
                    <label class="control-label" for="site-url-logo">Link da logo do site</label>
                    <input id="site-url-logo" class="form-control" type="url" name="url-logo" required autofocus/>
                    <p class="help-block"></p>
                </div>
                -->

                <div class="form-group">
                    <label class="control-label" for="site-address">Endereço</label>
                    <input id="site-address" class="form-control" type="text" name="address" required autofocus/>
                    <p class="help-block"></p>
                </div>
                
                <div class="form-group">
                    <label class="control-label" for="site-phone">Telefone</label>
                    <input id="site-phone" class="form-control" type="number" name="phone" required autofocus/>
                    <p class="help-block"></p>
                </div>
                
                <div class="form-group">
                    <label class="control-label" for="site-script">Script</label>
                    <TextArea id="site-script" class="form-control" rows="20" type="text" name="scriptText" required autofocus></textarea>
                    <p class="help-block"></p>
                </div>
                
                <div class="d-flex">
                    <div class="text-center">
                    <a class="btn btn-lg btn-white" href="${pageContext.servletContext.contextPath}/index">Voltar</a>
                </div>
                
                <div class="text-center">
                    <button class="btn btn-lg btn-success" type="submit">Salvar</button>
                </div>
                </div>
                
            </form>
        </div>
    </body>
</html>