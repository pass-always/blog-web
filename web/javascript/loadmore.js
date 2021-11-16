/*
---------
LOAD MORE
---------
*/
$('.posts .holder').slice(0,2).show();

$('#btnMore').on('click', function() {
    $('.posts .holder:hidden').slice(0,1).slideDown();
    if($('.posts .holder:hidden').length === 0) {
        $('#btnMore').fadeOut();
    }
});