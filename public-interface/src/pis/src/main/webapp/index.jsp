<%-- 
    Document   : index
    Created on : Feb 9, 2022, 8:18:13 PM
    Author     : wellinton
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Price Integration System</title>
        <link rel="stylesheet" href="./styles/index.css"/>
    </head>
    
    <body>
        <%@include file="/templates/header.jsp"%>

        <div id="home" class="container p-4 d-flex flex-column align-items-center justify-content-center">                             
            <a class="w-100" href="">
                <div class="d-flex">
                    <div class="col">
                        <img class="img-fluid" src="${cheapestIphoneImgLink}"/>
                    </div>
                    <div class="col p-3 d-flex flex-column justify-content-between">
                        <h2>Iphone mais barato registrado</h2>
                        <h3>${cheapestIphone.modelName}</h3>
                        <span>
                            <h5>Loja: ${cheapestIphoneStoreName}</h5>
                            <p>Data: <fmt:formatDate value="${cheapestIphone.date}" pattern="dd/MM/yyyy" /></p>
                        </span>
                        <div class="">
                            <p>Cor: ${cheapestIphone.color}</p>
                            <p>Memória: ${cheapestIphone.secondaryMemory}</p>
                        </div>
                        <div class="">
                            <h3>
                                <fmt:setLocale value = "pt_BR"/>
                                <fmt:formatNumber value = "${cheapestIphone.cashPayment}" type = "currency"/>
                            </h3>
                            <p>${cheapestIphone.installmentPayment}</p>
                        </div>
                    </div>
                </div>
            </a>
            
            <a class="w-100" href="">
                <div class="d-flex">
                    <div class="col-6">
                        <img class="img-fluid" src="${mostExpensiveIphoneImgLink}"/>
                    </div>
                    <div class="col-6 p-3 d-flex flex-column justify-content-between">
                        <h2>Iphone mais caro registrado</h2>
                        <h3>${mostExpensiveIphone.modelName}</h3>
                        <span>
                            <h5>Loja: ${mostExpensiveIphoneStoreName}</h5>
                            <p>Data: <fmt:formatDate value="${mostExpensiveIphone.date}" pattern="dd/MM/yyyy" /></p>
                        </span>
                        <div class="">
                            <p>Cor: ${mostExpensiveIphone.color}</p>
                            <p>Memória: ${mostExpensiveIphone.secondaryMemory}</p>
                        </div>
                        <div class="">
                            <h3>
                                <fmt:setLocale value = "pt_BR"/>
                                <fmt:formatNumber value = "${mostExpensiveIphone.cashPayment}" type = "currency"/>
                            </h3>
                            <p>${mostExpensiveIphone.installmentPayment}</p>
                        </div>
                    </div>
                </div>
            </a> 
        </div>
        <%@include file="/templates/footer.jsp"%>
    </body>
</html>