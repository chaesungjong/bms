$(document).ready(function () {
    $('.side_menu > li > a').click(function (e) {
        e.preventDefault();
        $(this).toggleClass('open');
        $('.side_menu > li > a').not(this).removeClass('open');
        var subMenu = $(this).siblings('.side_sub_menu');
        $('.side_sub_menu').not(subMenu).slideUp();
        subMenu.slideToggle();
    });

    // a 태그 클릭 이벤트를 바인딩합니다.
    $('a[data-ajax]').click(function (event) {
        event.preventDefault();
        var url = $(this).attr('href');
        loadContent(url);
    });
});
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
