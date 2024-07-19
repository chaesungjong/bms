/*
 * ems/employees.html 연동 스크립트 영역
 */
$('.user_info_btn').click(function () {
    var userBtn = $(this).attr('id');
    $('.member_info_wrap').removeClass('on');
    $('.member_info_wrap').removeClass('out');
    $('.member_info_wrap#' + userBtn).addClass('on');
    $('body, html').addClass('modal-active');
});

$('.member_info_close').click(function () {
    $('.member_info_wrap').addClass('out');
    $('body, html').removeClass('modal-active');
});
