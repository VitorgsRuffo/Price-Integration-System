/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function checkIfExistNextPage() {
    // Obtendo quantos iphone foram encontrados na busca
    var iphoneCounter = $('#results-counter')[0].innerText;
    
    // Obtendo em qual página está atualmente
    let queryString = window.location.search;    
    const urlParams = new URLSearchParams(queryString);
    let currentPage = urlParams.get('page');
    
    if(currentPage === null)
        currentPage = 1;
    
    if(iphoneCounter < 10) {
        $('.button-next').addClass('disabled');
    }
    if(currentPage == 1)
        $('.button-prev').addClass('disabled');
}


function callNextPage() {
    let completeURL = window.location.href;
    
    let queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    let currentPage = urlParams.get('page');
    
    var url = "";
    
    if(currentPage === null){
        url = completeURL + "&page=2";
    }
    else {
        currentPage = parseInt(currentPage);
        let nextPage = parseInt(currentPage + 1);
        completeURL = completeURL.replace("page="+currentPage, "page="+nextPage);
        url = completeURL;
    }
     
    if(!$('.button-next').hasClass('disabled')) {
        $('.button-next a').attr('href', url);
    }
}

function callPrevPage() {
    let completeURL = window.location.href;
    
    let queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
    let currentPage = urlParams.get('page');
    
    var url = "";
    
    if(currentPage === null){
        url = completeURL + "&page=2";
    }
    else {
        currentPage = parseInt(currentPage);
        var prevPage = parseInt(currentPage - 1);
        console.log(prevPage);
        completeURL = completeURL.replace("page="+currentPage, "page="+prevPage);
        url = completeURL;
    }
     
    if(!$('.button-prev').hasClass('disabled')) {
        $('.button-prev a').attr('href', url);
    }
}


$(document).ready(function () {
    checkIfExistNextPage();
    
    $(".button-next").click(function() {
        callNextPage();
    });
    
    $(".button-prev").click(function() {
        callPrevPage();
    });
});