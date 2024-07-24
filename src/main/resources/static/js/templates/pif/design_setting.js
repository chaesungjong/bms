$(document).ready(function () {
    $('.design_board_type>span').click(function () {
        $('.design_board_type>span').removeClass('active');
        $(this).addClass('active');
    });
    $('.main_board_wrstate').click(function () {
        $(this).children('.state_select').slideToggle(100);
    });
    $('.main_board_wrfilm').click(function () {
        $(this).children('.film_select').slideToggle(100);
    });
});
