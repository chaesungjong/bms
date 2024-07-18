/*
 * csf/business_management.html 연동 스크립트 영역
 */
$('.bm_info').click(function () {
    var userBtn = $(this).attr('id');
    $('.bm_info_wrap').removeClass('on');
    $('.bm_info_wrap').removeClass('out');
    $('.bm_info_wrap#' + userBtn).addClass('on');
    $('body, html').addClass('modal-active');
});

$('.bm_info_close').click(function () {
    $('.bm_info_wrap').addClass('out');
    $('body, html').removeClass('modal-active');
});
