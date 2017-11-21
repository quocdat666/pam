/*$('#list-navigator-next').click(function(e) {
    $.ajax({
        type : 'POST',
        //contentType:'application/json',
        dataType: 'text',
        url : jsRoutes.controllers.ApplicationController.searchAdminListByPage(),
        //headers: {'X-CSRF-Token': $('meta[name="csrfToken"]').attr('content')},
        data : {
            //CSRF:  $('input[name=csrfToken]').val(),
            firstRow: $("#firstRow").val()

        },
        success: function(data) {
            $('#list-detail').innerHTML = data;
        }
    });
    return false;
});*/

$('#list-navigator-next').click(function(e) {
    var r = jsRoutes.controllers.ApplicationController;
    $.ajax({
        contentType:'application/json',
        dataType: 'text',
        url: r.url,
        type: r.type,
        data: {
            //CSRF:  $('input[name=csrfToken]').val(),
            firstRow: $("#firstRow").val()

        },
        success: function (data) {
            alert('success' + data);
        },
        error: function () {
            alert('error');
        }
    });
    alert('hhhhhh');
});



