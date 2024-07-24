$('.dw_info').click(function () {
    var userBtn = $(this).attr('id');
    $('.dw_info_wrap').removeClass('on');
    $('.dw_info_wrap').removeClass('out');
    $('.dw_info_wrap#' + userBtn).addClass('on');
    $('body, html').addClass('modal-active');
});

$('.dw_info_close').click(function () {
    $('.dw_info_wrap').addClass('out');
    $('body, html').removeClass('modal-active');
});

$(document).ready(function () {
    $('.design_board_type>span').click(function () {
        $('.design_board_type>span').removeClass('active');
        $(this).addClass('active');
    });
    $('.main_board_state>span').click(function () {
        $('.main_board_state>span').removeClass('active');
        $(this).addClass('active');
    });
    $('.main_board_wrstate').click(function () {
        $(this).children('.state_select').slideToggle(100);
    });
});
