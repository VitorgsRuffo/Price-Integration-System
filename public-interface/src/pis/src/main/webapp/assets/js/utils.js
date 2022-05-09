/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


$(document).ready(function () {
    if($('.nenhum-iphone').hasClass('active')){
        $('.footer').addClass('fixed-bottom');
        $('.cabecalho').removeClass('offset-2');
        $('.cabecalho').removeClass('col-10');
        $('.cabecalho').addClass('col-12')
    } 
});
