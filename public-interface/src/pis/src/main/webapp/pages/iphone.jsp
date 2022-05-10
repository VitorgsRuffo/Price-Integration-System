<%-- 
    Document   : iphone
    Created on : May 9, 2022, 11:27:28 AM
    Author     : wellinton
--%>

<%@page import="model.Iphone"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Price Integration System</title>
        <link rel="stylesheet" href="./styles/iphone.css"/>
    </head>
    
    <body>
        <%@include file="/templates/header.jsp"%>
        
        <div class="container-fluid iphone-page">
            <div class="col-12 d-flex pt-3">
                <div class="iphone-img col-4">
                    <img class="img-fluid" src="${iphoneInfo.imageLink}" />
                </div>

                <div class="iphone-info col-7 offset-1 px-5 py-3">
                    <h1>${iphoneInfo.title}</h1>
                    <p class="ratings">Média avaliações:  <strong>${lastVersions.get(0).getRatingAverage()} (${lastVersions.get(0).getRatingAmount()})</strong></p>
                    <p class="best-price">Menor Preço</p>
                    <h3>
                        <fmt:setLocale value = "pt_BR"/>
                        <fmt:formatNumber value = "${lastVersions.get(0).cashPayment}" type = "currency"/>
                    </h3>
                    <p class="mb-3">${lastVersions.get(0).installmentPayment}</p>
                    <a href="${lastVersions.get(0).iphoneLink}">Ver mais</a>
                </div>
            </div>
        
        
            <div class="comparacao-precos d-flex flex-column align-items-center justify-content-center container">
                <h3>Compare os Preços</h3>
                <c:forEach var="version" items="${lastVersions}">
                    <div class="col-7 d-flex flex-row my-2 p-2 iphone-version">
                        <div class="version-img">
                            <img src="${iphoneInfo.imageLink}" width="200" class="img-fluid"/>
                        </div>
                        <div class="iphone-version-info py-3">
                            <p class="price">
                                <fmt:setLocale value = "pt_BR"/>
                                <fmt:formatNumber value = "${version.cashPayment}" type="currency"/>
                            </p>
                            <p>${version.installmentPayment}</p>
                            <p class="mt-3">
                                Em
                                <strong><fmt:formatDate value="${version.date}" pattern="dd/MM/yyyy" /></strong>
                                na <strong>${version.storeName}</strong>
                            </p> 
                            <p class="my-3">Média avaliações: ${version.ratingAverage} (${version.ratingAmount})</p>
                            <a href="${version.iphoneLink}">Ver na loja</a>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="estatisticas container">
                <h3>Gráfico de Variações de preços ao longo do tempo</h3>
                <div>Grafico</div>
            </div>

            <div class="ficha-tecnica container">
                <h3>Ficha técnica</h3>
                <table class="table table-striped w-100">
                    <thead>
                        <tr></tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Modelo</td>
                            <td>${iphoneInfo.modelName}</td>
                        </tr>
                        <tr>
                            <td>Memória Interna</td>
                            <td>${iphoneInfo.secondaryMemory}</td>
                        </tr>
                        <tr>
                            <td>Cor</td>
                            <td>${iphoneInfo.color}</td>
                        </tr>
                        <tr>
                            <td>Código</td>
                            <td>${iphoneInfo.modelCod}</td>
                        </tr>
                        <tr>
                            <td>Tamanho da Tela</td>
                            <td>${iphoneInfo.displaySize}</td>
                        </tr>
                        <tr>
                            <td>Câmera Frontal</td>
                            <td>${iphoneInfo.frontCam}</td>
                        </tr>
                        <tr>
                            <td>Câmera Traseira</td>
                            <td>${iphoneInfo.backCam}</td>
                        </tr>
                        <tr>
                            <td>Memória RAM</td>
                            <td>${iphoneInfo.ramMemory}</td>
                        </tr>
                        <tr>
                            <td>Voltagem</td>
                            <c:choose>
                                <c:when test="${iphone.voltage == null}">
                                  <td>Indisponível</td>
                                </c:when>
                                <c:otherwise>
                                  <td>${iphone.voltage}</td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div class="avaliacoes container">
                <h3>Avaliações</h3>
                <c:choose>
                    <c:when test="${fn:length(ratings) == 0}">
                        <h6>Nenhuma avaliação disponível :(</h6>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="rating" items="${ratings}">
                            <div class="d-flex flex-column mb-3">
                                <h5>${rating.title}</h5>
                                <p>${rating.description}</p>
                                <p>${rating.raterName} em <strong>${rating.date}</strong> na <strong>${rating.storeName}</strong></p>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
            
        </div>
        <%@include file="/templates/footer.jsp"%>
    </body>
</html>
