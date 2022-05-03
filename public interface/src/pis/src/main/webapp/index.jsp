<%-- 
    Document   : index
    Created on : Feb 9, 2022, 8:18:13 PM
    Author     : wellinton
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="/templates/header.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/include/head.jsp"%>   
        <title>Price Integration System</title>
    </head>
    
    <body>
        <div class="container pt-5">
            
            <!-- Botão cadastrar loja -->
            <div>
                <a class="btn btn-success" href="${pageContext.servletContext.contextPath}/store/create.jsp">Cadastrar Site</a>
            </div>

            <!-- Lista de lojas -->
            <ul class="list-inline pt-4 col-2">
                <c:forEach var="store" items="${requestScope.storeList}">
                    <li class="list-inline-item d-flex">
                        <a class="btn btn-default pl-0 " href="${pageContext.servletContext.contextPath}/store/read?id=${store.id}"><c:out value="${store.name}"/></a>
                        
                        <a class="btn btn-default" data-toggle="tooltip" data-original-title="Atualizar" href="${pageContext.servletContext.contextPath}/store/update?id=${store.id}"><i class="fa fa-pencil"></i></a>
                        <a class="btn btn-default link-delete-store" 
                            href="#" data-href="${pageContext.servletContext.contextPath}/store/delete?id=${store.id}"
                            data-toggle="tooltip"
                            data-original-title="Excluir">
                            <i class="fa fa-trash"></i>
                        </a>
                    </li>
                </c:forEach>
            </ul>

            <!-- Model excluir loja -->       
            <div class="modal fade modal-delete-store">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Confirmação</h4>
                            <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                        </div>
                        <div class="modal-body">
                            <p>Tem certeza de que deseja excluir este usuário?</p>
                        </div>
                        <div class="modal-footer">
                            <a class="btn btn-danger link-confirmation-delete-store">Sim</a>
                            <button class="btn btn-success" type="button" data-dismiss="modal">Não</button>
                        </div>
                    </div>
                </div>
            </div>      
        </div>

        <%@include file="/include/scripts.jsp"%>
        <script src="${pageContext.servletContext.contextPath}/assets/js/store.js"></script> 
        <%@include file="/templates/footer.jsp"%>
    </body>
</html>