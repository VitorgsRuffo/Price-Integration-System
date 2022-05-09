/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function insertParameterOnHiddenInput() {
    let queryString = window.location.search;    
    const urlParams = new URLSearchParams(queryString);
    
    let q = urlParams.get('q');
    if(q !== null){
        let input = document.getElementById('q-hidden');
        input.value = q;
    }
}

function insertResultKeyOnSearchPage(){
    let queryString = window.location.search;    
    const urlParams = new URLSearchParams(queryString);
    
    let q = urlParams.get('q');
    $('#result-key').text('Termo buscado: ' + q);
}


$(document).ready(function () {
    insertResultKeyOnSearchPage();
    
    $(document).on('click', '.filter-button', {'param': ''}, insertParameterOnHiddenInput());
    
    // Não deixa selecionar mais de uma cor ao mesmo tempo no filter
    $(function() {
        $("#form-filters :input").change(function() {
            $("input[name='" + this.name + "']:checked").not(this).prop("checked", false);  
        });
    });
});