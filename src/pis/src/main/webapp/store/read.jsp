<%-- 
    Document   : read
    Created on : Feb 13, 2022, 11:06:30 PM
    Author     : wellinton
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@include file="../templates/header.jsp"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="../include/head.jsp"%>
        <title>PIS - ${store.name}</title>
    </head>
    <body>
        <div class="container">
            <div>
                <h2>${store.name}</h2>
            </div>

            <div>
                <h4>Dados Básicos</h4>

                <p>${store.name}</p>
                <p>${store.address}</p>
                <p>${store.phone}</p>

                <button class="btn btn-success button-execute-crawling">Executar Crawling</button>
            </div>

            <div>
                <h4>Histórico de Scripts</h4>

                <c:forEach var="script" items="${requestScope.exes}">
                    <a class="link_version-history" href="#" data-href="${pageContext.servletContext.contextPath}/read/id?${script.id}">Script V${script.id}</a>
                </c:forEach>
            </div>
                
            <!-- Modal executar crawling -->
            <div class="modal fade modal-execute-crawling">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Instruções</h4>
                            <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                        </div>
                    <div class="modal-body">
                        <p>1 - Execute o crawling separadamente</p>
                        <p>2 - Importe o JSON resultante utilizando o input abaixo</p>
                        <form class="form" action="${pageContext.servletContext.contextPath}/store/crawling" enctype="multipart/form-data" method="POST">
                            <div class="form-group">
                                <label for="crawling-file">JSON do Crawling</label>
                                <input  type="file"
                                        class="form-control" id="crawling-file"
                                        name="crawling" accept=".json" />
                            </div>

                            <div class="text-center">
                                <button class="btn btn-success" type="submit">Executar</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>

             <!-- Modal historico de versoes -->
            <div class="modal modal-version-history">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Historico de Versoes</h4>
                            <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                        </div>
                        <div class="modal-body">
                            <div class="container-fluid">
                                <div class="d-flex flex-column">
                                    <p class="p_id"></p>
                                    <p class="p_date"></p>
                                    <p class="p_time"></p>
                                    <p class="p_text"></p>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-primary" type="button" data-dismiss="modal">Fechar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    
        <script src="${pageContext.servletContext.contextPath}/assets/js/store.js"></script>
        <%@include file="../templates/footer.jsp"%> 
    </body>
</html>
