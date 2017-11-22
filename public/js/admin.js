
$('.list-navigator-next').click(function () {
    callAjaxEvent('NEXT');
});

$('.list-navigator-previous').click(function () {
    callAjaxEvent('PREV');
});

function callAjaxEvent(action) {
    var controllerAction = jsRoutes.controllers.ApplicationController.searchAdminListByPage();
    $.ajax({
        url: controllerAction.url,
        method: controllerAction.type,
        data: {
            csrfToken:  $('input[name=csrfToken]').val(),
            filter: JSON.stringify(updateFilter(action, $('#filter').val()))
        },
        success: function (data) {
            $('#list-detail').html(data);

            $('.list-navigator-next').click(function () {
                callAjaxEvent('NEXT');
            });

            $('.list-navigator-previous').click(function () {
                callAjaxEvent('PREV');
            });
        }
    });
}

/*
function sendNavigationPageAjaxRequest(action, controllerAction) {
    $.ajax({
        url: controllerAction.url,
        method: controllerAction.type,
        data: {
            csrfToken:  $('input[name=csrfToken]').val(),
            filter: JSON.stringify(updateFilter(action, $('#filter').val()))
        },
        success: function (data) {
            $('#list-detail').html(data);
        }
    });
}
*/

function updateFilter(action, filterCondition) {
    var obj = jQuery.parseJSON(filterCondition);
    if(action == 'NEXT'){
       obj.firstRow += obj.maxRow;
    } else if(action == 'PREV'){
        obj.firstRow -= obj.maxRow;
    }
    return obj;
}



