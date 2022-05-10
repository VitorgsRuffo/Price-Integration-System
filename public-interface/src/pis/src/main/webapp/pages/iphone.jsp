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
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js@2.8.0"></script>
        
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
                <h3>Gráfico de Variações de preços ao longo do tempo ${allVersions} </h3>
                <canvas id="plot-container"></canvas>                 
                <script type="text/javascript">
                    
                    let data = [];
                    <c:forEach var="version" items="${allVersions}" varStatus="loop">
                    
                        data.push({'x': "${version.date}", 'y': "${version.cashPayment}"});
                    </c:forEach>

                    const ctx = document.querySelector("#plot-container").getContext('2d');
                    const config = {
                      type: 'line',
                      data: {
                        labels: [],
                        datasets: [{
                          data: data,
                          label: "Preço ao longo do tempo (BRL)",
                          borderColor: "#3e95cd",
                          fill: false
                        }]
                      },
                      options: {
                        scales: {
                          xAxes: [{
                            type: 'time',
                            time: {
                                unit:'day'
                            },
                            distribution: 'linear',
                          }],
                          title: {
                            display: false,
                          }
                        }
                      }
                    };
                    new Chart(ctx, config);
                    

                </script>
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
