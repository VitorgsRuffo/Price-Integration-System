<%-- 
    Document   : index
    Created on : Feb 10, 2022, 12:47:25 AM
    Author     : wellinton
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@include file="/view/templates/header.jsp"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/view/include/head.jsp"%> 
        <title>PIS - Sites</title>
    </head>
    <body> 
        <div class="register-site">
            <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/site">
                Cadastrar site
            </a>                 
        </div>

        <div class="sites-list">
            <ul>
                <c:forEach var="site" items="${$sitesList}">
                    <li>
                        <a href="${pageContext.servletContext.contextPath}/site">
                            ${site.name}
                        </a>

                        <a href="${pageContext.servletContext.contextPath}/site/update">
                            <i class="fa-light fa-pencil"></i>
                        </a>

                        <button type="button" class="" data-bs-toggle="modal" data-bs-target="#siteremove">
                            <i class="fa-light fa-trash"><i/>
                        </button>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <!-- Modal delete -->
        <div class="modal fade" id="siteremove" tabindex="-1" aria-labelledby="site-remove-modal" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Confirmação</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        Tem certeza que deseja remover esse site?
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Não</button>
                        <button type="button" class="btn btn-danger">Sim</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
    
    <%@include file="/view/templates/footer.jsp"%>
</html>
