<%-- 
    Document   : iphone
    Created on : May 9, 2022, 11:27:28 AM
    Author     : wellinton
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Price Integration System</title>
        <link rel="stylesheet" href="./styles/iphone.css"/>
    </head>
    <body>
        <%@include file="/templates/header.jsp"%>
        <div class="container-fluid">
            <div class="col-12">
                <div class="iphone-img col-4">

                </div>

                <div class="iphone-info">
                    <h3>Title</h3>
                    <p>Media nota (avaliações)</p>
                    <p>Menor preço</p>
                    <h3>R$ xxx,xx</h3>
                    <p>Numero de parcelas</p>
                    <a>Ver mais</a>
                </div>
            </div>
        </div>
        
        <div class="comparacao-precos">
            <h3>Compare os preços</h3>
            <!-- foreach sobre os iphones -->
            <div class="iphone-version">
                <p>Title</p>
                <p>Loja</p>
                <p>data</p>
                <p>preço</p>
                <p>parcelas</p>
            </div>
        </div>
        
        
        <div class="estatisticas">
            <h3>Gráfico de Variações de preços ao longo do tempo</h3>
            <div>Grafico</div>
        </div>
        
        <div class="ficha-tecnica">
            <div>
                informações sobre o iphone aqui
            </div>
        </div>
        
        <div class="avaliacoes">
            <!-- foreach nas avaliações -->
            
        </div>
        
        <%@include file="/templates/footer.jsp"%>
    </body>
</html>
