
$('.navigator-panel').click(function () {
    callAjaxEvent(this.id);
});

function callAjaxEvent(actionType) {
    jsRoutes.controllers.ApplicationController.doAjaxRequestAdminList.ajax({
        data: {
            csrfToken:  $('input[name=csrfToken]').val(),
            filter: JSON.stringify(updateFilterCondition(actionType, $('#filter').val()))
        },
        success: function (data) {
            $('#list-detail').html(data);

            $('.navigator-panel').click(function () {
                callAjaxEvent(this.id);
            });
        }
    });
}

function updateFilterCondition(actionType, filterCondition) {
    var obj = jQuery.parseJSON(filterCondition);
    if(actionType == 'next'){
       obj.firstRow += obj.maxRow;
    } else if(action == 'previous'){
        obj.firstRow -= obj.maxRow;
    }
    return obj;
}



