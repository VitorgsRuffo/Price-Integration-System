<%-- 
    Document   : update
    Created on : Feb 10, 2022, 1:10:14 AM
    Author     : wellinton
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="/templates/header.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/include/head.jsp"  %>
        <title>PIS - Editar Cadastro Site</title>
    </head>
    <body>
        <div class="container p-0 mt-3">
            <h2 class="text-center">Editar Cadastro do Site<c:out value="${store.name}"/></h2>

            <form
                class="form"
                action="${pageContext.servletContext.contextPath}/store/update"
                method="POST">
                
                <input type="hidden" name="id" value="${store.id}">
                    
                <div class="form-group">
                    <label class="control-label" for="site-name">Nome</label>
                    <input id="site-name" class="form-control" type="text" name="name" value="${store.name}" data-value="${store.name}" required autofocus/>
                    <p class="help-block"></p>
                </div>

                <div class="form-group">
                    <label class="control-label" for="site-url-logo">Link da logo do site</label>
                    <input id="site-url-logo" class="form-control" type="url" name="url-logo" value="${store.urlLogo}" data-value="${store.urlLogo}" required autofocus/>
                    <p class="help-block"></p>
                </div>

                <div class="form-group">
                    <label class="control-label" for="site-phone">Telefone</label>
                    <input id="site-phone" class="form-control" type="number" name="phone" value="${store.phone}" data-value="${store.phone}" required autofocus/>
                    <p class="help-block"></p>
                </div>

                <div class="form-group">
                    <label for="script-text" class="control-label">Script</label>
                    <!--<input id="script-text" class="form-control" type="text" name="nome" value="${store.nome}" required/>-->
                    <textarea class="form-control" id="script-text" rows="8" value="${script.text}"></textarea>
                </div>
                
                <div class="d-flex pt-3">
                    <div class="">
                        <a class="btn btn-lg btn-white" href="${pageContext.servletContext.contextPath}/index.jsp">Voltar</a>
                    </div>

                    <div class="">
                        <button class="btn btn-lg btn-success" type="submit">Atualizar</button>
                    </div>
                </div>
            </form>
        </div>
        
        <%@include file="/templates/footer.jsp"%>
    </body>
</html>
