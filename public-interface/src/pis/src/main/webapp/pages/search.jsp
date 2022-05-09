<%-- 
    Document   : searchpage
    Created on : May 8, 2022, 1:38:23 PM
    Author     : wellinton
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Price Integration System</title>
        <link rel="stylesheet" href="./styles/search.css"/>
        <link rel="stylesheet" href="./styles/filters.css"/>
    </head>
    
    
    <body>    
        <%@include file="/templates/header.jsp"%>
        <div class="container-fluid">
            <div class="offset-2 col-10 p-4 d-flex justify-content-between cabecalho">
                <div class="d-flex flex-column">
                    <span id="result-key"></span>
                    <span>Resultados: <span id="results-counter">${fn:length(iphones)}</span></span>
                </div>
                <%@include file="/templates/order-by.jsp"%>
            </div>
            
             <c:choose>
                <c:when test="${fn:length(iphones) == 0}">
                    <div class="d-flex justify-content-center align-items-center h-100 w-100">
                        <h1 class="nenhum-iphone active my-5 pt-5">Nenhum iPhone encontrado  :(</h1>
                    </div>
                </c:when>    
                <c:otherwise>

                    <div class="d-flex">
                        <div class="col-2 d-flex flex-column filters-area">
                            <h4>Filtros</h4>
                            <div class="d-flex">
                                <form id="form-filters" class="form w-100 d-flex flex-column justify-content-center" action="${pageContext.servletContext.contextPath}/search" method="GET">
                                    <input id="q-hidden" class="form-control"  type="hidden" name="q" value="" />

                                    <div class="form-group d-flex flex-column">
                                        <p class="p-0 m-0">Preço</p>
                                        <div class="d-flex pt-1">
                                            <input id="min-price" class="form-control w-50" type="text" name="minPrice" placeholder="min R$" autofocus/>
                                            <input id="max-price" class="form-control w-50" type="text" name="maxPrice" placeholder="max R$" autofocus/>
                                        </div>
                                    </div>

                                    <div class="form-group d-flex flex-column">
                                        <p>Cores</p>
                                        <span>
                                            <input type="checkbox" id="checkbox-vermelho" name="color" value="vermelho">
                                            <label for="checkbox-vermelho">Vermelho</label>
                                        </span>
                                        <span>
                                            <input type="checkbox" id="checkbox-azul" name="color" value="azul">
                                            <label for="checkbox-azul">Azul</label>
                                        </span>
                                        <span>
                                            <input type="checkbox" id="checkbox-amarelo" name="color" value="amarelo">
                                            <label for="checkbox-amarelo">Amarelo</label>
                                        </span>
                                        <span>
                                            <input type="checkbox" id="checkbox-branco" name="color" value="branco">
                                            <label for="checkbox-branco">Branco</label>
                                        </span>
                                        <span>
                                            <input type="checkbox" id="checkbox-cinza" name="color" value="cinza">
                                            <label for="checkbox-cinza">Cinza</label>
                                        </span>
                                        <span>
                                            <input type="checkbox" id="checkbox-preto" name="color" value="preto">
                                            <label for="checkbox-preto">Preto</label>
                                        </span>
                                        <span>
                                            <input type="checkbox" id="checkbox-outro" name="color" value="outro">
                                            <label for="checkbox-outro">Outro</label>
                                        </span>
                                    </div>

                                    <div class="form-group d-flex flex-column">
                                        <p>Memória Interna</p>
                                        <span>
                                            <input type="checkbox" id="checkbox-64gb" name="secMem" value="64GB">
                                            <label for="checkbox-64gb">64GB</label>
                                        </span>
                                        <span>
                                            <input type="checkbox" id="checkbox-128gb" name="secMem" value="128GB">
                                            <label for="checkbox-128gb">128GB</label>
                                        </span>
                                        <span>
                                            <input type="checkbox" id="checkbox-256gb" name="secMem" value="256GB">
                                            <label for="checkbox-256gb">256GB</label>
                                        </span>
                                   </div>
                                <div class="text-center">
                                    <button class="btn btn-md btn-dark filter-button" type="submit"><i class="fa fa-filter"></i><span>Filtrar</span></button>
                                </div>
                            </form>

                            </div>
                        </div>

                        <div class="col-10">
                            <div class="d-flex mt-4 flex-wrap thumbs">
                                <c:forEach var="iphone" items="${iphones}">
                                    <div class="col-3 d-flex align-items-center justify-content-center thumb">

                                        <a href="${pageContext.servletContext.contextPath}/iphone?modelName=${iphone.modelName}&color=${iphone.color}&secMem=${iphone.secondaryMemory}" class="d-flex flex-column w-75 thumb-info">
                                            <img src="${iphone.imageLink}" width="150" style="margin: 0 auto;">
                                            <h6>${iphone.title}</h6>
                                            <p class="price">
                                                <span>Menor Preço registrado</span>
                                                <fmt:setLocale value = "pt_BR"/>
                                                <fmt:formatNumber value = "${iphone.versions.get(0).getCashPayment()}" type = "currency"/>
                                            </p>
                                        </a>
                                    </div>

                                </c:forEach>
                            </div>
                            <div class="d-flex justify-content-center mt-4">
                                <%@include file="/templates/pagination.jsp"%>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <%@include file="/templates/footer.jsp"%>
    </body>
</html>
