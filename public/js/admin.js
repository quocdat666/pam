
$('.navigator-panel').click(function () {
    callAjaxEvent(this.id);
});

function callAjaxEvent(navigateType) {
    var controllerAction = jsRoutes.controllers.ApplicationController.doAjaxRequestAdminList();
    $.ajax({
        url: controllerAction.url,
        method: controllerAction.type,
        data: {
            //csrfToken:  $('input[name=csrfToken]').val(),
            filter: $('#filter').val(),
            page: updatePageIndex(navigateType, $('#page').val())
        },
        success: function (data) {
            $('#list-detail').html(data);

            $('.navigator-panel').click(function () {
                callAjaxEvent(this.id);
            });
        }
    });
}

function updatePageIndex(navigateType, page) {
    return (navigateType == 'next') ? ++page : --page;
}