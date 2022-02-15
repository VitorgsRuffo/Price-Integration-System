
function deleteStore(e) {
    e.preventDefault();
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