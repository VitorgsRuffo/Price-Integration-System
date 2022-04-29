/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

function deleteUser(e) {
    e.preventDefault();
    $('.link-confirmation-delete-store').attr('href', $(this).data('href'));
    $('.modal-delete-store').modal();
}

function readVersionHistory(e) {
    e.preventDefault();
    $.get($(this).data('href'), function (data) {
        var vhistory = JSON.parse(data);
        var $modal = $('.modal-version-history');

        /*$modal.find('.p_date').html('<strong>ID: </strong>' + vhistory.date);
        $modal.find(".p_login").html('<strong>Login: </strong>' + vhistory.login);
        $modal.find('.p_nome').html('<strong>Nome: </strong>' + vhistory.nome);*/
        console.log("Aqui");
        
        $modal.modal();
    });
}

function loadCrawling(e) {
    e.preventDefault();
    var $modal = $('.modal-execute-crawling');
    console.log("oi");
    
    $modal.modal();

}


$(document).on('focusout', '.password-input,.password-confirm', function(e) {
    var $form = $(this).closest("form");
    var $password = $form.find(".password-input");
    var $passwordConfirm = $form.find(".password-confirm");

    if ($password.val().trim() == '') {
        return false;
    }

    if ($password.val() !== $passwordConfirm.val()) {
        $passwordConfirm.closest('.form-group').addClass('has-error');
        $password.closest('.form-group').addClass('has-error');
        $passwordConfirm.next('p.help-block').html('<strong>Erro</strong>: as senhas não coincidem!');
        $form.find("button,input[type='submit']").prop('disabled', true);
    } else {
        $passwordConfirm.closest('.form-group').removeClass('has-error').addClass('has-success');
        $password.closest('.form-group').removeClass('has-error').addClass('has-success');
        $passwordConfirm.next('p.help-block').html('');
        $form.find("button,input[type='submit']").prop('disabled', false);
    }
});

$(document).on('focusout', '#usuario-login', function (e) {
    var $input = $(this);
    if ($("#usuario-login").val() == $(this).data('value')) {
        var $formGroup = $input.parents(".form-group").first();
        if ($formGroup.hasClass("has-error")) {
            $formGroup.removeClass("has-error");
        }
        $input.next("p").html("");
    }
    else {
        $.post($.url("//user/checkLogin"), { login: $("#usuario-login").val() }, function(data) {
            var $formGroup = $input.parents(".form-group").first();
            if (data.status == "USADO") {
                if (!$formGroup.hasClass("has-error")) {
                    $formGroup.addClass("has-error");
                }
                $input.next("p").html("O login escolhido existe. Por favor, tente outro.");
            } else {
                if ($formGroup.hasClass("has-error")) {
                    $formGroup.removeClass("has-error");
                }
                $input.next("p").html("");
            }
        });
    }
});

$(document).ready(function () {
    $(document).on('click', '.link-delete-store', deleteUser);
    $(document).on('click', '.link_version-history', readVersionHistory);
    $(document).on('click', '.link-load-crawling', loadCrawling);
    $("*[data-toggle='tooltip']").tooltip({
        'container': 'body'
    });
});






/*
function deleteStore(e) {
    e.preventDefault();
    console.log("ok");
    $('.link-confirmation-delete-store').attr('href', $(this).data('href'));
    $('.modal-delete-store').modal();
}

function uploadCrawling(e) {
    e.preventDefault();
    $('.modal-execute-crawling').modal();
}

function readScript(e) {
    e.preventDefault();
    $.get($(this).data('href'), function (data) {
        var script = JSON.parse(data);
        var $modal = $('.modal-version-history');

        $modal.find('.p_id').html('<strong>ID: </strong>' + script.id);
        $modal.find(".p_text").html('<strong>Texto: </strong>' + script.text);
        $modal.find('.p_date').html('<strong>Data: </strong>' + script.date);
        $modal.find('.p_time').html('<strong>Hora: </strong>' + script.time);
        
        $modal.modal();
    });
}

$(document).ready(function () {
   $(document).on('click', '.link-delete-store', deleteStore);
      $(document).on('click', '.button-execute-crawling', uploadCrawling);
});
*/
