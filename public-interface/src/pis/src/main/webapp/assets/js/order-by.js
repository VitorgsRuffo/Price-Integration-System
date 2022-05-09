/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function prepareURLAndDoRequest(order) {
    let completeURL = window.location.href;
    let url = "";
    
    // Checando se já tem esse parametro na url
    let queryString = window.location.search;    
    const urlParams = new URLSearchParams(queryString);
    let currentOrdering = urlParams.get('orderBy');
    
    console.log(currentOrdering);
    
    if(currentOrdering === null) {   
        url = completeURL + "&orderBy="+order;
    }
    else {    
        completeURL = completeURL.replace("orderBy="+currentOrdering, "orderBy="+order);
        url = completeURL
    }
    
    $('.order-'+order).attr('href', url);
    console.log(url);
}

$(document).ready(function () {
    $(".order-desc").click(function() {
        prepareURLAndDoRequest('desc');
    });
    $(".order-asc").click(function() {
        prepareURLAndDoRequest('asc');
    });    
});