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
        
        
            <div class="comparacao-precos">
                <h3>Compare os preços</h3>
                <!-- foreach sobre os iphones -->
                <div class="">
                    Foto aqui
                </div>
                <div class="iphone-version">
                    <p>preço</p>
                    <p>parcelas</p>
                    Em<p>data</p> na <p>Loja</p>
                    <p>Rating average(amount)</p>
                    <a>Ver na loja</a>
                </div>
            </div>

            <div class="estatisticas">
                <h3>Gráfico de Variações de preços ao longo do tempo</h3>
                <div>Grafico</div>
            </div>

            <div class="ficha-tecnica">
                <h3>Ficha técnica</h3>
                <div>
                    informações sobre o iphone aqui
                </div>
            </div>

            <div class="avaliacoes">
                <h3>Avaliações</h3>
                <!-- foreach nas avaliações -->
                <p>title da avaliacao</p>
                <p>descricao</p>
                <p>rater name</p>
                <p>em (data) na (nome_loja)</p>
            </div>
            
        </div>
        <%@include file="/templates/footer.jsp"%>
    </body>
</html>
