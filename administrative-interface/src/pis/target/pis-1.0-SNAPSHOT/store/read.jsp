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
        <div class="container pt-3">
            <div class="pt-2 pb-4 ">
                <h1>${store.name}</h1>
            </div>

            <div class="row">
                <div class="col-4">
                    <h4 class="pb-2">Dados Básicos</h4>
                    <p class="p-0 m-0 pb-1">Nome da loja: <strong>${store.name}</strong></p>
                    <p class="p-0 m-0 pb-1">Endereço: <strong>${store.address}</strong></p>
                    <p class="p-0 m-0 pb-1 mb-4">Telefone: <strong>${store.phone}</strong></p>
                    <button class="btn btn-success link-load-crawling">Executar Crawling</button>
                    <a href="${pageContext.servletContext.contextPath}/index" class="btn btn-default">Voltar</a>
                </div>

                <div class="col-6">
                    <h4>Histórico de Scripts</h4>
                    <div class="d-flex flex-column ">
                    <c:forEach var="script" items="${store.scripts}">
                        <a class="link-success link_version-history" href="" data-href="${pageContext.servletContext.contextPath}/store/read/script/executions?storeId=${script.storeId}&scriptVersionNum=${script.versionNum}">Script V${script.versionNum}</a>
                    </c:forEach>
                    </div>
                </div>
            </div>
                
            <!-- Modal executar crawling -->
            <div class="modal fade fade-in modal-execute-crawling">
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
                                <div class="form-group input-group">
                                    <input type="hidden" name="storeId" value="${store.id}">
                                    <label class="custom-file-label" for="crawling-file">JSON do Crawling</label>
                                    <input  type="file"
                                            class="form-control custom-file-input" id="crawling-file"
                                            name="crawling_file" accept=".json" />
                                </div>

                                <div class="text-center">
                                    <button class="btn btn-success" type="submit">Executar</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

             <!-- Modal historico de versoes -->
            <div class="modal fade fadein modal-version-history">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Historico de Execucoes</h4>
                            <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                        </div>
                        <div class="modal-body">
                            <div class="container-fluid">
                                <div class="d-flex flex-column">
                                    <p class="p_date"></p>
                                    <p class="p_time"></p>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-success" type="button" data-dismiss="modal">Fechar</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <%@include file="/templates/footer.jsp"%>
        <script src="${pageContext.servletContext.contextPath}/assets/js/store.js"></script>
    </body>
</html>
